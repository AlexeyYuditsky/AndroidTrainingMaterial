package com.alexeyyuditsky.test.model.employees

import androidx.paging.PagingData
import com.alexeyyuditsky.test.model.employees.entities.Employee
import kotlinx.coroutines.flow.Flow

interface EmployeesRepository {

    suspend fun initDatabaseIfEmpty()

    fun getPagedEmployees(): Flow<PagingData<Employee>>

}