package com.geekstudio.rickandmorty.di

import android.content.Context
import android.content.SharedPreferences
import com.geekstudio.rickandmorty.data.db.preferences.NotificationsPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("rickAndMorty", Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideAuthenticationPreferencesManager(sharedPreferences: SharedPreferences) =
        NotificationsPreferencesManager(sharedPreferences)
}