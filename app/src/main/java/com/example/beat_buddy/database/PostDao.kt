package com.example.beat_buddy.database

import androidx.room.Dao
import androidx.room.Query
import com.example.beat_buddy.ui.post.Post
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface PostDao {
    @Query("SELECT * FROM post")
    fun getPosts(): Flow<List<Post>>

    @Query("SELECT * FROM post WHERE id=(:id)")
    suspend fun getPost(id: UUID): Post
}