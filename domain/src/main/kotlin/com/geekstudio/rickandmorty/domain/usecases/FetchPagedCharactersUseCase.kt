package com.geekstudio.rickandmorty.domain.usecases

import com.geekstudio.rickandmorty.domain.repository.CharactersRepository
import javax.inject.Inject

class FetchPagedCharactersUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {

    operator fun invoke(
        name: String?,
        status: String?,
        species: String?,
        gender: String?
    ) = charactersRepository.fetchPagedCharacters(name, status, species, gender)

    operator fun invoke(id: Int) = charactersRepository.fetchSingleCharacter(id)
}