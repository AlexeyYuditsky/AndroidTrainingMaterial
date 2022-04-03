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

    private val _launchMainScreenEvent = MutableLiveData<Event<Boolean>>()
    val launchMainScreenEvent: LiveData<Event<Boolean>> = _launchMainScreenEvent

    init {
        viewModelScope.launch {
            _launchMainScreenEvent.value = Event(accountsRepository.isSignedIn())
        }
    }

}