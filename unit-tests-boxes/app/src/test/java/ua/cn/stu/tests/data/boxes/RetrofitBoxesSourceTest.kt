package ua.cn.stu.tests.data.boxes

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import ua.cn.stu.tests.data.base.RetrofitConfig
import ua.cn.stu.tests.domain.boxes.entities.BoxesFilter

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
    fun `getBoxes call with only active boxes`() = runTest {
        val filter = BoxesFilter.ONLY_ACTIVE

        retrofitBoxesSource.getBoxes(filter)

        coVerify(exactly = 1) { boxesApi.getBoxes(true) }
        confirmVerified(boxesApi)
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