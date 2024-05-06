package ua.cn.stu.tests.domain.accounts.entities

import org.junit.Assert.*
import org.junit.Test
import ua.cn.stu.tests.domain.EmptyFieldException
import ua.cn.stu.tests.domain.Field
import ua.cn.stu.tests.domain.PasswordMismatchException
import kotlin.Exception

class SignUpDataTest {

    @Test
    fun `validate call with blank username throw EmptyFieldException with enum Username`() {
        val signUpData = SignUpData(
            username = " ",
            email = "1",
            password = "1",
            repeatPassword = "1"
        )

        val exception = try {
            signUpData.validate()
        } catch (e: Exception) {
            e
        } as EmptyFieldException

        assertEquals(Field.Username, exception.field)
    }

    @Test
    fun `validate call with blank email throw EmptyFieldException with enum Email`() {
        val signUpData = SignUpData(
            username = "1",
            email = " ",
            password = "1",
            repeatPassword = "1"
        )

        val exception = try {
            signUpData.validate()
        } catch (e: Exception) {
            e
        } as EmptyFieldException

        assertEquals(Field.Email, exception.field)
    }

    @Test
    fun `validate call with blank password throw EmptyFieldException with enum Password`() {
        val signUpData = SignUpData(
            username = "1",
            email = "1",
            password = " ",
            repeatPassword = "1"
        )

        val exception = try {
            signUpData.validate()
        } catch (e: Exception) {
            e
        } as EmptyFieldException

        assertEquals(Field.Password, exception.field)
    }

    @Test(expected = PasswordMismatchException::class)
    fun `validate call with mismatch password throw PasswordMismatchException`() {
        val signUpData = SignUpData(
            username = "1",
            email = "1",
            password = "1",
            repeatPassword = "2"
        )

        signUpData.validate()
    }

    @Test
    fun `validate call with validate data`() {
        val signUpData = SignUpData(
            username = "1",
            email = "1",
            password = "1",
            repeatPassword = "1"
        )

        signUpData.validate()
    }

}