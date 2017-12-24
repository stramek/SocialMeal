package com.marcinstramowski.socialmeal.screens.login

import com.marcinstramowski.socialmeal.screens.base.BaseContract

/**
 * Contract interfaces between [AccountActivity] and [AccountPresenter]
 */
interface AccountContract {

    interface View : BaseContract.View<Presenter>

    interface Presenter : BaseContract.Presenter
}