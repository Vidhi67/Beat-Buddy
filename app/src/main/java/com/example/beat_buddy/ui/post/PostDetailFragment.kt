package com.example.beat_buddy.ui.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.beat_buddy.databinding.FragmentPostDetailBinding
import kotlinx.coroutines.launch

class PostDetailFragment : Fragment() {

    private var _binding: FragmentPostDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val args: PostDetailFragmentArgs by navArgs()

    private val postDetailViewModel: PostDetailViewModel by viewModels {
        PostDetailViewModelFactory(args.postId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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