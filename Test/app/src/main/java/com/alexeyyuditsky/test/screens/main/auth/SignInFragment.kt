package com.alexeyyuditsky.test.screens.main.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alexeyyuditsky.test.MainActivity
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.databinding.FragmentSignInBinding
import com.alexeyyuditsky.test.utils.listenResult

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private lateinit var binding: FragmentSignInBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignInBinding.bind(view)
        binding.signInButton.setOnClickListener { onSignInButtonPressed() }
        binding.createAccountButton.setOnClickListener { onCreateAccountButtonPressed() }

        listenResult<String>(EXTRA_EMAIL) { result -> binding.emailEditText.setText(result) }
    }

    private fun onSignInButtonPressed() {
       // if (inputValidation()) return

        findNavController().navigate(R.id.action_signInFragment_to_tabsFragment)
        (requireActivity() as MainActivity).toolbarTextViewListener(
            getString(R.string.toolbarTitle, binding.emailEditText.text.toString())
        )
    }

    private fun inputValidation(): Boolean {
        binding.emailTextInput.error =
            if (binding.emailEditText.text.isNullOrBlank()) "Field is empty"
            else if (!binding.emailEditText.text?.contains('@')!!) "Field is not contains @" else null
        if (binding.emailTextInput.error != null) return true

        binding.passwordTextInput.error =
            if (binding.passwordEditText.text.isNullOrBlank()) "Field is empty" else null
        if (binding.passwordTextInput.error != null) return true

        return false
    }

    private fun onCreateAccountButtonPressed() {
        val email = binding.emailEditText.text.toString()
        val emailArg = email.ifBlank { null }

        val direction = SignInFragmentDirections.actionSignInFragmentToSignUpFragment(emailArg)
        findNavController().navigate(direction)
    }

    companion object {
        const val EXTRA_EMAIL = "email"
    }

}