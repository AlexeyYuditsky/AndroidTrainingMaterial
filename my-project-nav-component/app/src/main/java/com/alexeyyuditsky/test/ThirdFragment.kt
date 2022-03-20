package com.alexeyyuditsky.test

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alexeyyuditsky.test.databinding.FragmentThirdBinding

class ThirdFragment : Fragment(R.layout.fragment_third) {

    private lateinit var binding: FragmentThirdBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentThirdBinding.bind(view)

        binding.goBackScreenButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.goMainScreenButton.setOnClickListener {
            findNavController().popBackStack(R.id.mainFragment, false)
        }

        val backgroundColor = requireArguments().getInt(ARG_COLOR)
        val text1 = getString(requireArguments().getInt(ARG_TEXT_VIEW))
        val text2 = getString(requireArguments().getInt(ARG_TEXT_TOAST))

        setTextColor(backgroundColor)

        binding.screenNameTextView.text = text1
        binding.root.children.forEach { if (it is Button) it.setBackgroundColor(backgroundColor) }

        Toast.makeText(
            requireContext(),
            getString(R.string.opened_screen, text2),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun setTextColor(@ColorInt backgroundColor: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val color = if (Color.luminance(backgroundColor) > 0.3) Color.BLACK else Color.WHITE
            binding.root.children.forEach { if (it is Button) it.setTextColor(color) }
        }
    }

    companion object {
        const val ARG_COLOR = "ARG_COLOR"
        const val ARG_TEXT_VIEW = "ARG_TEXT_VIEW"
        const val ARG_TEXT_TOAST = "ARG_TEXT_TOAST"
    }

}