package com.alexeyyuditsky.test

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.annotation.ColorInt
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alexeyyuditsky.test.databinding.FragmentSecondABinding
import kotlin.random.Random

class SecondAFragment : Fragment(R.layout.fragment_second_a) {

    private lateinit var binding: FragmentSecondABinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSecondABinding.bind(view)

        binding.goBackScreenButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.sendMessageNextButton.setOnClickListener {
            val color = Color.rgb(Random.nextInt(250), Random.nextInt(250), Random.nextInt(250))
            findNavController().navigate(
                R.id.action_secondAFragment_to_thirdFragment, bundleOf(
                    ThirdFragment.ARG_COLOR to color,
                    ThirdFragment.ARG_TEXT_VIEW to R.string.third_screen,
                    ThirdFragment.ARG_TEXT_TOAST to R.string.second_a_screen,
                )
            )
        }
        binding.sendMessageBackButton.setOnClickListener {
            parentFragmentManager.setFragmentResult(
                MainFragment.REQUEST_KEY, bundleOf(
                    MainFragment.EXTRA_TEXT to R.string.second_a_screen
                )
            )
            findNavController().popBackStack()
        }

        val backgroundColor = requireArguments().getInt(ARG_COLOR)
        val text = requireArguments().getInt(ARG_TEXT)

        setTextColor(backgroundColor)

        binding.nameScreenTextView.text = getString(text)
        binding.root.children.forEach { if (it is Button) it.setBackgroundColor(backgroundColor) }
    }

    private fun setTextColor(@ColorInt backgroundColor: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val color = if (Color.luminance(backgroundColor) > 0.3) Color.BLACK else Color.WHITE
            binding.root.children.forEach { if (it is Button) it.setTextColor(color) }
        }
    }

    companion object {
        const val ARG_COLOR = "ARG_COLOR"
        const val ARG_TEXT = "ARG_TEXT"
    }

}