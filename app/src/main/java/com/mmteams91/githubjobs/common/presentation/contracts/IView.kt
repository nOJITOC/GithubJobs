package com.mmteams91.githubjobs.common.presentation.contracts

import android.arch.lifecycle.LifecycleObserver
import com.mmteams91.githubjobs.common.extensions.safeSubscribe
import com.mmteams91.githubjobs.common.presentation.IDisposableBinder
import com.mmteams91.githubjobs.common.presentation.viewmodel.BaseViewModel
import com.mmteams91.githubjobs.common.presentation.viewmodel.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

interface IView<Vm : BaseViewModel> : LifecycleObserver {
    val viewModel: Vm
    val disposableBinder: IDisposableBinder


    fun onViewCreate() {
        subscribeToEvents()
        viewModel.onCreate()
    }

    fun subscribeToEvents() {
        viewModel.eventsFlow()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribe({
                subscribeToEvents()
            }) {
                obtainEvent(it)
            }.also { bind(it) }
    }

    fun obtainEvent(event: Event)

    fun bind(disposable: Disposable) = disposableBinder.bindToLifecycle(disposable)

}