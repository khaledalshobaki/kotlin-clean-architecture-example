@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class,
    ExperimentalMaterial3Api::class
)

package com.sample.rickandmorty.presentation.character_details

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.sample.rickandmorty.R
import com.sample.rickandmorty.domain.model.CharacterStatus
import com.sample.rickandmorty.domain.model.Gender
import com.sample.rickandmorty.domain.model.Species

@Composable
fun CharacterDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: CharacterDetailsViewModel = hiltViewModel(),
    onBackPress: () -> Unit
) {
    val character by viewModel.character.collectAsStateWithLifecycle()
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
        topBar = {
            CharacterDetailsTopAppBar(
                modifier = modifier,
                onBackPress = onBackPress
            )
        }
    ) { contentPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = contentPadding.calculateTopPadding())
        ) {
            character?.let { character ->
                CharacterDetailsContent(character = character)
            }
        }
    }
}

@Composable
private fun CharacterDetailsTopAppBar(
    modifier: Modifier = Modifier,
    onBackPress: () -> Unit
) {
    TopAppBar(
        title = {  },
        navigationIcon = {
            IconButton(onClick = onBackPress) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back_button_description)
                )
            }
        },
        modifier = modifier
    )
}

@Composable
fun CharacterDetailsContent(
    character: UiCharacterDetails,
    modifier: Modifier = Modifier
) {
    val statusText = stringResource(
        when (character.status) {
            CharacterStatus.Alive -> R.string.status_alive
            CharacterStatus.Dead -> R.string.status_dead
            else -> R.string.status_unknown
        }
    )

    val statusColor = when (character.status) {
        CharacterStatus.Alive -> Color.Green
        CharacterStatus.Dead -> Color.Red
        else -> Color.Gray
    }

    val speciesText = stringResource(
        when (character.species) {
            Species.Human -> R.string.species_human
            Species.Alien -> R.string.species_alien
            else -> R.string.species_unknown
        }
    )

    val genderText = stringResource(
        when (character.gender) {
            Gender.Male -> R.string.gender_male
            Gender.Female -> R.string.gender_female
            else -> R.string.gender_unknown
        }
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(dimensionResource(id = R.dimen.character_list_item_margin_horizontal)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            model = character.image,
            contentDescription = stringResource(
                R.string.character_image_description,
                character.name
            ),
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.character_details_image_size))
                .clip(CircleShape),
            contentScale = ContentScale.None
        )

        Spacer(
            modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_large))
        )

        Text(
            text = character.name,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        )

        Spacer(
            modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small))
        )

        Text(
            text = statusText,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold,
                color = statusColor
            )
        )

        Spacer(
            modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small))
        )


        Text(
            text = "$speciesText • $genderText",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(
            modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small))
        )

        Divider(
            color = MaterialTheme.colorScheme.primary,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.spacing_large))
        )

        Spacer(
            modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small))
        )

        Text(
            text = character.createdAt,
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
        )
    }
}
