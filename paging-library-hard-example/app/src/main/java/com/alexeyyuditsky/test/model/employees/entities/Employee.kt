package com.alexeyyuditsky.test.model.employees.entities

data class Employee(
    val id: Long,
    val image: String,
    val name: String,
    val nation: String,
    val email: String,
    val age: Int
)