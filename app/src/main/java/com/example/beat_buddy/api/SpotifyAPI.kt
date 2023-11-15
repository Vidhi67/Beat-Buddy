package com.example.beat_buddy.api

import retrofit2.http.GET
import retrofit2.http.Query

interface SpotifyAPI {
    @GET("v1/me/tracks")
    suspend fun fetchSongs(): SpotifyResponse

    @GET("v1/search")
    suspend fun searchSongs(@Query("text") query: String): SpotifyResponse
}