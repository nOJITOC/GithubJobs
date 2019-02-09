package com.mmteams91.githubjobs

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mmteams91.githubjobs.ui.jobs.JobsFragment

class AppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, JobsFragment.newInstance())
                .commitNow()
        }
    }

}
