package com.alexeyyuditsky.test.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alexeyyuditsky.test.MainActivity
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.contract.navigator
import com.alexeyyuditsky.test.databinding.FragmentMenuBinding
import com.alexeyyuditsky.test.model.Options

class MenuFragment : Fragment(R.layout.fragment_menu) {

    private lateinit var options: Options

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        options = savedInstanceState?.getParcelable(KEY_OPTIONS) ?: Options.DEFAULT
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentMenuBinding.bind(view).apply {
            openBoxButton.setOnClickListener { onOpenBoxPressed() }
            optionsButton.setOnClickListener { onOptionsPressed() }
            aboutButton.setOnClickListener { onAboutPressed() }
            exitButton.setOnClickListener { onExitPressed() }
        }

        navigator().listenResult<Options>(KEY_RESULT, viewLifecycleOwner) {
            this.options = it
        }
    }

    private fun onOpenBoxPressed() {
        navigator().showBoxSelectionScreen(options)
    }

    private fun onOptionsPressed() {
        navigator().showOptionsScreen(options)
    }

    private fun onAboutPressed() {
        navigator().showAboutScreen()
    }

    private fun onExitPressed() {
        navigator().goBack()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_OPTIONS, options)
    }

    companion object {
        private const val KEY_OPTIONS = "KEY_OPTIONS"
        const val KEY_RESULT = "KEY_RESULT"
    }

}