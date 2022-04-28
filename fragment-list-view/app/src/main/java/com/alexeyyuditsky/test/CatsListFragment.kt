package com.alexeyyuditsky.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.alexeyyuditsky.test.contract.contract
import com.alexeyyuditsky.test.databinding.FragmentCatsListBinding
import com.alexeyyuditsky.test.model.Cat

class CatsListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentCatsListBinding.inflate(inflater, container, false).apply {

            val cats = contract().catsService.cats
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, cats)
            catsListView.adapter = adapter
            catsListView.setOnItemClickListener { _, _, position, _ ->
                val currentCat = adapter.getItem(position)!!
                contract().launchCatDetailsScreen(currentCat)
            }

        }.root
    }

}