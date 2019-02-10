package com.mmteams91.githubjobs.common.presentation.viewmodel

import android.arch.lifecycle.ViewModel
import com.mmteams91.githubjobs.app.AppViewModel
import com.mmteams91.githubjobs.features.job.presentation.list.JobsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AppViewModel::class)
    abstract fun appViewModel(viewModel: AppViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(JobsViewModel::class)
    abstract fun jobsViewModel(viewModel: JobsViewModel): ViewModel

}