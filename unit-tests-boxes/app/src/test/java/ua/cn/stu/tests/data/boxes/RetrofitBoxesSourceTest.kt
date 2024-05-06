package ua.cn.stu.tests.data.boxes

import android.graphics.Color
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import ua.cn.stu.tests.data.base.RetrofitConfig
import ua.cn.stu.tests.data.boxes.entities.GetBoxResponseEntity
import ua.cn.stu.tests.data.boxes.entities.UpdateBoxRequestEntity
import ua.cn.stu.tests.domain.AppException
import ua.cn.stu.tests.domain.BackendException
import ua.cn.stu.tests.domain.ConnectionException
import ua.cn.stu.tests.domain.ParseBackendResponseException
import ua.cn.stu.tests.domain.boxes.entities.Box
import ua.cn.stu.tests.domain.boxes.entities.BoxAndSettings
import ua.cn.stu.tests.domain.boxes.entities.BoxesFilter
import java.io.IOException
import java.lang.RuntimeException

class RetrofitBoxesSourceTest {

    @get:Rule
    val rule = MockKRule(this)

    @RelaxedMockK
    lateinit var boxesApi: BoxesApi

    private lateinit var retrofitBoxesSource: RetrofitBoxesSource

    @Before
    fun setUp() {
        retrofitBoxesSource = createRetrofitBoxesSource()
    }

    @Test
    fun `getBoxes call with receive active boxes`() = runTest {
        val filter = BoxesFilter.ONLY_ACTIVE

        retrofitBoxesSource.getBoxes(filter)

        coVerify(exactly = 1) { boxesApi.getBoxes(true) }
        confirmVerified(boxesApi)
    }

    @Test
    fun `getBoxes call with receive all boxes`() = runTest {
        val filter = BoxesFilter.ALL

        retrofitBoxesSource.getBoxes(filter)

        coVerify(exactly = 1) { boxesApi.getBoxes(null) }
        confirmVerified(boxesApi)
    }

    @Test
    fun `getBoxes call return boxes from endpoint`() = runTest {
        val expectedBox1 = BoxAndSettings(
            box = Box(id = 1, colorName = "Red", colorValue = Color.RED),
            isActive = false
        )
        val expectedBox2 = BoxAndSettings(
            box = Box(id = 3, colorName = "Green", colorValue = Color.GREEN),
            isActive = false
        )
        val box1Response = mockk<GetBoxResponseEntity>()
        val box2Response = mockk<GetBoxResponseEntity>()
        every { box1Response.toBoxAndSettings() } returns expectedBox1
        every { box2Response.toBoxAndSettings() } returns expectedBox2
        coEvery { boxesApi.getBoxes(any()) } returns listOf(box1Response, box2Response)

        val boxes = retrofitBoxesSource.getBoxes(BoxesFilter.ALL)

        assertEquals(boxes.size, 2)
        assertEquals(boxes[0], expectedBox1)
        assertEquals(boxes[1], expectedBox2)
    }

    @Test(expected = AppException::class)
    fun `getBoxes call with throw RuntimeException rethrow AppException`() = runTest {
        coEvery { boxesApi.getBoxes(any()) } throws RuntimeException()

        retrofitBoxesSource.getBoxes(BoxesFilter.ALL)
    }

    @Test(expected = ConnectionException::class)
    fun `getBoxes call with throw IOException rethrow ConnectionException`() = runTest {
        coEvery { boxesApi.getBoxes(any()) } throws IOException()

        retrofitBoxesSource.getBoxes(BoxesFilter.ALL)
    }

    @Test(expected = BackendException::class)
    fun `getBoxes call with throw HttpException rethrow BackendException`() = runTest {
        val httpException = mockk<HttpException>()
        val response = mockk<Response<*>>()
        val errorBody = mockk<ResponseBody>()
        val errorJson = "{\"error\": \"Oops\"}"
        every { httpException.response() } returns response
        every { httpException.code() } returns 409
        every { response.errorBody() } returns errorBody
        every { errorBody.string() } returns errorJson
        coEvery { boxesApi.getBoxes(any()) } throws httpException

        retrofitBoxesSource.getBoxes(BoxesFilter.ALL)
    }

    @Test(expected = ParseBackendResponseException::class)
    fun `getBoxes call with throw JsonEncodingException rethrow ParseBackendResponseException`() =
        runTest {
            coEvery { boxesApi.getBoxes(any()) } throws JsonEncodingException("Boom")

            retrofitBoxesSource.getBoxes(BoxesFilter.ALL)
        }

    @Test(expected = ParseBackendResponseException::class)
    fun `getBoxes call with throw JsonDataException rethrow ParseBackendResponseException`() =
        runTest {
            coEvery { boxesApi.getBoxes(any()) } throws JsonDataException()

            retrofitBoxesSource.getBoxes(BoxesFilter.ALL)
        }

    @Test
    fun `setIsActive call`() = runTest {
        retrofitBoxesSource.setIsActive(5L, true)

        coVerify(exactly = 1) { boxesApi.setIsActive(5L, UpdateBoxRequestEntity(true)) }
        confirmVerified(boxesApi)
    }

    @Test(expected = AppException::class)
    fun `setIsActive call with throw RuntimeException rethrow AppException`() = runTest {
        coEvery { boxesApi.setIsActive(any(), any()) } throws RuntimeException()

        retrofitBoxesSource.setIsActive(5L, true)
    }

    @Test(expected = ConnectionException::class)
    fun `setIsActive call with throw IOException rethrow ConnectionException`() = runTest {
        coEvery { boxesApi.setIsActive(any(), any()) } throws IOException()

        retrofitBoxesSource.setIsActive(5L, true)
    }

    @Test(expected = BackendException::class)
    fun `setIsActive call with throw HttpException rethrow BackendException`() = runTest {
        val httpException = mockk<HttpException>()
        val response = mockk<Response<*>>()
        val errorBody = mockk<ResponseBody>()
        val errorJson = "{\"error\": \"Oops\"}"
        every { httpException.code() } returns 409
        every { httpException.response() } returns response
        every { response.errorBody() } returns errorBody
        every { errorBody.string() } returns errorJson
        coEvery { boxesApi.setIsActive(any(), any()) } throws httpException

        retrofitBoxesSource.setIsActive(5L, true)
    }

    @Test(expected = ParseBackendResponseException::class)
    fun `setIsActive call with throw JsonEncodingException rethrow ParseBackendResponseException`() =
        runTest {
            coEvery { boxesApi.setIsActive(any(), any()) } throws JsonEncodingException("Boom")

            retrofitBoxesSource.setIsActive(5L, true)
        }

    @Test(expected = ParseBackendResponseException::class)
    fun `setIsActive call with throw JsonDataException rethrow ParseBackendResponseException`() =
        runTest {
            coEvery { boxesApi.setIsActive(any(), any()) } throws JsonDataException()

            retrofitBoxesSource.setIsActive(5L, true)
        }

    private fun createRetrofitBoxesSource(): RetrofitBoxesSource {
        return RetrofitBoxesSource(
            config = RetrofitConfig(
                retrofit = createRetrofit(),
                moshi = createMoshi()
            )
        )
    }

    private fun createRetrofit(): Retrofit {
        val retrofit = mockk<Retrofit>()
        every { retrofit.create(BoxesApi::class.java) } returns boxesApi
        return retrofit
    }

    private fun createMoshi(): Moshi {
        return Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    }

}