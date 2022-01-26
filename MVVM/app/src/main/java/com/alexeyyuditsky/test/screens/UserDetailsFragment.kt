package com.alexeyyuditsky.test.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.databinding.FragmentUserDetailsBinding
import com.alexeyyuditsky.test.utils.navigator
import com.alexeyyuditsky.test.utils.viewModelCreator
import com.alexeyyuditsky.test.viewModels.UserDetailsViewModel
import com.bumptech.glide.Glide

class UserDetailsFragment : Fragment() {

    private val viewModel: UserDetailsViewModel by viewModelCreator {
        UserDetailsViewModel(it.usersService, requireArguments().getLong(ARG_USER_ID))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentUserDetailsBinding.inflate(inflater, container, false).apply {

            viewModel.userDetails.observe(viewLifecycleOwner) {
                if (it.user.photo.isNotBlank()) {
                    Glide.with(this@UserDetailsFragment)
                        .load(it.user.photo)
                        .circleCrop()
                        .into(photoImageView)
                } else {
                    Glide.with(this@UserDetailsFragment)
                        .load(R.drawable.ic_avatar)
                        .into(photoImageView)
                }
                userNameTextView.text = it.user.name
                userDetailsTextView.text = it.details
            }

            deleteButton.setOnClickListener {
                viewModel.deleteUser()
                navigator().toast(R.string.user_has_been_deleted)
                navigator().goBack()
            }

        }.root
    }

    companion object {
        private const val ARG_USER_ID = "ARG_USER_ID"

        fun newInstance(userId: Long): UserDetailsFragment = UserDetailsFragment().apply {
            arguments = bundleOf(ARG_USER_ID to userId)
        }
    }

}