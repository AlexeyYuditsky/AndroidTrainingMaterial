package com.alexeyyuditsky.room.screens.tabs.admin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexeyyuditsky.room.R
import com.alexeyyuditsky.room.Repositories
import com.alexeyyuditsky.room.databinding.FragmentAdminTreeBinding
import com.alexeyyuditsky.room.utils.resources.ContextResources
import com.alexeyyuditsky.room.utils.viewModelCreator

/**
 * Contains only RecyclerView which displays the whole tree of data from the database
 * starting from accounts and ending with box settings.
 */
class AdminFragment : Fragment(R.layout.fragment_admin_tree) {

    private lateinit var binding: FragmentAdminTreeBinding

    private val viewModel by viewModelCreator {
        AdminViewModel(Repositories.accountsRepository, ContextResources(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAdminTreeBinding.bind(view)

        val adapter = setupAdapter()
        viewModel.items.observe(viewLifecycleOwner) { treeItems ->
            adapter.renderItems(treeItems)
        }
    }

    private fun setupAdapter(): AdminItemsAdapter {
        val adapter = AdminItemsAdapter(viewModel)
        binding.adminTreeRecyclerView.adapter = adapter
        return adapter
    }

}