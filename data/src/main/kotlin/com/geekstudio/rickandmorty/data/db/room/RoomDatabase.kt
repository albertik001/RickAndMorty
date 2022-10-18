package com.geekstudio.rickandmorty.data.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.geekstudio.rickandmorty.data.db.room.daos.CharactersDao
import com.geekstudio.rickandmorty.data.db.room.daos.EpisodesDao
import com.geekstudio.rickandmorty.data.remote.dtos.CharactersDto
import com.geekstudio.rickandmorty.data.remote.dtos.EpisodesDto

@Database(
    entities = [CharactersDto::class, EpisodesDto::class],
    version = 7,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RoomDatabase : RoomDatabase() {

    abstract fun characterDao(): CharactersDao
    abstract fun episodesDao(): EpisodesDao
}