package com.mmteams91.githubjobs.features.job.data

import com.mmteams91.githubjobs.common.transform.ITransformer
import com.mmteams91.githubjobs.features.job.domain.Job

class JobFromSmTransformer : ITransformer<JobSm, Job> {
    override fun transform(from: JobSm): Job {
        return Job(
            from.id,
            from.type,
            from.url,
            from.createdAt,
            from.company,
            from.companyUrl,
            from.location,
            from.title,
            from.description,
            from.howToApply,
            from.companyLogo
        )
    }
}