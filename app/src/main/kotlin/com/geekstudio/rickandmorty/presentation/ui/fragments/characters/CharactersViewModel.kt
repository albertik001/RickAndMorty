package com.geekstudio.rickandmorty.presentation.ui.fragments.characters

import androidx.lifecycle.viewModelScope
import com.geekstudio.rickandmorty.core.base.BaseViewModel
import com.geekstudio.rickandmorty.domain.usecases.FetchEpisodeUseCase
import com.geekstudio.rickandmorty.domain.usecases.FetchLocalEpisodeUseCase
import com.geekstudio.rickandmorty.domain.usecases.FetchLocalPagedCharacterUseCase
import com.geekstudio.rickandmorty.domain.usecases.FetchPagedCharactersUseCase
import com.geekstudio.rickandmorty.presentation.models.CharactersUI
import com.geekstudio.rickandmorty.presentation.models.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val fetchPagedCharactersUseCase: FetchPagedCharactersUseCase,
    private val fetchLocalPagedCharacterUseCase: FetchLocalPagedCharacterUseCase,
    private val fetchSingleEpisodeUseCase: FetchEpisodeUseCase,
    private val fetchLocalEpisodeUseCase: FetchLocalEpisodeUseCase
) : BaseViewModel() {

    private val _localCharactersState = MutableStateFlow<List<CharactersUI>>(emptyList())
    val localCharactersState = _localCharactersState.asStateFlow()


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
    ) {
        viewModelScope.launch {
            fetchLocalPagedCharacterUseCase(name, status, species, gender).collectLatest {
                _localCharactersState.value = it.map { characterModel -> characterModel.toUI() }
            }
        }
    }

    fun fetchLocalSingleEpisode(url: String) = fetchLocalEpisodeUseCase(url)
}
