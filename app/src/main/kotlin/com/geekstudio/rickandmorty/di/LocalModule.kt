package com.geekstudio.rickandmorty.di

import android.content.Context
import com.geekstudio.rickandmorty.data.db.room.RoomClient
import com.geekstudio.rickandmorty.data.db.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) = RoomClient().provideRoom(context)

    @Singleton
    @Provides
    fun provideCharactersDao(roomDatabase: RoomDatabase) =
        RoomClient().provideCharacterDao(roomDatabase)

    @Singleton
    @Provides
    fun provideEpisodesDao(roomDatabase: RoomDatabase) =
        RoomClient().provideEpisodesDao(roomDatabase)
}