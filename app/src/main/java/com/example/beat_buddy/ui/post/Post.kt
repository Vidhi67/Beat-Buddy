package com.example.beat_buddy.ui.post

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity
data class Post (
    @PrimaryKey val id: UUID,
    val title: String,
    val date: Date,
    val description: String,
    val location: String
)