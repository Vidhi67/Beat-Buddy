package com.example.beat_buddy.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class GalleryItem (
    val name: String,
    val id: String,
    val artists: List<ArtistItem>,
)

@JsonClass(generateAdapter = true)
data class ArtistItem (
    val name: String,
    val id: String,
    val type: String
)