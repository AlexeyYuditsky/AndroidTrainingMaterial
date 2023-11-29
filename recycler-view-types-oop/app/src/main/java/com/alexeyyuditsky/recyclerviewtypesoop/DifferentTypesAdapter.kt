package com.alexeyyuditsky.recyclerviewtypesoop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.recyclerviewtypesoop.databinding.TypeALayoutBinding
import com.alexeyyuditsky.recyclerviewtypesoop.databinding.TypeBLayoutBinding

class DifferentTypesAdapter : RecyclerView.Adapter<ExampleObjectViewHolder>() {

    private val list: MutableList<ExampleObject> = ArrayList()

    override fun getItemViewType(position: Int): Int = when (list[position]) {
        is ExampleObject.TypeA -> R.layout.type_a_layout
        is ExampleObject.TypeB -> R.layout.type_b_layout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleObjectViewHolder =
        when (viewType) {
            R.layout.type_a_layout -> ExampleObjectViewHolder.TypeA(
                TypeALayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            R.layout.type_b_layout -> ExampleObjectViewHolder.TypeB(
                TypeBLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            else -> throw IllegalStateException("cannot find type")
        }

    override fun onBindViewHolder(holder: ExampleObjectViewHolder, position: Int) =
        holder.bind(list[position])

    override fun getItemCount(): Int = list.size

    fun update(list: List<ExampleObject>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}

abstract class ExampleObjectViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bind(item: ExampleObject)

    class TypeA(private val binding: TypeALayoutBinding) : ExampleObjectViewHolder(binding.root) {
        override fun bind(item: ExampleObject) = item.map(binding.typeATextView)
    }

    class TypeB(private val binding: TypeBLayoutBinding) : ExampleObjectViewHolder(binding.root) {
        override fun bind(item: ExampleObject) =
            item.map(binding.typeBCheckBox, binding.typeBTextView)
    }
}