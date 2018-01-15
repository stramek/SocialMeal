package com.marcinstramowski.socialmeal.rxSchedulers

import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler

/**
 * Provider for test [SchedulerProvider]
 */
class TestSchedulerProvider(private val scheduler: TestScheduler) : SchedulerProvider {

    override fun ui(): Scheduler {
        return scheduler
    }

    override fun computation(): Scheduler {
        return scheduler
    }

    override fun trampoline(): Scheduler {
        return scheduler
    }

    override fun newThread(): Scheduler {
        return scheduler
    }

    override fun io(): Scheduler {
        return scheduler
    }
}