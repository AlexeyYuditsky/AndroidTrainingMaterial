package com.alexeyyuditsky.simplemvvm.view.currentcolor

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexeyyuditsky.simplemvvm.databinding.FragmentCurrentColorBinding
import com.alexeyyuditsky.foundation.views.BaseFragment
import com.alexeyyuditsky.foundation.views.BaseScreen
import com.alexeyyuditsky.foundation.views.screenViewModel
import com.alexeyyuditsky.simplemvvm.view.onTryAgain
import com.alexeyyuditsky.simplemvvm.view.renderSimpleResult

class CurrentColorFragment : BaseFragment() {

    // no arguments for this screen
    class Screen : BaseScreen()

    override val viewModel by screenViewModel<CurrentColorViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentCurrentColorBinding.inflate(inflater, container, false)
        viewModel.currentColor.observe(viewLifecycleOwner) { result ->
            renderSimpleResult(
                root = binding.root,
                result = result,
                onSuccess = { binding.colorView.setBackgroundColor(it.value) }
            )
        }

        binding.changeColorButton.setOnClickListener {
            viewModel.changeColor()
        }

        onTryAgain(binding.root) { viewModel.onTryAgain() }

        return binding.root
    }

}