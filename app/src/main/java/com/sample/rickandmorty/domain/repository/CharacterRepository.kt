package com.sample.rickandmorty.domain.repository

import com.sample.rickandmorty.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharacters(): Flow<List<Character>>
    fun getCharacterById(id: Int): Flow<Character?>
    suspend fun fetchAndCacheCharacters(page: Int): Boolean

}