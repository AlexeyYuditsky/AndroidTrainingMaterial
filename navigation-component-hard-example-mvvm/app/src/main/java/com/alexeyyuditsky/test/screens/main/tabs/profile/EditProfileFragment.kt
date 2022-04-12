package com.alexeyyuditsky.test.screens.main.tabs.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.Repositories
import com.alexeyyuditsky.test.databinding.FragmentEditProfileBinding
import com.alexeyyuditsky.test.utils.findTopNavController
import com.alexeyyuditsky.test.utils.viewModelCreator

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {

    private lateinit var binding: FragmentEditProfileBinding

    private val viewModel by viewModelCreator { EditProfileViewModel(Repositories.accountsRepository) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditProfileBinding.bind(view)

        binding.cancelButton.setOnClickListener { onCancelButtonPressed() }
        binding.saveButton.setOnClickListener { onSaveButtonPressed() }

        if (savedInstanceState == null) observeInitialUsernameEvent()
        observeShowInProgress()
        observeEmptyFieldErrorEvent()
        observeGoBack()
    }

    private fun observeInitialUsernameEvent() = viewModel.initialUsernameEvent.observe(viewLifecycleOwner) {
        it.get()?.let { account ->
            binding.usernameEditText.setText(account.username)
        }
    }

    private fun observeShowInProgress() = viewModel.saveInProgress.observe(viewLifecycleOwner) {
        if (it) {
            binding.progressBar.isVisible = true
            binding.usernameTextInput.isEnabled = false
            binding.saveButton.isEnabled = false
        } else {
            binding.progressBar.isVisible = false
            binding.usernameTextInput.isEnabled = true
            binding.saveButton.isEnabled = true
        }
    }

    private fun observeEmptyFieldErrorEvent() = viewModel.emptyFieldErrorEvent.observe(viewLifecycleOwner) {
        it.get()?.let {
            Toast.makeText(requireContext(), R.string.field_is_empty, Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeGoBack() = viewModel.goBack.observe(viewLifecycleOwner) {
        it.get()?.let {
            findNavController().popBackStack()
        }
    }

    private fun onCancelButtonPressed() {
        findNavController().popBackStack()
    }

    private fun onSaveButtonPressed() {
        viewModel.saveUsername(binding.usernameEditText.text.toString())
    }

}