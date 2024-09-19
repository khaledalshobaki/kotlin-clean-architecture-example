@file:OptIn(ExperimentalMaterial3Api::class)

package com.sample.rickandmorty.presentation.character_list

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sample.rickandmorty.R
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun CharacterListScreen(
    onCharacterClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CharacterListViewModel = hiltViewModel()
) {
    val characters by viewModel.characters.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val userMessage by viewModel.userMessage.collectAsStateWithLifecycle()

    // Retrieve the context for showing Toast
    val context = LocalContext.current

    // Show a Toast message when userMessage changes
    LaunchedEffect(userMessage) {
        userMessage?.getContentIfNotHandled()?.let { event ->
            // Use context to retrieve string resource
            val message = context.getString(event) // event should be the string resource ID
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = { CharacterListTopAppBar() }
    ) { contentPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            if (isLoading && characters.isEmpty()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            if (characters.isNotEmpty()) {
                CharacterListContent(
                    characters = characters,
                    onCharacterClick = onCharacterClick,
                    onBottomReached = { viewModel.loadCharactersPage() }
                )
            }
        }
    }
}

@Composable
private fun CharacterListTopAppBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    )
}

@Composable
fun CharacterListContent(
    characters: List<UiCharacter>,
    onCharacterClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onBottomReached: () -> Unit // Callback for when the bottom is reached
) {
    // Remember the LazyGridState to maintain the scroll position
    val gridState = rememberSaveable(saver = LazyGridState.Saver) {
        LazyGridState()
    }

    // Check if we are close to the bottom of the list and trigger loading more items
    LaunchedEffect(gridState) {
        snapshotFlow { gridState.layoutInfo.visibleItemsInfo }
            .map { visibleItemsInfo ->
                val totalItems = gridState.layoutInfo.totalItemsCount
                val lastVisibleItemIndex = visibleItemsInfo.lastOrNull()?.index ?: 0
                totalItems to lastVisibleItemIndex
            }
            .distinctUntilChanged()
            .collect { (totalItems, lastVisibleItemIndex) ->
                if (totalItems > 0 && lastVisibleItemIndex >= totalItems - 10) {
                    onBottomReached()
                }
            }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        state = gridState,
        contentPadding = PaddingValues(
            horizontal = dimensionResource(id = R.dimen.spacing_medium),
            vertical = dimensionResource(id = R.dimen.spacing_large)
        )
    ) {
        items(items = characters, key = { it.id }) { character ->
            CharacterListItemView(
                character = character,
                onClick = { onCharacterClick(character.id) }
            )
        }
    }
}
