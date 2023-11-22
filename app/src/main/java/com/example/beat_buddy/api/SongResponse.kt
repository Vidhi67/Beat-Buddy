package com.example.beat_buddy.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class SongResponse (
    @Json(name = "items") val galleryItems: List<GalleryItem>
)
