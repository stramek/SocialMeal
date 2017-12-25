package com.marcinstramowski.socialmeal.screens.main.myEvents

import com.marcinstramowski.socialmeal.screens.base.BaseContract
import io.reactivex.Observable

/**
 * Contract interfaces between [MyEventsFragment] and [MyEventsPresenter]
 */
interface MyEventsContract {
    interface View : BaseContract.View<Presenter> {

    }

    interface Presenter : BaseContract.Presenter {

    }
}