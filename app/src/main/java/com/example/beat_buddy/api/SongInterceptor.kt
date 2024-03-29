package com.example.beat_buddy.api

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


// Spotify requires bearer token with Oauth 2.0 so we must pass it in
// we need to store the auth token in the users preferences and it can read within the interceptor
class SongInterceptor(private val context: Context): Interceptor {

    private var authToken: String? = null

    fun getAuthTokenFromPreferences() {
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        authToken = sharedPreferences.getString("authToken", null)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        getAuthTokenFromPreferences()
        val originalRequest: Request = chain.request()

        if (!originalRequest.url.toString().contains("accounts.spotify.com")) {
            val newRequest: Request = originalRequest.newBuilder()
                .header("Authorization", "Bearer $authToken")
                .build()

            return chain.proceed(newRequest)
        }

        return chain.proceed(originalRequest)
    }
}