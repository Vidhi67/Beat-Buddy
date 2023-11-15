package com.example.beat_buddy.api

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

const val API_KEY = "ab4600c4afee4545aec86074f3eea5e7"

// Spotify requires bearer token with Oauth 2.0 so we must pass it in
// we need to store the auth token in the users preferences and it can read within the interceptor
class SongInterceptor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()

        val newRequest: Request = originalRequest.newBuilder()
            .header("Authorization", "Bearer $authToken")
            .build()

        return chain.proceed(newRequest)
    }
}