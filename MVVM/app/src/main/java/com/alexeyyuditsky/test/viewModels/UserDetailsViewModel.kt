package com.alexeyyuditsky.test.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexeyyuditsky.test.utils.UserNotFoundException
import com.alexeyyuditsky.test.model.UserDetails
import com.alexeyyuditsky.test.model.UsersService

class UserDetailsViewModel(
    private val usersService: UsersService
) : ViewModel() {

    private val _userDetails = MutableLiveData<UserDetails>()
    val userDetails: LiveData<UserDetails> = _userDetails

    fun loadUser(userId: Long) {
        try {
            _userDetails.value = usersService.getById(userId)
        } catch (e: UserNotFoundException) {
            e.printStackTrace()
        }
    }

    fun deleteUser() {
        val userDetails = this.userDetails.value ?: return
        usersService.deleteUser(userDetails.user)
    }

}