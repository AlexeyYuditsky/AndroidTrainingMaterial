package com.alexeyyuditsky.test.model.employees.repositories.room

import androidx.room.*
import com.alexeyyuditsky.test.model.employees.repositories.room.RoomEmployeesRepository.Companion.DATABASE_SIZE

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

    @Delete(entity = EmployeeDbEntity::class)
    fun deleteEmployee(idTuple: IdTuple)

}