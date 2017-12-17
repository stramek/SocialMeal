package com.marcinstramowski.socialmeal.screens.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment

/**
 * Created by marcinstramowski on 09.12.2017.
 */
abstract class BaseFragment<out T : BaseContract.Presenter> : DaggerFragment(), BaseContract.View<T> {

    abstract val contentViewId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(contentViewId, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreated(savedInstanceState)
        presenter.onCreate()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    abstract fun onCreated(savedInstanceState: Bundle?)
}