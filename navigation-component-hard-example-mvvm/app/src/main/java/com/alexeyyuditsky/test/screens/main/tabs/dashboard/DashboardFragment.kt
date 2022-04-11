package com.alexeyyuditsky.test.screens.main.tabs.dashboard

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.Repositories
import com.alexeyyuditsky.test.databinding.FragmentDashboardBinding
import com.alexeyyuditsky.test.model.boxes.entities.Box
import com.alexeyyuditsky.test.utils.viewModelCreator
import com.alexeyyuditsky.test.views.DashboardItemView

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private val viewModel by viewModelCreator { DashboardViewModel(Repositories.boxesRepository) }

    private lateinit var binding: FragmentDashboardBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDashboardBinding.bind(view)

        clearBoxViews()

        viewModel.boxes.observe(viewLifecycleOwner) { renderBoxes(it) }
    }

    private fun renderBoxes(boxes: List<Box>) {
        clearBoxViews()
        if (boxes.isEmpty()) {
            binding.boxesContainer.isVisible = false
            binding.noBoxesTextView.isVisible = true
        } else {
            binding.noBoxesTextView.isVisible = false
            binding.boxesContainer.isVisible = true
            createBoxes(boxes)
        }
    }

    private fun createBoxes(boxes: List<Box>) {
        val width = resources.getDimensionPixelSize(R.dimen.dashboard_item_width)
        val height = resources.getDimensionPixelSize(R.dimen.dashboard_item_height)
        val generatedIdentifiers = boxes.map { box ->
            val dashboardItemView = DashboardItemView(requireContext())
            dashboardItemView.setBox(box)
            dashboardItemView.id = box.id
            dashboardItemView.tag = box
            dashboardItemView.setOnClickListener(boxClickListener)
            val params = ConstraintLayout.LayoutParams(width, height)
            binding.boxesContainer.addView(dashboardItemView, params)
            return@map box.id
        }.toIntArray()
        binding.flowView.referencedIds = generatedIdentifiers
    }

    private fun clearBoxViews() {
        binding.boxesContainer.removeViews(1, binding.root.childCount - 2)
    }

    private val boxClickListener = View.OnClickListener {
        val box = it.tag as Box
        val direction = DashboardFragmentDirections.actionDashboardFragmentToBoxFragment(
            box.id,
            getString(box.colorNameRes),
            box.colorValue
        )
        findNavController().navigate(direction)
    }

}