package com.alexeyyuditsky.test.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexeyyuditsky.test.App
import com.alexeyyuditsky.test.interfaces.Navigator
import com.alexeyyuditsky.test.viewModels.UsersListViewModel

typealias ViewModelCreator = (App) -> ViewModel?

class ViewModelFactory(
    private val app: App,
    private val viewModelCreator: ViewModelCreator = { null }
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            UsersListViewModel::class.java -> UsersListViewModel(app.usersService)
            else -> viewModelCreator(app) ?: throw IllegalStateException("Unknown view model class")
        } as T
    }

}

fun Fragment.factory() = ViewModelFactory(requireContext().applicationContext as App)

fun Fragment.navigator() = requireActivity() as Navigator

inline fun <reified VM : ViewModel> Fragment.viewModelCreator(noinline creator: ViewModelCreator): Lazy<VM> {
    return viewModels { ViewModelFactory(requireContext().applicationContext as App, creator) }
}