package com.mmteams91.githubjobs.app

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.mmteams91.githubjobs.R
import com.mmteams91.githubjobs.app.AppActivity.Events.EVENT_ERROR
import com.mmteams91.githubjobs.app.AppActivity.Events.EVENT_HIDE_PROGRESS
import com.mmteams91.githubjobs.app.AppActivity.Events.EVENT_MESSAGE
import com.mmteams91.githubjobs.app.AppActivity.Events.EVENT_POP_SCREEN
import com.mmteams91.githubjobs.app.AppActivity.Events.EVENT_SHOW_PROGRESS
import com.mmteams91.githubjobs.common.extensions.getColorCompat
import com.mmteams91.githubjobs.common.extensions.setVisible
import com.mmteams91.githubjobs.common.presentation.IDisposableBinder
import com.mmteams91.githubjobs.common.presentation.contracts.IActivityView
import com.mmteams91.githubjobs.common.presentation.contracts.getTitle
import com.mmteams91.githubjobs.common.presentation.view.DisposableBinder
import com.mmteams91.githubjobs.common.presentation.viewmodel.Event
import com.mmteams91.githubjobs.common.presentation.viewmodel.EventWithPayload
import com.mmteams91.githubjobs.common.presentation.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.app_activity.*
import javax.inject.Inject


class AppActivity : AppCompatActivity(), IActivityView<AppViewModel> {


    @Inject
    lateinit var viewModelFactory: ViewModelFactory


    override val viewModel: AppViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(AppViewModel::class.java)
    }
    private val navigator: Navigator = FragmentNavigator(supportFragmentManager, R.id.content)

    override val disposableBinder: IDisposableBinder = DisposableBinder.on(this.lifecycle)

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity)
        setupToolbar()
        trackNavigationEvents()
        if (savedInstanceState == null) {
            viewModel.onFirstViewAttached(intent.extras)
            viewModel.prepareDefaultNavigateEvent()
        }
        lifecycle.addObserver(this)
        onViewCreate()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun showError(event: EventWithPayload) {
        val backgroundColor = getColorCompat(R.color.main_red)
        showSnackBar(event, backgroundColor)
    }


    private fun showMessage(event: EventWithPayload) {
        showSnackBar(event, getColorCompat(R.color.main_green))
    }

    private fun showSnackBar(event: EventWithPayload, backgroundColor: Int) {
        val message = if (event.payload is Int) getString(event.payload) else event.payload.toString()
        val snackbar = Snackbar.make(container ?: return, message, Snackbar.LENGTH_LONG)
        val view = snackbar.view
        val context = view.context
        val params = view.layoutParams as CoordinatorLayout.LayoutParams
        params.setMargins(0, 0, 0, 0)
        view.setBackgroundColor(backgroundColor)
        snackbar.setActionTextColor(ContextCompat.getColor(context, android.R.color.white))
        snackbar.setAction(android.R.string.ok) {
            if (snackbar.isShown) {
                snackbar.dismiss()
            }
        }
        snackbar.show()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportFragmentManager.addOnBackStackChangedListener {
            updateToolbar()
        }
    }

    private fun updateToolbar() {
        supportFragmentManager.findFragmentById(R.id.content)?.let {
            val title = it.getTitle()
            setToolbarTitle(title)
            title?.let {
                supportActionBar?.show()
            } ?: supportActionBar?.hide()
        }
        showUpButton(supportFragmentManager.backStackEntryCount != 0)
    }

    override fun onResume() {
        super.onResume()
        updateToolbar()
    }

    private fun setToolbarTitle(title: CharSequence?) {
        if (supportActionBar?.title != title) supportActionBar?.title = title
    }


    private fun trackNavigationEvents() {
        viewModel.navigateFlow()
                .subscribe({ screen ->
                    navigator.navigateTo(screen)
                }) { trackNavigationEvents() }
                .also { bind(it) }
    }

    override fun obtainEvent(event: Event) {
        when (event.name) {
            EVENT_SHOW_PROGRESS -> showProgress()
            EVENT_HIDE_PROGRESS -> hideProgress()
            EVENT_ERROR -> showError(event as? EventWithPayload ?: return)
            EVENT_MESSAGE -> showMessage(event as? EventWithPayload ?: return)
            EVENT_POP_SCREEN -> {
                supportFragmentManager.popBackStack(
                        event.typedPayload<String?>(),
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
            }
        }

    }

    override fun hideProgress() {
        progress.hide()
        progress.setVisible(false)
    }

    override fun showProgress() {
        progress.show()
        progress.setVisible(true)
    }

    private fun showUpButton(enable: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(enable)
    }

    object Events {
        const val EVENT_ERROR = "Error"
        const val EVENT_SHOW_PROGRESS = "Show progress"
        const val EVENT_HIDE_PROGRESS = "Hide progress"
        const val EVENT_POP_SCREEN = "Pop screen"
        const val EVENT_MESSAGE = "Message"
    }

}
