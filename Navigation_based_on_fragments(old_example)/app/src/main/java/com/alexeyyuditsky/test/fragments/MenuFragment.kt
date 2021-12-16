package com.alexeyyuditsky.test.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.contract.navigator
import com.alexeyyuditsky.test.databinding.FragmentMenuBinding
import com.alexeyyuditsky.test.model.Options

class MenuFragment : Fragment() {
    private lateinit var options: Options

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        options = savedInstanceState?.getParcelable(KEY_OPTIONS) ?: Options.DEFAULT
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMenuBinding.inflate(inflater, container, false)

        navigator().listenResult(Options::class.java, viewLifecycleOwner) { this.options = it }

        binding.openBoxButton.setOnClickListener { onOpenBoxPressed() }
        binding.optionsButton.setOnClickListener { onOptionsPressed() }
        binding.aboutButton.setOnClickListener { onAboutPressed() }
        binding.exitButton.setOnClickListener { onExitPressed() }

        return binding.root
    }

    private fun onOpenBoxPressed(): Unit = navigator().showBoxSelectionScreen(options)
    private fun onOptionsPressed(): Unit = navigator().showOptionsScreen(options)
    private fun onAboutPressed(): Unit = navigator().showAboutScreen()
    private fun onExitPressed(): Unit = navigator().goBack()

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_OPTIONS, options)
    }

    companion object {
        private const val KEY_OPTIONS = "KEY_OPTIONS"
    }

}