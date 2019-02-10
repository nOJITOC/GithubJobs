package com.mmteams91.githubjobs.common.presentation.view

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.View
import com.mmteams91.githubjobs.app.AppActivity
import com.mmteams91.githubjobs.app.AppViewModel
import com.mmteams91.githubjobs.common.extensions.obtainViewModel
import com.mmteams91.githubjobs.common.presentation.IDisposableBinder
import com.mmteams91.githubjobs.common.presentation.contracts.ITitleKeeper
import com.mmteams91.githubjobs.common.presentation.contracts.IView
import com.mmteams91.githubjobs.common.presentation.viewmodel.BaseFragmentViewModel
import kotlinx.android.synthetic.*

abstract class BaseFragment<T : BaseFragmentViewModel>(
        private val viewModelType: Class<T>,
        private val isViewModelInActivityScope: Boolean = false
) : Fragment(), IView<T>, ITitleKeeper {
    private val handler by lazy { Handler() }
    override val title: CharSequence? = null

    override val viewModel: T by lazy {
        (if (isViewModelInActivityScope) {
            requireActivity().obtainViewModel(viewModelType)
        } else obtainViewModel(viewModelType)).apply {
            appViewModel = requireActivity().obtainViewModel(AppViewModel::class.java)
        }
    }

    override val disposableBinder: IDisposableBinder = DisposableBinder.on(this.lifecycle)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init(arguments)
        lifecycle.addObserver(this)
        super.onViewCreate()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        clearFindViewByIdCache()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun appActivity() = (activity as? AppActivity)


    fun runOnUiThread(runnable: () -> Unit) {
        activity?.runOnUiThread(runnable) ?: view?.post(runnable) ?: handler.post(runnable)
    }

}


