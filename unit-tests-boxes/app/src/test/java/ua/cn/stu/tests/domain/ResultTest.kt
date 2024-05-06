package ua.cn.stu.tests.domain

import org.junit.Assert.*
import org.junit.Test

class ResultTest {

    @Test
    fun `getValueOrNull call return null for non successful results`() {
        val emptyResult = Empty<String>()
        val pendingResult = Pending<String>()
        val errorResult = Error<String>(Exception())

        val emptyValue = emptyResult.getValueOrNull()
        val pendingValue = pendingResult.getValueOrNull()
        val errorValue = errorResult.getValueOrNull()

        assertNull(emptyValue)
        assertNull(pendingValue)
        assertNull(errorValue)
    }

    @Test
    fun `getValueOrNull call return value for success result`() {
        val successResult = Success("test")

        val value = successResult.getValueOrNull()

        assertEquals("test", value)
    }

    @Test
    fun `isFinished call for Success and Error return true`() {
        val errorResult = Error<String>(Exception())
        val successResult = Success("test")

        val isErrorFinished = errorResult.isFinished()
        val isSuccessfulFinished = successResult.isFinished()

        assertEquals(true, isErrorFinished)
        assertEquals(true, isSuccessfulFinished)
    }

    @Test
    fun `isFinished call for Empty and Pending return false`() {
        val emptyResult = Empty<String>()
        val pendingResult = Pending<String>()

        val isEmptyFinished = emptyResult.isFinished()
        val isPendingFinished = pendingResult.isFinished()

        assertEquals(false, isEmptyFinished)
        assertEquals(false, isPendingFinished)
    }

    @Test
    fun `map call for Error, Empty and Pending`() {
        val exception = Exception()
        val emptyResult = Empty<String>()
        val pendingResult = Pending<String>()
        val errorResult = Error<String>(exception)

        val mappedEmptyResult = emptyResult.map<Int>()
        val mappedPendingResult = pendingResult.map<Int>()
        val mappedErrorResult = errorResult.map<Int>()

        assertTrue(mappedEmptyResult is Empty<Int>)
        assertTrue(mappedPendingResult is Pending<Int>)
        assertTrue(mappedErrorResult is Error<Int>)
        assertSame(exception, (mappedErrorResult as Error<Int>).error)
    }

    @Test(expected = IllegalStateException::class)
    fun `map call without mapper cant convert Success result`() {
        val successResult = Success("test")

        successResult.map<Int>()
    }

    @Test
    fun `map call with Success result`() {
        val successResult = Success("123")

        val mappedResult = successResult.map { it.toInt() }

        assertTrue(mappedResult is Success<Int>)
        assertEquals(123, (mappedResult as Success<Int>).value)
    }

    @Test
    fun `equals call for equal objects`() {
        val exception = IllegalStateException()
        val pending1 = Pending<String>()
        val pending2 = Pending<String>()
        val error1 = Error<String>(exception)
        val error2 = Error<String>(exception)
        val empty1 = Empty<String>()
        val empty2 = Empty<String>()
        val success1 = Success("123")
        val success2 = Success("123")

        assertEquals(pending1, pending2)
        assertEquals(error1, error2)
        assertEquals(empty1, empty2)
        assertEquals(success1, success2)
    }

    @Test
    fun `equals call for unequal objects`() {
        val pending1 = Pending<String>()
        val empty1 = Empty<String>()
        val error1 = Error<String>(IllegalStateException())
        val error2 = Error<String>(IllegalStateException())
        val success1 = Success("123")
        val success2 = Success("456")

        assertNotEquals(pending1, error1)
        assertNotEquals(pending1, empty1)
        assertNotEquals(pending1, success1)
        assertNotEquals(empty1, error1)
        assertNotEquals(empty1, success1)
        assertNotEquals(pending1, success1)
        assertNotEquals(error1, error2)
        assertNotEquals(error1, success1)
        assertNotEquals(success1, success2)
    }

    @Test
    fun `hashcode call`() {
        val exception = IllegalStateException()
        val pending1 = Pending<String>()
        val pending2 = Pending<String>()
        val empty1 = Empty<String>()
        val empty2 = Empty<String>()
        val error1 = Error<String>(exception)
        val error2 = Error<String>(exception)
        val success1 = Success("123")
        val success2 = Success("123")

        assertEquals(pending1.hashCode(), pending2.hashCode())
        assertEquals(empty1.hashCode(), empty2.hashCode())
        assertEquals(error1.hashCode(), error2.hashCode())
        assertEquals(success1.hashCode(), success2.hashCode())
    }

}