package com.mmteams91.githubjobs.features.job.presentation.list

import android.graphics.Rect
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fourxxi.mc21.common.ui.decorations.OffsetsItemDecoration
import com.mmteams91.githubjobs.R
import com.mmteams91.githubjobs.common.extensions.safeSubscribe
import com.mmteams91.githubjobs.common.presentation.view.BaseFragment
import com.mmteams91.githubjobs.common.presentation.viewmodel.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.jobs_fragment.view.*

class JobsFragment : BaseFragment<JobsViewModel>(JobsViewModel::class.java, true) {

    override val title: CharSequence? by lazy {
        viewModel.query
    }
    private val adapter by lazy {
        JobsAdapter(
                onItemBind = viewModel::checkIsNeedNextPage,
                onItemClick = viewModel::onJobClick
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeToJobs()
    }

    private fun subscribeToJobs() {
        adapter.items = viewModel.jobs
        viewModel.jobsPageFlow()
                .observeOn(AndroidSchedulers.mainThread())
                .safeSubscribe { adapter.addAll(it) }
                .also { bind(it) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return view ?: inflater.inflate(R.layout.jobs_fragment, container, false).apply {
            jobsContainer.adapter = adapter
            val spacing = context.resources.getDimensionPixelSize(R.dimen.spacing_8)
            jobsContainer.addItemDecoration(OffsetsItemDecoration(
                    getOffsets = { parent, position ->
                        Rect(
                                spacing,
                                if (position == 0) spacing else 0,
                                spacing,
                                spacing
                        )
                    }
            ))
        }
    }

    override fun obtainEvent(event: Event) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        fun newInstance(): Fragment = JobsFragment()
    }

}