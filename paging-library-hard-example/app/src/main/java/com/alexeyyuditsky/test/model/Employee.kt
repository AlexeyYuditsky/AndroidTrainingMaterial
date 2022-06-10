package com.alexeyyuditsky.test.model

data class Employee(
    private val id: Long,
    private val image: String,
    private val name: String,
    private val nation: String,
    private val email: String,
    private val age: Int
)