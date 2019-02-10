package com.mmteams91.githubjobs.common.presentation.contracts

import android.support.v4.app.Fragment

interface ITitleKeeper {
    val title: CharSequence?
}
fun Fragment.getTitle() = (this as? ITitleKeeper)?.title
