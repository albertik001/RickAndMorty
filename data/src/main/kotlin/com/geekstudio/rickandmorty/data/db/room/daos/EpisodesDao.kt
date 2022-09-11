package com.geekstudio.rickandmorty.data.db.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.geekstudio.rickandmorty.data.remote.dtos.EpisodesDto
import kotlinx.coroutines.flow.Flow

@Dao
interface EpisodesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllEpisodes(episode: List<EpisodesDto>)

    @Query("SELECT * FROM episodesdto WHERE url = :url")
    fun getSingleEpisode(url: String): Flow<EpisodesDto>
}