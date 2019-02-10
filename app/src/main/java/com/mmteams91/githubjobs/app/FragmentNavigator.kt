package com.mmteams91.githubjobs.app

import android.os.Bundle
import android.support.transition.Slide
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import com.mmteams91.githubjobs.common.extensions.transact

class FragmentNavigator(
    private val fragmentManager: FragmentManager,
    private val containerId: Int
) : Navigator {

    private var root: Screen? = null

    override fun navigateTo(screen: Screen) {
        if (screen.isRoot) {
            addRoot(screen)
            return
        }
        val name = screen.name()
        if (isInStack(name)) {
            fragmentManager.popBackStack(name, if (screen.addToBackStack) 0 else POP_BACK_STACK_INCLUSIVE)
            if (!screen.addToBackStack) {
                addScreen(screen)
            }
        } else {
            addScreen(screen)
        }
    }

    private fun addScreen(screen: Screen) {
        fragmentManager.transact {
            val fragment = screen.newInstance()
            if (!screen.isRoot) {
                val enterTransition = Slide()
                fragment.enterTransition = enterTransition
            }
            fragment.screen = screen
            add(containerId, fragment)
            if (screen.addToBackStack) {
                addToBackStack(screen.name())
            }
        }
    }

    private fun isInStack(screenName: String): Boolean {
        for (i in 0 until fragmentManager.backStackEntryCount) {
            if (fragmentManager.getBackStackEntryAt(i).name == screenName) {
                return true
            }
        }
        return false
    }

    private fun addRoot(screen: Screen) {
        if (screen.name() == root?.name()) {
            if (fragmentManager.findFragmentById(containerId)?.screen != screen)
                fragmentManager.popBackStack(null, POP_BACK_STACK_INCLUSIVE)
        } else {
            root = screen
            fragmentManager.popBackStack(null, POP_BACK_STACK_INCLUSIVE)
            addScreen(screen)
        }
    }

}

private const val SCREEN_KEY = "navigator_screen"
private var Fragment.screen: Screen?
    get() = arguments?.getParcelable(SCREEN_KEY)
    set(value) = (arguments ?: Bundle().also { arguments = it }).putParcelable(SCREEN_KEY, value)