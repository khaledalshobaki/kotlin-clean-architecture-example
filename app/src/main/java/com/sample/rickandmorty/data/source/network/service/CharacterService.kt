package com.sample.rickandmorty.data.source.network.service

import com.sample.rickandmorty.data.source.network.model.CharacterPage
import com.sample.rickandmorty.data.source.network.model.NetworkCharacter
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterService {

    /**
     * Retrieves a paginated list of characters from the API.
     *
     * @param paging The page number to retrieve.
     * @return A CharacterPage object containing the characters and pagination info.
     */
    @GET("character")
    suspend fun getCharacters(@Query("page") paging: Int): CharacterPage

    /**
     * Retrieves a specific character by its ID from the API.
     *
     * @param characterId The ID of the character to retrieve.
     * @return A NetworkCharacter object representing the character with the specified ID.
     */
    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") characterId: Int): NetworkCharacter

}
