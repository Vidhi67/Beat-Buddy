package com.example.beat_buddy.ui.post

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beat_buddy.databinding.FragmentPostsListBinding


private const val TAG = "PostsListFragment"

class PostsListFragment : Fragment() {

    private var _binding: FragmentPostsListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val postsListViewModel: PostsListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total posts: ${postsListViewModel.posts.size}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostsListBinding.inflate(inflater, container, false)

        binding.postsRecyclerView.layoutManager = LinearLayoutManager(context)

        val posts = postsListViewModel.posts
        val adapter = PostsListAdapter(posts)
        binding.postsRecyclerView.adapter = adapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}