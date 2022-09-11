package com.geekstudio.rickandmorty.domain.models

data class RickAndMortyResponseModel(
    val info: InfoModel,
    val results: List<CharactersModel>
)

data class InfoModel(
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: Any
)

data class LocationModel(
    val name: String,
    val url: String
)

data class OriginModel(
    val name: String,
    val url: String
)

data class CharactersModel(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: OriginModel,
    val location: LocationModel,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)