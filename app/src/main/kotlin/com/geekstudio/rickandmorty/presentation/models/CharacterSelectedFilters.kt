package com.geekstudio.rickandmorty.presentation.models

import java.io.Serializable

data class CharacterSelectedFilters(
    var status: String? = null,
    var species: String? = null,
    var gender: String? = null
) : Serializable
