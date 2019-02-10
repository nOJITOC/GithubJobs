package com.mmteams91.githubjobs.common.domain


interface UseCase<Q, P> {
    fun run(requestValue: Q): P
}

fun <T> UseCase<Unit, T>.run(): T = run(Unit)
