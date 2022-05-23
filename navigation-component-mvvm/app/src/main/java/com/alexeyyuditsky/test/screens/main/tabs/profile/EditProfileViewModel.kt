package com.alexeyyuditsky.test.screens.main.tabs.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.test.model.EmptyFieldException
import com.alexeyyuditsky.test.model.accounts.AccountsRepository
import com.alexeyyuditsky.test.model.accounts.entities.Account
import com.alexeyyuditsky.test.utils.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EditProfileViewModel(
    private val accountsRepository: AccountsRepository
) : ViewModel() {

    private val _initialUsernameEvent = MutableLiveData<Event<Account>>()
    val initialUsernameEvent: LiveData<Event<Account>> = _initialUsernameEvent

    private val _saveInProgress = MutableLiveData(false)
    val saveInProgress: LiveData<Boolean> = _saveInProgress

    private val _emptyFieldErrorMessageEvent = MutableLiveData<Event<Unit>>()
    val emptyFieldErrorEvent: LiveData<Event<Unit>> = _emptyFieldErrorMessageEvent

    private val _goBack = MutableLiveData<Event<Unit>>()
    val goBack: LiveData<Event<Unit>> = _goBack

    init {
        viewModelScope.launch {
            accountsRepository.getAccount().collect {
                _initialUsernameEvent.value = Event(it)
            }
        }
    }

    fun saveUsername(newUsername: String) = viewModelScope.launch {
        showProgress()
        try {
            accountsRepository.updateAccountUsername(newUsername)
            goBack()
        } catch (e: EmptyFieldException) {
            hideProgress()
            showEmptyFieldErrorMessage()
        }
    }

    private fun showEmptyFieldErrorMessage() {
        _emptyFieldErrorMessageEvent.value = Event(Unit)
    }

    private fun goBack() {
        _goBack.value = Event(Unit)
    }

    private fun showProgress() {
        _saveInProgress.value = true
    }

    private fun hideProgress() {
        _saveInProgress.value = false
    }

}