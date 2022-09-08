package com.alexeyyuditsky.test.domain.usecase

import com.alexeyyuditsky.test.domain.repository.UserRepository
import com.alexeyyuditsky.test.domain.model.UserName

class GetUserNameUseCase(private val userRepository: UserRepository) {

    fun execute(): UserName {
        return userRepository.getName()
    }

}