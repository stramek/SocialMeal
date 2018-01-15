package com.marcinstramowski.socialmeal.rxSchedulers

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Provider for non-test [SchedulerProvider]
 */
@Singleton
class AppSchedulerProvider @Inject constructor() : SchedulerProvider {

    override fun ui() = AndroidSchedulers.mainThread()

    override fun computation() = Schedulers.computation()

    override fun trampoline() = Schedulers.trampoline()

    override fun newThread() = Schedulers.newThread()

    override fun io() = Schedulers.io()
}