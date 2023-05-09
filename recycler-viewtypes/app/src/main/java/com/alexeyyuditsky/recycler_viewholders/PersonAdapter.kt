package com.alexeyyuditsky.recycler_viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alexeyyuditsky.recycler_viewholders.databinding.StudentItemViewBinding
import com.alexeyyuditsky.recycler_viewholders.databinding.TeacherItemViewBinding

class PersonAdapter : RecyclerView.Adapter<ViewHolder>() {

    val differ = AsyncListDiffer(this, PersonDiffCallback)

    override fun getItemViewType(position: Int): Int {
        return when (differ.currentList[position]) {
            is Person.Student -> R.layout.student_item_view
            is Person.Teacher -> R.layout.teacher_item_view
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == R.layout.student_item_view) {
            val binding = StudentItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            StudentViewHolder(binding)
        } else {
            val binding = TeacherItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            TeacherViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = differ.currentList[position] ?: return
        when (person) {
            is Person.Student -> (holder as StudentViewHolder).bind(person)
            is Person.Teacher -> (holder as TeacherViewHolder).bind(person)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

}