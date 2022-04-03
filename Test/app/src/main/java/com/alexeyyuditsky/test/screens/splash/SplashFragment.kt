package com.alexeyyuditsky.test.screens.splash

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.Repositories
import com.alexeyyuditsky.test.databinding.FragmentSplashBinding
import com.alexeyyuditsky.test.utils.viewModelCreator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private lateinit var binding: FragmentSplashBinding

    private val viewModel by viewModelCreator { SplashViewModel(Repositories.accountsRepository) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSplashBinding.bind(view)

        renderAnimations()

        lifecycleScope.launch {
            delay(2000)
            findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
        }
    }

    private fun renderAnimations() {
        binding.progressBar.alpha = 0f
        binding.progressBar.animate()
            .alpha(0.7f)
            .setDuration(1000)
            .start()

        binding.progressBar.alpha = 0f
        binding.progressBar.animate()
            .alpha(1f)
            .setStartDelay(500)
            .setDuration(1000)
            .start()
    }

}