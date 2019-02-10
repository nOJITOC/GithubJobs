package com.mmteams91.githubjobs.app

import android.app.Application


class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initDi()
    }


    private fun initDi() {
        appComponent = DaggerAppComponent.builder()
                .application(this)
                .build()
        appComponent.inject(this)
    }


}