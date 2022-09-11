package com.geekstudio.rickandmorty.presentation.ui.fragments.characters.detail

import com.geekstudio.rickandmorty.core.base.BaseViewModel
import com.geekstudio.rickandmorty.domain.usecases.FetchEpisodeUseCase
import com.geekstudio.rickandmorty.domain.usecases.FetchLocalEpisodeUseCase
import com.geekstudio.rickandmorty.domain.usecases.FetchLocalPagedCharacterUseCase
import com.geekstudio.rickandmorty.domain.usecases.FetchPagedCharactersUseCase
import com.geekstudio.rickandmorty.presentation.models.CharactersUI
import com.geekstudio.rickandmorty.presentation.models.EpisodesUI
import com.geekstudio.rickandmorty.presentation.models.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val fetchSingleCharacterUseCase: FetchPagedCharactersUseCase,
    private val fetchLocalPagedCharacterUseCase: FetchLocalPagedCharacterUseCase,
    private val fetchSingleEpisodeUseCase: FetchEpisodeUseCase,
    private val fetchLocalEpisodeUseCase: FetchLocalEpisodeUseCase
) : BaseViewModel() {

    private val _characterState = mutableUiStateFlow<CharactersUI>()
    val characterState = _characterState.asStateFlow()

    private val _episodesState = mutableUiStateFlow<EpisodesUI>()
    val episodesState = _episodesState.asStateFlow()

    fun fetchSingleCharacter(id: Int) =
        fetchSingleCharacterUseCase(id).gatherRequest(_characterState) {
            it.toUI()
        }

    fun fetchSingleEpisode(id: Int) =
        fetchSingleEpisodeUseCase(id).gatherRequest(_episodesState) {
            it.toUI()
        }

    fun fetchLocalSingleCharacter(id: Int) =
        fetchLocalPagedCharacterUseCase(id)

    fun fetchLocalSingleEpisode(url: String) = fetchLocalEpisodeUseCase(url)
}