package com.geekstudio.rickandmorty.presentation.ui.fragments.characters

import com.geekstudio.rickandmorty.core.base.BaseViewModel
import com.geekstudio.rickandmorty.domain.usecases.FetchEpisodeUseCase
import com.geekstudio.rickandmorty.domain.usecases.FetchLocalEpisodeUseCase
import com.geekstudio.rickandmorty.domain.usecases.FetchLocalPagedCharacterUseCase
import com.geekstudio.rickandmorty.domain.usecases.FetchPagedCharactersUseCase
import com.geekstudio.rickandmorty.presentation.models.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val fetchPagedCharactersUseCase: FetchPagedCharactersUseCase,
    private val fetchLocalPagedCharacterUseCase: FetchLocalPagedCharacterUseCase,
    private val fetchSingleEpisodeUseCase: FetchEpisodeUseCase,
    private val fetchLocalEpisodeUseCase: FetchLocalEpisodeUseCase
) : BaseViewModel() {

    fun fetchPagedCharacters(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        gender: String? = null
    ) = fetchPagedCharactersUseCase(
        name,
        status,
        species,
        gender
    ).gatherPagingRequest { it.toUI() }

    fun fetchSingleEpisode(id: Int) = fetchSingleEpisodeUseCase(id)

    fun fetchLocalPagedCharacters(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        gender: String? = null
    ) = fetchLocalPagedCharacterUseCase(name, status, species, gender)

    fun fetchLocalSingleEpisode(url: String) = fetchLocalEpisodeUseCase(url)
}
