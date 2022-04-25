package com.alexeyyuditsky.test.screens.main.tabs.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.Repositories
import com.alexeyyuditsky.test.databinding.FragmentBoxBinding
import com.alexeyyuditsky.test.utils.viewModelCreator
import com.alexeyyuditsky.test.views.DashboardItemView

class BoxFragment : Fragment(R.layout.fragment_box) {

    private lateinit var binding: FragmentBoxBinding

    private val viewModel by viewModelCreator { BoxViewModel(getBoxId()) }

    private val args by navArgs<BoxFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBoxBinding.bind(view)

        binding.goBackButton.setOnClickListener { onGoBackButtonPressed() }

        binding.root.setBackgroundColor(DashboardItemView.getBackgroundColor(getColorValue()))
        binding.boxTextView.text = getString(R.string.this_is_box, getColorName())

        observeShouldExitEvent()
    }

    private fun observeShouldExitEvent() = viewModel.shouldExitEvent.observe(viewLifecycleOwner) {
        it.get()?.let { shouldExit ->
            if (shouldExit) findNavController().popBackStack()
        }
    }

    private fun onGoBackButtonPressed() {
        findNavController().popBackStack()
    }

    private fun getBoxId(): Long = args.boxId

    private fun getColorName(): String = args.colorName

    private fun getColorValue(): Int = args.colorValue

}