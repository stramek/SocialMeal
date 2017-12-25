package com.marcinstramowski.socialmeal.screens.main.searchEvents

import com.marcinstramowski.socialmeal.screens.base.BaseContract

/**
 * Contract interfaces between [SearchFragment] and [SearchPresenter]
 */
interface SearchContract {
    interface View : BaseContract.View<Presenter> {

    }

    interface Presenter : BaseContract.Presenter {

    }
}