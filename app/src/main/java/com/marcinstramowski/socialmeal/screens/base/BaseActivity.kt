package com.marcinstramowski.socialmeal.screens.base

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity

/**
 * Created by marcinstramowski on 09.12.2017.
 */
abstract class BaseActivity<out T : BaseContract.Presenter> : DaggerAppCompatActivity(), BaseContract.View<T> {

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
}
