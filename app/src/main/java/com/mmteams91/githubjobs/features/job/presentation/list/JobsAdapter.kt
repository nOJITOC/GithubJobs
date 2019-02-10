package com.mmteams91.githubjobs.features.job.presentation.list

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.NO_POSITION
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mmteams91.githubjobs.R
import com.mmteams91.githubjobs.common.extensions.inflate
import com.mmteams91.githubjobs.common.presentation.adapter.ListAdapter
import com.mmteams91.githubjobs.features.job.domain.Job
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_list_job.*

class JobsAdapter(
        var onItemBind: (position: Int) -> Unit,
        var onItemClick: (item: Job) -> Unit
) : ListAdapter<Job, JobsAdapter.JobViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        return JobViewHolder(parent.inflate(R.layout.view_list_job).apply {
            clipToOutline = true
        }).apply {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != NO_POSITION) onItemClick.invoke(items[position])
            }
        }
    }

    override fun onBindViewHolder(viewHolder: JobViewHolder, position: Int) {
        viewHolder.apply {
            val item = items[position]
            Glide.with(logo)
                    .asDrawable()
                    .apply(RequestOptions().optionalCenterInside())
                    .load(item.companyLogo)
                    .into(logo)
            title.text = item.title
            company.text = item.company
            onItemBind.invoke(position)
        }
    }

    inner class JobViewHolder(
            override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer
}