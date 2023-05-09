package com.alexeyyuditsky.test.spinner

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.alexeyyuditsky.test.databinding.ActivityMainWithSpinnerBinding
import com.alexeyyuditsky.test.databinding.DialogAddCharacterBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainWithSpinnerBinding
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
        binding =
            ActivityMainWithSpinnerBinding.inflate(layoutInflater).also { setContentView(it.root) }

        setupList()
        binding.addButton.setOnClickListener { onAddPressed() }
    }

    private fun setupList() {
        adapter = CharacterAdapter(data, object : OnPressedViewListener {
            override fun onDeletePressed(character: Character) {
                deleteCharacter(character)
            }

            override fun onNamePressed(name: String) {
                showCharacterName(name)
            }
        })
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val character = data[position]
                binding.characterInfoTextView.text = "Персонаж: $character c Id: ${character.id}"
            }
        }
    }

    private fun showCharacterName(name: String) {
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show()
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
