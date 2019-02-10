package com.mmteams91.githubjobs.app

import com.mmteams91.githubjobs.app.AppActivity.Events
import com.mmteams91.githubjobs.app.AppActivity.Events.EVENT_MESSAGE
import com.mmteams91.githubjobs.app.AppActivity.Events.EVENT_POP_SCREEN
import com.mmteams91.githubjobs.common.extensions.doOnFirst
import com.mmteams91.githubjobs.common.presentation.viewmodel.BaseViewModel
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.processors.PublishProcessor
import javax.inject.Inject

open class AppViewModel @Inject constructor(
) : BaseViewModel() {
    private var currentScreen: Screen? = null
    private val navigateEventPublisher = PublishProcessor.create<Screen>()

    fun prepareDefaultNavigateEvent() {
        navigateTo(RequestJobsScreen())
    }

    fun navigateFlow(): Flowable<Screen> = navigateEventPublisher

    @Synchronized
    fun navigateTo(screen: Screen) {
        currentScreen = screen
        navigateEventPublisher.onNext(screen)
    }

    fun publishShowProgressEvent() = publishEvent(Events.EVENT_SHOW_PROGRESS)

    fun publishHideProgressEvent() = publishEvent(Events.EVENT_HIDE_PROGRESS)

    fun <T> wrapWithProgress(flowable: Flowable<T>): Flowable<T> = flowable
            .doOnFirst { publishHideProgressEvent() }
            .doFinally { publishHideProgressEvent() }
            .doOnSubscribe { publishShowProgressEvent() }


    fun <T> wrapWithProgress(single: Single<T>): Single<T> = single
            .doFinally(::publishHideProgressEvent)
            .doOnSubscribe { publishShowProgressEvent() }



    override fun publishError(message: CharSequence) {
        publishEvent(Events.EVENT_ERROR, message)

    }

    override fun publishError(messageRes: Int) {
        publishEvent(Events.EVENT_ERROR, messageRes)
    }

    fun closeScreen() {
        publishEvent(EVENT_POP_SCREEN, currentScreen?.name())
    }


    fun showMessage(stringRes: Int) {
        publishEvent(EVENT_MESSAGE, stringRes)
    }

}