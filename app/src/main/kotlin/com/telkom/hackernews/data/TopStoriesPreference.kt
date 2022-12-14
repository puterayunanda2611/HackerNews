package com.telkom.hackernews.data

import android.content.Context
import android.content.SharedPreferences

interface TopStoriesPreference {

    fun get(key: String, defValue: String): String?

    fun set(key: String, value: String)
}

class TopStoriesPreferenceImpl private constructor(
    private val sharedPreference: SharedPreferences
): TopStoriesPreference {

    override fun get(key: String, defValue: String): String? {
        return sharedPreference.getString(key, defValue)
    }

    override fun set(key: String, value: String) {
        sharedPreference.edit().putString(key, value).apply()
    }

    companion object {
        @Volatile
        private var INSTANCE: TopStoriesPreferenceImpl? = null

        private const val PREFERENCE_NAME = "HackerNewsStorage"

        @Synchronized
        fun getInstance(context: Context): TopStoriesPreferenceImpl {
            if(INSTANCE == null) {
                INSTANCE = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                    .let(::TopStoriesPreferenceImpl)
            }
            return INSTANCE!!
        }
    }
}
