package com.geekstudio.rickandmorty.data.db.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.geekstudio.rickandmorty.data.remote.dtos.CharactersDto
import kotlinx.coroutines.flow.Flow

@Dao
interface CharactersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacters(characters: List<CharactersDto>)

    @Query(
        """ SELECT * FROM charactersdto WHERE 
(:name IS NULL OR name LIKE '%' || :name || '%') AND
(:status IS NULL OR status = :status) AND
(:gender IS NULL OR gender = :gender) AND 
(:species IS NULL OR species = :species)"""
    )
    fun getAllCharacters(
        name: String?,
        status: String?,
        species: String?,
        gender: String?
    ): Flow<List<CharactersDto>>

    @Query("SELECT * FROM charactersdto WHERE id = :id")
    fun getSingleCharacter(id: Int): Flow<CharactersDto>

    @Query("DELETE FROM charactersdto")
    suspend fun deleteAllCharacters()
}