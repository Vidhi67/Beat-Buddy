package com.example.beat_buddy.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.beat_buddy.ui.post.Post

@Database(entities = [Post::class ], version=1)
@TypeConverters(PostTypeConverters::class)
abstract class PostDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}