package com.alexeyyuditsky.test

import android.graphics.Color
import android.os.Bundle
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

        binding.openSecondScreenButton.setOnClickListener {
            openSecondScreen()
        }

        resultListener<String>(EXTRA_TEXT) { text ->
            Toast.makeText(
                requireContext(),
                getString(R.string.opened_screen, text),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun openSecondScreen() {
        val color = Color.rgb(Random.nextInt(250), Random.nextInt(250), Random.nextInt(250))
        val screenName = getString(R.string.second_screen)
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToSecondFragment(color, screenName)
        )
    }

    companion object {
        const val EXTRA_TEXT = "EXTRA_TEXT"
    }

}