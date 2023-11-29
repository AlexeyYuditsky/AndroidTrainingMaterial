package com.alexeyyuditsky.recyclerviewtypesoop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.recyclerviewtypesoop.databinding.TypeALayoutBinding
import com.alexeyyuditsky.recyclerviewtypesoop.databinding.TypeBLayoutBinding

class DifferentViewTypesAdapter : RecyclerView.Adapter<DifferentViewTypeViewHolder>() {

    private val list: MutableList<DVTObject> = ArrayList()

    override fun getItemViewType(position: Int): Int = list[position].type().ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DifferentViewTypeViewHolder =
        when (viewType) {
            DVTType.TYPE_A.ordinal -> DifferentViewTypeViewHolder.TypeA(
                TypeALayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            DVTType.TYPE_B.ordinal -> DifferentViewTypeViewHolder.TypeB(
                TypeBLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            else -> throw IllegalStateException("unknown viewType $viewType")
        }

    override fun onBindViewHolder(holder: DifferentViewTypeViewHolder, position: Int) =
        holder.bind(list[position])

    override fun getItemCount(): Int = list.size

    fun update(list: List<DVTObject>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}

abstract class DifferentViewTypeViewHolder(view: View) : RecyclerView.ViewHolder(view), DVTUi {

    abstract fun bind(dvtObject: DVTObject)

    class TypeA(private val binding: TypeALayoutBinding) :
        DifferentViewTypeViewHolder(binding.root) {

        override fun bind(dvtObject: DVTObject) = dvtObject.map(this)

        override fun mapTypeA(
            title: String
        ) = with(binding) {
            typeATextView.text = title
        }
    }

    class TypeB(private val binding: TypeBLayoutBinding) :
        DifferentViewTypeViewHolder(binding.root) {

        override fun bind(dvtObject: DVTObject) = dvtObject.map(this)

        override fun mapTypeB(
            checked: Boolean,
            url: String
        ) = with(binding) {
            typeBTextView.text = url
            typeBCheckBox.isChecked = checked
        }
    }
}