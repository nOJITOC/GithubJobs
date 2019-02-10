package com.mmteams91.githubjobs.common.presentation.viewmodel

import com.mmteams91.githubjobs.app.AppViewModel
import com.mmteams91.githubjobs.app.Screen


open class BaseFragmentViewModel : BaseViewModel() {
    lateinit var appViewModel: AppViewModel

    override fun publishError(message: CharSequence) = appViewModel.publishError(message)

    override fun publishError(messageRes: Int) = appViewModel.publishError(messageRes)

    fun publishAppEvent(event: Event) = appViewModel.publishEvent(event)

    fun publishAppEvent(eventName: String, payload: Any? = null) = appViewModel.publishEvent(eventName, payload)

    fun navigateTo(screen: Screen) = appViewModel.navigateTo(screen)


}