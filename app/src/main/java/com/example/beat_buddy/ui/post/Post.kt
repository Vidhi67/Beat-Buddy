package com.example.beat_buddy.ui.post

import java.util.Date

data class Post (
    val id: String,
    val title: String,
    val date: Date,
    val description: String,
    val location: String
)