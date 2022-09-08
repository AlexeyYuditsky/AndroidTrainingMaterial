package com.alexeyyuditsky.domain.repository

import com.alexeyyuditsky.domain.model.UserName
import com.alexeyyuditsky.domain.model.UserNameParam

interface UserRepository {

    fun getName(): UserName

    fun saveName(param: UserNameParam): Boolean

}