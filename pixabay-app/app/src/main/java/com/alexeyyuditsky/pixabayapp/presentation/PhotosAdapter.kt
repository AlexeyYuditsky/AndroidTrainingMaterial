package com.alexeyyuditsky.pixabayapp.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alexeyyuditsky.pixabay_app.databinding.PhotoItemBinding
import com.alexeyyuditsky.pixabayapp.data.Photo
import com.squareup.picasso.Picasso

class PhotosAdapter : RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder>() {

    private var photos: List<Photo> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun updatePhotos(photos: List<Photo>) {
        this.photos = photos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = PhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int = photos.size

    class PhotoViewHolder(private val binding: PhotoItemBinding) : ViewHolder(binding.root) {
        fun bind(photo: Photo) = with(binding) {
            Picasso.with(itemView.context).load(photo.imageUrl).into(photoImageView)
            userTextView.text = photo.user
            likesTextView.text = photo.likes
            commentsTextView.text = photo.comments
        }
    }

}