package com.sample.weather.ui.user

import android.content.Context
import android.content.SharedPreferences


class Preferences private constructor(context: Context) {

    var userName: String?
        get() = sharedPreferences.getString(Config.USER_NAME, "")
        set(userName) {
            editor.putString(Config.USER_NAME, userName)
            editor.apply()
        }

    var temperature: String?
        get() = sharedPreferences.getString(Config.TEMPERATURE_TYPE, "metric")
        set(temp) {
            editor.putString(Config.TEMPERATURE_TYPE, temp)
            editor.apply()
        }


    var latitiude: Float?
        get() = sharedPreferences.getFloat(Config.LATITUDE, 0F)
        set(lat) {
            editor.putFloat(Config.LATITUDE, lat ?: 0F)
            editor.apply()
        }


    var longitude: Float?
        get() = sharedPreferences.getFloat(Config.LONGITUDE, 0F)
        set(long) {
            editor.putFloat(Config.LONGITUDE, long ?: 0F)
            editor.apply()
        }

    companion object {
        private var myPreferences: Preferences? = null
        private lateinit var sharedPreferences: SharedPreferences
        private lateinit var editor: SharedPreferences.Editor

        fun getPreferences(context: Context): Preferences? {
            if (myPreferences == null) myPreferences = Preferences(context)
            return myPreferences
        }
    }

    init {
        sharedPreferences = context.getSharedPreferences(Config.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        editor.apply()
    }
}