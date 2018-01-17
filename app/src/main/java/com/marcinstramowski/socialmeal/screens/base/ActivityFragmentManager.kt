package com.marcinstramowski.socialmeal.screens.base

import android.support.annotation.IdRes
import com.marcinstramowski.socialmeal.R

/**
 * Defines fragment transition methods
 */
interface ActivityFragmentManager {

    fun <T : BaseFragment<*>> setFragmentNoAnimation(fragment: T, @IdRes containerId: Int = R.id.container)

    fun <T : BaseFragment<*>> setFragment(fragment: T, @IdRes containerId: Int = R.id.container)

    fun <T : BaseFragment<*>> addFragmentToBackStack(fragment: T, @IdRes containerId: Int = R.id.container)
}