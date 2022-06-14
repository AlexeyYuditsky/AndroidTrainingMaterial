package com.alexeyyuditsky.test

import androidx.lifecycle.ViewModel
import com.alexeyyuditsky.test.model.employees.EmployeesRepository
import kotlinx.coroutines.flow.Flow

class MainViewModel(private val employeesRepository: EmployeesRepository) : ViewModel() {

    val isErrorsEnabled: Flow<Boolean> = employeesRepository.enableErrorFlow

    fun setEnableError(value: Boolean) {
        employeesRepository.setErrorEnabled(value)
    }

}