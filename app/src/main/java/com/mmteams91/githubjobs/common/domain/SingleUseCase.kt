package com.mmteams91.githubjobs.common.domain

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

abstract class SingleUseCase<Q, P> protected constructor(protected val backgroundScheduler: Scheduler = Schedulers.newThread()) : UseCase<Q, Single<P>> {

    override fun run(requestValue: Q): Single<P> {
        return executeUseCaseFlow(requestValue)
                .subscribeOn(backgroundScheduler)
    }

    protected abstract fun executeUseCaseFlow(requestValue: Q): Single<P>

}