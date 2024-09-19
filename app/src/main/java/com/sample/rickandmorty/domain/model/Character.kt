package com.sample.rickandmorty.domain.model

data class Character(
    val id: Int,
    val name: String,
    val image: String,
    val status: CharacterStatus,
    val species: Species,
    val gender: Gender,
    val createdAt: String
)

enum class CharacterStatus {
    Alive,
    Dead,
    Unknown
}

enum class Species {
    Human,
    Alien,
    Unknown
}

enum class Gender {
    Male,
    Female,
    Unknown
}