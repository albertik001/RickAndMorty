package com.geekstudio.rickandmorty.presentation.ui.fragments.characters

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.geekstudio.rickandmorty.core.base.BaseViewModel
import com.geekstudio.rickandmorty.domain.usecases.FetchPagedCharactersUseCase
import com.geekstudio.rickandmorty.presentation.models.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val fetchPagedCharactersUseCase: FetchPagedCharactersUseCase
) : BaseViewModel() {

    fun fetchPagedCharacters() = fetchPagedCharactersUseCase().gatherPagingRequest { it.toUI() }


}