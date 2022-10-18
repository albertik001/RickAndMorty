package com.geekstudio.rickandmorty.domain.usecases

import com.geekstudio.rickandmorty.domain.repository.EpisodesRepository
import javax.inject.Inject

class FetchEpisodeUseCase @Inject constructor(
    private val repository: EpisodesRepository
) {

    operator fun invoke(id: Int) = repository.fetchSingleEpisode(id)
}