package com.alexeyyuditsky.test.screens.main

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.adapters.EmployeesAdapter
import com.alexeyyuditsky.test.model.employees.Employee
import com.alexeyyuditsky.test.model.employees.EmployeeListItem
import com.alexeyyuditsky.test.model.employees.repositories.EmployeesRepository
import com.alexeyyuditsky.test.utils.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@FlowPreview
class ListViewModel(private val employeesRepository: EmployeesRepository) : ViewModel(), EmployeesAdapter.Listener {

    val isErrorsEnabled: Flow<Boolean> = employeesRepository.enableErrorFlow

    val employeesFlow: Flow<PagingData<EmployeeListItem>>

    private var searchBy = MutableLiveData("")

    private val localChanges = LocalChanges()
    private val localChangesFlow = MutableStateFlow(OnChange(localChanges))

    private val _invalidateListEvent = MutableLiveData<Event<Unit>>()
    val invalidateListEvent: LiveData<Event<Unit>> = _invalidateListEvent

    private val _scrollEvent = MutableLiveData<Event<Unit>>()
    val scrollEvent: LiveData<Event<Unit>> = _scrollEvent

    private val _errorEvents = MutableLiveData<Event<Int>>()
    val errorEvents: LiveData<Event<Int>> = _errorEvents

    init {
        viewModelScope.launch { employeesRepository.initDatabaseIfEmpty() }

        val originEmployeesFlow = searchBy.asFlow()
            .debounce(500)
            .flatMapLatest { employeesRepository.getPagedEmployees(it) }
            .cachedIn(viewModelScope)

        employeesFlow = combine(
            originEmployeesFlow,
            localChangesFlow,
            this::merge
        )
    }

    override fun onEmployeeDelete(employeeListItem: EmployeeListItem) {
        viewModelScope.launch {
            try {
                setProgress(employeeListItem, true)
                deleteEmployee(employeeListItem)
            } catch (e: Exception) {
                showError(R.string.error_delete)
            } finally {
                setProgress(employeeListItem, false)
            }
        }
    }

    override fun onEmployeeToggleFavoriteFlag(employeeListItem: EmployeeListItem) {
        viewModelScope.launch {
            try {
                setProgress(employeeListItem, true)
                setFavoriteFlag(employeeListItem)
            } catch (e: Exception) {
                showError(R.string.error_change_favorite)
            } finally {
                setProgress(employeeListItem, false)
            }
        }
    }

    fun searchByName(value: String) {
        if (this.searchBy.value == value) return
        this.searchBy.value = value
        scrollListToTop()
    }

    fun refresh() {
        this.searchBy.value = this.searchBy.value
    }

    fun setEnableError(value: Boolean) {
        employeesRepository.setErrorEnabled(value)
    }

    private fun scrollListToTop() {
        _scrollEvent.value = Event(Unit)
    }

    private suspend fun setFavoriteFlag(employeeListItem: EmployeeListItem) {
        val newFlagValue = !employeeListItem.isFavorite
        employeesRepository.setIsFavorite(employeeListItem.employee, newFlagValue)
        localChanges.favoriteFlags[employeeListItem.id] = newFlagValue
        localChangesFlow.value = OnChange(localChanges)
    }

    private fun setProgress(userListItem: EmployeeListItem, inProgress: Boolean) {
        if (inProgress) {
            localChanges.idsInProgress.add(userListItem.id)
        } else {
            localChanges.idsInProgress.remove(userListItem.id)
        }
        localChangesFlow.value = OnChange(localChanges)
    }

    private fun showError(@StringRes errorMessage: Int) {
        _errorEvents.value = Event(errorMessage)
    }

    private suspend fun deleteEmployee(employeeListItem: EmployeeListItem) {
        employeesRepository.deleteEmployee(employeeListItem.employee)
        invalidateList()
    }

    private fun invalidateList() {
        _invalidateListEvent.value = Event(Unit)
    }

    private fun merge(
        employees: PagingData<Employee>,
        localChanges: OnChange<LocalChanges>
    ): PagingData<EmployeeListItem> {
        return employees.map { employee ->
            val isInProgress = localChanges.value.idsInProgress.contains(employee.id)
            val localFavoriteFlag = localChanges.value.favoriteFlags[employee.id]

            val userWithLocalChanges = if (localFavoriteFlag == null) {
                employee
            } else {
                employee.copy(isFavorite = localFavoriteFlag)
            }

            EmployeeListItem(userWithLocalChanges, isInProgress)
        }
    }

    class OnChange<T>(val value: T)

    class LocalChanges {
        val idsInProgress = mutableSetOf<Long>()
        val favoriteFlags = mutableMapOf<Long, Boolean>()
    }

}