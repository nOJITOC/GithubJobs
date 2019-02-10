package com.mmteams91.githubjobs.features.job.presentation.list

import com.mmteams91.githubjobs.app.JobDetailsScreen
import com.mmteams91.githubjobs.app.JobsScreen
import com.mmteams91.githubjobs.common.domain.run
import com.mmteams91.githubjobs.common.presentation.viewmodel.BaseFragmentViewModel
import com.mmteams91.githubjobs.features.job.domain.IJobRepository
import com.mmteams91.githubjobs.features.job.domain.Job
import com.mmteams91.githubjobs.features.job.domain.RequestNextPageUseCase
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.processors.SingleQueueProcessor
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

private const val POSITIONS_BEFORE_WHERE_REQUEST_NEXT = 2

class JobsViewModel @Inject constructor(
        private val jobsRepository: IJobRepository
) : BaseFragmentViewModel() {
    lateinit var query: String
    private lateinit var requestNextPageUseCase: RequestNextPageUseCase
    var jobs = mutableListOf<Job>()
    private var isLast = false
    private val jobsProcessor = SingleQueueProcessor.create<List<Job>>()
    private var positionWhereNextRequestHappened = -1
    private var nextPageLoadDisposable: Disposable? = null
    fun jobsPageFlow(): Flowable<List<Job>> = jobsProcessor

    fun onQuery(query: String) {
        if (!networkStatusProvider.isNetworkAvailable()) {
            onNetworkNotAvailable()
            return
        }
        this.query = query
        nextPageLoadDisposable?.dispose()
        nextPageLoadDisposable = null
        jobs.clear()
        positionWhereNextRequestHappened = -1
        isLast = false
        requestNextPageUseCase = RequestNextPageUseCase(query, jobsRepository)
        navigateTo(JobsScreen())
        onNextPageRequest()
    }

    fun checkIsNeedNextPage(jobPosition: Int, countOfJobs: Int) {
        if (!isLast && positionWhereNextRequestHappened < jobPosition && jobPosition == countOfJobs - POSITIONS_BEFORE_WHERE_REQUEST_NEXT) {
            onNextPageRequest()
            positionWhereNextRequestHappened = jobPosition
        }
    }


    private fun onNextPageRequest() {
        nextPageLoadDisposable = requestNextPageUseCase.run()
                .compose { appViewModel.wrapWithProgress(it) }
                .subscribeOn(Schedulers.io())
                .subscribeWithNetworkErrorParse { nextPart ->
                    isLast = nextPart.isLast
                    jobsProcessor.onNext(nextPart.jobs)
                }
    }

    override fun onCleared() {
        super.onCleared()
        nextPageLoadDisposable?.dispose()
    }

    fun onJobClick(job: Job) {
        navigateTo(JobDetailsScreen(job))
    }
}
