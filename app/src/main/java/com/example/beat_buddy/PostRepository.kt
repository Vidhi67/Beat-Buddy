package com.example.beat_buddy

import android.content.Context
import androidx.room.Room
import com.example.beat_buddy.database.PostDatabase
import com.example.beat_buddy.ui.post.Post
import kotlinx.coroutines.flow.Flow
import java.util.UUID

private const val DATABASE_NAME = "post-database"
class PostRepository private constructor(context: Context) {

    private val database: PostDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            PostDatabase::class.java,
            DATABASE_NAME
        )
        .createFromAsset(DATABASE_NAME)
        .build()

    fun getPosts(): Flow<List<Post>> = database.postDao().getPosts()

    suspend fun getPost(id: UUID): Post = database.postDao().getPost(id)

    suspend fun addPost(post: Post){
        database.postDao().addPost(post)
    }

    companion object {
        private var INSTANCE: PostRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = PostRepository(context)
            }
        }

        fun get(): PostRepository {
            return INSTANCE
                ?: throw IllegalStateException("PostRepository must be initialized")
        }
    }
}