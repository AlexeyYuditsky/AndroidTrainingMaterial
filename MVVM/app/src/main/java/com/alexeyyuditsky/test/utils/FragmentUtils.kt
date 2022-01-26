package com.alexeyyuditsky.test.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexeyyuditsky.test.App
import com.alexeyyuditsky.test.interfaces.Navigator
import com.alexeyyuditsky.test.viewModels.UserDetailsViewModel
import com.alexeyyuditsky.test.viewModels.UsersListViewModel

fun Fragment.factory() = ViewModelFactory(requireContext().applicationContext as App)

fun Fragment.navigator() = requireActivity() as Navigator

class ViewModelFactory(
    private val app: App
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            UsersListViewModel::class.java -> UsersListViewModel(app.usersService)
            UserDetailsViewModel::class.java -> UserDetailsViewModel(app.usersService)
            else -> throw IllegalStateException("Unknown view model class")
        } as T
    }

}