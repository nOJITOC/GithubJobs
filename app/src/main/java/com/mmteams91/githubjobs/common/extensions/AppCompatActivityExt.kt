package com.mmteams91.githubjobs.common.extensions

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.mmteams91.githubjobs.app.AppActivity
import com.mmteams91.githubjobs.common.presentation.viewmodel.BaseViewModel
import com.mmteams91.githubjobs.common.presentation.viewmodel.ViewModelFactory


fun <T : ViewModel> FragmentActivity.obtainViewModel(viewModelClass: Class<T>): T {
    return if (this is AppActivity) {
        ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
    } else ViewModelProviders.of(this).get(viewModelClass)
}


fun <T : BaseViewModel> Fragment.obtainViewModel(viewModelClass: Class<T>): T {
    return if (activity is AppActivity) {
        val appActivity = activity as AppActivity
        ViewModelProviders.of(this, appActivity.viewModelFactory).get(viewModelClass)
    } else ViewModelProviders.of(this).get(viewModelClass)
}


inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()

}