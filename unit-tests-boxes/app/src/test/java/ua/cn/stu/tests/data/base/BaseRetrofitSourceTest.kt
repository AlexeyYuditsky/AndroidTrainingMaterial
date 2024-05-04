package ua.cn.stu.tests.data.base

import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import ua.cn.stu.tests.domain.AppException
import ua.cn.stu.tests.domain.BackendException
import ua.cn.stu.tests.domain.ConnectionException
import ua.cn.stu.tests.domain.ParseBackendResponseException
import java.io.IOException

class BaseRetrofitSourceTest {

    @Test
    fun `getRetrofit call returns instance from config`() {
        val expectedRetrofit = mockk<Retrofit>()
        val source = createBaseRetrofitSource(retrofit = expectedRetrofit)

        val retrofit = source.retrofit

        assertSame(expectedRetrofit, retrofit)
    }

    @Test
    fun `wrapRetrofitExceptions call receive result from block of lambda`() = runTest {
        val source = createBaseRetrofitSource()
        val block = createMockedBlock()
        coEvery { block.invoke() } returns "TEST"

        val result = source.wrapRetrofitExceptions(block)

        assertEquals("TEST", result)
    }

    @Test(expected = AppException::class)
    fun `wrapRetrofitExceptions call with throw RuntimeException rethrow AppException`() =
        runTest {
            val source = createBaseRetrofitSource()
            val block = createMockedBlock()
            coEvery { block.invoke() } throws RuntimeException()

            source.wrapRetrofitExceptions(block)
        }

    @Test(expected = ParseBackendResponseException::class)
    fun `wrapRetrofitExceptions call with throw JsonDataException rethrow ParseBackendResponseException`() =
        runTest {
            val source = createBaseRetrofitSource()
            val block = createMockedBlock()
            coEvery { block.invoke() } throws JsonDataException()

            source.wrapRetrofitExceptions(block)
        }

    @Test(expected = ParseBackendResponseException::class)
    fun `wrapRetrofitExceptions call with throw JsonEncodingException rethrow ParseBackendResponseException`() =
        runTest {
            val source = createBaseRetrofitSource()
            val block = createMockedBlock()
            coEvery { block.invoke() } throws JsonEncodingException("Boom")

            source.wrapRetrofitExceptions(block)
        }

    @Test(expected = BackendException::class)
    fun `wrapRetrofitExceptions call with throw HttpException rethrow BackendException`() =
        runTest {
            val source = createBaseRetrofitSource()
            val block = createMockedBlock()
            val httpException = mockk<HttpException>()
            val response = mockk<Response<*>>()
            val errorBody = mockk<ResponseBody>()
            val errorJson = "{\"error\": \"Oops\"}"
            coEvery { block.invoke() } throws httpException
            every { httpException.response() } returns response
            every { httpException.code() } returns 409
            every { response.errorBody() } returns errorBody
            every { errorBody.string() } returns errorJson

            source.wrapRetrofitExceptions(block)
        }

    @Test(expected = ConnectionException::class)
    fun `wrapRetrofitExceptions call with throw IOException rethrow ConnectionException`() =
        runTest {
            val source = createBaseRetrofitSource()
            val block = createMockedBlock()
            coEvery { block.invoke() } throws IOException()

            source.wrapRetrofitExceptions(block)
        }

    private fun createMockedBlock(): suspend () -> String {
        return mockk()
    }

    private fun createBaseRetrofitSource(
        retrofit: Retrofit = mockk()
    ) = BaseRetrofitSource(
        retrofitConfig = RetrofitConfig(
            retrofit = retrofit,
            moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        )
    )

}