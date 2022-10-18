package com.geekstudio.rickandmorty.presentation.models

import com.geekstudio.rickandmorty.core.base.BaseDiffModel
import com.geekstudio.rickandmorty.domain.models.EpisodesModel

data class EpisodesUI(
    override val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
) : BaseDiffModel<Int>

fun EpisodesModel.toUI() = EpisodesUI(id, name, air_date, episode, characters, url, created)