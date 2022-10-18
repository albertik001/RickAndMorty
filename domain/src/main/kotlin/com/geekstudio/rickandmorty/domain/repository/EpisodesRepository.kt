package com.geekstudio.rickandmorty.domain.repository

import com.geekstudio.rickandmorty.domain.common.Either
import com.geekstudio.rickandmorty.domain.models.EpisodesModel
import kotlinx.coroutines.flow.Flow

interface EpisodesRepository {

    fun fetchSingleEpisode(id: Int): Flow<Either<String, EpisodesModel>>
    fun fetchLocalSingleEpisode(url: String): Flow<EpisodesModel>
}