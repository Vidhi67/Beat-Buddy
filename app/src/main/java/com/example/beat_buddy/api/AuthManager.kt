package com.example.beat_buddy.api

import android.content.Context

class AuthManager {
    companion object {
        fun saveAuthTokenToPreferences(context: Context, authToken: String) {
            val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("authToken", authToken)
            editor.apply()
        }

    }
}