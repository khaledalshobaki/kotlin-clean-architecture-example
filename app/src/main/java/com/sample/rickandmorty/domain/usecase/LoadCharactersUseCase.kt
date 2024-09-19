package com.sample.rickandmorty.domain.usecase

import com.sample.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

class LoadCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    /**
     * Executes the use case to fetch characters from the remote API and cache them in the local database.
     *
     * This method calls the repository to fetch characters for a specific page and cache them.
     * It returns a Boolean indicating whether there are more pages to load.
     *
     * @param page The page number to fetch from the remote API.
     * @return `true` if there are more pages to load, `false` otherwise.
     */
    suspend operator fun invoke(page: Int): Boolean {
        return characterRepository.fetchAndCacheCharacters(page)
    }
}