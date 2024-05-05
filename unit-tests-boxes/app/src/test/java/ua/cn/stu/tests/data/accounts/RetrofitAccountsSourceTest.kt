package ua.cn.stu.tests.data.accounts

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.mockk.called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import ua.cn.stu.tests.data.accounts.entities.GetAccountResponseEntity
import ua.cn.stu.tests.data.accounts.entities.SignInRequestEntity
import ua.cn.stu.tests.data.accounts.entities.SignInResponseEntity
import ua.cn.stu.tests.data.accounts.entities.SignUpRequestEntity
import ua.cn.stu.tests.data.accounts.entities.UpdateUsernameRequestEntity
import ua.cn.stu.tests.data.base.RetrofitConfig
import ua.cn.stu.tests.domain.accounts.entities.Account
import ua.cn.stu.tests.domain.accounts.entities.SignUpData

class RetrofitAccountsSourceTest {

    @get:Rule
    val rule = MockKRule(this)

    @MockK
    lateinit var accountsApi: AccountsApi

    @Test
    fun `signIn call and return token`() = runTest {
        val requestEntity = SignInRequestEntity(
            email = "email",
            password = "password"
        )
        coEvery { accountsApi.signIn(requestEntity) } returns SignInResponseEntity("token")
        val retrofitAccountSource = createRetrofitAccountSource()

        val token = retrofitAccountSource.signIn(
            email = "email",
            password = "password"
        )

        assertEquals("token", token)
        coVerify(exactly = 1) { accountsApi.signIn(requestEntity) }
        confirmVerified(accountsApi)
    }

    @Test
    fun `signIn call with wrapRetrofitException call`() = runTest {
        val retrofitAccountSource = spyk(createRetrofitAccountSource())
        val requestEntity = SignInRequestEntity(
            email = "email",
            password = "password"
        )
        coEvery { accountsApi.signIn(requestEntity) } returns SignInResponseEntity("token")
        val slot = slot<suspend () -> String>()
        coEvery { retrofitAccountSource.wrapRetrofitExceptions(capture(slot)) } returns ""

        retrofitAccountSource.signIn(
            email = "email",
            password = "password"
        )
        val token = slot.captured.invoke()

        assertEquals("token", token)
    }

    @Test
    fun `signUp call`() = runTest {
        val retrofitAccountsSource = createRetrofitAccountSource()
        val signUpData = SignUpData(
            username = "username",
            password = "password",
            email = "email",
            repeatPassword = "repeatPassword",
        )
        val requestEntity = SignUpRequestEntity(
            email = signUpData.email,
            password = signUpData.password,
            username = signUpData.username
        )
        coEvery { accountsApi.signUp(requestEntity) } just runs

        retrofitAccountsSource.signUp(signUpData)

        coVerify(exactly = 1) { accountsApi.signUp(requestEntity) }
        confirmVerified(accountsApi)
    }

    @Test
    fun `signUp call with wrapRetrofitException call`() = runTest {
        val retrofitAccountsSource = spyk(createRetrofitAccountSource())
        val signUpData = SignUpData(
            username = "username",
            password = "password",
            email = "email",
            repeatPassword = "repeatPassword",
        )
        val requestEntity = SignUpRequestEntity(
            email = signUpData.email,
            password = signUpData.password,
            username = signUpData.username
        )
        coEvery { accountsApi.signUp(requestEntity) } just runs
        val slot = slot<suspend () -> Unit>()
        coEvery { retrofitAccountsSource.wrapRetrofitExceptions(capture(slot)) } just runs

        retrofitAccountsSource.signUp(signUpData)

        verify { accountsApi wasNot called }
        slot.captured.invoke()
        coVerify { accountsApi.signUp(requestEntity) }
    }

    @Test
    fun `getAccount call`() = runTest {
        val retrofitAccountsSource = createRetrofitAccountSource()
        val getAccountResponseEntity = mockk<GetAccountResponseEntity>()
        val expectedAccount = Account(
            id = 1,
            username = "username",
            email = "email"
        )
        coEvery { accountsApi.getAccount() } returns getAccountResponseEntity
        every { getAccountResponseEntity.toAccount() } returns expectedAccount

        val account = retrofitAccountsSource.getAccount()

        assertSame(expectedAccount, account)
        coVerify(exactly = 1) { accountsApi.getAccount() }
        confirmVerified(accountsApi)
    }

    @Test
    fun `getAccount call with wrapRetrofitException call`() = runTest {
        val retrofitAccountsSource = spyk(createRetrofitAccountSource())
        val getAccountResponseEntity = mockk<GetAccountResponseEntity>()
        val expectedAccount = Account(
            id = 1,
            username = "username",
            email = "email"
        )
        every { getAccountResponseEntity.toAccount() } returns expectedAccount
        coEvery { accountsApi.getAccount() } returns getAccountResponseEntity
        val slot = slot<suspend () -> Account>()
        coEvery { retrofitAccountsSource.wrapRetrofitExceptions(capture(slot)) } returns expectedAccount

        retrofitAccountsSource.getAccount()
        val account = slot.captured.invoke()

        assertSame(expectedAccount, account)
    }

    @Test
    fun `setUsername call`() = runTest {
        val username = "username"
        val requestUsername = UpdateUsernameRequestEntity(username = username)
        val retrofitAccountsSource = createRetrofitAccountSource()
        coEvery { accountsApi.setUsername(requestUsername) } just runs

        retrofitAccountsSource.setUsername(username)

        coVerify(exactly = 1) { accountsApi.setUsername(requestUsername) }
        confirmVerified(accountsApi)
    }

    @Test
    fun `setUsername call with wrapRetrofitException call`() = runTest {
        val retrofitAccountsSource = spyk(createRetrofitAccountSource())
        val username = "username"
        val requestUsername = UpdateUsernameRequestEntity(username = username)
        val slot = slot<suspend () -> Unit>()
        coEvery { retrofitAccountsSource.wrapRetrofitExceptions(capture(slot)) } just runs
        coEvery { accountsApi.setUsername(requestUsername) } just runs

        retrofitAccountsSource.setUsername(username)

        coVerify { accountsApi wasNot called }
        slot.captured.invoke()
        coVerify { accountsApi.setUsername(requestUsername) }
    }

    private fun createRetrofitAccountSource() = RetrofitAccountsSource(
        config = RetrofitConfig(
            retrofit = createRetrofit(),
            moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        )
    )

    private fun createRetrofit(): Retrofit {
        val retrofit = mockk<Retrofit>()
        every { retrofit.create(AccountsApi::class.java) } returns accountsApi
        return retrofit
    }

}