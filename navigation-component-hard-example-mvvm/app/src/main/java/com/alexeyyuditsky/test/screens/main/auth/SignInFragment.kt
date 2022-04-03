package com.alexeyyuditsky.test.screens.main.auth

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.Repositories
import com.alexeyyuditsky.test.databinding.FragmentSignInBinding
import com.alexeyyuditsky.test.utils.viewModelCreator

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private lateinit var binding: FragmentSignInBinding

    private val viewModel by viewModelCreator { SignInViewModel(Repositories.accountsRepository) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignInBinding.bind(view)
        binding.signInButton.setOnClickListener { onSignInButtonPressed() }
        binding.createAccountButton.setOnClickListener { onCreateAccountButtonPressed() }

        observeState()
    }

    private fun onSignInButtonPressed() {
        viewModel.signIn(
            email = binding.emailEditText.text.toString(),
            password = binding.passwordEditText.text.toString()
        )
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) {
        binding.emailTextInput.error = if (it.emptyEmailError) getString(R.string.field_is_empty) else null
        binding.passwordTextInput.error = if (it.signInInProgress) getString(R.string.field_is_empty) else null

        binding.emailEditText.isEnabled = it.enableViews
        binding.passwordEditText.isEnabled = it.enableViews
        binding.signInButton.isEnabled = it.enableViews
        binding.createAccountButton.isEnabled = it.enableViews
        binding.progressBar.isVisible = it.showProgress
    }

    private fun onCreateAccountButtonPressed() {
        TODO("Not yet implemented")
    }

}