package com.geekstudio.rickandmorty.domain.usecases

import com.geekstudio.rickandmorty.domain.repository.CharactersRepository
import javax.inject.Inject

class FetchLocalPagedCharacterUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {
    operator fun invoke(
        name: String?,
        status: String?,
        species: String? = null,
        gender: String?
    ) = charactersRepository.fetchLocalPagedCharacters(name, status, species, gender)

    operator fun invoke(id: Int) = charactersRepository.fetchLocalSingleCharacter(id)

}