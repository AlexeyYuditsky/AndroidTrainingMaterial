package com.alexeyyuditsky.test.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexeyyuditsky.test.utils.UserNotFoundException
import com.alexeyyuditsky.test.model.UserDetails
import com.alexeyyuditsky.test.model.UsersService

class UserDetailsViewModel(
    private val usersService: UsersService,
    private val userId: Long
) : ViewModel() {

    private val _userDetails = MutableLiveData<UserDetails>()
    val userDetails: LiveData<UserDetails> = _userDetails

    init {
        loadUser()
    }

    fun deleteUser() {
        val userDetails = this.userDetails.value ?: return
        usersService.deleteUser(userDetails.user)
    }

    private fun loadUser() {
        try {
            _userDetails.value = usersService.getById(userId)
        } catch (e: UserNotFoundException) {
            e.printStackTrace()
        }
    }

}