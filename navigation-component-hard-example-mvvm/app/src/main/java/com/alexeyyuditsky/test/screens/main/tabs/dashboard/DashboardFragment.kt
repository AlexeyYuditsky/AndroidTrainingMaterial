package com.alexeyyuditsky.test.screens.main.tabs.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private lateinit var binding: FragmentDashboardBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDashboardBinding.bind(view)

        binding.box1.setOnClickListener { onBox1Pressed() }
        binding.box2.setOnClickListener { onBox2Pressed() }
        binding.box3.setOnClickListener { onBox3Pressed() }
    }

    private fun onBox1Pressed() {
        val arg = "Purple"
        val direction = DashboardFragmentDirections.actionDashboardFragmentToBoxFragment(arg)
        findNavController().navigate(direction)
    }

    private fun onBox2Pressed() {
        val arg = "Black"
        val direction = DashboardFragmentDirections.actionDashboardFragmentToBoxFragment(arg)
        findNavController().navigate(direction)
    }

    private fun onBox3Pressed() {
        val arg = "Blue"
        val direction = DashboardFragmentDirections.actionDashboardFragmentToBoxFragment(arg)
        findNavController().navigate(direction)
    }

}