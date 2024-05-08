package ua.cn.stu.tests.data.boxes.entities

import android.graphics.Color
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ua.cn.stu.tests.domain.boxes.entities.Box
import ua.cn.stu.tests.domain.boxes.entities.BoxAndSettings

class GetBoxResponseEntityTest {


    @Before
    fun setUp() {
        mockkStatic(Color::class)
    }

    @After
    fun tearDown() {
        unmockkStatic(Color::class)
    }

    @Test
    fun `GetBoxResponseEntity map to BoxAndSettings`() {
        val getBoxResponseEntity = GetBoxResponseEntity(
            id = 5,
            colorName = "RED",
            colorValue = "#ff0000",
            isActive = true
        )
        every { Color.parseColor(any()) } returns Color.RED

        val boxAndSettings = getBoxResponseEntity.toBoxAndSettings()

        val expectedBoxAndSettings = BoxAndSettings(
            box = Box(
                id = 5,
                colorName = "RED",
                colorValue = Color.RED
            ),
            isActive = true
        )
        assertEquals(expectedBoxAndSettings, boxAndSettings)
    }

}