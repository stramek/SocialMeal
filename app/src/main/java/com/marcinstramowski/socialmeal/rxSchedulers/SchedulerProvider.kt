package com.marcinstramowski.socialmeal.rxSchedulers

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun ui(): Scheduler
    fun computation(): Scheduler
    fun io(): Scheduler
    fun trampoline(): Scheduler
    fun newThread(): Scheduler
}