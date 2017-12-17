package com.marcinstramowski.socialmeal.screens.base

/**
 * Created by marcinstramowski on 09.12.2017.
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