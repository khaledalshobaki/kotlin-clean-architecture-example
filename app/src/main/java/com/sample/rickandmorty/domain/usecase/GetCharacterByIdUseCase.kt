package com.sample.rickandmorty.domain.usecase

import com.sample.rickandmorty.domain.model.Character
import com.sample.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for retrieving a specific character by its ID from the local cache.
 *
 * This use case abstracts the process of fetching a character from the local database
 * as the single source of truth. It provides a clean API for calling layers like ViewModels
 * or other use cases, ensuring that all data is retrieved from the cache.
 *
 * @property characterRepository The repository that interacts with the local cache.
 */
class GetCharacterByIdUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    /**
     * Retrieves a specific character by its ID from the local cache.
     *
     * This function returns a [Flow] that emits the [Character] object when found,
     * or `null` if no character with the given ID exists in the local database.
     * As the local database is used as the single source of truth, this method
     * ensures consistency between the cache and UI data.
     *
     * @param id The ID of the character to retrieve.
     * @return A [Flow] emitting the [Character] object or `null` if not found in the cache.
     */
    operator fun invoke(id: Int): Flow<Character?> {
        return characterRepository.getCharacterById(id)
    }
}
