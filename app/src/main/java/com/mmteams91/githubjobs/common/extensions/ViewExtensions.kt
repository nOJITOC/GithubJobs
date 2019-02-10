package com.mmteams91.githubjobs.common.extensions

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun View.show() {
    if (visibility != View.VISIBLE) {
        post { visibility = View.VISIBLE }
    }
}

fun View.hide() {
    if (visibility != View.GONE) {
        post { visibility = View.GONE }
    }
}


fun View.invisible() {
    if (visibility != View.INVISIBLE) {
        post { visibility = View.INVISIBLE }
    }
}


fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View = LayoutInflater.from(this.context).inflate(layoutRes, this, attachToRoot)

fun View.setVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

