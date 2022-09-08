package com.alexeyyuditsky.domain.model

class UserName(
    val firstName:String,
    val lastName:String
) {

    override fun toString(): String {
        return "$firstName $lastName"
    }

}