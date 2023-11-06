package com.example.beat_buddy.ui.post


import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beat_buddy.R
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID
import android.util.Log
import android.widget.TextView
import com.example.beat_buddy.databinding.FragmentPostBinding

private const val TAG = "PostFragment"

class PostFragment : Fragment() {

    private var _binding: FragmentPostBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val postListViewModel: PostsListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostBinding.inflate(inflater, container, false)

        binding.postsRecyclerView.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                postListViewModel.posts.collect { posts ->
                    binding.postsRecyclerView.adapter = PostsListAdapter(posts) { postId ->
                        Log.d(TAG, "navigating to the fragment")
                        findNavController().navigate(
                            PostFragmentDirections.showPostDetail(postId)

                        )
                    }
                }
                Log.d(TAG, "navigated to the fragment")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_post_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_post -> {
                showNewPost()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showNewPost() {
        viewLifecycleOwner.lifecycleScope.launch {
            val newPost = Post(
                id = UUID.randomUUID(),
                title = "",
                date = Date(),
                description = "",
                location = ""
            )
            postListViewModel.addPost(newPost)
            findNavController().navigate(
                PostFragmentDirections.showPostDetail(newPost.id)
            )
        }
    }
}
