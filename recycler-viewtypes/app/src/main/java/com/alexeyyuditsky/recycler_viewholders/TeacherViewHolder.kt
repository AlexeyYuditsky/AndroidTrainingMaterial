package com.alexeyyuditsky.recycler_viewholders

import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.recycler_viewholders.databinding.TeacherItemViewBinding

class TeacherViewHolder(
    private val binding: TeacherItemViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(teacher: Person.Teacher) = with(binding) {
        nameTextView.text = teacher.name
        ageTextView.text = teacher.age.toString()
        subjectTextView.text = teacher.subject
    }

}