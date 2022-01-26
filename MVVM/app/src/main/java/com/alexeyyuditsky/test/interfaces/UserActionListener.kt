package com.alexeyyuditsky.test.interfaces

import com.alexeyyuditsky.test.model.User

interface UserActionListener {

    fun onUserMove(user: User, moveBy: Int)

    fun onUserDelete(user: User)

    fun onUserDetails(user: User)

}