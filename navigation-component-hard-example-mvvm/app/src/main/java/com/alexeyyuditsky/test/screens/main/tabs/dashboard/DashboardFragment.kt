package com.alexeyyuditsky.test.screens.main.tabs.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.Repositories
import com.alexeyyuditsky.test.databinding.FragmentDashboardBinding
import com.alexeyyuditsky.test.utils.viewModelCreator

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private val viewModel by viewModelCreator { DashboardViewModel(Repositories.boxesRepository) }

    private lateinit var binding: FragmentDashboardBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDashboardBinding.bind(view)

        clearBoxViews()

        viewModel.boxes.observe(viewLifecycleOwner) { renderBoxes() }
    }

    private fun renderBoxes() {

    }

    private fun clearBoxViews() {
        binding.boxesContainer.removeViews(1, binding.root.childCount - 2)
    }

}