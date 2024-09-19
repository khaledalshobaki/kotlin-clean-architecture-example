package com.sample.rickandmorty.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sample.rickandmorty.domain.model.Character
import com.sample.rickandmorty.domain.model.CharacterStatus
import com.sample.rickandmorty.domain.model.Gender
import com.sample.rickandmorty.domain.model.Species

@Entity
class CharacterEntity(

    @PrimaryKey
    val id: Int,

    val image: String,

    val name: String,

    val status: String,

    val gender: String,

    val species: String,

    val createdAt: String
)

/**
 * Extension function to map a CharacterEntity to a Character.
 * This is used to transform the local database entity back into the domain model.
 */
fun CharacterEntity.toCharacter() = Character(
    id = id,       // Maps the entity ID to the domain model ID
    name = name,   // Maps the entity name to the domain model name
    image = image, // Maps the entity image URL to the domain model image
    status = when (status) {
        // Maps the entity status string to the corresponding CharacterStatus enum
        "Alive" -> CharacterStatus.Alive   // Maps "Alive" to CharacterStatus.Alive
        "Dead" -> CharacterStatus.Dead     // Maps "Dead" to CharacterStatus.Dead
        else -> CharacterStatus.Unknown    // Default case for unrecognized or null values
    },
    species = when (species) {
        // Maps the entity species string to the corresponding Species enum
        "Human" -> Species.Human           // Maps "Human" to Species.Human
        "Alien" -> Species.Alien           // Maps "Alien" to Species.Alien
        else -> Species.Unknown            // Default case for unrecognized or null values, mapped to Species.Unknown
    },
    gender = when (gender) {
        // Maps the entity gender string to the corresponding Gender enum
        "Male" -> Gender.Male              // Maps "Male" to Gender.Male
        "Female" -> Gender.Female          // Maps "Female" to Gender.Female
        else -> Gender.Unknown             // Default case for unrecognized or null values, mapped to Gender.Unknown
    },
    createdAt = createdAt // Map the date string using the utility method
)


/**
 * Extension function to map a list of CharacterEntity objects to a list of Character objects.
 * This is useful when retrieving multiple characters from the local database and transforming them into domain models.
 */
fun List<CharacterEntity>.toCharacters() = map(CharacterEntity::toCharacter)
