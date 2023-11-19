package com.example.beat_buddy.api

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface SpotifyAPI {
    @FormUrlEncoded
    @POST("token")
    @Headers(
        "grant_type: client_credentials",
        "Content-Type: application/x-www-form-urlencoded"
    )
    suspend fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): AccessTokenResponse

    @GET("v1/me/tracks")
    suspend fun fetchSongs(): SpotifyResponse

    @GET("v1/search")
    suspend fun searchSongs(@Query("text") query: String): SpotifyResponse


}