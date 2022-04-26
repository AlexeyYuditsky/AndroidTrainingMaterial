package com.alexeyyuditsky.test.screens.main.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.test.model.AuthException
import com.alexeyyuditsky.test.model.EmptyFieldException
import com.alexeyyuditsky.test.model.Field
import com.alexeyyuditsky.test.model.StorageException
import com.alexeyyuditsky.test.model.accounts.AccountsRepository
import com.alexeyyuditsky.test.utils.Event
import com.alexeyyuditsky.test.utils.requireValue
import kotlinx.coroutines.launch

class SignInViewModel(
    private val accountsRepository: AccountsRepository
) : ViewModel() {

    private val _state = MutableLiveData(State())
    val state: LiveData<State> = _state

    private val _clearPasswordEvent = MutableLiveData<Event<Unit>>()
    val clearPasswordEvent: LiveData<Event<Unit>> = _clearPasswordEvent

    private val _showAuthErrorToastEvent = MutableLiveData<Event<Unit>>()
    val showAuthErrorToastEvent: LiveData<Event<Unit>> = _showAuthErrorToastEvent

    private val _navigateToTabsScreenEvent = MutableLiveData<Event<Unit>>()
    val navigateToTabsScreenEvent: LiveData<Event<Unit>> = _navigateToTabsScreenEvent

    fun signIn(email: String, password: String) = viewModelScope.launch {
        showProgress()
        try {
            accountsRepository.signIn(email, password)
            launchTabsScreen()
        } catch (e: EmptyFieldException) {
            handleEmptyFieldException(e)
        } catch (e: AuthException) {
            handleAuthException()
        } catch (e: StorageException) {
            handleStorageException()
        }
    }

    private fun launchTabsScreen() {
        _navigateToTabsScreenEvent.value = Event(Unit)
    }

    private fun handleStorageException() {
        _showAuthErrorToastEvent.value = Event(Unit)
        _state.value = _state.requireValue().copy(signInInProgress = false)
    }

    private fun handleAuthException() {
        _state.value = State(
            signInInProgress = false
        )
        _clearPasswordEvent.value = Event(Unit)
        _showAuthErrorToastEvent.value = Event(Unit)
    }

    private fun handleEmptyFieldException(e: EmptyFieldException) {
        _state.value = State(
            emptyEmailError = e.field == Field.Email,
            emptyPasswordError = e.field == Field.Password,
            signInInProgress = false
        )
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