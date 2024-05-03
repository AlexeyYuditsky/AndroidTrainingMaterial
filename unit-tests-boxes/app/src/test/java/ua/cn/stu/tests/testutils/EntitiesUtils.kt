package ua.cn.stu.tests.testutils

import ua.cn.stu.tests.domain.accounts.entities.Account

fun createAccount(
    id: Long = 1,
    username: String = "username",
    email: String = "email"
) = Account(
    id = id,
    username = username,
    email = email,
    createdAt = Account.UNKNOWN_CREATED_AT
)