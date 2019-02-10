package com.mmteams91.githubjobs.common.domain

import io.reactivex.Maybe
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers


abstract class MaybeUseCase<Q, P> protected constructor(private val backgroundScheduler: Scheduler = Schedulers.newThread()) :
    UseCase<Q, Maybe<P>> {


    override fun run(requestValue: Q): Maybe<P> {
        return executeUseCaseFlow(requestValue)
                .subscribeOn(backgroundScheduler)
    }

    protected abstract fun executeUseCaseFlow(requestValue: Q): Maybe<P>

}
