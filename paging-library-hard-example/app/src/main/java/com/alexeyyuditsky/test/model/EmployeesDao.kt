package com.alexeyyuditsky.test.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EmployeesDao {

    @Query(
        "SELECT * FROM employees " +
                "WHERE name = '' OR name LIKE '%' || :searchBy || '%' "
    )
    fun getEmployees(searchBy: String = ""): List<EmployeeDbEntity>

    @Insert(entity = EmployeeDbEntity::class)
    fun insertEmployeesList(employeesList: List<EmployeeDbEntity>)

}