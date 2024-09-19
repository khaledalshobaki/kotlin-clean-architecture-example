package com.sample.rickandmorty.data.source.network.model

import com.google.gson.annotations.SerializedName

data class NetworkLocation(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)
