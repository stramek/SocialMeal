package com.marcinstramowski.socialmeal.rxSchedulers

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

/**
 * Provider for test [SchedulerProvider]
 */
class TrampolineSchedulerProvider : SchedulerProvider {

    override fun ui(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun computation(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun trampoline(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun newThread(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun io(): Scheduler {
        return Schedulers.trampoline()
    }
}