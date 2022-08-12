package com.geekstudio.rickandmorty.domain.usecases

import com.geekstudio.rickandmorty.domain.repository.CharactersRepository
import javax.inject.Inject

class FetchPagedCharactersUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {
    operator fun invoke() = charactersRepository.fetchPagedCharacters()
}