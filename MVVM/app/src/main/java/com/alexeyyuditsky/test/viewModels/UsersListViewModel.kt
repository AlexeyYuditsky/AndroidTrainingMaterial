package com.alexeyyuditsky.test.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexeyyuditsky.test.model.User
import com.alexeyyuditsky.test.model.UsersService

class UsersListViewModel(
    private val usersService: UsersService
) : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    init {
        loadUsers()
    }

    private fun loadUsers() = usersService.addListener { _users.value = it }

    fun moveUser(user: User, moveBy: Int) = usersService.moveUser(user, moveBy)

    fun deleteUser(user: User) = usersService.deleteUser(user)

    override fun onCleared() {
        super.onCleared()
        usersService.removeListener()
    }

}