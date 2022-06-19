package com.alexeyyuditsky.test.model.employees.repositories.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.alexeyyuditsky.test.model.employees.Employee

@Entity(
    tableName = "employees",
    indices = [
        Index("name")
    ]
)
data class EmployeeDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val image: String,
    @ColumnInfo(collate = ColumnInfo.NOCASE) val name: String,
    val nation: String,
    val email: String,
    @ColumnInfo(defaultValue = "0") val isFavorite: Boolean = false
) {

    fun toEmployee(): Employee = Employee(
        id = id,
        image = image,
        name = name,
        nation = nation,
        email = email,
        isFavorite = isFavorite
    )

}