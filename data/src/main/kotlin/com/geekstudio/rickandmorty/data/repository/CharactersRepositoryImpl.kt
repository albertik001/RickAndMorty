package com.geekstudio.rickandmorty.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.geekstudio.rickandmorty.data.db.room.daos.CharactersDao
import com.geekstudio.rickandmorty.data.remote.apiservices.CharactersApi
import com.geekstudio.rickandmorty.data.remote.dtos.CharactersDto
import com.geekstudio.rickandmorty.data.remote.dtos.toDomain
import com.geekstudio.rickandmorty.data.remote.pagingsource.CharactersPagingSource
import com.geekstudio.rickandmorty.data.repository.base.doPagingRequest
import com.geekstudio.rickandmorty.data.repository.base.doRequest
import com.geekstudio.rickandmorty.domain.common.Either
import com.geekstudio.rickandmorty.domain.models.CharactersModel
import com.geekstudio.rickandmorty.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val charactersApi: CharactersApi,
    private val charactersDao: CharactersDao,
) : CharactersRepository {

    override fun fetchPagedCharacters(
        name: String?, status: String?, species: String?, gender: String?
    ): Flow<PagingData<CharactersModel>> {
        return doPagingRequest(
            CharactersPagingSource(
                charactersApi, name, status, species, gender
            )
        ).map { pagingData ->
            pagingData.map {
                addingDataFromRoom(it)
                it.toDomain()
            }
        }
    }

    private suspend fun addingDataFromRoom(charactersDto: CharactersDto) {
        val list = arrayListOf<CharactersDto>()
        list.add(charactersDto)
        charactersDao.insertAllCharacters(list)
    }

    override fun fetchSingleCharacter(id: Int): Flow<Either<String, CharactersModel>> = doRequest {
        charactersApi.fetchCharacter(id).toDomain()
    }

    override fun fetchLocalPagedCharacters(
        name: String?, status: String?, species: String?, gender: String?
    ) = charactersDao.getAllCharacters(name, status, species, gender).map { list ->
        list.map { it.toDomain() }
    }

    override fun fetchLocalSingleCharacter(id: Int): Flow<CharactersModel> {
        return charactersDao.getSingleCharacter(id).map { it.toDomain() }
    }
}