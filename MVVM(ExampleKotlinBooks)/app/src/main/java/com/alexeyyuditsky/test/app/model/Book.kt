package com.alexeyyuditsky.test.app.model

data class Book(
    val id: Long,
    val image: String,
    val name: String,
    val authors: String,
    val year: String,
    val description: String,
)