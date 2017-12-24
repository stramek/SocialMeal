package com.marcinstramowski.socialmeal.screens.base

/**
 * Contract interfaces between [BaseActivity] or [BaseActivity] and [BasePresenter]
 */
interface BaseContract {

    interface View<out T : Presenter> {
        val presenter: T
    }

    interface Presenter {

        fun onCreate() {}

        fun onDestroy() {}

        fun onStart() {}

        fun onStop() {}
    }
}