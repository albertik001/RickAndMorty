package com.geekstudio.rickandmorty.data.remote.dtos


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.geekstudio.rickandmorty.domain.models.EpisodesModel
import com.google.gson.annotations.SerializedName

@Entity
data class EpisodesDto(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("episode")
    val episode: String,
    @SerializedName("characters")
    val characters: List<String>,
    @SerializedName("url")
    val url: String,
    @SerializedName("created")
    val created: String
)

fun EpisodesDto.toDomain() = EpisodesModel(id, name, airDate, episode, characters, url, created)
