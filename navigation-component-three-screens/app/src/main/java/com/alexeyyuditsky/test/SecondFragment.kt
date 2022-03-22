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
import androidx.navigation.fragment.navArgs
import com.alexeyyuditsky.test.databinding.FragmentSecondBinding
import kotlin.random.Random

class SecondFragment : Fragment(R.layout.fragment_second) {

    private lateinit var binding: FragmentSecondBinding

    private val args: SecondFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSecondBinding.bind(view).apply {

            goBackScreenButton.setOnClickListener {
                findNavController().popBackStack()
            }
            sendMessageNextButton.setOnClickListener {
                openThirdScreen()
            }
            sendMessageBackButton.setOnClickListener {
                publishResult(MainFragment.EXTRA_TEXT, args.screenName)
                findNavController().popBackStack()
            }
        }

        setTextColor(args.color)
        binding.nameScreenTextView.text = args.screenName
        binding.root.children.forEach { if (it is Button) it.setBackgroundColor(args.color) }
    }

    private fun openThirdScreen() {
        val color = Color.rgb(Random.nextInt(250), Random.nextInt(250), Random.nextInt(250))
        findNavController().navigate(
            SecondFragmentDirections.actionSecondFragmentToThirdFragment(
                color,
                getString(R.string.third_screen),
                getString(R.string.second_screen)
            )
        )
    }

    private fun setTextColor(@ColorInt backgroundColor: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val color = if (Color.luminance(backgroundColor) > 0.3) Color.BLACK else Color.WHITE
            binding.root.children.forEach { if (it is Button) it.setTextColor(color) }
        }
    }

}