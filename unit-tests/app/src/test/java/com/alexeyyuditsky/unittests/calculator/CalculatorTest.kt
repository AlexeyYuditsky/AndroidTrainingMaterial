package com.alexeyyuditsky.unittests.calculator

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CalculatorTest {

    private val delta = 0.00001

    private lateinit var calculator: Calculator

    @Before
    fun setUp() {
        calculator = Calculator()
        calculator.init()
    }

    @After
    fun cleanUp() {
        calculator.destroy()
    }

    @Test(expected = IllegalStateException::class)
    fun `divide() method with b=0 should throw IllegalStateException`() {
        calculator.divide(10.0, 0.0)
    }

    @Test
    fun subtractCalculatesSubtraction() {
        val result = calculator.subtract(3.0, 2.0)
        Assert.assertEquals(1.0, result, delta)
    }

    @Test
    fun sumCalculatesSum() {
        val result = calculator.sum(2.0, 2.0)
        Assert.assertEquals(4.0, result, delta)
    }

}