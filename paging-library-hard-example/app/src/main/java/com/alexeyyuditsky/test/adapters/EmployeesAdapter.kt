package com.alexeyyuditsky.test.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.databinding.ItemEmployeeBinding
import com.alexeyyuditsky.test.model.employees.Employee
import com.bumptech.glide.Glide

class EmployeesAdapter(
    private val listener: Listener
) : PagingDataAdapter<Employee, EmployeesAdapter.Holder>(UsersDiffCallback()), View.OnClickListener {

    override fun onClick(v: View) {
        val employee = v.tag as Employee
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

            nameTextView.text = employee.name
            nationTextView.text = employee.nation
            emailTextView.text = employee.email

            deleteImageView.tag = employee
            favoriteImageView.tag = employee
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

        fun onEmployeeDelete(employee: Employee)

        fun onEmployeeFavorite(employee: Employee)

    }

}

class UsersDiffCallback : DiffUtil.ItemCallback<Employee>() {
    override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean = oldItem == newItem
}