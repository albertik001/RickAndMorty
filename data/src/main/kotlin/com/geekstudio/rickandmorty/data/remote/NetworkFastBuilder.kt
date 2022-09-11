package com.geekstudio.rickandmorty.data.remote

import com.geekstudio.rickandmorty.data.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NetworkFastBuilder @Inject constructor() {
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}

class OkHttp @Inject constructor() {
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(provideLoggingInterceptor())
            .callTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
        return okHttpClient.build()

    }

    private fun provideLoggingInterceptor() = HttpLoggingInterceptor().setLevel(
        HttpLoggingInterceptor.Level.BODY

    )
}