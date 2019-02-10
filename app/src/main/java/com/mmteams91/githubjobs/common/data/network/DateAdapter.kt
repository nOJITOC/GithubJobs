package com.mmteams91.githubjobs.common.data.network

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.*

const val PARSE_DATE_FORMAT = "EEE MMM dd HH:mm:ss 'UTC' yyyy"
private val formatter: SimpleDateFormat by lazy { SimpleDateFormat(PARSE_DATE_FORMAT, Locale.US) }


class DateAdapter {

    @FromJson
    fun fromJson(json: String?): Date? = json?.let { formatter.parse(it) }

    @ToJson
    fun toJson(date: Date?): String? = date?.let { formatter.format(it) }

}