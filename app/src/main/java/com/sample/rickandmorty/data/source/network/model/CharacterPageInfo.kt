package com.sample.rickandmorty.data.source.network.model

import com.google.gson.annotations.SerializedName

class CharacterPageInfo(
    @SerializedName("count") val count: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("prev") val prev: String?
)