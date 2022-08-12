package com.geekstudio.rickandmorty.data.remote

import com.geekstudio.rickandmorty.data.extensions.createAnApi
import com.geekstudio.rickandmorty.data.remote.apiservices.CharactersApi
import javax.inject.Inject

class NetworkClient @Inject constructor(
    retrofitClient: NetworkFastBuilder,
    okHttp: OkHttp
) {
    private val retrofit = retrofitClient.provideRetrofit(okHttp.provideOkHttpClient())
    fun provideCharactersApiService(): CharactersApi = retrofit.createAnApi()

}