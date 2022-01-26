package com.alexeyyuditsky.test.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alexeyyuditsky.test.adapters.UsersAdapter
import com.alexeyyuditsky.test.databinding.FragmentUsersListBinding
import com.alexeyyuditsky.test.interfaces.UserActionListener
import com.alexeyyuditsky.test.model.User
import com.alexeyyuditsky.test.utils.factory
import com.alexeyyuditsky.test.utils.navigator
import com.alexeyyuditsky.test.viewModels.UsersListViewModel

class UsersListFragment : Fragment() {

    private val viewModel: UsersListViewModel by viewModels { factory() }

    private lateinit var adapter: UsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentUsersListBinding.inflate(inflater, container, false).apply {

            adapter = UsersAdapter(object : UserActionListener {
                override fun onUserMove(user: User, moveBy: Int) = viewModel.moveUser(user, moveBy)

                override fun onUserDelete(user: User) = viewModel.deleteUser(user)

                override fun onUserDetails(user: User) = navigator().showDetails(user)
            })

            recyclerView.adapter = adapter

            viewModel.users.observe(viewLifecycleOwner) {
                adapter.users = it
            }

        }.root

    }

}