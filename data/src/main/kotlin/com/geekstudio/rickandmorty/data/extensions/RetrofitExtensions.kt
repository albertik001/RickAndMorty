package com.geekstudio.rickandmorty.data.extensions

import retrofit2.Retrofit

inline fun <reified T : Any> Retrofit.createAnApi(): T {
    return create(T::class.java)
}