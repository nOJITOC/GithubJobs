package com.mmteams91.githubjobs.app

import android.app.Application
import com.mmteams91.githubjobs.common.presentation.viewmodel.BaseViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
    AppModule::class
    ]
)
interface AppComponent {


    fun inject(activity: AppActivity)
    fun inject(app: App)
    fun inject(viewModel: BaseViewModel)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
