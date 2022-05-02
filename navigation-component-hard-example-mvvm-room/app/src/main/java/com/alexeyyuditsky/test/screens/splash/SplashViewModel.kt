package com.alexeyyuditsky.test.screens.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.test.model.accounts.AccountsRepository
import com.alexeyyuditsky.test.utils.Event
import kotlinx.coroutines.launch

class SplashViewModel(
    accountsRepository: AccountsRepository
) : ViewModel() {

    private val _navigateToMainScreenEvent = MutableLiveData<Event<Boolean>>()
    val navigateToMainScreenEvent: LiveData<Event<Boolean>> = _navigateToMainScreenEvent

    init {
        viewModelScope.launch {
            _navigateToMainScreenEvent.value = Event(accountsRepository.isSignedIn())
        }
    }

}