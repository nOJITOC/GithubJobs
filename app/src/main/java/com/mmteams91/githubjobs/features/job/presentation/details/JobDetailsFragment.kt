package com.mmteams91.githubjobs.features.job.presentation.details

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.text.HtmlCompat
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mmteams91.githubjobs.R
import com.mmteams91.githubjobs.common.extensions.hide
import com.mmteams91.githubjobs.common.extensions.medium
import com.mmteams91.githubjobs.common.presentation.contracts.ITitleKeeper
import com.mmteams91.githubjobs.features.job.domain.Job
import kotlinx.android.synthetic.main.job_details_fragment.view.*
import java.text.SimpleDateFormat
import java.util.*

class JobDetailsFragment : Fragment(), ITitleKeeper {
    override val title: CharSequence? by lazy { getString(R.string.vacancy) }
    private val job: Job by lazy { arguments!!.getParcelable<Job>(JOB_KEY)!! }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.job_details_fragment, container, false).apply {
            job.companyLogo?.let { companyLogo ->
                Glide.with(logo)
                        .asDrawable()
                        .apply(RequestOptions().optionalCenterInside())
                        .load(companyLogo)
                        .into(logo)
            } ?: logo.hide()
            createdAt.text = SimpleDateFormat("dd MMMM yyyy", Locale.US).format(job.createdAt)
            title.text = job.title
            title.text = if (job.url.isNullOrBlank()) job.title
            else HtmlCompat.fromHtml("<a href=\"${job.url}\">${job.title}</a>", 0)
            title.movementMethod = LinkMovementMethod.getInstance()
            job.location?.let { locationString ->
                location.text = SpannableStringBuilder()
                        .append(getString(R.string.location).medium())
                        .append(' ')
                        .append(locationString)
            } ?: location.hide()
            type.text = SpannableStringBuilder()
                    .append(getString(R.string.type).medium())
                    .append(' ')
                    .append(job.type)
            val companyText = if (job.companyUrl.isNullOrBlank()) job.company
            else HtmlCompat.fromHtml("<a href=\"${job.companyUrl}\">${job.company}</a>", 0)
            company.movementMethod = LinkMovementMethod.getInstance()
            company.text = SpannableStringBuilder()
                    .append(getString(R.string.company).medium())
                    .append(' ')
                    .append(companyText)
            job.description?.let { descriptionText ->
                description.text = HtmlCompat.fromHtml(descriptionText, HtmlCompat.FROM_HTML_MODE_COMPACT)
                description.movementMethod = LinkMovementMethod.getInstance()
            } ?: description.hide()
            job.howToApply?.let { howToApplyText ->
                howToApply.text = HtmlCompat.fromHtml(howToApplyText, HtmlCompat.FROM_HTML_MODE_COMPACT)
                howToApply.movementMethod = LinkMovementMethod.getInstance()
            } ?: howToApply.hide()
        }
    }

    companion object {
        private const val JOB_KEY = "job"
        fun newInstance(job: Job): Fragment = JobDetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(JOB_KEY, job)
            }
        }
    }
}