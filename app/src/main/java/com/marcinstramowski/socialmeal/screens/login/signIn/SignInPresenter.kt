package com.marcinstramowski.socialmeal.screens.login.signIn

import com.marcinstramowski.socialmeal.screens.login.resetPassword.ResetPasswordContract
import javax.inject.Inject

/**
 * Created by marcinstramowski on 17.12.2017.
 */
class SignInPresenter @Inject constructor(
        private val view: SignInContract.View
) : SignInContract.Presenter {

    override fun onCreate() {

    }

    override fun onDestroy() {

    }
}