package com.alexeyyuditsky.test.baseAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.alexeyyuditsky.test.databinding.ItemCharacterBinding

typealias OnDeletePressedListener = (Character) -> Unit

class CharacterAdapter(
    private val characters: List<Character>,
    private val onDeletePressedListener: OnDeletePressedListener
) : BaseAdapter() {
    override fun getCount() = characters.size
    override fun getItem(position: Int) = characters[position]
    override fun getItemId(position: Int) = characters[position].id

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding =
            convertView?.tag as ItemCharacterBinding? ?: createBinding(parent.context)

        val character = characters[position]

        binding.titleTextView.text = character.name
        binding.deleteImageView.tag = character
        binding.infoImageView.tag = character
        binding.deleteImageView.visibility = if (character.isCustom) View.VISIBLE else View.GONE
        binding.infoImageView.visibility = View.GONE

        return binding.root
    }

    private fun createBinding(context: Context): ItemCharacterBinding {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(context))
        binding.root.tag = binding
        binding.deleteImageView.setOnClickListener {
            val character = it.tag as Character
            onDeletePressedListener(character)
        }
        return binding
    }

}