package com.alexeyyuditsky.test

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alexeyyuditsky.test.databinding.FragmentMainBinding
import kotlin.random.Random

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        binding.openSecondAScreenButton.setOnClickListener {
            val color = Color.rgb(Random.nextInt(250), Random.nextInt(250), Random.nextInt(250))
            findNavController().navigate(
                R.id.action_mainFragment_to_secondAFragment,
                bundleOf(
                    SecondAFragment.ARG_COLOR to color,
                    SecondAFragment.ARG_TEXT to R.string.second_a_screen
                )
            )
        }
        binding.openSecondBScreenButton.setOnClickListener {
            val color = Color.rgb(Random.nextInt(250), Random.nextInt(250), Random.nextInt(250))
            findNavController().navigate(
                R.id.action_mainFragment_to_secondBFragment,
                bundleOf(
                    SecondBFragment.ARG_COLOR to color,
                    SecondBFragment.ARG_TEXT to R.string.second_b_screen
                )
            )
        }

        parentFragmentManager.setFragmentResultListener(
            REQUEST_KEY,
            viewLifecycleOwner
        ) { _, data ->
            val text = getString(data.getInt(EXTRA_TEXT))
            Toast.makeText(
                requireContext(),
                getString(R.string.opened_screen, text),
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    companion object {
        const val REQUEST_KEY = "REQUEST_KEY"
        const val EXTRA_TEXT = "EXTRA_TEXT"
    }

}