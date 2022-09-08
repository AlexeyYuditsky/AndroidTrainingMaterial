package com.alexeyyuditsky.test.domain.repository

import com.alexeyyuditsky.test.domain.model.UserName
import com.alexeyyuditsky.test.domain.model.UserNameParam

interface UserRepository {

    fun getName(): UserName

    fun saveName(param: UserNameParam): Boolean

}