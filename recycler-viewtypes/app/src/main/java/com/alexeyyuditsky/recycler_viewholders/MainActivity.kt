package com.alexeyyuditsky.recycler_viewholders

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import com.alexeyyuditsky.recycler_viewholders.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bindList()
    }

    private fun ActivityMainBinding.bindList() {
        val adapter = PersonAdapter()

        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(root.context, DividerItemDecoration.VERTICAL))

        adapter.differ.submitList(personsList)
    }

    private companion object {
        val personsList = listOf(
            Person.Student("Alexey", 25),
            Person.Student("Ivan", 22),
            Person.Teacher("Ilia", 35, "mathematica"),
            Person.Teacher("Olga", 65, "biology"),
            Person.Student("Oleg", 18),
            Person.Teacher("Tamara", 55, "russian language"),
            Person.Teacher("Anastas", 42, "english language"),
            Person.Student("Zina", 17)
        )
    }

}