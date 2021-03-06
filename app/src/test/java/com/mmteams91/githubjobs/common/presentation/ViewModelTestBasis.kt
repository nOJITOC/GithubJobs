package com.mmteams91.githubjobs.common.presentation

import com.mmteams91.githubjobs.common.presentation.viewmodel.BaseViewModel
import com.mmteams91.githubjobs.common.presentation.viewmodel.Event
import io.reactivex.subscribers.TestSubscriber

abstract class ViewModelTestBasis<T : BaseViewModel> {


    protected lateinit var subject: T
    protected lateinit var subscriber: TestSubscriber<Event>

    protected fun subscribeToEvents() {
        subscriber = subject.eventsFlow().test()
    }


    protected fun thenEventSubscriberConsumeValues(vararg events: Event) {
        subscriber.assertValues(*events)
    }

    protected fun thenEventSubscriberNotComplete() {
        subscriber.assertNotComplete()
    }
}