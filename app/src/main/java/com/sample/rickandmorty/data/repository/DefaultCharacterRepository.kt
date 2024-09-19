package com.sample.rickandmorty.data.repository

import com.sample.rickandmorty.data.source.local.dao.CharacterDao
import com.sample.rickandmorty.data.source.local.entity.toCharacter
import com.sample.rickandmorty.data.source.local.entity.toCharacters
import com.sample.rickandmorty.data.source.network.model.toLocalCharacters
import com.sample.rickandmorty.data.source.network.service.CharacterService
import com.sample.rickandmorty.domain.model.Character
import com.sample.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultCharacterRepository @Inject constructor(
    private val characterService: CharacterService,
    private val characterDao: CharacterDao
) : CharacterRepository {

    /**
     * Retrieves a list of characters from the local database.
     *
     * This method transforms the list of character entities from the database
     * into domain models and returns them as a Flow of List<Character>.
     *
     * @return A [Flow] emitting a list of [Character] objects.
     */
    override fun getCharacters(): Flow<List<Character>> {
        return characterDao.getCharacters()
            .map { entities -> entities.toCharacters() }
    }

    /**
     * Retrieves a specific character by their ID from the local database.
     *
     * This method fetches the character entity from the database using its ID,
     * converts it to a domain model, and emits the result. If the character is not found,
     * it emits null.
     *
     * @param id The ID of the character to retrieve.
     * @return A [Flow] emitting the [Character] object or `null` if not found.
     */
    override fun getCharacterById(id: Int): Flow<Character?> {
        return characterDao.getCharacterById(id)
            .map { entity -> entity?.toCharacter() }
    }

    /**
     * Fetches a page of characters from the remote API and caches them in the local database.
     *
     * This method makes a network request to fetch a list of characters for the given page number.
     * It then inserts the retrieved characters into the local database.
     *
     * @param page The page number to fetch from the remote API.
     * @return `true` if there are more pages to load, `false` otherwise.
     */
    override suspend fun fetchAndCacheCharacters(page: Int): Boolean {
        val characterPage = characterService.getCharacters(page)
        characterDao.insertCharacters(characterPage.results.toLocalCharacters())
        return characterPage.info.next != null
    }
}
