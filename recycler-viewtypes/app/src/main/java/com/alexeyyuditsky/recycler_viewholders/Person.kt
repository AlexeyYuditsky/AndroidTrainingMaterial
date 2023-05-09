package com.alexeyyuditsky.recycler_viewholders

sealed class Person {
    data class Student(val name: String, val age: Int) : Person()
    data class Teacher(val name: String, val age: Int, val subject: String) : Person()
}