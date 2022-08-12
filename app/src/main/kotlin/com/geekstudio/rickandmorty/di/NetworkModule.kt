package com.geekstudio.rickandmorty.di

import com.geekstudio.rickandmorty.data.remote.NetworkClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideCharactersApiService(networkClient: NetworkClient) =
        networkClient.provideCharactersApiService()
}