package com.alexeyyuditsky.test.screens.main.tabs.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.alexeyyuditsky.test.MainActivity
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.databinding.FragmentProfileBinding
import com.alexeyyuditsky.test.screens.main.tabs.TabsFragmentDirections
import com.alexeyyuditsky.test.utils.listenResult

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        binding.editProfileButton.setOnClickListener { onEditProfileButtonPressed() }
        binding.logoutButton.setOnClickListener { onLogoutButtonPressed() }

        requireActivity().supportFragmentManager.setFragmentResultListener(
            EXTRA_USERNAME,
            viewLifecycleOwner
        ) { _, result ->
            binding.userNameTextView.text = result.getString(EXTRA_USERNAME)
        }
    }

    private fun onLogoutButtonPressed() {
        val topLevelHost =
            requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment?
        topLevelHost?.navController?.navigate(R.id.action_tabsFragment_to_signInFragment)
        (requireActivity() as MainActivity).toolbarTextViewListener("")
    }

    private fun onEditProfileButtonPressed() {
        val arg = binding.userNameTextView.text.toString()
        val direction = TabsFragmentDirections.actionTabsFragmentToEditProfileFragment(arg)

        val topLevelHost =
            requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment?
        topLevelHost?.navController?.navigate(direction)
    }

    companion object {
        const val EXTRA_USERNAME = "username"
    }

}