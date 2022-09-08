package com.alexeyyuditsky.test.domain.model

class UserName(
    val firstName:String,
    val lastName:String
) {

    override fun toString(): String {
        return "$firstName $lastName"
    }

}