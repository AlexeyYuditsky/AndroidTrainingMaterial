package com.alexeyyuditsky.test.screens.main.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.databinding.FragmentSignUpBinding
import com.alexeyyuditsky.test.utils.publishResult

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var binding: FragmentSignUpBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)
        binding.createAccountButton.setOnClickListener { onCreateAccountButtonPressed() }

        if (savedInstanceState == null && getEmailArgument() != null) {
            binding.emailEditText.setText(getEmailArgument())
        }
    }

    private fun onCreateAccountButtonPressed() {
        if (inputValidation()) return
        publishResult(SignInFragment.EXTRA_EMAIL, binding.emailEditText.text.toString())
        findNavController().popBackStack()
    }

    private fun inputValidation(): Boolean {
        binding.emailTextInput.error =
            if (binding.emailEditText.text.isNullOrBlank()) "Field is empty"
            else if (!binding.emailEditText.text?.contains('@')!!) "Field is not contains @" else null
        if (binding.emailTextInput.error != null) return true

        binding.usernameTextInput.error =
            if (binding.usernameEditText.text.isNullOrBlank()) "Field is empty" else null
        if (binding.usernameTextInput.error != null) return true

        binding.passwordTextInput.error =
            if (binding.passwordEditText.text.isNullOrBlank()) "Field is empty" else null
        if (binding.passwordTextInput.error != null) return true

        binding.repeatPasswordTextInput.error =
            if (binding.repeatPasswordEditText.text.isNullOrBlank()) "Field is empty" else null
        if (binding.repeatPasswordTextInput.error != null) return true
        return false
    }

    private fun getEmailArgument(): String? {
        val args by navArgs<SignUpFragmentArgs>()
        return args.email
    }

}