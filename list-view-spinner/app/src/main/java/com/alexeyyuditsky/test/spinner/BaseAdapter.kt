package com.alexeyyuditsky.test.spinner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.alexeyyuditsky.test.databinding.ItemCharacterBinding

interface OnPressedViewListener {
    fun onDeletePressed(character: Character)
    fun onNamePressed(name: String)
}

class CharacterAdapter(
    private val characters: List<Character>,
    private val onPressedViewListener: OnPressedViewListener
) : BaseAdapter() {
    override fun getCount() = characters.size
    override fun getItem(position: Int) = characters[position]
    override fun getItemId(position: Int) = characters[position].id

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getDefaultView(position, convertView, parent, isDropDownView = false)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getDefaultView(position, convertView, parent, isDropDownView = true)
    }

    private fun getDefaultView(
        position: Int,
        convertView: View?,
        parent: ViewGroup,
        isDropDownView: Boolean
    ): View {
        val binding =
            convertView?.tag as ItemCharacterBinding? ?: createBinding(parent.context)

        val character = characters[position]

        binding.titleTextView.text = character.name
        binding.deleteImageView.tag = character
        binding.infoImageView.tag = character
        binding.deleteImageView.visibility =
            if (character.isCustom && isDropDownView) View.VISIBLE else View.GONE
        binding.infoImageView.visibility =
            if (character.isCustom && isDropDownView) View.VISIBLE else View.GONE

        return binding.root
    }

    private fun createBinding(context: Context): ItemCharacterBinding {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(context))
        binding.root.tag = binding
        binding.deleteImageView.setOnClickListener {
            val character = it.tag as Character
            onPressedViewListener.onDeletePressed(character)
        }
        binding.infoImageView.setOnClickListener {
            val character = it.tag as Character
            onPressedViewListener.onNamePressed(character.name)
        }
        return binding
    }

}