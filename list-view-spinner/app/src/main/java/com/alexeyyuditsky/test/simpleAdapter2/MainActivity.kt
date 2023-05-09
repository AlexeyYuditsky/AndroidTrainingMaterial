package com.alexeyyuditsky.test.simpleAdapter2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AlertDialog
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.databinding.ActivityMainWithSimpleAdapter2Binding

private const val KEY_TITLE = "title"
private const val KEY_DESCRIPTION = "description"

class MainActivityWithSimpleAdapter2 : AppCompatActivity() {
    private lateinit var binding: ActivityMainWithSimpleAdapter2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainWithSimpleAdapter2Binding.inflate(layoutInflater)
            .also { setContentView(it.root) }

        setupListViewSimple()
    }

    private fun setupListViewSimple() {
        val data = listOf(
            mapOf(
                KEY_TITLE to "First title",
                KEY_DESCRIPTION to "The first some description",
            ),
            mapOf(
                KEY_TITLE to "Second title",
                KEY_DESCRIPTION to "The second some description",
            ),
            mapOf(
                KEY_TITLE to "Third title",
                KEY_DESCRIPTION to "The third some description",
            )
        )

        val adapter = SimpleAdapter(
            this,
            data,
            android.R.layout.simple_list_item_2,
            arrayOf(KEY_TITLE, KEY_DESCRIPTION),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )

        binding.listView.adapter = adapter

        binding.listView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val selectedItemTitle = data[position][KEY_TITLE]!!
                val selectedItemDescription = data[position][KEY_DESCRIPTION]!!

                AlertDialog.Builder(this)
                    .setTitle(selectedItemTitle)
                    .setMessage(getString(R.string.selected, selectedItemDescription))
                    .setPositiveButton("Ok") { _, _ -> }
                    .create()
                    .show()
            }
    }
}