package com.alexeyyuditsky.test.screens.main.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.view.allViews
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.Repositories
import com.alexeyyuditsky.test.databinding.FragmentSignUpBinding
import com.alexeyyuditsky.test.model.accounts.entities.SignUpData
import com.alexeyyuditsky.test.utils.viewModelCreator
import com.google.android.material.textfield.TextInputLayout

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var binding: FragmentSignUpBinding

    private val viewModel by viewModelCreator { SignUpViewModel(Repositories.accountsRepository) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)
        binding.createAccountButton.setOnClickListener { onCreateAccountButtonPressed() }

        if (savedInstanceState == null && getEmailArgument() != null) {
            binding.emailEditText.setText(getEmailArgument())
        }

        observeState()
        observeGoBackEvent()
        observeShowSuccessSignUpMessage()
    }

    private fun onCreateAccountButtonPressed() {
        val signUpData = SignUpData(
            email = binding.emailEditText.text.toString(),
            username = binding.usernameEditText.text.toString(),
            password = binding.passwordEditText.text.toString(),
            repeatPassword = binding.repeatPasswordEditText.text.toString()
        )
        viewModel.signUp(signUpData)
    }

    private fun observeShowSuccessSignUpMessage() =
        viewModel.showSuccessSignUpMessageEvent.observe(viewLifecycleOwner) {
            it.get()?.let {
                Toast.makeText(requireContext(), getString(R.string.sign_up_success), Toast.LENGTH_SHORT).show()
            }
        }

    private fun observeGoBackEvent() = viewModel.goBackEvent.observe(viewLifecycleOwner) {
        it.get()?.let {
            findNavController().popBackStack()
        }
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) { state ->
        binding.container.children.forEach { it.isEnabled = state.enableViews }

        binding.emailTextInput.error =
            if (state.emailErrorMessageRes != SignUpViewModel.NO_ERROR_MESSAGE) getString(state.emailErrorMessageRes) else null
        binding.usernameTextInput.error =
            if (state.usernameErrorMessageRes != SignUpViewModel.NO_ERROR_MESSAGE) getString(state.usernameErrorMessageRes) else null
        binding.passwordTextInput.error =
            if (state.passwordErrorMessageRes != SignUpViewModel.NO_ERROR_MESSAGE) getString(state.passwordErrorMessageRes) else null
        binding.repeatPasswordTextInput.error =
            if (state.repeatPasswordErrorMessageRes != SignUpViewModel.NO_ERROR_MESSAGE) getString(state.repeatPasswordErrorMessageRes) else null

        binding.progressBar.isVisible = state.showProgress
    }

    private fun getEmailArgument(): String? {
        val arg by navArgs<SignUpFragmentArgs>()
        return arg.email
    }

}