package com.marcinstramowski.socialmeal.screens.base

import android.os.Bundle
import com.marcinstramowski.socialmeal.R
import dagger.android.support.DaggerAppCompatActivity


/**
 * Base application activity defining [BasePresenter] lifecycle methods
 * such as [onCreate], [onStart], [onStop] or [onDestroy]
 * Also specifies fragment change logic implementing [ActivityFragmentManager] to easily
 * manage them though fragments.
 */
abstract class BaseActivity<out T : BaseContract.Presenter> : DaggerAppCompatActivity(),
    BaseContract.View<T>, ActivityFragmentManager {

    /**
     * Defines layout resource of activity content view
     */
    abstract val contentViewId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentViewId)
        onCreated(savedInstanceState)
        presenter.onCreate()
    }

    abstract fun onCreated(savedInstanceState: Bundle?)

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onStop() {
        presenter.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    /**
     * Sets (no back stack) passed [fragment] to view of assigned [containerId] without animation.
     */
    override fun <T : BaseFragment<*>> setFragmentNoAnimation(fragment: T, containerId: Int) {
        supportFragmentManager.beginTransaction().apply { replace(containerId, fragment) }.commit()
    }

    /**
     * Sets (no back stack) passed [fragment] to view of assigned [containerId] with animation.
     */
    override fun <T : BaseFragment<*>> setFragment(fragment: T, containerId: Int) {
        val fragmentTag = fragment.javaClass.name
        val fragmentNotExists = supportFragmentManager.findFragmentByTag(fragmentTag) == null
        if (fragmentNotExists) {
            supportFragmentManager.beginTransaction().apply {
                setCustomAnimations(R.anim.enter_fade, R.anim.exit_fade)
                replace(containerId, fragment, fragmentTag)
            }.commit()
        }
    }

    /**
     * Sets (with back stack) passed [fragment] to view of assigned [containerId] with animation.
     */
    override fun <T : BaseFragment<*>> addFragmentToBackStack(fragment: T, containerId: Int) {
        val fragmentTag = fragment.javaClass.name
        val fragmentNotExists = supportFragmentManager.findFragmentByTag(fragmentTag) == null
        if (fragmentNotExists) {
            supportFragmentManager.beginTransaction().apply {
                setCustomAnimations(
                    R.anim.enter_fade,
                    R.anim.exit_fade,
                    R.anim.enter_fade,
                    R.anim.exit_fade
                )
                replace(containerId, fragment, fragmentTag)
                addToBackStack(fragmentTag)
            }.commit()
        }
    }
}
