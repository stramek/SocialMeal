package com.marcinstramowski.socialmeal.screens.login.signIn

import com.marcinstramowski.socialmeal.screens.base.BaseContract
import io.reactivex.Observable

/**
 * Contract interfaces between [SignInFragment] and [SignInPresenter]
 */
class SignInContract {
    interface View : BaseContract.View<Presenter> {
        fun showResetPasswordScreen()
        fun showSignUpScreen()
        fun showMainActivity()
        fun setSignInButtonProcessing()
        fun setSignInButtonProcessingFinished()
        fun setLoginButtonEnabled()
        fun setLoginButtonDisabled()
    }

    interface Presenter : BaseContract.Presenter {
        fun onSignInButtonClick()
        fun onResetPasswordClick()
        fun onSignUpButtonClick()
        fun observeFieldsChanges(emailField: Observable<String>, passwordField: Observable<String>)
    }
}