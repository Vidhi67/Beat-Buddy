package com.example.beat_buddy.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query


interface SpotifyAPI {
    @FormUrlEncoded
    @POST("token")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    suspend fun getAccessToken(@Body request: AuthorizationData): AccessTokenResponse

    @GET("v1/me/tracks")
    suspend fun fetchSongs(): SpotifyResponse

    @GET("v1/search")
    suspend fun searchSongs(@Query("text") query: String): SpotifyResponse


}