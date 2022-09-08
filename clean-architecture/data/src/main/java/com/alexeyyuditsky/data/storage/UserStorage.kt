package com.alexeyyuditsky.data.storage

import com.alexeyyuditsky.data.storage.model.User

interface UserStorage {

    fun save(user: User): Boolean

    fun get(): User

}