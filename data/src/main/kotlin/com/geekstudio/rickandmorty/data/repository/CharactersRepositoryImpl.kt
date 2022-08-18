package com.geekstudio.rickandmorty.data.repository

import androidx.paging.map
import com.geekstudio.rickandmorty.data.base.BaseRepository
import com.geekstudio.rickandmorty.data.remote.apiservices.CharactersApi
import com.geekstudio.rickandmorty.data.remote.dtos.toDomain
import com.geekstudio.rickandmorty.data.remote.pagingsource.CharactersPagingSource
import com.geekstudio.rickandmorty.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(private val charactersApi: CharactersApi) :
    BaseRepository(), CharactersRepository {

    override fun fetchPagedCharacters() =
        doPagingRequest(CharactersPagingSource(charactersApi)).map { pagingData ->
            pagingData.map {
                it.toDomain()
            }
        }
}