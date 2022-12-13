package com.telkom.hackernews.data

import android.content.Context
import android.content.SharedPreferences

interface HackerNewsPreference {

    fun get(key: String, defValue: String): String?

    fun set(key: String, value: String)
}

class HackerNewsPreferenceImpl private constructor(
    private val sharedPreference: SharedPreferences
): HackerNewsPreference {

    override fun get(key: String, defValue: String): String? {
        return sharedPreference.getString(key, defValue)
    }

    override fun set(key: String, value: String) {
        sharedPreference.edit().putString(key, value).apply()
    }

    companion object {
        @Volatile
        private var INSTANCE: HackerNewsPreferenceImpl? = null

        private const val PREFERENCE_NAME = "HackerNewsStorage"

        @Synchronized
        fun getInstance(context: Context): HackerNewsPreferenceImpl {
            if(INSTANCE == null) {
                INSTANCE = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                    .let(::HackerNewsPreferenceImpl)
            }
            return INSTANCE!!
        }
    }
}
