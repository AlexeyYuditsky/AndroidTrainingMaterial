package com.alexeyyuditsky.test.screens.main.tabs.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.databinding.FragmentEditProfileBinding
import com.alexeyyuditsky.test.utils.publishResult

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {

    private lateinit var binding: FragmentEditProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditProfileBinding.bind(view)
        binding.cancelButton.setOnClickListener { onCancelButtonPressed() }
        binding.saveButton.setOnClickListener { onSaveButtonPressed() }

        if (savedInstanceState == null) {
            binding.usernameEditText.setText(getUsername())
        }
    }

    private fun getUsername(): String {
        val arg by navArgs<EditProfileFragmentArgs>()
        return arg.username
    }

    private fun onSaveButtonPressed() {
        requireActivity().supportFragmentManager.setFragmentResult(
            ProfileFragment.EXTRA_USERNAME,
            bundleOf(ProfileFragment.EXTRA_USERNAME to binding.usernameEditText.text.toString())
        )
        findNavController().popBackStack()
    }

    private fun onCancelButtonPressed() {
        findNavController().popBackStack()
    }

}