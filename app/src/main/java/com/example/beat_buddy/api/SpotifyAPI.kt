package com.example.beat_buddy.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SpotifyAPI {
    @GET("v1/tracks/{id}")
    fun getTrack(
        @Header("Authorization") authorization: String,
        @Query("id") trackId: String
    ): Call<SpotifyResponse> // Define a data class for the response

}