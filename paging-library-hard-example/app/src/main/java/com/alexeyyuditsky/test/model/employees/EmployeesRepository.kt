package com.alexeyyuditsky.test.model.employees

import androidx.paging.PagingData
import com.alexeyyuditsky.test.model.employees.entities.Employee
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface EmployeesRepository {

    val enableErrorFlow: Flow<Boolean>

    suspend fun initDatabaseIfEmpty()

    fun setErrorEnabled(value: Boolean)

    fun getPagedEmployees(): Flow<PagingData<Employee>>

}