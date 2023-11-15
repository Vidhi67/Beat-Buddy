package com.example.beat_buddy.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SpotifyResponse (
    val tracks: SongResponse
)
