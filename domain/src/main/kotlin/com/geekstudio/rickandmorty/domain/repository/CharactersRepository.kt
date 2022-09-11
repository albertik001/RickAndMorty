package com.geekstudio.rickandmorty.domain.repository

import androidx.paging.PagingData
import com.geekstudio.rickandmorty.domain.common.Either
import com.geekstudio.rickandmorty.domain.models.CharactersModel
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    fun fetchPagedCharacters(
        name: String?,
        status: String?,
        species: String?,
        gender: String?
    ): Flow<PagingData<CharactersModel>>

    fun fetchSingleCharacter(id: Int): Flow<Either<String, CharactersModel>>

    fun fetchLocalPagedCharacters(
        name: String?,
        status: String?,
        species: String?,
        gender: String?
    ): Flow<List<CharactersModel>>

    fun fetchLocalSingleCharacter(id: Int): Flow<CharactersModel>
}