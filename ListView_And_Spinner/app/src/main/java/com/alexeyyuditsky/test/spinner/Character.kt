package com.alexeyyuditsky.test.spinner

class Character(
    val id: Long,
    val name: String,
    val isCustom: Boolean = false
) {
    override fun toString() = name
}