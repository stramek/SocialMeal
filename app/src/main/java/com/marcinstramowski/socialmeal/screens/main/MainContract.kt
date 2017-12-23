package com.marcinstramowski.socialmeal.screens.main

import com.marcinstramowski.socialmeal.screens.base.BaseContract

/**
 * Contract interfaces between [MainActivity] and [MainPresenter]
 */
interface MainContract {

    interface View : BaseContract.View<Presenter> {

    }

    interface Presenter : BaseContract.Presenter {

    }
}