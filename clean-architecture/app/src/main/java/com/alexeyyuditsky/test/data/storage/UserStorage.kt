package com.alexeyyuditsky.test.data.storage

import com.alexeyyuditsky.test.data.storage.model.User

interface UserStorage {

    fun save(user: User): Boolean

    fun get(): User

}