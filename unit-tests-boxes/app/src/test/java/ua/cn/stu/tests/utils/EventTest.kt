package ua.cn.stu.tests.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MediatorLiveData
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test

class EventTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `get return value only once`() {
        val event = Event("value")

        val value1 = event.get()
        val value2 = event.get()

        assertEquals("value", value1)
        assertNull(value2)
    }

    @Test
    fun `publish event to liveData`() {
        val mutableLiveEvent = MutableLiveEvent<String>()
        val liveEvent = mutableLiveEvent.share()

        mutableLiveEvent.publishEvent("value")

        assertEquals("value", liveEvent.value!!.get())
    }

    @Test
    fun `observeEvent call for event with any data`() {
        val mutableLiveEvent = MutableLiveEvent<String>()
        val listener = mockk<EventListener<String>>(relaxed = true)
        val liveEvent = mutableLiveEvent.share()
        val lifecycleOwner = createLifecycleOwner()

        mutableLiveEvent.publishEvent("value")
        liveEvent.observeEvent(lifecycleOwner, listener)

        verify(exactly = 1) { listener.invoke("value") }
        confirmVerified(listener)
    }

    @Test
    fun `observeEvent call for event with Unit`() {
        val mutableLiveEvent = MutableUnitLiveEvent()
        val listener = mockk<UnitEventListener>(relaxed = true)
        val liveEvent = mutableLiveEvent.share()
        val lifecycleOwner = createLifecycleOwner()

        mutableLiveEvent.publishEvent(Unit)
        liveEvent.observeEvent(lifecycleOwner, listener)

        verify(exactly = 1) { listener.invoke() }
        confirmVerified(listener)
    }

    private fun createLifecycleOwner(): LifecycleOwner {
        val lifecycleOwner = mockk<LifecycleOwner>()
        val lifecycle = mockk<Lifecycle>()
        every { lifecycle.currentState } returns Lifecycle.State.STARTED
        every { lifecycle.addObserver(any()) } answers {
            firstArg<LifecycleEventObserver>().onStateChanged(
                lifecycleOwner,
                Lifecycle.Event.ON_START
            )
        }
        every { lifecycleOwner.lifecycle } returns lifecycle
        return lifecycleOwner
    }
}