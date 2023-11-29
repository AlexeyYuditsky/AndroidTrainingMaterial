package com.alexeyyuditsky.recyclerviewtypesoop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alexeyyuditsky.recyclerviewtypesoop.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /*val list = listOf(
            DVTObject.TypeA("Type A title"),
            DVTObject.TypeB(true, "Type B url"),
            DVTObject.TypeB(false, "Type B url 2"),
            DVTObject.TypeA("Type A title 2"),
        )

        val adapter = DifferentViewTypesAdapter()
        adapter.update(list)
        binding.recyclerView.adapter = adapter*/

        val list = listOf(
            ExampleObject.TypeA("type a title 1"),
            ExampleObject.TypeB("type b title 1", true),
            ExampleObject.TypeB("type b title 2", false),
            ExampleObject.TypeA("type a title 2")
        )

        val adapter = DifferentTypesAdapter()
        adapter.update(list)
        binding.recyclerView.adapter = adapter
    }
}