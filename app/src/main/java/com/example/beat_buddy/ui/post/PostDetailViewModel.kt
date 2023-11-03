package com.example.beat_buddy.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.beat_buddy.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class PostDetailViewModel(postId: UUID): ViewModel() {
    private val postRepository = PostRepository.get()

    private val _post: MutableStateFlow<Post?> = MutableStateFlow(null)
    val post: StateFlow<Post?> = _post.asStateFlow()

    init {
        viewModelScope.launch {
            _post.value = postRepository.getPost(postId)
        }
    }

    fun updatePost(onUpdate: (Post) -> Post) {
        _post.update { oldPost ->
            oldPost?.let {
                onUpdate(it)
            }
        }
    }

//    fun deleteCrime() {
//        post.value?.let {
//            postRepository.deleteCrime(it)
//        }
//    }

    override fun onCleared() {
        super.onCleared()
        post.value?.let { postRepository.updatePost(it) }
    }
}

class PostDetailViewModelFactory(private val postId: UUID) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PostDetailViewModel(postId) as T
    }
}