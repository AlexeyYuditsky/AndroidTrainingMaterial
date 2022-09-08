package com.alexeyyuditsky.domain.usecase

import com.alexeyyuditsky.domain.repository.UserRepository
import com.alexeyyuditsky.domain.model.UserName

class GetUserNameUseCase(private val userRepository: UserRepository) {

    fun execute(): UserName {
        return userRepository.getName()
    }

}