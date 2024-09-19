package com.sample.rickandmorty.domain.usecase

import com.sample.rickandmorty.domain.model.Character
import com.sample.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for retrieving a list of characters from the local cache.
 *
 * This use case abstracts the process of fetching all characters from the local database,
 * which is treated as the single source of truth. It provides a clean API for calling layers
 * like ViewModels or other use cases, ensuring consistent data retrieval from the cache.
 *
 * @property characterRepository The repository that interacts with the local cache.
 */
class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    /**
     * Retrieves a list of characters from the local cache.
     *
     * This function returns a [Flow] emitting a list of [Character] objects
     * stored in the local database. Since the database is used as the single source of truth,
     * this ensures that all character data is consistent and up-to-date.
     *
     * @return A [Flow] emitting a list of [Character] objects from the cache.
     */
    operator fun invoke(): Flow<List<Character>> {
        return characterRepository.getCharacters()
    }
}
