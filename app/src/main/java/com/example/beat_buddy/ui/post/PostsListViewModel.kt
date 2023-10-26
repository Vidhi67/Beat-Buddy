package com.example.beat_buddy.ui.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Date
class PostsListViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is post Fragment"
    }
    val text: LiveData<String> = _text
    val posts = mutableListOf<Post>()
    val places = listOf(
        "Anne Belk",
        "Plemmons Student Union",
        "The Rock",
        "The Convocation Center",
        "Smith-Wright",
        "Peacock Hall",
        "Sandford Mall"
    )
    val descriptions = listOf(
        "Visited the CS department",
        "Played spike ball",
        "Lost at the rock",
        "Won at the rock",
        "Hit the gym"
    )

    init {
        for (i in 0 until 20) {
            val post = Post(
                id = "this is the id",
                location = "This is the location",
                title = places[i % places.size],
                description = descriptions[i % descriptions.size],
                date = Date(),
            )

            posts += post
        }
    }
}