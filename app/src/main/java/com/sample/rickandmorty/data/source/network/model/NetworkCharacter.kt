package com.sample.rickandmorty.data.source.network.model

import com.google.gson.annotations.SerializedName
import com.sample.rickandmorty.data.source.local.entity.CharacterEntity

data class NetworkCharacter(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("species") val species: String,
    @SerializedName("type") val type: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("location") val location: NetworkLocation,
    @SerializedName("image") val image: String,
    @SerializedName("episode") val episode: List<String>,
    @SerializedName("url") val url: String,
    @SerializedName("created") val created: String
)

/**
 * Extension function to map a NetworkCharacter to a CharacterEntity.
 * This is used to transform the network model into a local database entity.
 */
fun NetworkCharacter.toCharacterEntity() = CharacterEntity(
    id = id,           // Maps the network ID to the local entity ID
    name = name,       // Maps the network name to the local entity name
    image = image,     // Maps the network image URL to the local entity image field
    status = status,   // Maps the network status to the local entity status field
    gender = gender,   // Maps the network gender to the local entity gender field
    species = species,  // Maps the network species to the local entity species field,
    createdAt = created // Maps the network created to the local entity createdAt field,
)


/**
 * Extension function to map a list of NetworkCharacter objects to a list of CharacterEntity objects.
 * This is used when you want to cache a list of characters in the local database.
 */
fun List<NetworkCharacter>.toLocalCharacters() = map(NetworkCharacter::toCharacterEntity)