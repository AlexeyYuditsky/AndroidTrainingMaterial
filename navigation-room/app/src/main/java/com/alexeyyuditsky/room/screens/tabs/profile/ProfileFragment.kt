package com.alexeyyuditsky.room.screens.tabs.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.alexeyyuditsky.room.R
import com.alexeyyuditsky.room.Repositories
import com.alexeyyuditsky.room.databinding.FragmentProfileBinding
import com.alexeyyuditsky.room.model.accounts.entities.Account
import com.alexeyyuditsky.room.utils.findTopNavController
import com.alexeyyuditsky.room.utils.observeEvent
import com.alexeyyuditsky.room.utils.viewModelCreator
import java.text.SimpleDateFormat
import java.util.*

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

    private fun observeAccountDetails() {
        val formatter = SimpleDateFormat.getDateTimeInstance()
        viewModel.account.observe(viewLifecycleOwner) { account ->
            if (account == null) return@observe
            binding.emailTextView.text = account.email
            binding.usernameTextView.text = account.username
            binding.createdAtTextView.text =
                if (account.createdAt == Account.UNKNOWN_CREATED_AT) getString(R.string.placeholder)
                else formatter.format(Date(account.createdAt))
        }
    }

    private fun onEditProfileButtonPressed() {
        findTopNavController().navigate(R.id.editProfileFragment)
    }

    private fun onLogoutButtonPressed() {
        viewModel.logout()
        findTopNavController().navigate(R.id.action_tabsFragment_to_signInFragment)
    }

}