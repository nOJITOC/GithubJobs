package com.mmteams91.githubjobs.app

import android.app.Application
import android.content.Context
import com.mmteams91.githubjobs.common.data.network.RestModule
import com.mmteams91.githubjobs.common.domain.RepositoryModule
import com.mmteams91.githubjobs.common.presentation.viewmodel.ViewModelModule
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = [
    RestModule::class,
    RepositoryModule::class,
    ViewModelModule::class
])
abstract class AppModule {
    //expose Application as an injectable context

    @Binds
    @Singleton
    abstract fun bindContext(application: Application): Context

}

