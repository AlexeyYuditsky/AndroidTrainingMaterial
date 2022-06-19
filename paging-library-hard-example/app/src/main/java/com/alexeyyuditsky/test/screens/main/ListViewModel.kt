package com.alexeyyuditsky.test.screens.main

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alexeyyuditsky.test.adapters.EmployeesAdapter
import com.alexeyyuditsky.test.model.employees.Employee
import com.alexeyyuditsky.test.model.employees.repositories.EmployeesRepository
import com.alexeyyuditsky.test.utils.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@FlowPreview
class ListViewModel(private val employeesRepository: EmployeesRepository) : ViewModel(), EmployeesAdapter.Listener {

    val isErrorsEnabled: Flow<Boolean> = employeesRepository.enableErrorFlow

    val employeesFlow: Flow<PagingData<Employee>>

    private val _invalidateEvent = MutableLiveData<Event<Unit>>()
    val invalidateEvent: LiveData<Event<Unit>> = _invalidateEvent

    private var searchBy = MutableLiveData("")

    init {
        viewModelScope.launch {
            employeesRepository.initDatabaseIfEmpty()
        }

        employeesFlow = searchBy.asFlow()
            .debounce(500)
            .flatMapLatest { employeesRepository.getPagedEmployees(it) }
            .cachedIn(viewModelScope)
    }

    override fun onEmployeeDelete(employee: Employee) {
        viewModelScope.launch {
            employeesRepository.deleteEmployee(employee)
            invalidateList()
        }
    }

    override fun onEmployeeFavorite(employee: Employee) {

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

    private fun invalidateList() {
        _invalidateEvent.value = Event(Unit)
    }

}