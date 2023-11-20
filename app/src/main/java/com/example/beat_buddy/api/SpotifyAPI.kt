package com.example.beat_buddy.api

import android.os.Build
import androidx.annotation.RequiresApi
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface SpotifyAPI {
    @POST()
    @Headers("Content-Type: application/x-www-form-urlencoded",
        "Authorization: Basic MTAyZjA2MDlmMDA0NGEwYmJkYmVjZmRmMTViNTAyNmM6YWI0NjAwYzRhZmVlNDU0NWFlYzg2MDc0ZjNlZWE1ZTc=")
    suspend fun getAccessToken(
        @Body request: Map<String, String>,
        @Url url : String
    ): AccessTokenResponse

    @GET("v1/me/tracks")
    suspend fun fetchSongs(): SpotifyResponse

    @GET("v1/search")
    suspend fun searchSongs(@Query("text") query: String): SpotifyResponse


}