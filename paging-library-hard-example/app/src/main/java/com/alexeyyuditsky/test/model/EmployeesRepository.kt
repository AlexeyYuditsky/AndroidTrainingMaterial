package com.alexeyyuditsky.test.model

interface EmployeesRepository {

    suspend fun initDatabaseIfEmpty()

}