package com.mmteams91.githubjobs.features.job.domain

import io.reactivex.Single

interface IJobRepository {
    fun getJobs(query: String, page: Int): Single<List<Job>>
}