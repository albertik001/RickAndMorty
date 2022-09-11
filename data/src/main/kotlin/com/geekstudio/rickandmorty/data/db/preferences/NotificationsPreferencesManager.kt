package com.geekstudio.rickandmorty.data.db.preferences

import android.content.SharedPreferences
import com.geekstudio.rickandmorty.data.db.preferences.PreferencesKeys.IS_SHOW_CHECK_INTERNET_KEY
import javax.inject.Inject

class NotificationsPreferencesManager @Inject constructor(private val preferences: SharedPreferences) {

    var isShowCheckInternet: Boolean
        get() = preferences.getBoolean(IS_SHOW_CHECK_INTERNET_KEY, false)
        set(value) = preferences.edit().putBoolean(IS_SHOW_CHECK_INTERNET_KEY, value).apply()
}