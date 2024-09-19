@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)

package com.sample.rickandmorty.presentation.character_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.sample.rickandmorty.R

@Composable
fun CharacterListItemView(character: UiCharacter, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        modifier = Modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.character_list_item_margin_horizontal),
                vertical = dimensionResource(id = R.dimen.character_list_item_margin_vertical)
            )
    ) {
        Column(Modifier.fillMaxWidth()) {
            GlideImage(
                model = character.image,
                contentDescription = stringResource(
                    R.string.character_image_description,
                    character.name
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.character_list_item_image_height)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = character.name,
                textAlign = TextAlign.Center,
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(id = R.dimen.spacing_medium))
            )
        }
    }
}

@Preview
@Composable
private fun CharacterListItemPreview(
    @PreviewParameter(UiCharacterListProvider::class) characters: List<UiCharacter>
) {
    Column {
        characters.forEach { character ->
            CharacterListItemView(character = character, onClick = {})
        }
    }
}

private class UiCharacterListProvider : PreviewParameterProvider<List<UiCharacter>> {
    override val values: Sequence<List<UiCharacter>> = sequenceOf(
        emptyList(),
        listOf(
            UiCharacter(
                id = 1,
                name = "Rick Sanchez",
                image = "https://example.com/rick_sanchez.png"

            ),
            UiCharacter(
                id = 2,
                name = "Citadel of Ricks",
                image = "https://example.com/citadel_of_ricks.png"
            ),
            UiCharacter(
                id = 3,
                name = "Summer Smith",
                image = "https://example.com/summer_smith.png"
            ),
            UiCharacter(
                id = 4,
                name = "Beth Smith",
                image = "https://example.com/beth_smith.png"
            )
        )
    )
}
