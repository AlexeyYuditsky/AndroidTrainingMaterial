package com.alexeyyuditsky.test.domain.usecase

import com.alexeyyuditsky.test.domain.repository.UserRepository
import com.alexeyyuditsky.test.domain.model.UserNameParam

class SaveUserNameUseCase(private val userRepository: UserRepository) {

    fun execute(param: UserNameParam): Boolean {
        if (param.name == userRepository.getName().firstName) return false
        userRepository.saveName(param = param)
        return true
    }

}