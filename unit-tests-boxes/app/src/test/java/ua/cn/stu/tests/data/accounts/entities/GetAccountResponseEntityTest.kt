package ua.cn.stu.tests.data.accounts.entities

import org.junit.Assert.assertEquals
import org.junit.Test
import ua.cn.stu.tests.domain.accounts.entities.Account

class GetAccountResponseEntityTest {

    @Test
    fun `GetAccountResponseEntity map to Account`() {
        val getAccountResponseEntity = GetAccountResponseEntity(
            id = 5,
            email = "some email",
            username = "some username",
            createdAt = 5555
        )
        val expectedAccount = Account(
            id = 5,
            email = "some email",
            username = "some username",
            createdAt = 5555
        )

        val account = getAccountResponseEntity.toAccount()

        assertEquals(expectedAccount, account)
    }

}