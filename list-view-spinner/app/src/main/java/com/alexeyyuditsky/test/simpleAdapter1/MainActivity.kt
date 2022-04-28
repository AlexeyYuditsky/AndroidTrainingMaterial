package com.alexeyyuditsky.test.simpleAdapter1

import android.os.Bundle
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.databinding.ActivityMainWithSimpleAdapter1Binding

private const val KEY_TITLE = "title"
private const val KEY_DESCRIPTION = "description"
private const val KEY_BOOLEAN = "boolean"

class MainActivityWithSimpleAdapter1 : AppCompatActivity() {
    private lateinit var binding: ActivityMainWithSimpleAdapter1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainWithSimpleAdapter1Binding.inflate(layoutInflater)
            .also { setContentView(it.root) }

        setupListViewWithSimpleGeneratedData()
    }

    private fun setupListViewWithSimpleGeneratedData() {
        val data = (1..100).map {
            mapOf(
                KEY_TITLE to "item #$it",
                KEY_DESCRIPTION to "description #$it",
                KEY_BOOLEAN to (it % 10 >= 5),
            )
        }

        val adapter = SimpleAdapter(
            this,
            data,
            R.layout.item_custom,
            arrayOf(KEY_TITLE, KEY_DESCRIPTION, KEY_BOOLEAN),
            intArrayOf(R.id.titleTextView, R.id.descriptionTextView, R.id.checkBox)
        )

        binding.listView.adapter = adapter
    }

}