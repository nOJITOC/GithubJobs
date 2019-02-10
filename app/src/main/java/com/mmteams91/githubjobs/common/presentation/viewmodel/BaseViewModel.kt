package com.mmteams91.githubjobs.common.presentation.viewmodel

import android.arch.lifecycle.ViewModel
import android.os.Bundle
import android.support.annotation.StringRes
import com.mmteams91.githubjobs.R
import com.mmteams91.githubjobs.common.data.network.NetworkNotAvailableException
import com.mmteams91.githubjobs.common.data.network.NetworkStatusProvider
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.processors.SingleQueueProcessor
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {
    @Inject
    lateinit var networkStatusProvider: NetworkStatusProvider
    private var isInitialized = false

    /**
     * container for disposables that depends on viewModel  lifecycle
     */
    private val disposables: CompositeDisposable = CompositeDisposable()

    private val eventsFlow = SingleQueueProcessor.create<Event>()

    fun eventsFlow(): Flowable<Event> {
        return eventsFlow
    }


    protected fun addDisposable(disposable: Disposable) = disposables.add(disposable)


    fun publishEvent(event: Event) = eventsFlow.onNext(event)

    fun publishEvent(eventName: String, payload: Any? = null) = publishEvent(
        payload?.let { EventWithPayload(eventName, payload) } ?: Event(eventName)
    )

    protected fun <T> checkNetwork(
        single: Single<T>,
        onNetworkNotAvailable: () -> Unit = ::onNetworkNotAvailable
    ): Single<T> {
        return networkStatusProvider.isNetworkAvailableSingle()
            .flatMap { isConnected ->
                if (isConnected) single
                else {
                    onNetworkNotAvailable.invoke()
                    Single.error(NetworkNotAvailableException())
                }
            }
    }

    protected fun <T> Single<T>.subscribeWithNetworkErrorParse(onSuccess: (T) -> Unit):Disposable = checkNetwork(this)
        .subscribe(onSuccess, ::parseNetworkError)

    protected fun parseNetworkError(throwable: Throwable) {
        if (throwable is SocketTimeoutException || throwable is NetworkNotAvailableException || throwable is UnknownHostException) {
            onNetworkNotAvailable()
        } else publishError()
    }

    override fun onCleared() {
        super.onCleared()
        isInitialized = false
        disposables.clear()
    }


    /**
     * override if need seek onCreate lifecycle event
     */
    open fun onCreate() {
    }

    /**
     * override if need seek onViewCreate lifecycle event
     */
    open fun onStart() {

    }

    fun onNetworkNotAvailable() = publishError(R.string.network_error_message)

    abstract fun publishError(message: CharSequence)

    abstract fun publishError(@StringRes messageRes: Int = R.string.default_snack_bar_message)


    open fun init(arguments: Bundle?) {
        if (!isInitialized) {
            onFirstViewAttached(arguments)
            isInitialized = true
        }
    }

    open fun onFirstViewAttached(arguments: Bundle?) {
    }

}

