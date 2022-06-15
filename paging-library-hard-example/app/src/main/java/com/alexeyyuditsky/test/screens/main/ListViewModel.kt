package com.alexeyyuditsky.test.screens.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alexeyyuditsky.test.model.employees.EmployeesRepository
import com.alexeyyuditsky.test.model.employees.entities.Employee
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest

@ExperimentalCoroutinesApi
@FlowPreview
class ListViewModel(private val employeesRepository: EmployeesRepository) : ViewModel() {

    val isErrorsEnabled: Flow<Boolean> = employeesRepository.enableErrorFlow

    val employeesFlow: Flow<PagingData<Employee>>

    private var searchBy = MutableLiveData("")

    init {
        employeesFlow = searchBy.asFlow()
            .debounce(500)
            .flatMapLatest { employeesRepository.getPagedEmployees(it) }
            .cachedIn(viewModelScope)
    }

    fun searchByName(value: String) {
        searchBy.value = value
    }

    fun refresh() {
        searchBy.value = searchBy.value
    }

    fun setEnableError(value: Boolean) {
        employeesRepository.setErrorEnabled(value)
    }

}