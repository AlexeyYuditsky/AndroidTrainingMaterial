package com.alexeyyuditsky.room.screens.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.alexeyyuditsky.room.R
import com.alexeyyuditsky.room.model.AuthException
import com.alexeyyuditsky.room.model.EmptyFieldException
import com.alexeyyuditsky.room.model.Field
import com.alexeyyuditsky.room.model.StorageException
import com.alexeyyuditsky.room.model.accounts.AccountsRepository
import com.alexeyyuditsky.room.utils.*

class SignInViewModel(
    private val accountsRepository: AccountsRepository
) : ViewModel() {

    private val _state = MutableLiveData(State())
    val state = _state.share()

    private val _clearPasswordEvent = MutableLiveEvent<Unit>()
    val clearPasswordEvent = _clearPasswordEvent.share()

    private val _showAuthErrorToastEvent = MutableLiveEvent<Int>()
    val showAuthToastEvent = _showAuthErrorToastEvent.share()

    private val _navigateToTabsEvent = MutableLiveEvent<Unit>()
    val navigateToTabsEvent = _navigateToTabsEvent.share()

    fun signIn(email: String, password: CharArray) = viewModelScope.launch {
        showProgress()
        try {
            accountsRepository.signIn(email, password)
            launchTabsScreen()
        } catch (e: EmptyFieldException) {
            processEmptyFieldException(e)
        } catch (e: AuthException) {
            processAuthException()
        } catch (e: StorageException) {
            processStorageException()
        }
    }

    private fun processEmptyFieldException(e: EmptyFieldException) {
        _state.value = _state.value?.copy(
            emptyEmailError = e.field == Field.Email,
            emptyPasswordError = e.field == Field.Password,
            signInInProgress = false
        )
    }

    private fun processAuthException() {
        _state.value = _state.value?.copy(signInInProgress = false)
        clearPasswordField()
        showAuthErrorToast()
    }

    private fun processStorageException() {
        _showAuthErrorToastEvent.publishEvent(R.string.storage_error)
        _state.value = _state.value?.copy(signInInProgress = false)
    }

    private fun showProgress() {
        _state.value = State(signInInProgress = true)
    }

    private fun clearPasswordField() = _clearPasswordEvent.publishEvent(Unit)

    private fun showAuthErrorToast() = _showAuthErrorToastEvent.publishEvent(R.string.invalid_email_or_password)

    private fun launchTabsScreen() = _navigateToTabsEvent.publishEvent(Unit)

    data class State(
        val emptyEmailError: Boolean = false,
        val emptyPasswordError: Boolean = false,
        val signInInProgress: Boolean = false
    ) {
        val showProgress: Boolean get() = signInInProgress
        val enableViews: Boolean get() = !signInInProgress
    }

}