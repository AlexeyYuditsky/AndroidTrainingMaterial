package com.alexeyyuditsky.recycler_viewholders

import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.recycler_viewholders.databinding.StudentItemViewBinding

class StudentViewHolder(
    private val binding: StudentItemViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(student: Person.Student) = with(binding) {
        nameTextView.text = student.name
        ageTextView.text = student.age.toString()
    }

}