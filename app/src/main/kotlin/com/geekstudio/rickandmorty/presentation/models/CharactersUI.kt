package com.geekstudio.rickandmorty.presentation.models

import com.geekstudio.rickandmorty.core.base.BaseDiffModel
import com.geekstudio.rickandmorty.domain.models.*

data class RickAndMortyResponseUI(
    val info: InfoUI,
    val results: List<CharactersUI>
)

data class InfoUI(
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: Any
)

data class LocationUI(
    val name: String,
    val url: String
)

data class OriginUI(
    val name: String,
    val url: String
)

data class CharactersUI(
    override val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: OriginUI,
    val location: LocationUI,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String,
    var firstSeenIn: String = ""
) : BaseDiffModel<Int>

fun CharactersModel.toUI() = CharactersUI(
    id,
    name,
    status,
    species,
    type,
    gender,
    origin.toUI(),
    location.toUI(),
    image,
    episode,
    url,
    created
)

fun OriginModel.toUI() = OriginUI(name, url)

fun LocationModel.toUI() = LocationUI(name, url)

fun InfoModel.toUI() = InfoUI(count, pages, next, prev)

fun RickAndMortyResponseModel.toUI() =
    RickAndMortyResponseUI(info.toUI(), results.map { it.toUI() })

fun CharactersUI.toModel() = CharactersModel(
    id,
    name,
    status,
    species,
    type,
    gender,
    origin.toModel(),
    location.toModel(),
    image,
    episode,
    url,
    created
)

fun OriginUI.toModel() = OriginModel(name, url)

fun LocationUI.toModel() = LocationModel(name, url)

fun InfoUI.toModel() = InfoModel(count, pages, next, prev)

fun RickAndMortyResponseUI.toModel() =
    RickAndMortyResponseModel(info.toModel(), results.map { it.toModel() })