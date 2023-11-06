package com.example.beat_buddy.ui.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.beat_buddy.databinding.ListItemPostBinding
import java.util.UUID

class PostHolder(
    val binding: ListItemPostBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post, onPostClicked: (postId: UUID) -> Unit) {
        binding.postsTitle.text = post.title
        binding.postsTimestamp.text = post.date.toString()
        binding.postsDescription.text = post.description

        binding.root.setOnClickListener {
            onPostClicked(post.id)

        }
    }
}

class PostsListAdapter(
    private val posts: List<Post>,
    private val onPostClicked: (postId: UUID) -> Unit
) : RecyclerView.Adapter<PostHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemPostBinding.inflate(inflater, parent, false)
        return PostHolder(binding)
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val post = posts[position]
        holder.bind(post, onPostClicked)
    }
}