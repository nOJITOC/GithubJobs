package com.mmteams91.githubjobs.app

import android.support.annotation.StringRes
import com.mmteams91.githubjobs.app.AppActivity.Events.EVENT_ERROR
import com.mmteams91.githubjobs.app.AppActivity.Events.EVENT_HIDE_PROGRESS
import com.mmteams91.githubjobs.app.AppActivity.Events.EVENT_SHOW_PROGRESS
import com.mmteams91.githubjobs.common.data.network.NetworkStatusProvider
import com.mmteams91.githubjobs.common.presentation.ViewModelTestBasis
import com.mmteams91.githubjobs.common.presentation.viewmodel.Event
import com.mmteams91.githubjobs.common.presentation.viewmodel.EventWithPayload
import io.reactivex.processors.PublishProcessor
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class AppViewModelTest : ViewModelTestBasis<AppViewModel>() {

    @Mock
    lateinit var networkStatusProvider: NetworkStatusProvider

    @Before
    fun setUp() {
        initMocks(this)
        subject = AppViewModel()
        subject.networkStatusProvider = networkStatusProvider
        subscribeToEvents()
    }

    @Test
    fun `publish error events`() {
        val errorMessage = "error"
        val errorMessageRes = 2
        val errors = arrayOf(errorMessage, errorMessageRes)
        whenViewModelPublishError(errorMessage)
        whenViewModelPublishError(errorMessageRes)
        thenEventSubscriberConsumeValues(*errors.map { EventWithPayload(EVENT_ERROR, it) }.toTypedArray())
        thenEventSubscriberNotComplete()
    }

    @Test
    fun `publish SHOW_PROGRESS when subscribe with wrapWithProgress`() {
        //GIVEN
        val flowable = PublishProcessor.create<Any>()
        flowable.compose(subject::wrapWithProgress).test()
        thenEventSubscriberConsumeValues(Event(EVENT_SHOW_PROGRESS))
        thenEventSubscriberNotComplete()
    }

    @Test
    fun `publish HIDE_PROGRESS when subscriber with wrapWithProgress has error`() {
        //GIVEN
        val flowable = PublishProcessor.create<Any>()
        flowable.compose(subject::wrapWithProgress).test()
        //WHEN
        flowable.onError(RuntimeException())
        //THEN
        thenEventSubscriberConsumeValues(Event(EVENT_SHOW_PROGRESS), Event((EVENT_HIDE_PROGRESS)))
        thenEventSubscriberNotComplete()
    }


    @Test
    fun `publish HIDE_PROGRESS when subscriber with wrapWithProgress completed`() {
        //GIVEN
        val flowable = PublishProcessor.create<Any>()
        flowable.compose(subject::wrapWithProgress).test()
        //WHEN
        flowable.onComplete()
        //THEN
        thenEventSubscriberConsumeValues(Event(EVENT_SHOW_PROGRESS), Event((EVENT_HIDE_PROGRESS)))
        thenEventSubscriberNotComplete()
    }


    @Test
    fun `publish HIDE_PROGRESS when subscriber with wrapWithProgress disposed`() {
        //GIVEN
        val flowable = PublishProcessor.create<Any>()
        val flowSubscriber = flowable.compose(subject::wrapWithProgress).test()
        //WHEN
        flowSubscriber.dispose()
        //THEN
        thenEventSubscriberConsumeValues(Event(EVENT_SHOW_PROGRESS), Event((EVENT_HIDE_PROGRESS)))
        thenEventSubscriberNotComplete()
    }

    private fun whenViewModelPublishError(message: CharSequence) {
        subject.publishError(message)
    }

    private fun whenViewModelPublishError(@StringRes messageRes: Int) {
        subject.publishError(messageRes)
    }

}