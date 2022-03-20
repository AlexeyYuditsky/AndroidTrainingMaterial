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

    private lateinit var binding: FragmentRootBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRootBinding.bind(view)

        binding.openGreenBoxButton.setOnClickListener {
            openBox(Color.rgb(100, 255, 100))
        }
        binding.openYellowBoxButton.setOnClickListener {
            openBox(Color.rgb(255, 255, 100))
        }

        parentFragmentManager.setFragmentResultListener(
            BoxFragment.REQUEST_CODE,
            viewLifecycleOwner
        ) { _, result ->
            val number = result.getInt(BoxFragment.EXTRA_RANDOM_NUMBER)
            Toast.makeText(
                requireContext(),
                getString(R.string.generated_number, number),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun openBox(color: Int) {
        findNavController().navigate(
            R.id.action_rootFragment_to_boxFragment,
            bundleOf(BoxFragment.ARG_COLOR to color),
            navOptions {
                anim {
                    enter = R.anim.enter
                    exit = R.anim.exit
                    popEnter = R.anim.pop_enter
                    popExit = R.anim.pop_exit
                }
            }
        )
    }

}