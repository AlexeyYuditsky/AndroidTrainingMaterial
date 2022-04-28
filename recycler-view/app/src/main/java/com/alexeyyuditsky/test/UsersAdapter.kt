package com.alexeyyuditsky.test

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.test.databinding.ItemUserBinding
import com.alexeyyuditsky.test.model.User
import com.bumptech.glide.Glide

class UsersAdapter(
    private val actionListener: UserActionListener
) : RecyclerView.Adapter<UsersViewHolder>(), View.OnClickListener {

    var users: List<User> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.moreImageViewButton -> showPopupMenu(view)
            else -> actionListener.onUserDetails(view.tag as User)
        }
    }

    private fun showPopupMenu(view: View) {
        val context = view.context
        val user = view.tag as User
        val position = users.indexOfFirst { user.id == it.id }
        val popupMenu = PopupMenu(context, view)

        popupMenu.menu.add(0, ID_MOVE_UP, Menu.NONE, context.getString(R.string.move_up)).run {
            isEnabled = position > 0
        }
        popupMenu.menu.add(0, ID_MOVE_DOWN, Menu.NONE, context.getString(R.string.move_down)).run {
            isEnabled = position < users.size - 1
        }
        popupMenu.menu.add(0, ID_REMOVE, Menu.NONE, context.getString(R.string.remove))

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                ID_MOVE_UP -> actionListener.onUserMove(user, ID_MOVE_UP)
                ID_MOVE_DOWN -> actionListener.onUserMove(user, ID_MOVE_DOWN)
                ID_REMOVE -> actionListener.onUserDelete(user)
            }
            return@setOnMenuItemClickListener true
        }

        popupMenu.show()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.moreImageViewButton.setOnClickListener(this)

        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = users[position]
        holder.binding.apply {
            holder.itemView.tag = user
            moreImageViewButton.tag = user

            userNameTextView.text = user.name
            userCompanyTextView.text = user.company
            if (user.photo.isNotBlank()) {
                Glide.with(photoImageView.context)
                    .load(user.photo)
                    .circleCrop()
                    .placeholder(R.drawable.ic_avatar)
                    .error(R.drawable.ic_avatar)
                    .into(photoImageView)
            } else {
                photoImageView.setImageResource(R.drawable.ic_avatar)
            }
        }
    }

    override fun getItemCount(): Int = users.size

    private companion object {
        private const val ID_MOVE_UP = -1
        private const val ID_MOVE_DOWN = 1
        private const val ID_REMOVE = 0
    }

}

class UsersViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)