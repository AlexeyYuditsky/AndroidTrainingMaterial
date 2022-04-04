package com.alexeyyuditsky.test.screens.main.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
        observeClearPasswordEvent()
        observeShowAuthErrorMessageEvent()
        observeNavigateToTabsScreenEvent()
    }

    private fun observeNavigateToTabsScreenEvent() = viewModel.navigateToTabsScreenEvent.observe(viewLifecycleOwner) {
        it.get()?.let {
            findNavController().navigate(R.id.action_signInFragment_to_tabsFragment)
        }
    }

    private fun observeShowAuthErrorMessageEvent() = viewModel.showAuthErrorToastEvent.observe(viewLifecycleOwner) {
        it.get()?.let {
            Toast.makeText(requireContext(), R.string.invalid_email_or_password, Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeClearPasswordEvent() = viewModel.clearPasswordEvent.observe(viewLifecycleOwner) {
        it.get()?.let {
            binding.passwordEditText.text?.clear()
        }
    }

    private fun onSignInButtonPressed() {
        viewModel.signIn(
            email = binding.emailEditText.text.toString(),
            password = binding.passwordEditText.text.toString()
        )
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) { state ->
        binding.emailTextInput.error = if (state.emptyEmailError) getString(R.string.field_is_empty) else null
        binding.passwordTextInput.error = if (state.emptyPasswordError) getString(R.string.field_is_empty) else null

        binding.linearLayout.children.forEach { it.isEnabled = state.enableViews }

        binding.progressBar.isVisible = state.showProgress
    }

    private fun onCreateAccountButtonPressed() {
        val email = binding.emailEditText.text.toString()
        val emailArg = email.ifBlank { null }

        val direction = SignInFragmentDirections.actionSignInFragmentToSignUpFragment(emailArg)
        findNavController().navigate(direction)
    }

}