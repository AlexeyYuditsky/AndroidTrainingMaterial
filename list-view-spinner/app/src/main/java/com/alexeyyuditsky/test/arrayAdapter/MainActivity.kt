package com.alexeyyuditsky.test.arrayAdapter

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.alexeyyuditsky.test.databinding.ActivityMainBinding
import com.alexeyyuditsky.test.databinding.DialogAddCharacterBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ArrayAdapter<Character>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListWithArrayAdapter()
        binding.addButton.setOnClickListener { onAddPressed() }
    }

    private fun setupListWithArrayAdapter() {
        val data = mutableListOf(
            Character(id = UUID.randomUUID().toString(), name = "Reptile"),
            Character(id = UUID.randomUUID().toString(), name = "Subzero"),
            Character(id = UUID.randomUUID().toString(), name = "Scorpion"),
            Character(id = UUID.randomUUID().toString(), name = "Raiden"),
            Character(id = UUID.randomUUID().toString(), name = "Smoke"),
        )

        adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, data)

        binding.listView.adapter = adapter

        binding.listView.setOnItemClickListener { parent, view, position, id ->
            adapter.getItem(position)?.let {
                deleteCharacter(it)
            }
        }
    }

    private fun deleteCharacter(character: Character) {
        val listener = DialogInterface.OnClickListener { dialog, which ->
            Log.d("MyLog", "dialog = $dialog, which = $which")
            if (which == DialogInterface.BUTTON_POSITIVE) adapter.remove(character)
        }
        AlertDialog.Builder(this)
            .setTitle("Delete Character")
            .setMessage("Are you sure want to delete the character: $character ?")
            .setPositiveButton("Delete", listener)
            .setNegativeButton("Cancel", listener)
            .create()
            .show()
    }

    private fun onAddPressed() {
        val dialogBinding = DialogAddCharacterBinding.inflate(layoutInflater)
        AlertDialog.Builder(this)
            .setTitle("Create Character")
            .setView(dialogBinding.root)
            .setPositiveButton("Add") { _, _ ->
                val name = dialogBinding.characterNameEditText.text.toString()
                if (name.isNotBlank()) adapter.add(createCharacter(name))
            }
            .create()
            .show()
    }

    private fun createCharacter(name: String): Character {
        return Character(id = UUID.randomUUID().toString(), name = name)
    }

    class Character(
        private val id: String,
        private val name: String
    ) {
        override fun toString() = name
    }

}

