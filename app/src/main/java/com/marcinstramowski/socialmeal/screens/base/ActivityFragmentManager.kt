package com.marcinstramowski.socialmeal.screens.base

import android.support.annotation.IdRes
import android.view.View
import com.marcinstramowski.socialmeal.R

/**
 * Defines fragment transition methods
 */
interface ActivityFragmentManager {
    fun <T : BaseFragment<*>> setFragment(fragment: T, @IdRes containerId: Int = R.id.container)
    fun <T : BaseFragment<*>> changeFragment(fragment: T, @IdRes containerId: Int = R.id.container)
}