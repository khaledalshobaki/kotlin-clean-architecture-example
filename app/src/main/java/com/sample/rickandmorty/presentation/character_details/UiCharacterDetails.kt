package com.sample.rickandmorty.presentation.character_details

import com.sample.rickandmorty.domain.model.CharacterStatus
import com.sample.rickandmorty.domain.model.Gender
import com.sample.rickandmorty.domain.model.Species

class UiCharacterDetails (
    val id: Int,
    val name: String,
    val image: String,
    val status: CharacterStatus,
    val species: Species,
    val gender: Gender,
    val createdAt: String
)