package com.example.beat_buddy

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.beat_buddy.api.GalleryItem
import com.example.beat_buddy.api.SongInterceptor
import com.example.beat_buddy.api.SpotifyAPI
import com.example.beat_buddy.database.PostDatabase
import com.example.beat_buddy.ui.post.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.UUID

private const val TAG = "PostRepository"
private const val DATABASE_NAME = "post-database"
private const val CLIENT_ID = "102f0609f0044a0bbdbecfdf15b5026c"
private const val CLIENT_SECRET = "ab4600c4afee4545aec86074f3eea5e7"
private const val REDIRECT_URI = "https://github.com/eliorians/Beat-Buddy"

class PostRepository private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope
) {
    private val spotifyApi: SpotifyAPI

    init {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(SongInterceptor(context))
            .addInterceptor(logging)
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.spotify.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()

        spotifyApi = retrofit.create(SpotifyAPI::class.java)
    }

    private val database: PostDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            PostDatabase::class.java,
            DATABASE_NAME
        )
        .build()

    fun getPosts(): Flow<List<Post>> = database.postDao().getPosts()

    suspend fun getPost(id: UUID): Post = database.postDao().getPost(id)

    suspend fun addPost(post: Post){
        database.postDao().addPost(post)
    }

    fun updatePost(post: Post) {
        coroutineScope.launch {
            database.postDao().updatePost(post)
        }
    }

    companion object {
        private var INSTANCE: PostRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                Log.d(TAG, "making sure the repo isnt null")
                INSTANCE = PostRepository(context)
            }
        }

        fun get(): PostRepository {
            Log.d(TAG, "getting the repo")
            return INSTANCE
                ?: throw IllegalStateException("PostRepository must be initialized")
        }
    }
    suspend fun getAccessToken(): String {
        val response = spotifyApi.getAccessToken("client_credentials", "https://accounts.spotify.com/api/token")
        return response.accessToken
    }


    suspend fun searchSongs(query: String): List<GalleryItem> =
        spotifyApi.searchSongs(query).tracks.galleryItems

}