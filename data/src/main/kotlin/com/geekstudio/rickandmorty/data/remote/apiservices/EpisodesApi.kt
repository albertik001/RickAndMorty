package com.geekstudio.rickandmorty.data.remote.apiservices

import com.geekstudio.rickandmorty.data.remote.dtos.EpisodesDto
import com.geekstudio.rickandmorty.data.remote.dtos.RickAndMortyResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EpisodesApi {

    @GET("episode")
    suspend fun fetchEpisodes(
        @Query("page") page: Int,
        @Query("name") name: String?,
        @Query("episode") episode: String?
    ): RickAndMortyResponse<EpisodesDto>

    @GET("episode/{id}")
    suspend fun fetchEpisode(
        @Path("id") id: Int
    ): EpisodesDto
}