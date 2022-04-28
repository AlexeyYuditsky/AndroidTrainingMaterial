package com.alexeyyuditsky.simplemvvm.view.changecolor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.simplemvvm.databinding.ItemColorBinding
import com.alexeyyuditsky.simplemvvm.model.colors.NamedColor

/** Adapter for displaying the list of available colors
 * @param listener callback which notifies about user actions on items in the list, see [Listener] for details. */
class ColorsAdapter(
    private val listener: Listener,
) : RecyclerView.Adapter<ColorsAdapter.ViewHolder>(), View.OnClickListener {

    var items: List<NamedColorListItem> = emptyList()
        set(value) {
            if (field == value) return
            field = value
            notifyDataSetChanged()
        }

    override fun onClick(v: View) {
        val item = v.tag as NamedColor
        listener.onColorChosen(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemColorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.root.setOnClickListener(this)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val namedColor = items[position].namedColor
        val selected = items[position].selected
        viewHolder.binding.apply {
            root.tag = namedColor
            colorNameTextView.text = namedColor.name
            colorView.setBackgroundColor(namedColor.value)
            selectedIndicatorImageView.visibility = if (selected) View.VISIBLE else View.GONE
        }
    }

    override fun getItemCount(): Int = items.size

    interface Listener {
        /** Called when user chooses the specified color
         * @param namedColor color chosen by the user */
        fun onColorChosen(namedColor: NamedColor)
    }

    class ViewHolder(val binding: ItemColorBinding) : RecyclerView.ViewHolder(binding.root)

}