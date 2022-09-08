package com.alexeyyuditsky.domain.usecase

import com.alexeyyuditsky.domain.repository.UserRepository
import com.alexeyyuditsky.domain.model.UserNameParam

class SaveUserNameUseCase(private val userRepository: UserRepository) {

    fun execute(param: UserNameParam): Boolean {
        if (param.name == userRepository.getName().firstName) return false
        userRepository.saveName(param = param)
        return true
    }

}