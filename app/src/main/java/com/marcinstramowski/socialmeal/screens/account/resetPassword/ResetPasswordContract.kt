package com.marcinstramowski.socialmeal.screens.account.resetPassword

import com.marcinstramowski.socialmeal.screens.base.BaseContract
import io.reactivex.Observable

/**
* Contract interfaces between [ResetPasswordFragment] and [ResetPasswordPresenter]
*/
interface ResetPasswordContract {
    interface View : BaseContract.View<Presenter> {
        fun setResetButtonEnabled()
        fun setResetButtonDisabled()
    }

    interface Presenter : BaseContract.Presenter {
        fun observeFieldsChanges(emailField: Observable<String>)
    }
}