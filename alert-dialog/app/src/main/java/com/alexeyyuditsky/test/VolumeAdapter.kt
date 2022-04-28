package com.alexeyyuditsky.test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.alexeyyuditsky.test.databinding.ItemVolumeSingleChoiceBinding

class VolumeAdapter(private val values: List<Int>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val context = parent.context
        val binding = convertView?.tag as ItemVolumeSingleChoiceBinding?
            ?: ItemVolumeSingleChoiceBinding.inflate(LayoutInflater.from(context))
                .also { it.root.tag = it }

        val volume = getItem(position)

        binding.volumeValueTextView.text = context.getString(R.string.volume_description, volume)
        binding.volumeValueProgressBar.progress = volume

        return binding.root
    }

    override fun hasStableIds(): Boolean = true

    override fun getItem(position: Int): Int = values[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = values.size

}