package com.mmteams91.githubjobs.common.extensions

import com.squareup.moshi.Moshi

/**
 * Created by mmaruknin on 14.01.19.
 * mc21
 */

inline fun <reified T> Moshi.fromJson(string: String?): T? = string?.let { adapter(T::class.java).fromJson(it) }


inline fun <reified T> Moshi.toJson(value: T?): String? = value?.let { adapter(T::class.java).toJson(it) }

