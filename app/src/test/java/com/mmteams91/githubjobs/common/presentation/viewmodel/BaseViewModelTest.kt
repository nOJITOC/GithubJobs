package com.mmteams91.githubjobs.common.presentation.viewmodel

import com.mmteams91.githubjobs.common.presentation.ViewModelTestBasis
import org.junit.Before
import org.junit.Test

class BaseViewModelTest : ViewModelTestBasis<BaseViewModel>() {


    protected lateinit var events: Array<Event>

    @Before
    fun setUp() {
        subject = object : BaseViewModel() {
            override fun publishError(message: CharSequence) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun publishError(messageRes: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
        events = arrayOf(Event("event 1"), Event("event 2"), EventWithPayload("event 3", "payload"))
    }

    @Test
    fun `publish event`() {
        whenSubscribeToEvents()
        whenViewModelPublishEvents(*events)
        thenEventSubscriberConsumeValues(*events)
        thenEventSubscriberNotComplete()
    }

    @Test
    fun `publish event before subscribe`() {
        whenViewModelPublishEvents(*events)
        whenSubscribeToEvents()
        thenEventSubscriberConsumeValues(*events)
        thenEventSubscriberNotComplete()
    }

    private fun whenViewModelPublishEvents(vararg events: Event) {
        events.forEach { subject.publishEvent(it) }
    }

    private fun whenSubscribeToEvents() = subscribeToEvents()
}