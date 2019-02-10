package com.mmteams91.githubjobs.features.job.presentation.list

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.mmteams91.githubjobs.R
import com.mmteams91.githubjobs.common.presentation.view.BaseFragment
import com.mmteams91.githubjobs.common.presentation.viewmodel.Event
import kotlinx.android.synthetic.main.request_jobs_fragment.view.*

class RequestJobsFragment : BaseFragment<JobsViewModel>(JobsViewModel::class.java, true) {
    override fun obtainEvent(event: Event) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return view ?: inflater.inflate(R.layout.request_jobs_fragment, container, false).apply {
            findJob.setOnClickListener {
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(windowToken, 0)
                requestContainer.requestFocusFromTouch()
                viewModel.onQuery(searchText.text.toString())
            }
        }
    }

    companion object {
        fun newInstance(): Fragment = RequestJobsFragment()
    }
}