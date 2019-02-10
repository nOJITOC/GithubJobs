package com.mmteams91.githubjobs.common.extensions

import android.text.Spannable
import android.text.SpannableString
import android.text.style.TypefaceSpan

/**
 * Created by mmaruknin on 16.01.19.
 * mc21
 */


fun CharSequence.medium(forMediumInContent: CharSequence? = null): CharSequence = forMediumInContent?.let { forMedium ->
    val index = this.indexOf(forMedium.toString())
    if (index == -1) this
    else SpannableString(this).apply { setSpan(TypefaceSpan("sans-serif-medium"), index, index + forMedium.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) }
} ?: SpannableString(this).apply {
    setSpan(TypefaceSpan("sans-serif-medium"), 0, this.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
}
