package com.marcinstramowski.socialmeal.screens.base

import android.support.annotation.IdRes
import com.marcinstramowski.socialmeal.R

/**
 * Created by Stramek on 05.06.2017.
 */

interface ActivityFragmentManager {
    fun <T : BaseFragment<*>> setFragment(fragment: T, @IdRes containerId: Int = R.id.container)
    fun <T : BaseFragment<*>> changeFragment(fragment: T, @IdRes containerId: Int = R.id.container)
}