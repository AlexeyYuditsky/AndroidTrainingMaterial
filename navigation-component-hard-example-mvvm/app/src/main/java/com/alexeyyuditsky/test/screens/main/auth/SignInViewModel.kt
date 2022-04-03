package com.alexeyyuditsky.test.screens.main.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.test.model.accounts.AccountsRepository
import kotlinx.coroutines.launch

class SignInViewModel(
    val accountsRepository: AccountsRepository
) : ViewModel() {

    private val _state = MutableLiveData(State())
    val state: LiveData<State> = _state

    fun signIn(email: String, password: String) = viewModelScope.launch {
        showProgress()
    }

    private fun showProgress() {
        _state.value = State(signInInProgress = true)
    }

    data class State(
        val emptyEmailError: Boolean = false,
        val emptyPasswordError: Boolean = false,
        val signInInProgress: Boolean = false
    ) {
        val showProgress: Boolean get() = signInInProgress
        val enableViews: Boolean get() = !signInInProgress
    }

}