package com.sample.rickandmorty.data.source.network.model

import com.google.gson.annotations.SerializedName

data class CharacterPage(
    @SerializedName("info") val info: CharacterPageInfo,
    @SerializedName("results") val results: List<NetworkCharacter>
)

