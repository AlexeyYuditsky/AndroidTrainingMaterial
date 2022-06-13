package com.alexeyyuditsky.test.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.databinding.ItemEmployeeBinding
import com.alexeyyuditsky.test.model.employees.entities.Employee
import com.bumptech.glide.Glide

class EmployeesAdapter : PagingDataAdapter<Employee, EmployeesAdapter.Holder>(UsersDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemEmployeeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val employee = getItem(position) ?: return
        holder.binding.apply {
            loadUserPhoto(photoImageView, employee.image)
            nameTextView.text = employee.name
            nationTextView.text = employee.nation
            emailTextView.text = employee.email
            ageTextView.text = employee.age.toString()
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

}

class UsersDiffCallback : DiffUtil.ItemCallback<Employee>() {

    override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean = oldItem == newItem

}