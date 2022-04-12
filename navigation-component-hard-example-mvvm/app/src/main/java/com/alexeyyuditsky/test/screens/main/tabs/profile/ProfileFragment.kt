package com.alexeyyuditsky.test.screens.main.tabs.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.Repositories
import com.alexeyyuditsky.test.databinding.FragmentProfileBinding
import com.alexeyyuditsky.test.utils.findTopNavController
import com.alexeyyuditsky.test.utils.viewModelCreator

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel by viewModelCreator { ProfileViewModel(Repositories.accountsRepository) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        binding.editProfileButton.setOnClickListener { onEditProfileButtonPressed() }
        binding.logoutButton.setOnClickListener { onLogoutButtonPressed() }

        observeAccountDetails()
    }

    private fun observeAccountDetails() = viewModel.account.observe(viewLifecycleOwner) { account ->
        if (account == null) return@observe
        binding.emailTextView.text = account.email
        binding.userNameTextView.text = account.username
        binding.createdAtTextView.text = account.createdAt
    }

    private fun onLogoutButtonPressed() {
        viewModel.logout()
        findTopNavController().navigate(R.id.action_tabsFragment_to_signInFragment)
    }

    private fun onEditProfileButtonPressed() {
        findTopNavController().navigate(R.id.editProfileFragment)
    }

}