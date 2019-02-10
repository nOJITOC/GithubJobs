package com.mmteams91.githubjobs.app

import android.os.Parcelable
import android.support.v4.app.Fragment
import com.mmteams91.githubjobs.features.job.domain.Job
import com.mmteams91.githubjobs.features.job.presentation.details.JobDetailsFragment
import com.mmteams91.githubjobs.features.job.presentation.list.JobsFragment
import com.mmteams91.githubjobs.features.job.presentation.list.RequestJobsFragment
import kotlinx.android.parcel.Parcelize


sealed class Screen(
        val addToBackStack: Boolean = true,
        val isRoot: Boolean = false
) : Parcelable {

    abstract fun newInstance(): Fragment

    open fun name(): String = javaClass.simpleName

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Screen) return false
        return name() == other.name()
    }

    override fun hashCode(): Int {
        return 31 * name().hashCode()
    }
}

@Parcelize
class RequestJobsScreen : Screen(false, true) {
    override fun newInstance(): Fragment = RequestJobsFragment.newInstance()
}

@Parcelize
class JobsScreen : Screen(true, false) {
    override fun newInstance(): Fragment = JobsFragment.newInstance()
}

@Parcelize
class JobDetailsScreen(private val job: Job) : Screen(true, false) {
    override fun newInstance(): Fragment = JobDetailsFragment.newInstance(job)

    override fun name(): String = super.name() + job.id

}



