package com.alexeyyuditsky.unittests

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.concurrent.Executor

class ResourceManagerTest {

    @Test
    fun consumeResourceAfterSetResourceCallReceivesResource() {
        // arrange
        val resourceManager = createResourceManager()
        val consumer = TestConsumer()

        // act
        resourceManager.setResource("TEST")
        resourceManager.consumeResource(consumer)

        // assert
        assertEquals("TEST", consumer.lastResource)
        assertEquals(1, consumer.invokeCount)
    }

    @Test
    fun consumeResourceCallsAfterSetResourceCallReceiveResourceForEachConsumer() {
        // arrange
        val resourceManager = createResourceManager()
        val consumer1 = TestConsumer()
        val consumer2 = TestConsumer()

        // act
        resourceManager.setResource("TEST")
        resourceManager.consumeResource(consumer1)
        resourceManager.consumeResource(consumer2)

        // assert
        assertEquals("TEST", consumer1.lastResource)
        assertEquals(1, consumer1.invokeCount)
        assertEquals("TEST", consumer2.lastResource)
        assertEquals(1, consumer2.invokeCount)
    }

    @Test
    fun consumeResourceAfterSetResourceCallsReceivesLatestResource() {
        // arrange
        val resourceManager = createResourceManager()
        val consumer = TestConsumer()

        // act
        resourceManager.setResource("TEST1")
        resourceManager.setResource("TEST2")
        resourceManager.consumeResource(consumer)

        //assert
        assertEquals("TEST2", consumer.lastResource)
        assertEquals(1, consumer.invokeCount)
    }

    @Test
    fun consumeResourceCallsWithSameConsumerCanReceiveTheSameResource() {
        val resourceManager = createResourceManager()
        val consumer = TestConsumer()

        resourceManager.setResource("TEST")
        resourceManager.consumeResource(consumer)
        resourceManager.consumeResource(consumer)

        assertEquals("TEST", consumer.resources[0])
        assertEquals("TEST", consumer.resources[1])
        assertEquals(2, consumer.invokeCount)
    }

    @Test
    fun consumeResourceWithoutActiveResourceDoesNothing() {
        val resourceManager = ResourceManager(TestExecutor(), TestErrorHandler())
        val consumer = TestConsumer()

        resourceManager.consumeResource(consumer)

        assertEquals(0, consumer.invokeCount)
    }

    @Test
    fun setResourceAfterConsumeResourceCallDeliversResourceToConsumer() {
        val resourceManager = createResourceManager()
        val consumer = TestConsumer()

        resourceManager.consumeResource(consumer)
        resourceManager.setResource("TEST")

        assertEquals("TEST", consumer.lastResource)
        assertEquals(1, consumer.invokeCount)
    }

    @Test(expected = Test.None::class)
    fun setResourceHandlesConcurrentConsumersModification() {
        val resourceManager = createResourceManager()
        val consumer = TestConsumer()

        resourceManager.consumeResource {
            resourceManager.clearResource()
            resourceManager.consumeResource(consumer)
        }
        resourceManager.setResource("TEST")

        assertEquals(1, consumer.invokeCount)
        assertEquals("TEST", consumer.lastResource)
    }

    private fun createResourceManager(
        executor: Executor = TestExecutor(),
        errorHandler: ErrorHandler<String> = TestErrorHandler()
    ): ResourceManager<String> {
        return ResourceManager(executor, errorHandler)
    }

}