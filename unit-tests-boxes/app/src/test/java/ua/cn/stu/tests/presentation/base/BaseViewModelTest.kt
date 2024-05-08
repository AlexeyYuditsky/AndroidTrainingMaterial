package ua.cn.stu.tests.presentation.base

import io.mockk.confirmVerified
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import ua.cn.stu.tests.testutils.ViewModelTest

@ExperimentalCoroutinesApi
class BaseViewModelTest : ViewModelTest() {

    @InjectMockKs
    private lateinit var viewModel: BaseViewModel

    @Test
    fun `logError call`() {
        val exception = IllegalArgumentException()

        viewModel.logError(exception)

        verify(exactly = 1) { logger.error(any(), refEq(exception)) }
        confirmVerified(logger)
    }

    @Test
    fun `logout call`() {
        viewModel.logout()

        verify(exactly = 1) { accountsRepository.logout() }
        confirmVerified(accountsRepository)
    }

}