package com.marcinstramowski.socialmeal.screens.account.signIn

import com.marcinstramowski.socialmeal.screens.base.BaseContract
import io.reactivex.Observable

/**
 * Contract interfaces between [SignInFragment] and [SignInPresenter]
 */
interface SignInContract {
    interface View : BaseContract.View<Presenter> {
        fun showResetPasswordScreen()
        fun showSignUpScreen()
        fun showMainActivity()
        fun setSignInButtonProcessing(processing: Boolean)
        fun setSignInButtonEnabled(enabled: Boolean)
    }

    interface Presenter : BaseContract.Presenter {
        fun onSignInButtonClick()
        fun onResetPasswordClick()
        fun onSignUpButtonClick()
        fun observeFieldsChanges(emailField: Observable<String>, passwordField: Observable<String>)
    }
}