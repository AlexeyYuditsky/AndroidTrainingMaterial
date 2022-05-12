package com.alexeyyuditsky.room.screens.tabs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.alexeyyuditsky.room.model.accounts.AccountsRepository
import com.alexeyyuditsky.room.utils.share

class TabsViewModel(
    private val accountsRepository: AccountsRepository
) : ViewModel() {

    private val _showAdminTab = MutableLiveData<Boolean>()
    val showAdminTab = _showAdminTab.share()

    init {
        viewModelScope.launch {
            accountsRepository.getAccount().collect {
                _showAdminTab.value = it?.isAdmin() == true
            }
        }
    }
}