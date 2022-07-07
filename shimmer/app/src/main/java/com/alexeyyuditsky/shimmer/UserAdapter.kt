package com.alexeyyuditsky.shimmer

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.shimmer.databinding.UserLayoutBinding
import com.bumptech.glide.Glide
import kotlin.math.log

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    var usersList = emptyList<User>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UserLayoutBinding.inflate(inflater, parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = usersList[position]
        holder.binding.apply {
            loadImageView(userImageView, user.photo)
            nameTextView.text = user.name
            addressTextView.text = user.address
        }
    }

    override fun getItemCount(): Int = usersList.size

    private fun loadImageView(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
            .load(url)
            .circleCrop()
            .into(imageView)
    }

    class UserViewHolder(val binding: UserLayoutBinding) : RecyclerView.ViewHolder(binding.root)

}