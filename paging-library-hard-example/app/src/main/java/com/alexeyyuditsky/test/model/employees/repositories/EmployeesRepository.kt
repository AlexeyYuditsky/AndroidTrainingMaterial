package com.alexeyyuditsky.test.model.employees.repositories

import androidx.paging.PagingData
import com.alexeyyuditsky.test.model.employees.Employee
import kotlinx.coroutines.flow.Flow

interface EmployeesRepository {

    val enableErrorFlow: Flow<Boolean>

    suspend fun initDatabaseIfEmpty()

    fun setErrorEnabled(value: Boolean)

    fun getPagedEmployees(searchBy: String = ""): Flow<PagingData<Employee>>

    suspend fun deleteEmployee(employee: Employee)

    suspend fun setIsFavorite(employee: Employee, isFavorite: Boolean)

}