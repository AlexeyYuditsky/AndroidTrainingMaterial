package com.alexeyyuditsky.test

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.CustomPopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.test.databinding.ItemUserBinding
import com.alexeyyuditsky.test.model.User
import com.bumptech.glide.Glide

class UsersDiffCallback(
    private val oldList: List<User>,
    private val newList: List<User>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}

class UsersAdapter(
    private val actionListener: UserActionListener
) : RecyclerView.Adapter<UsersViewHolder>(), View.OnClickListener {

    var users: List<User> = emptyList()
        set(value) {
            val diffCallback = UsersDiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
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
        val popupMenu = CustomPopupMenu(context, view)

        popupMenu.menu.add(0, ID_MOVE_UP, Menu.NONE, context.getString(R.string.move_up)).run {
            isEnabled = position > 0
            setIcon(R.drawable.ic_up)
        }
        popupMenu.menu.add(0, ID_MOVE_DOWN, Menu.NONE, context.getString(R.string.move_down)).run {
            isEnabled = position < users.size - 1
            setIcon(R.drawable.ic_down)
        }
        popupMenu.menu.add(0, ID_REMOVE, Menu.NONE, context.getString(R.string.remove)).run {
            setIcon(R.drawable.ic_delete)
        }

        if (user.company != view.resources.getString(R.string.unemployed)) {
            popupMenu.menu.add(0, ID_FIRE, Menu.NONE, context.getString(R.string.fire)).run {
                setIcon(R.drawable.ic_fire)
            }
        }

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                ID_MOVE_UP -> actionListener.onUserMove(user, ID_MOVE_UP)
                ID_MOVE_DOWN -> actionListener.onUserMove(user, ID_MOVE_DOWN)
                ID_REMOVE -> actionListener.onUserDelete(user)
                ID_FIRE -> actionListener.onUserFire(user)
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
        val context = holder.itemView.context
        holder.binding.apply {
            holder.itemView.tag = user
            moreImageViewButton.tag = user

            userNameTextView.text = user.name

            userCompanyTextView.text = if (user.company.isNotBlank()) user.company
            else context.getString(R.string.unemployed)

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
        private const val ID_FIRE = 2
    }

}

class UsersViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)