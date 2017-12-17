package com.marcinstramowski.socialmeal.screens.login.signIn

import com.marcinstramowski.socialmeal.screens.base.BaseContract

/**
 * Created by marcinstramowski on 17.12.2017.
 */
class SignInContract {
    interface View : BaseContract.View<Presenter> {
        fun showResetPasswordScreen()
        fun showSignUpScreen()
        fun showMainActivity()
        fun setSignInButtonProcessing()
        fun setSignInButtonProcessingFinished()
    }

    interface Presenter : BaseContract.Presenter {
        fun onSignInButtonClick()
        fun onResetPasswordClick()
        fun onSignUpButtonClick()
    }
}