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
import androidx.navigation.fragment.navArgs
import com.alexeyyuditsky.test.databinding.FragmentThirdBinding

class ThirdFragment : Fragment(R.layout.fragment_third) {

    private lateinit var binding: FragmentThirdBinding

    private val args: ThirdFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentThirdBinding.bind(view)

        binding.goBackScreenButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.goMainScreenButton.setOnClickListener {
            findNavController().popBackStack(R.id.mainFragment, false)
        }

        setTextColor(args.color)

        binding.screenNameTextView.text = args.screenName
        binding.root.children.forEach { if (it is Button) it.setBackgroundColor(args.color) }

        Toast.makeText(
            requireContext(),
            getString(R.string.opened_screen, args.screenNameForToast),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun setTextColor(@ColorInt backgroundColor: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val color = if (Color.luminance(backgroundColor) > 0.3) Color.BLACK else Color.WHITE
            binding.root.children.forEach { if (it is Button) it.setTextColor(color) }
        }
    }

}