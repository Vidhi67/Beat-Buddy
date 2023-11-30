package com.example.beat_buddy.ui.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.beat_buddy.api.GalleryItem
import com.example.beat_buddy.databinding.ListItemGalleryBinding
import java.util.UUID

class SongHolder(
    val binding: ListItemGalleryBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(song: GalleryItem, onSongClicked: (song: GalleryItem) -> Unit) {
        binding.songTitle.text = song.name
        binding.songId.text = song.artists[0].name
        binding.root.setOnClickListener {
            onSongClicked(song)
        }
    }
}
class SongListAdapter(
    private val songs: MutableList<GalleryItem>,
    private val onSongClicked: (song: GalleryItem) -> Unit
) : RecyclerView.Adapter<SongHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemGalleryBinding.inflate(inflater, parent, false)
        return SongHolder(binding)
    }

    override fun getItemCount() = songs.size

    override fun onBindViewHolder(holder: SongHolder, position: Int) {
        val song = songs[position]
        holder.bind(song, onSongClicked)
    }

    fun addSong(songItem: GalleryItem) {
        songs.add(songItem)
        notifyItemInserted(songs.size - 1)
    }
    fun clearSongs() {
        songs.clear()
        notifyDataSetChanged()
    }
}