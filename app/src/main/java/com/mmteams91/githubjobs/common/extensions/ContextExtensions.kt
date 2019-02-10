package com.mmteams91.githubjobs.common.extensions

import android.content.Context
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat

/**
 * Created by mmaruknin on 28.12.18.
 * mc21
 */


fun Context.resolveColorFromAttribute(attrId: Int): Int {
    val a = this.obtainStyledAttributes(intArrayOf(attrId))
    val color = a.getColor(0, 0)
    a.recycle()
    return color
}

fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)
