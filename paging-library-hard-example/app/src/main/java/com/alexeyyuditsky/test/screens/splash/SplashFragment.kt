package com.alexeyyuditsky.test.screens.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.Repositories
import com.alexeyyuditsky.test.utils.viewModelCreator

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel by viewModelCreator { SplashViewModel(Repositories.employeesRepository) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNavigateToMainEvent()
    }

    private fun observeNavigateToMainEvent() {
        viewModel.navigateToMainEvent.observe(viewLifecycleOwner) {
            it.get()?.let {
                findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
            }
        }
    }

}