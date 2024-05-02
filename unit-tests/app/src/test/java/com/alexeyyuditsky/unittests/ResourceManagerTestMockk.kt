package com.alexeyyuditsky.unittests

import io.mockk.called
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import io.mockk.verifySequence
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.concurrent.Executor

class ResourceManagerTestMockk {

    @Test
    fun `consumeResource call after setResource call receives resource`() {
        val resourceManager = createResourceManager()
        val consumer = createConsumer()

        resourceManager.setResource("TEST")
        resourceManager.consumeResource(consumer)

        verify(exactly = 1) { consumer.invoke("TEST") }
        confirmVerified(consumer)
    }

    @Test
    fun `consumeResource calls after setResource call receive resource for each consumer`() {
        val resourceManager = createResourceManager()
        val listConsumer = List(10) { createConsumer() }

        resourceManager.setResource("TEST")
        listConsumer.forEach { consumer ->
            resourceManager.consumeResource(consumer)
        }

        listConsumer.forEach { consumer ->
            verify(exactly = 1) { consumer.invoke("TEST") }
            confirmVerified(consumer)
        }
    }

    @Test
    fun `consumeResource call after setResource call receives latest resource`() {
        val resourceManager = createResourceManager()
        val consumer = createConsumer()

        resourceManager.setResource("TEST1")
        resourceManager.setResource("TEST2")
        resourceManager.consumeResource(consumer)

        verify(exactly = 1) { consumer.invoke("TEST2") }
        confirmVerified(consumer)
    }

    @Test
    fun `consumeResource calls with same consumer can receive the same resource`() {
        val resourceManager = createResourceManager()
        val consumer = createConsumer()

        resourceManager.setResource("TEST")
        resourceManager.consumeResource(consumer)
        resourceManager.consumeResource(consumer)

        verify(exactly = 2) { consumer.invoke("TEST") }
        confirmVerified(consumer)
    }

    @Test
    fun `consumeResource call without active resource does nothing`() {
        val resourceManager = createResourceManager()
        val consumer = createConsumer()

        resourceManager.consumeResource(consumer)

        verify { consumer wasNot called }
    }

    @Test
    fun `setResource call after consumeResource call delivers resource to consumer`() {
        val resourceManager = createResourceManager()
        val consumer = createConsumer()

        resourceManager.consumeResource(consumer)
        resourceManager.setResource("TEST")

        verify(exactly = 1) { consumer.invoke("TEST") }
        confirmVerified(consumer)
    }

    @Test
    fun `consumeResource call receives resource only once`() {
        val resourceManager = createResourceManager()
        val consumer = createConsumer()

        resourceManager.setResource("TEST1")
        resourceManager.consumeResource(consumer)
        resourceManager.setResource("TEST2")

        verify(exactly = 1) { consumer.invoke("TEST1") }
        confirmVerified(consumer)
    }

    @Test
    fun `consumerResource calls with same consumer can receive multiple resources`() {
        val resourceManager = createResourceManager()
        val consumer = createConsumer()

        resourceManager.setResource("TEST1")
        resourceManager.consumeResource(consumer)
        resourceManager.setResource("TEST2")
        resourceManager.consumeResource(consumer)

        verifySequence {
            consumer.invoke("TEST1")
            consumer.invoke("TEST2")
        }
    }

    @Test
    fun `setResource call after multiple consumeResource calls delivers resource to all consumers`() {
        val resourceManager = createResourceManager()
        val consumerList = List(10) { createConsumer() }

        consumerList.forEach { consumer ->
            resourceManager.consumeResource(consumer)
        }
        resourceManager.setResource("TEST")

        consumerList.forEach { consumer ->
            verify { consumer.invoke("TEST") }
            confirmVerified(consumer)
        }
    }

    @Test
    fun `setResource calls after consumeResource call delivers the first resource once`() {
        val resourceManager = createResourceManager()
        val consumer = createConsumer()

        resourceManager.consumeResource(consumer)
        resourceManager.setResource("TEST1")
        resourceManager.setResource("TEST2")

        verify(exactly = 1) {
            consumer.invoke("TEST1")
        }
        confirmVerified(consumer)
    }

    @Test
    fun `setResource call between consumeResource calls delivers the same resource to all consumers`() {
        val resourceManager = createResourceManager()
        val consumer = createConsumer()

        resourceManager.consumeResource(consumer)
        resourceManager.setResource("TEST")
        resourceManager.consumeResource(consumer)

        verifySequence {
            consumer.invoke("TEST")
            consumer.invoke("TEST")
        }
    }

    @Test
    fun `setResource double call between consumeResource calls delivers different resources`() {
        val resourceManager = createResourceManager()
        val consumer = createConsumer()

        resourceManager.consumeResource(consumer)
        resourceManager.setResource("TEST1")
        resourceManager.setResource("TEST2")
        resourceManager.consumeResource(consumer)

        verifySequence {
            consumer.invoke("TEST1")
            consumer.invoke("TEST2")
        }
    }

    @Test
    fun `consumeResource call after clearResource call`() {
        val resourceManager = createResourceManager()
        val consumer = createConsumer()

        resourceManager.setResource("TEST")
        resourceManager.clearResource()
        resourceManager.consumeResource(consumer)

        verify { consumer wasNot called }
    }

    @Test
    fun `consumeResource call after clearResource and setResource calls receives latest resource`() {
        val resourceManager = createResourceManager()
        val consumer = createConsumer()

        resourceManager.setResource("TEST1")
        resourceManager.clearResource()
        resourceManager.setResource("TEST2")
        resourceManager.consumeResource(consumer)

        verify(exactly = 1) { consumer.invoke("TEST2") }
        confirmVerified(consumer)
    }

    @Test
    fun `setResource call after consumeResource and clearResource calls delivers latest resource`() {
        val resourceManager = createResourceManager()
        val consumer = createConsumer()

        resourceManager.setResource("TEST1")
        resourceManager.clearResource()
        resourceManager.consumeResource(consumer)
        resourceManager.setResource("TEST2")

        verify(exactly = 1) { consumer.invoke("TEST2") }
        confirmVerified(consumer)
    }

    @Test
    fun `destroy call clears current resource`() {
        val resourceManager = createResourceManager()
        val consumer = createConsumer()

        resourceManager.setResource("TEST")
        resourceManager.destroy()
        resourceManager.consumeResource(consumer)

        verify { consumer wasNot called }
    }

    @Test
    fun `destroy call clears pending consumers`() {
        val resourceManager = createResourceManager()
        val consumer = createConsumer()

        resourceManager.consumeResource(consumer)
        resourceManager.destroy()
        resourceManager.setResource("TEST")

        verify { consumer wasNot called }
    }

    @Test
    fun `setResource call after destroy call does nothing`() {
        val resourceManager = createResourceManager()
        val consumer = createConsumer()

        resourceManager.destroy()
        resourceManager.setResource("TEST")
        resourceManager.consumeResource(consumer)

        verify { consumer wasNot called }
    }

    @Test
    fun `consumeResource call after destroy call does nothing`() {
        val resourceManager = createResourceManager()
        val consumer = createConsumer()

        resourceManager.destroy()
        resourceManager.setResource("TEST")
        resourceManager.consumeResource(consumer)

        verify { consumer wasNot called }
    }

    @Test(expected = Test.None::class)
    fun `setResource handles concurrent consumers modification`() {
        val resourceManager = createResourceManager()
        val consumer = createConsumer()

        resourceManager.consumeResource {
            resourceManager.clearResource()
            resourceManager.consumeResource(consumer)
        }
        resourceManager.setResource("TEST")

        verify(exactly = 1) { consumer.invoke("TEST") }
        confirmVerified(consumer)
    }

    @Test
    fun `consumeResource call delivers exceptions to errorHandler`() {
        val errorHandler = mockk<ErrorHandler<String>>()
        every { errorHandler.onError(any(), any()) } just runs
        val resourceManager = createResourceManager(errorHandler = errorHandler)
        val expectedException = IllegalStateException("Test exception")

        resourceManager.setResource("TEST")
        resourceManager.consumeResource { throw expectedException }

        verify(exactly = 1) {
            errorHandler.onError(refEq(expectedException), "TEST")
        }
        confirmVerified(errorHandler)
    }

    @Test
    fun `setResource call delivers exceptions to errorHandler`() {
        val errorHandler = mockk<ErrorHandler<String>>()
        every { errorHandler.onError(any(), any()) } just runs
        val resourceManager = createResourceManager(errorHandler = errorHandler)
        val expectedException = IllegalStateException("Test exception")

        resourceManager.consumeResource { throw expectedException }
        resourceManager.setResource("TEST")

        verify(exactly = 1) {
            errorHandler.onError(refEq(expectedException), "TEST")
        }
        confirmVerified(errorHandler)
    }

    @Test
    fun `setResource call delivers exceptions to errorHandler and further operation of consumer in normal mode`() {
        val errorHandler = mockk<ErrorHandler<String>>()
        every { errorHandler.onError(any(), any()) } just runs
        val resourceManager = createResourceManager(errorHandler = errorHandler)
        val expectedException = IllegalStateException("Test exception")
        val consumer = createConsumer()

        resourceManager.consumeResource { throw expectedException }
        resourceManager.setResource("TEST")
        resourceManager.consumeResource(consumer)

        verifySequence {
            errorHandler.onError(refEq(expectedException), "TEST")
            consumer.invoke("TEST")
        }
    }

    @Test
    fun `consumeResource invokes consumer in executor`() {
        val executor = mockk<Executor>()
        val commandSlot = slot<Runnable>()
        every { executor.execute(capture(commandSlot)) } just runs
        val resourceManager = createResourceManager(executor = executor)
        val consumer = createConsumer()

        resourceManager.setResource("TEST")
        resourceManager.consumeResource(consumer)

        verify { consumer wasNot called }
        verify(exactly = 1) { executor.execute(any()) }
        assertTrue(commandSlot.isCaptured)
        commandSlot.captured.run()
        verify(exactly = 1) { consumer.invoke("TEST") }
        confirmVerified(consumer)
    }

    @Test
    fun `setResource invokes pending consumer in executor`() {
        val executor = mockk<Executor>()
        val commandSlot = slot<Runnable>()
        every { executor.execute(capture(commandSlot)) } just runs
        val resourceManager = createResourceManager(executor = executor)
        val consumer = createConsumer()

        resourceManager.consumeResource(consumer)
        resourceManager.setResource("TEST")

        verify { consumer wasNot called }
        verify(exactly = 1) { executor.execute(any()) }
        assertTrue(commandSlot.isCaptured)
        commandSlot.captured.run()
        verify(exactly = 1) { consumer.invoke("TEST") }
        confirmVerified(consumer)
    }

    private fun createResourceManager(
        executor: Executor = immediateExecutor(),
        errorHandler: ErrorHandler<String> = dummyErrorHandler()
    ): ResourceManager<String> = ResourceManager(
        executor = executor,
        errorHandler = errorHandler
    )

    private fun dummyErrorHandler(): ErrorHandler<String> = mockk()

    private fun immediateExecutor(): Executor {
        val executor = mockk<Executor>()
        every { executor.execute(any()) } answers {
            firstArg<Runnable>().run()
        }
        return executor
    }

    private fun createConsumer(): Consumer<String> = mockk(relaxed = true)

}