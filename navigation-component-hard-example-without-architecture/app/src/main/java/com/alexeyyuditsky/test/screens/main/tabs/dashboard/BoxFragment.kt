package com.alexeyyuditsky.test.screens.main.tabs.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.databinding.FragmentBoxBinding

class BoxFragment : Fragment(R.layout.fragment_box) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentBoxBinding.bind(view)

        binding.goBackButton.setOnClickListener { onGoBackPressed() }

        binding.boxTextView.text = getString(R.string.this_is_box, getColorName())
    }

    private fun onGoBackPressed() {
        findNavController().popBackStack()
    }

    private fun getColorName(): String {
        val arg by navArgs<BoxFragmentArgs>()
        return arg.colorName
    }

}