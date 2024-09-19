package com.sample.rickandmorty.presentation

import kotlinx.serialization.Serializable

sealed class Screen {

    // Represents the CharacterListScreen, with no arguments
    @Serializable
    data object CharacterList : Screen()

    // Represents the CharacterDetailsScreen, with a required characterId argument
    @Serializable
    data class CharacterDetails(val characterId: Int) : Screen()
}