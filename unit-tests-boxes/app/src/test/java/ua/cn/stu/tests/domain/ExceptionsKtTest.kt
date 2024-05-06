package ua.cn.stu.tests.domain

import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class ExceptionsKtTest {

    @get:Rule
    val rule = MockKRule(this)

    @MockK
    private val block = mockk<suspend () -> String>()

    @Test(expected = BackendException::class)
    fun `wrapBackendExceptions call with any code throw BackendException`() = runTest {
        coEvery { block.invoke() } throws BackendException(code = 402, message = "Oops")

        wrapBackendExceptions(block)
    }

    @Test(expected = AuthException::class)
    fun `wrapBackendExceptions call with 401 code throw AuthException`() = runTest {
        coEvery { block.invoke() } throws BackendException(code = 401, message = "Oops")

        wrapBackendExceptions(block)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `wrapBackendExceptions call with throw IllegalArgumentException`() = runTest {
        coEvery { block.invoke() } throws IllegalArgumentException()

        wrapBackendExceptions(block)
    }

}