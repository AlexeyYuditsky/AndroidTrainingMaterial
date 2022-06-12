package com.alexeyyuditsky.test.screens.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.test.model.EmployeesRepository
import com.alexeyyuditsky.test.utils.Event
import kotlinx.coroutines.launch

class SplashViewModel(private val employeesRepository: EmployeesRepository) : ViewModel() {

    private val _navigateToMainEvent: MutableLiveData<Event<Unit>> = MutableLiveData()
    val navigateToMainEvent: LiveData<Event<Unit>> = _navigateToMainEvent

    init {
        viewModelScope.launch {
            employeesRepository.initDatabaseIfEmpty()
            _navigateToMainEvent.value = Event(Unit)
        }
    }

}