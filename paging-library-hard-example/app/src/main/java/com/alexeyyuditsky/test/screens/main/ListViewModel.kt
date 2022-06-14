package com.alexeyyuditsky.test.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.alexeyyuditsky.test.model.employees.EmployeesRepository
import com.alexeyyuditsky.test.model.employees.entities.Employee
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@FlowPreview
class ListViewModel(private val employeesRepository: EmployeesRepository) : ViewModel() {

    private val _employeesFlow =
        MutableSharedFlow<PagingData<Employee>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val employeesFlow: SharedFlow<PagingData<Employee>> = _employeesFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            employeesRepository.initDatabaseIfEmpty()
            _employeesFlow.emitAll(employeesRepository.getPagedEmployees())
        }
    }

    fun refresh() = viewModelScope.launch {
        _employeesFlow.emitAll(employeesRepository.getPagedEmployees())
    }

}