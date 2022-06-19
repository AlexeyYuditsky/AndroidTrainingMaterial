package com.alexeyyuditsky.test.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.databinding.ItemEmployeeBinding
import com.alexeyyuditsky.test.model.employees.Employee
import com.alexeyyuditsky.test.model.employees.EmployeeListItem
import com.bumptech.glide.Glide

class EmployeesAdapter(private val listener: Listener) :
    PagingDataAdapter<EmployeeListItem, EmployeesAdapter.Holder>(UsersDiffCallback()), View.OnClickListener {

    override fun onClick(v: View) {
        val employee = v.tag as EmployeeListItem
        if (v.id == R.id.deleteImageView) listener.onEmployeeDelete(employee)
        if (v.id == R.id.favoriteImageView) listener.onEmployeeFavorite(employee)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemEmployeeBinding.inflate(inflater, parent, false)
        binding.favoriteImageView.setOnClickListener(this)
        binding.deleteImageView.setOnClickListener(this)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val employee = getItem(position) ?: return
        holder.binding.apply {
            loadUserPhoto(photoImageView, employee.image)
            setIsFavorite(favoriteImageView, employee.isFavorite)

            nameTextView.text = employee.name
            nationTextView.text = employee.nation
            emailTextView.text = employee.email

            progressBar.isVisible = employee.inProgress
            favoriteImageView.isInvisible = employee.inProgress
            deleteImageView.isInvisible = employee.inProgress

            deleteImageView.tag = employee
            favoriteImageView.tag = employee
        }
    }

    private fun setIsFavorite(starImageView: ImageView, isFavorite: Boolean) {
        val context = starImageView.context
        if (isFavorite) {
            starImageView.setImageResource(R.drawable.ic_star)
            starImageView.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.active))
        } else {
            starImageView.setImageResource(R.drawable.ic_star_outline)
            starImageView.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.inactive))
        }
    }

    private fun loadUserPhoto(imageView: ImageView, url: String) {
        val context = imageView.context
        if (url.isNotBlank()) {
            Glide.with(context)
                .load(url)
                .circleCrop()
                .placeholder(R.drawable.ic_person)
                .error(R.drawable.ic_person)
                .into(imageView)
        } else {
            Glide.with(context)
                .load(R.drawable.ic_person)
                .into(imageView)
        }
    }

    class Holder(val binding: ItemEmployeeBinding) : RecyclerView.ViewHolder(binding.root)

    interface Listener {
        fun onEmployeeDelete(employeeListItem: EmployeeListItem)
        fun onEmployeeFavorite(employeeListItem: EmployeeListItem)
    }

}

class UsersDiffCallback : DiffUtil.ItemCallback<EmployeeListItem>() {
    override fun areItemsTheSame(oldItem: EmployeeListItem, newItem: EmployeeListItem) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: EmployeeListItem, newItem: EmployeeListItem) = oldItem == newItem
}