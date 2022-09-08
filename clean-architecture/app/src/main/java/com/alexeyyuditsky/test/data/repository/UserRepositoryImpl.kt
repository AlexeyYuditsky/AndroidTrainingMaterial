package com.alexeyyuditsky.test.data.repository

import com.alexeyyuditsky.test.data.storage.model.User
import com.alexeyyuditsky.test.data.storage.UserStorage
import com.alexeyyuditsky.test.domain.repository.UserRepository
import com.alexeyyuditsky.test.domain.model.UserName
import com.alexeyyuditsky.test.domain.model.UserNameParam

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