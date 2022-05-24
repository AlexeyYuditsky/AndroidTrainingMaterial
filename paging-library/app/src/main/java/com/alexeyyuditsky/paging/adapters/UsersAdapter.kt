package com.alexeyyuditsky.paging.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.paging.R
import com.alexeyyuditsky.paging.databinding.ItemUserBinding
import com.alexeyyuditsky.paging.model.users.User
import com.bumptech.glide.Glide

/**
 * Adapter for rendering users list in a RecyclerView.
 */
class UsersAdapter : PagingDataAdapter<User, UsersAdapter.Holder>(UsersDiffCallback()) {

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val user = getItem(position) ?: return
        holder.binding.apply {
            userNameTextView.text = user.name
            userCompanyTextView.text = user.company
            loadUserPhoto(photoImageView, user.imageUrl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    private fun loadUserPhoto(imageView: ImageView, url: String) {
        val context = imageView.context
        if (url.isNotBlank()) {
            Glide.with(context)
                .load(url)
                .circleCrop()
                .placeholder(R.drawable.ic_user_avatar)
                .error(R.drawable.ic_user_avatar)
                .into(imageView)
        } else {
            Glide.with(context)
                .load(R.drawable.ic_user_avatar)
                .into(imageView)
        }
    }

    class Holder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

}

// ---

class UsersDiffCallback : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean = oldItem == newItem

}