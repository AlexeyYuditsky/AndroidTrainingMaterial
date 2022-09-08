package com.alexeyyuditsky.data.repository

import com.alexeyyuditsky.data.storage.model.User
import com.alexeyyuditsky.data.storage.UserStorage
import com.alexeyyuditsky.domain.model.UserName
import com.alexeyyuditsky.domain.model.UserNameParam
import com.alexeyyuditsky.domain.repository.UserRepository

class UserRepositoryImpl(private val userStorage: UserStorage) : UserRepository {

    override fun getName(): UserName {
        val user = userStorage.get()
        return mapToDomain(user)
    }

    override fun saveName(param: UserNameParam): Boolean {
        val user = mapToStorage(param)
        userStorage.save(user)
        return true
    }


    private fun mapToStorage(param: UserNameParam): User {
        return User(firstName = param.name, lastName = "")
    }

    private fun mapToDomain(user: User): UserName {
        return UserName(firstName = user.firstName, lastName = user.lastName)
    }

}