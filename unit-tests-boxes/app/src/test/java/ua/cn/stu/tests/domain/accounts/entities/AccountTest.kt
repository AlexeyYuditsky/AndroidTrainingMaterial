package ua.cn.stu.tests.domain.accounts.entities

import org.junit.Assert.*
import org.junit.Test

class AccountTest {

    @Test
    fun `create Account without property createdAt`() {
        val newAccount = Account(
            id = 1,
            username = "username",
            email = "email"
        )

        val expectedAccount = Account(
            id = 1,
            username = "username",
            email = "email",
            createdAt = Account.UNKNOWN_CREATED_AT
        )
        assertEquals(expectedAccount, newAccount)
    }

    @Test
    fun `create Account with property createdAt`() {
        val newAccount = Account(
            id = 1,
            username = "username",
            email = "email",
            createdAt = 555L
        )

        val expectedAccount = Account(
            id = 1,
            username = "username",
            email = "email",
            createdAt = 555L
        )
        assertEquals(expectedAccount, newAccount)
    }

}