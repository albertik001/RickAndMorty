package com.geekstudio.rickandmorty.domain.usecases

import com.geekstudio.rickandmorty.domain.repository.EpisodesRepository
import javax.inject.Inject

class FetchLocalEpisodeUseCase @Inject constructor(
    private val episodesRepository: EpisodesRepository
) {

    operator fun invoke(url: String) = episodesRepository.fetchLocalSingleEpisode(url)
}