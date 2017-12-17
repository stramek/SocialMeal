package com.marcinstramowski.socialmeal.screens.base

import android.os.Bundle
import com.marcinstramowski.socialmeal.R
import dagger.android.support.DaggerAppCompatActivity

/**
 * Created by marcinstramowski on 09.12.2017.
 */
abstract class BaseActivity<out T : BaseContract.Presenter> : DaggerAppCompatActivity(), BaseContract.View<T>, ActivityFragmentManager {

    abstract val contentViewId : Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentViewId)
        onCreated(savedInstanceState)
        presenter.onCreate()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    abstract fun onCreated(savedInstanceState: Bundle?)

    override fun <T : BaseFragment<*>> setFragment(fragment: T, containerId: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(containerId, fragment)
        transaction.commit()
    }

    override fun <T : BaseFragment<*>> changeFragment(fragment: T, containerId: Int) {
        val backStateName = fragment.javaClass.name
        val fragmentPopped = supportFragmentManager.popBackStackImmediate(backStateName, 0)
        if (!fragmentPopped && supportFragmentManager.findFragmentByTag(backStateName) == null) {
            supportFragmentManager.beginTransaction().apply {
                replace(containerId, fragment, backStateName)
                addToBackStack(backStateName)
            }.commit()
        }
    }
}
