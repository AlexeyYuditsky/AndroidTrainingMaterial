package com.alexeyyuditsky.test.baseAdapter

class Character(
    val id: Long,
    val name: String,
    val isCustom: Boolean = false
) {
    override fun toString() = name
}