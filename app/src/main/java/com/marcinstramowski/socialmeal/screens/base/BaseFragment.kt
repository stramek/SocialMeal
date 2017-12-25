package com.marcinstramowski.socialmeal.screens.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment

/**
 * Base application fragment defining [BasePresenter] lifecycle methods
 * such as [onCreate], [onStart], [onStop] or [onDestroy]
 */
abstract class BaseFragment<out T : BaseContract.Presenter> : DaggerFragment(), BaseContract.View<T> {

    /**
     * Allows inheriting fragments to easily change view to other fragment
     * though [ActivityFragmentManager.setFragmentNoAnimation] and [ActivityFragmentManager.addFragmentToBackStack] methods
     */
    lateinit var activityFragmentManager: ActivityFragmentManager

    /**
     * Defines layout resource of inflated view
     */
    abstract val contentViewId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(contentViewId, container, false)!!

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActivityFragmentManager()
        onCreated(savedInstanceState)
        presenter.onCreate()
    }

    abstract fun onCreated(savedInstanceState: Bundle?)

    private fun initActivityFragmentManager() {
        if (activity is ActivityFragmentManager) {
            activityFragmentManager = activity as ActivityFragmentManager
        } else {
            throw RuntimeException("Activity should implements ParentRouterManager!")
        }
    }

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
}