package com.mmteams91.githubjobs.features.job.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Job(
    val id: String,
    val type: String,
    val url: String?,
    val createdAt: Date,
    val company: String,
    val companyUrl: String?,
    val location: String?,
    val title: String,
    val description: String?,
    val howToApply: String?,
    val companyLogo: String?
):Parcelable