package com.alexeyyuditsky.test.model.employees

data class EmployeeListItem(
    val employee: Employee,
    val inProgress: Boolean
) {
    val id: Long get() = employee.id
    val image: String get() = employee.image
    val name: String get() = employee.name
    val nation: String get() = employee.nation
    val email: String get() = employee.email
    val isFavorite: Boolean get() = employee.isFavorite
}