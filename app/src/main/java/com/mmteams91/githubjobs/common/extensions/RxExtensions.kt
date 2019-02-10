package com.mmteams91.githubjobs.common.extensions

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import java.util.concurrent.atomic.AtomicBoolean


fun <T> Flowable<T>.doOnFirst(doOnFirst: (T) -> Unit): Flowable<T> {
    val isFirstEmittedItem = AtomicBoolean(true)
    return doOnNext {
        if (isFirstEmittedItem.getAndSet(false)) {
            doOnFirst.invoke(it)
        }
    }
}

fun <T, R> Flowable<out Iterable<T>>.withIterable(func: (T) -> R): Flowable<List<R>> {
    return this.flatMap {
        Flowable.fromIterable(it)
                .map { func(it) }
                .toList()
                .toFlowable()
    }
}


fun <T> Flowable<T>.safeSubscribe(onError: (Throwable) -> Unit = {}, onNext: (T) -> Unit): Disposable = subscribe(onNext, onError)

fun <T> Single<T>.safeSubscribe(onError: (Throwable) -> Unit = {}, onSuccess: (T) -> Unit): Disposable = subscribe(onSuccess, onError)

fun Completable.safeSubscribe(onError: (Throwable) -> Unit = {}, onComplete: () -> Unit): Disposable = subscribe(onComplete, onError)