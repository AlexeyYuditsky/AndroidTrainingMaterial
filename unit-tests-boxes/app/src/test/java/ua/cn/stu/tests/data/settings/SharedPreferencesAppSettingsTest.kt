package ua.cn.stu.tests.data.settings

import android.content.Context
import android.content.SharedPreferences
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.verify
import io.mockk.verifySequence
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SharedPreferencesAppSettingsTest {

    @get:Rule
    val rule = MockKRule(this)

    @MockK
    lateinit var context: Context

    @MockK
    lateinit var sharedPreferences: SharedPreferences

    @RelaxedMockK
    lateinit var editor: SharedPreferences.Editor

    private lateinit var settings : SharedPreferencesAppSettings

    private val expectedTokenKey = "currentToken"

    @Before
    fun setUp() {
        every { context.getSharedPreferences(any(), any()) } returns sharedPreferences
        every { sharedPreferences.edit() } returns editor
        settings = SharedPreferencesAppSettings(context)
    }

    @Test
    fun `setCurrentToken call put token value to preferences`() {
        settings.setCurrentToken(token = "token")

        verifySequence {
            sharedPreferences.edit()
            editor.putString(expectedTokenKey, "token")
            editor.apply()
        }
    }

    @Test
    fun `setCurrentToken call delete token value from preferences`() {
        settings.setCurrentToken(token = null)

        verifySequence {
            sharedPreferences.edit()
            editor.remove(expectedTokenKey)
            editor.apply()
        }
    }

    @Test
    fun `getCurrentToken call return token value from preferences`() {
        every { sharedPreferences.getString(any(), any()) } returns "token"

        val token = settings.getCurrentToken()

        assertEquals("token", token)
        verify(exactly = 1) { sharedPreferences.getString(expectedTokenKey, null) }
        confirmVerified(sharedPreferences)
    }
}