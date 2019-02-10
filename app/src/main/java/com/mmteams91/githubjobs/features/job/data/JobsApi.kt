package com.mmteams91.githubjobs.features.job.data

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface JobsApi {
    @GET("positions.json")
    fun getJobs(@Query("search") search: String, @Query("page") page: Int): Single<List<JobSm>>
}