package com.alexeyyuditsky.test.screens.main.auth

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.model.AccountAlreadyExistsException
import com.alexeyyuditsky.test.model.EmptyFieldException
import com.alexeyyuditsky.test.model.Field
import com.alexeyyuditsky.test.model.PasswordMismatchException
import com.alexeyyuditsky.test.model.accounts.AccountsRepository
import com.alexeyyuditsky.test.model.accounts.entities.SignUpData
import com.alexeyyuditsky.test.utils.Event
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val accountsRepository: AccountsRepository
) : ViewModel() {

    private val _state = MutableLiveData(State())
    val state: LiveData<State> = _state

    private val _goBackEvent = MutableLiveData<Event<Unit>>()
    val goBackEvent: LiveData<Event<Unit>> = _goBackEvent

    private val _showSuccessSignUpMessageEvent = MutableLiveData<Event<Unit>>()
    val showSuccessSignUpMessageEvent: LiveData<Event<Unit>> = _showSuccessSignUpMessageEvent

    fun signUp(signUpData: SignUpData) = viewModelScope.launch {
        showProgress()
        try {
            accountsRepository.signUp(signUpData)
            showSuccessSignUpMessage()
            goBack()
        } catch (e: EmptyFieldException) {
            handleEmptyFieldException(e)
        } catch (e: PasswordMismatchException) {
            handlePasswordMismatchException()
        } catch (e: AccountAlreadyExistsException) {
            handleAccountAlreadyExistsException()
        }
    }

    private fun handleEmptyFieldException(e: EmptyFieldException) {
        _state.value = when (e.field) {
            Field.Email -> State(emailErrorMessageRes = R.string.field_is_empty)
            Field.Username -> State(usernameErrorMessageRes = R.string.field_is_empty)
            Field.Password -> State(passwordErrorMessageRes = R.string.field_is_empty)
        }
    }

    private fun handlePasswordMismatchException() {
        _state.value = State(repeatPasswordErrorMessageRes = R.string.password_mismatch)
    }

    private fun handleAccountAlreadyExistsException() {
        _state.value = State(emailErrorMessageRes = R.string.account_already_exists)
    }

    private fun showProgress() {
        _state.value = State(signUpInProgress = true)
    }

    private fun goBack() {
        _goBackEvent.value = Event(Unit)
    }

    private fun showSuccessSignUpMessage() {
        _showSuccessSignUpMessageEvent.value = Event(Unit)
    }

    data class State(
        @StringRes val emailErrorMessageRes: Int = NO_ERROR_MESSAGE,
        @StringRes val usernameErrorMessageRes: Int = NO_ERROR_MESSAGE,
        @StringRes val passwordErrorMessageRes: Int = NO_ERROR_MESSAGE,
        @StringRes val repeatPasswordErrorMessageRes: Int = NO_ERROR_MESSAGE,
        val signUpInProgress: Boolean = false
    ) {
        val showProgress: Boolean get() = signUpInProgress
        val enableViews: Boolean get() = !signUpInProgress
    }

    companion object {
        const val NO_ERROR_MESSAGE = 0
    }

}