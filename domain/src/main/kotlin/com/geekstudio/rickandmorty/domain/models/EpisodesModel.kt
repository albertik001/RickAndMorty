package com.geekstudio.rickandmorty.domain.models

data class EpisodesModel(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)