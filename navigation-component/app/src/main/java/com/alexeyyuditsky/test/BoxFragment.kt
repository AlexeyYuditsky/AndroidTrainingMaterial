package com.alexeyyuditsky.test

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alexeyyuditsky.test.databinding.FragmentBoxBinding
import kotlin.random.Random

class BoxFragment : Fragment(R.layout.fragment_box) {

    private val args: BoxFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentBoxBinding.bind(view).apply {
            root.setBackgroundColor(args.color)
            goBackButton.setOnClickListener {
                findNavController().popBackStack()
            }
            openSecret.setOnClickListener {
                findNavController().navigate(BoxFragmentDirections.actionBoxFragmentToSecretFragment())
            }
            generateNumber.setOnClickListener {
                publishResults(EXTRA_RANDOM_NUMBER, Random.nextInt(100))
                findNavController().popBackStack()
            }
        }
    }

    companion object {
        const val EXTRA_RANDOM_NUMBER = "EXTRA_RANDOM_NUMBER"
    }

}