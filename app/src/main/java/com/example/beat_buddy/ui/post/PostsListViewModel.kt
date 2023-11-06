package com.example.beat_buddy.ui.post


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beat_buddy.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


private const val TAG = "PostListViewModel"
class PostsListViewModel : ViewModel() {

    private val postRepository = PostRepository.get()

    private val _posts: MutableStateFlow<List<Post>> = MutableStateFlow(emptyList())

    val posts: StateFlow<List<Post>>
        get() = _posts.asStateFlow()

    init {
        viewModelScope.launch {
            postRepository.getPosts().collect {
                _posts.value = it
            }
        }
    }

    suspend fun addPost(post: Post) {
        postRepository.addPost(post)
    }
}