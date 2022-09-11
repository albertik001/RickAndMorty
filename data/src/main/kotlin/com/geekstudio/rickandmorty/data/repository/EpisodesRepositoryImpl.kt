package com.geekstudio.rickandmorty.data.repository

import com.geekstudio.rickandmorty.data.base.BaseRepository
import com.geekstudio.rickandmorty.data.db.room.daos.EpisodesDao
import com.geekstudio.rickandmorty.data.remote.apiservices.EpisodesApi
import com.geekstudio.rickandmorty.data.remote.dtos.EpisodesDto
import com.geekstudio.rickandmorty.data.remote.dtos.toDomain
import com.geekstudio.rickandmorty.domain.common.Either
import com.geekstudio.rickandmorty.domain.models.EpisodesModel
import com.geekstudio.rickandmorty.domain.repository.EpisodesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class EpisodesRepositoryImpl @Inject constructor(
    private val episodeApi: EpisodesApi,
    private val episodesDao: EpisodesDao
) : BaseRepository(), EpisodesRepository {

    private val episodeListToDao = arrayListOf<EpisodesDto>()

    override fun fetchSingleEpisode(id: Int): Flow<Either<String, EpisodesModel>> {
        return doRequest {
            addRoom(episodeApi.fetchEpisode(id)).toDomain()
        }
    }

    private suspend fun addRoom(episodesDto: EpisodesDto): EpisodesDto {
        episodeListToDao.add(episodesDto)
        episodesDao.insertAllEpisodes(episodeListToDao)
        return episodesDto
    }

    override fun fetchLocalSingleEpisode(url: String): Flow<EpisodesModel> {
        return episodesDao.getSingleEpisode(url).map { it.toDomain() }
    }
}