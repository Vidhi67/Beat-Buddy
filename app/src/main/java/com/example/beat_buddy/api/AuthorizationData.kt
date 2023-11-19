package com.example.beat_buddy.api

import com.google.gson.annotations.SerializedName

data class AuthorizationData (
    @SerializedName("grant_type") val grantType: String = "client_credentials",
    @SerializedName("client_id") val userName: String = "102f0609f0044a0bbdbecfdf15b5026c",
    @SerializedName("client_secret") val userEmail: String = "ab4600c4afee4545aec86074f3eea5e7",
)
