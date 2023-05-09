package com.alexeyyuditsky.recycler_viewholders

import androidx.recyclerview.widget.DiffUtil

object PersonDiffCallback : DiffUtil.ItemCallback<Person>() {
    override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
        return ((oldItem is Person.Student) && (newItem is Person.Student) && oldItem.name == newItem.name) ||
                ((oldItem is Person.Teacher) && (newItem is Person.Teacher) && oldItem.name == newItem.name)
    }

    override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem == newItem
    }
}