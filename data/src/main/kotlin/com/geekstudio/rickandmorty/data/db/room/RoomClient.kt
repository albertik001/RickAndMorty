package com.geekstudio.rickandmorty.data.db.room

import android.content.Context
import androidx.room.Room
import com.geekstudio.rickandmorty.data.db.room.daos.CharactersDao
import com.geekstudio.rickandmorty.data.db.room.daos.EpisodesDao

class RoomClient {

    fun provideRoom(context: Context) = Room
        .databaseBuilder(context, RoomDatabase::class.java, "appDatabase")
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    fun provideCharacterDao(roomDatabase: RoomDatabase): CharactersDao = roomDatabase.characterDao()
    fun provideEpisodesDao(roomDatabase: RoomDatabase): EpisodesDao = roomDatabase.episodesDao()

}