package com.alexeyyuditsky.test.model.employees.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.alexeyyuditsky.test.model.employees.room.RoomEmployeesRepository.Companion.DATABASE_SIZE
import com.alexeyyuditsky.test.model.employees.room.entities.EmployeeDbEntity

@Dao
interface EmployeesDao {

    @Query(
        "SELECT * FROM employees " +
                "WHERE :searchBy = '' OR name LIKE '%' || :searchBy || '%' " +
                "ORDER BY name " +
                "LIMIT :limit OFFSET :offset"
    )
    fun getEmployees(limit: Int = DATABASE_SIZE, offset: Int = 0, searchBy: String = ""): List<EmployeeDbEntity>

    @Insert(entity = EmployeeDbEntity::class)
    fun insertEmployeesList(employeesList: List<EmployeeDbEntity>)

}