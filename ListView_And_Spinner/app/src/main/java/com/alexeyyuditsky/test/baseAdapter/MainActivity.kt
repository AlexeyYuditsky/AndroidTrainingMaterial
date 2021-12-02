package com.alexeyyuditsky.test.baseAdapter

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.alexeyyuditsky.test.databinding.ActivityMainBinding
import com.alexeyyuditsky.test.databinding.DialogAddCharacterBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CharacterAdapter
    private val data = mutableListOf(
        Character(id = 1, name = "Reptile"),
        Character(id = 2, name = "Subzero"),
        Character(id = 3, name = "Scorpion"),
        Character(id = 4, name = "Raiden"),
        Character(id = 5, name = "Smoke")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        setupList()
        binding.addButton.setOnClickListener { onAddPressed() }
    }

    private fun setupList() {
        adapter = CharacterAdapter(data) { deleteCharacter(it) }
        binding.listView.adapter = adapter

        binding.listView.setOnItemClickListener { _, _, position, _ ->
            showCharacterInfo(adapter.getItem(position))
        }
    }

    private fun showCharacterInfo(character: Character) {
        AlertDialog.Builder(this)
            .setTitle("Информация по персонажу")
            .setMessage("Персонаж: $character\nId: ${character.id}")
            .setPositiveButton("Ok", null)
            .create()
            .show()
    }

    private fun deleteCharacter(character: Character) {
        val listener = DialogInterface.OnClickListener { _, which ->
            if (which == DialogInterface.BUTTON_POSITIVE) {
                data.remove(character)
                adapter.notifyDataSetChanged()
            }
        }
        AlertDialog.Builder(this)
            .setTitle("Вы действительно хотите удалить $character?")
            .setPositiveButton("Yes", listener)
            .setNegativeButton("No", listener)
            .create()
            .show()
    }

    private fun onAddPressed() {
        val dialogBinding = DialogAddCharacterBinding.inflate(layoutInflater)
        AlertDialog.Builder(this)
            .setTitle("Create character")
            .setView(dialogBinding.root)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Add") { _, _ ->
                val name = dialogBinding.characterNameEditText.text.toString()
                if (name.isNotBlank()) {
                    data.add(createCharacter(name))
                    adapter.notifyDataSetChanged()
                }
            }
            .create()
            .show()
    }

    private fun createCharacter(name: String): Character {
        return Character(id = Random.nextLong(), name = name, true)
    }
}
