package com.marcinstramowski.socialmeal.screens.login

import com.marcinstramowski.socialmeal.screens.base.BaseContract

/**
 * This specifies the contract between the view and the presenter.
 */
interface LoginContract {

    interface View : BaseContract.View<Presenter> {

    }

    interface Presenter : BaseContract.Presenter {

    }
}