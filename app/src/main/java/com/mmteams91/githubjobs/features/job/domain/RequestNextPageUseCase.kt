package com.mmteams91.githubjobs.features.job.domain

import com.mmteams91.githubjobs.common.domain.SingleUseCase
import io.reactivex.Single

class RequestNextPageUseCase(
    private var queue: String,
    private val jobsRepository: IJobRepository
) : SingleUseCase<Unit, RequestNextPageUseCase.Response>() {
    private var page = 1
    private var previousJobsCount = 0
    override fun executeUseCaseFlow(requestValue: Unit): Single<Response> {
        return jobsRepository.getJobs(queue, page)
            .map { jobs ->
                val isLast = jobs.size < previousJobsCount || jobs.isEmpty()
                if (!isLast) {
                    previousJobsCount = jobs.size
                }
                Response(isLast, jobs)
            }.doOnSuccess { page++ }
    }

    class Response(val isLast: Boolean, val jobs: List<Job>)
}