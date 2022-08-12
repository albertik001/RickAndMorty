package com.geekstudio.rickandmorty.data.remote.apiservices

import com.geekstudio.rickandmorty.data.remote.dtos.CharactersDto
import com.geekstudio.rickandmorty.data.remote.dtos.RickAndMortyResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersApi {

    @GET("character")
    suspend fun fetchCharacters(
        @Query("page") page: Int,
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("species") species: String? = null,
        @Query("type") type: String? = null,
        @Query("gender") gender: String? = null
    ): RickAndMortyResponse<CharactersDto>

    @GET("character/{id}")
    suspend fun fetchCharacter(
        @Path("id") id: Int
    ): CharactersDto
}