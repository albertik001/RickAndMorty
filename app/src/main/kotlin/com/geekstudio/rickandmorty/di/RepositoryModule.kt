package com.geekstudio.rickandmorty.di

import com.geekstudio.rickandmorty.data.repository.CharactersRepositoryImpl
import com.geekstudio.rickandmorty.data.repository.EpisodesRepositoryImpl
import com.geekstudio.rickandmorty.domain.repository.CharactersRepository
import com.geekstudio.rickandmorty.domain.repository.EpisodesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindCharactersRepository(charactersRepositoryImpl: CharactersRepositoryImpl): CharactersRepository

    @Singleton
    @Binds
    abstract fun bindEpisodesRepository(episodesRepositoryImpl: EpisodesRepositoryImpl): EpisodesRepository
}