package com.marcinstramowski.socialmeal.screens.account.signIn

import android.support.annotation.StringRes
import com.marcinstramowski.socialmeal.model.SignInFormFields
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

        fun showErrorMessage(@StringRes stringId: Int)

        fun signInWithFacebook()

        fun signInWithTwitter()

        fun signInWithGoogle()
    }

    interface Presenter : BaseContract.Presenter {

        fun onSignUpButtonClick()

        fun onResetPasswordClick()

        fun onSignInButtonClick(fields: SignInFormFields)

        fun onSignInWithFacebookClick()

        fun onSignInWithTwitterClick()

        fun onSignInWithGoogleClick()

        fun observeFieldsChanges(observable: Observable<SignInFormFields>)
    }
}