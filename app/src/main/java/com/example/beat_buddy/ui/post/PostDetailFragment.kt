package com.example.beat_buddy.ui.post

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beat_buddy.PostRepository
import com.example.beat_buddy.R
import com.example.beat_buddy.api.AuthManager
import com.example.beat_buddy.api.GalleryItem
import com.example.beat_buddy.databinding.FragmentPostDetailBinding
import kotlinx.coroutines.launch
import retrofit2.HttpException

private const val TAG = "PostGalleryFragment"
private val postRepository = PostRepository.get()
class PostDetailFragment : Fragment() {

    private var _binding: FragmentPostDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val args: PostDetailFragmentArgs by navArgs()
    private var searchView: SearchView? = null
    private var galleryAdapter: SongListAdapter? = null
    private val postDetailViewModel: PostDetailViewModel by viewModels {
        PostDetailViewModelFactory(args.postId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_post_detail, menu)

        val searchItem: MenuItem = menu.findItem(R.id.menu_item_search)
        searchView = searchItem.actionView as? SearchView

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, "QueryTextSubmit: $query")
//                photoGalleryViewModel.setQuery(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d(TAG, "QueryTextChange: $newText")
                return false
            }
        })
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentPostDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            postTitle.doOnTextChanged { text, _, _, _ ->
                postDetailViewModel.updatePost { oldPost ->
                    oldPost.copy(title = text.toString())
                }
            }
        }
        binding.apply {
            postDescription.doOnTextChanged { text, _, _, _ ->
                postDetailViewModel.updatePost { oldPost ->
                    oldPost.copy(description = text.toString())
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                postDetailViewModel.post.collect { post ->
                    post?.let { updateUi(it) }
                    binding.songRecyclerView.layoutManager = LinearLayoutManager(context)
                    galleryAdapter = SongListAdapter(mutableListOf())
                    binding.songRecyclerView.adapter = galleryAdapter
                    context?.let { AuthManager.saveAuthTokenToPreferences(it, postRepository.getAccessToken()) }
                    Log.d(TAG, "Access Token: ${postRepository.getAccessToken()}")
                }
            }
        }
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, "QueryTextSubmit: $query")
                query?.let { searchSpotifyApi(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d(TAG, "QueryTextChange: $newText")
                return false
            }
        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        searchView = null
    }
    private fun searchSpotifyApi(query: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val searchResults = postRepository.searchSongs(query) // Replace with your actual Spotify API call
                for (song in searchResults) {
                    updateSongSearchResults(song)
                }

            } catch (e: HttpException) {
                val errorMessage = e.response()?.errorBody()?.string() ?: "Unknown error"
                Log.e(TAG, "HTTP 400 Bad Request: $errorMessage")
                Log.e(TAG, "Failed URL: ${e.response()?.raw()?.request?.url}")
                Log.e(TAG, "Request: ${e.response()?.raw()?.request?.body}")
            } catch (e: Exception) {
                Log.e(TAG, "Error searching Spotify API: ${e.message}")
            }
        }
    }

    private fun updateSongSearchResults(song: GalleryItem) {
        galleryAdapter?.addSong(song)
    }

    private fun updateUi(post: Post) {
        binding.apply {
            if (postTitle.text.toString() != post.title) {
                postTitle.setText(post.title)
            }
        }
        binding.apply {
            if (postDescription.text.toString() != post.description) {
                postDescription.setText(post.description)
            }
        }
    }
}