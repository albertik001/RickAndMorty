package com.geekstudio.rickandmorty.domain.repository

import androidx.paging.PagingData
import com.geekstudio.rickandmorty.domain.models.CharactersModel
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    fun fetchPagedCharacters(): Flow<PagingData<CharactersModel>>
}