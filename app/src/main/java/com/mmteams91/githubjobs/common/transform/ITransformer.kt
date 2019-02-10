package com.mmteams91.githubjobs.common.transform

interface ITransformer<From, To> {
    fun transform(from: From): To
}