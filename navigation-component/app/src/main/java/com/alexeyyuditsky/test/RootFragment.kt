package com.alexeyyuditsky.test

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.alexeyyuditsky.test.databinding.FragmentRootBinding

class RootFragment : Fragment(R.layout.fragment_root) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentRootBinding.bind(view).apply {
            openGreenBoxButton.setOnClickListener {
                openBox(Color.rgb(100, 255, 100), getString(R.string.green))
            }
            openYellowBoxButton.setOnClickListener {
                openBox(Color.rgb(255, 255, 100), getString(R.string.yellow))
            }
        }

        listenResult<Int>(BoxFragment.EXTRA_RANDOM_NUMBER) {
            Toast.makeText(
                requireContext(),
                getString(R.string.generated_number, it),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun openBox(color: Int, colorName: String) {
        val direction = RootFragmentDirections.actionRootFragmentToBoxFragment(color, colorName)
        findNavController().navigate(direction)
    }

}