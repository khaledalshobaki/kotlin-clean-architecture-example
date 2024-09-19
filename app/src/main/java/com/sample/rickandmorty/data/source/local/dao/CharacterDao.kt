package com.sample.rickandmorty.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sample.rickandmorty.data.source.local.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    /**
     * Inserts a single [CharacterEntity] into the database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterEntity)

    /**
     * Inserts a list of [CharacterEntity] records into the database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<CharacterEntity>)

    /**
     * Updates an existing [CharacterEntity] in the database.
     */
    @Update
    suspend fun updateCharacter(character: CharacterEntity)

    /**
     * Deletes the specified [CharacterEntity] from the database.
     */
    @Delete
    suspend fun deleteCharacter(character: CharacterEntity)

    /**
     * Deletes all [CharacterEntity] records from the database.
     */
    @Query("DELETE FROM CharacterEntity")
    suspend fun deleteAll()

    /**
     * Deletes the [CharacterEntity] record with the specified ID from the database.
     */
    @Query("DELETE FROM CharacterEntity WHERE id = :characterId")
    suspend fun deleteCharacterById(characterId: Int)

    /**
     * Retrieves all [CharacterEntity] records from the database.
     *
     * Returns a [Flow] that emits a list of [CharacterEntity] objects,
     * representing all characters stored in the local database, ordered by their ID in ascending order.
     */
    @Query("SELECT * FROM CharacterEntity ORDER BY id ASC")
    fun getCharacters(): Flow<List<CharacterEntity>>

    /**
     * Retrieves a single [CharacterEntity] record by its ID from the database.
     *
     * Returns a [Flow] that emits the [CharacterEntity] object,
     * or `null` if no character with the specified ID exists.
     *
     * @param characterId The ID of the character to retrieve.
     * @return A [Flow] emitting the [CharacterEntity] object, or `null` if not found.
     */
    @Query("SELECT * FROM CharacterEntity WHERE id = :characterId LIMIT 1")
    fun getCharacterById(characterId: Int): Flow<CharacterEntity?>
}
