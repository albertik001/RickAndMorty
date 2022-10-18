package com.geekstudio.rickandmorty.data.remote.dtos


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.geekstudio.rickandmorty.domain.models.*
import com.google.gson.annotations.SerializedName

data class RickAndMortyResponse<T>(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val results: List<T>
)

data class Info(
    @SerializedName("count")
    val count: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("prev")
    val prev: Any
)

data class Location(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)

data class Origin(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)

@Entity
data class CharactersDto(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("species")
    val species: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("origin")
    val origin: Origin,
    @SerializedName("location")
    val location: Location,
    @SerializedName("image")
    val image: String,
    @SerializedName("episode")
    val episode: List<String>,
    @SerializedName("url")
    val url: String,
    @SerializedName("created")
    val created: String,
)

fun CharactersDto.toDomain() = CharactersModel(
    id,
    name,
    status,
    species,
    type,
    gender,
    origin.toDomain(),
    location.toDomain(),
    image,
    episode,
    url,
    created
)

fun Origin.toDomain() = OriginModel(name, url)

fun Location.toDomain() = LocationModel(name, url)

fun Info.toDomain() = InfoModel(count, pages, next, prev)

fun RickAndMortyResponse<CharactersDto>.toDomain() =
    RickAndMortyResponseModel(info.toDomain(), results.map { it.toDomain() })

fun CharactersModel.toDto() = CharactersDto(
    id,
    name,
    status,
    species,
    type,
    gender,
    origin.toDto(),
    location.toDto(),
    image,
    episode,
    url,
    created
)

fun OriginModel.toDto() = Origin(name, url)

fun LocationModel.toDto() = Location(name, url)

fun InfoModel.toDto() = Info(count, pages, next, prev)

fun RickAndMortyResponseModel.toDto() =
    RickAndMortyResponse(info.toDto(), results.map { it.toDto() })

