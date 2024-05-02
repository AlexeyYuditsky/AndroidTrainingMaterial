package com.alexeyyuditsky.unittests

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.just
import io.mockk.runs
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.Executor

class ExampleMockk {

    interface Bar {
        fun print(n: Int): Boolean
        fun foo()
        fun getInt(): Int
        fun getBoolean(): Boolean
    }

    @get:Rule
    val rule = MockKRule(this)

    @MockK
    lateinit var executor: Executor

    @MockK
    lateinit var errorHandler: ErrorHandler<String>

    @InjectMockKs
    lateinit var resourceManager: ResourceManager<String>

    @RelaxedMockK
    lateinit var bar: Bar

    @Test
    fun `test`() {
        every { bar.foo() } just runs
        every { bar.getBoolean() } returns true
        every { bar.getInt() } returns 5
        bar.foo()
        bar.getInt()
        bar.getBoolean()


        every { executor.execute(any()) } answers {
            firstArg<Runnable>().run()
        }


        every { bar.print(less(0)) } answers {
            println(false)
            false
        }
        every { bar.print(more(0, true)) } answers {
            println(true)
            true
        }
        bar.print(-1)
        bar.print(0)
        bar.print(1)


        val intSlot = mutableListOf<Int>()
        every { bar.print(capture(intSlot)) } returns true
        bar.print(123)
        bar.print(22)
        bar.print(33)
        println("Captured arg: ${intSlot.joinToString()}")
    }

}