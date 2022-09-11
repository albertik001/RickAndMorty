package com.geekstudio.rickandmorty.data.db.room

import androidx.room.TypeConverter
import com.geekstudio.rickandmorty.data.remote.dtos.Location
import com.geekstudio.rickandmorty.data.remote.dtos.Origin
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private inline fun <reified T> typeToken() = object : TypeToken<T>() {}.type

    private inline fun <reified T> fromJson(value: String?): T {
        return Gson().fromJson(value, typeToken<T>())
    }

    private inline fun <reified T> toJson(generic: T): String? {
        return Gson().toJson(generic, typeToken<T>())
    }

    @TypeConverter
    fun fromOrigin(value: String?) = fromJson<Origin>(value)

    @TypeConverter
    fun originToJson(origin: Origin?) = toJson(origin)

    @TypeConverter
    fun fromSimpleLocation(value: String?) = fromJson<Location>(value)

    @TypeConverter
    fun simpleLocationToJson(simpleLocation: Location) = toJson(simpleLocation)

    @TypeConverter
    fun fromEpisodes(value: String?) = fromJson<MutableList<String>?>(value)

    @TypeConverter
    fun episodeToJson(episodes: MutableList<String>) = toJson(episodes)
}