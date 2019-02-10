package com.mmteams91.githubjobs.common.domain

import com.mmteams91.githubjobs.features.job.data.JobRepository
import com.mmteams91.githubjobs.features.job.data.JobsApi
import com.mmteams91.githubjobs.features.job.domain.IJobRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun jobsRepository(jobsApi: JobsApi): IJobRepository = JobRepository(jobsApi)
}