package com.mmteams91.githubjobs.common.presentation.contracts

import com.mmteams91.githubjobs.common.presentation.viewmodel.BaseViewModel

/**
 * Created by mmaruknin on 27.12.18.
 * mc21
 */
interface IActivityView<Vm:BaseViewModel>: IView<Vm> {

    fun hideProgress()

    fun showProgress()
}