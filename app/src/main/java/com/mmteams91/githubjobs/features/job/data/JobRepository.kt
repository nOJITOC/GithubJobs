package com.mmteams91.githubjobs.features.job.data

import com.mmteams91.githubjobs.features.job.domain.IJobRepository
import com.mmteams91.githubjobs.features.job.domain.Job
import io.reactivex.Single

class JobRepository (private val jobsApi: JobsApi) : IJobRepository {
    private val transformer = JobFromSmTransformer()

    override fun getJobs(query: String, page: Int): Single<List<Job>> {
        return jobsApi.getJobs(query, page).map { it.map { sm -> transformer.transform(sm) } }
    }
}