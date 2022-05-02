package com.alexeyyuditsky.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.test.model.accounts.AccountsRepository
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val accountsRepository: AccountsRepository
) : ViewModel() {

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    init {
        viewModelScope.launch {
            accountsRepository.getAccount().collect {
                _username.value = it?.let { "@${it.username}" } ?: ""
            }
        }
    }

}