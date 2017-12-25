package com.marcinstramowski.socialmeal.screens.account.signIn

import android.os.Bundle
import com.jakewharton.rxbinding2.widget.textChanges
import com.marcinstramowski.socialmeal.R
import com.marcinstramowski.socialmeal.screens.base.BaseFragment
import com.marcinstramowski.socialmeal.screens.account.resetPassword.ResetPasswordFragment
import com.marcinstramowski.socialmeal.screens.account.signUp.SignUpFragment
import com.marcinstramowski.socialmeal.screens.main.MainActivity
import kotlinx.android.synthetic.main.fragment_sign_in.*
import org.jetbrains.anko.support.v4.startActivity
import javax.inject.Inject

/**
 * Screen that allows user sign in to the application
 */
class SignInFragment : BaseFragment<SignInContract.Presenter>(), SignInContract.View {

    @Inject override lateinit var presenter: SignInContract.Presenter

    override val contentViewId = R.layout.fragment_sign_in

    override fun onCreated(savedInstanceState: Bundle?) {
        loginProgressButton.setOnClickListener { presenter.onSignInButtonClick() }
        resetPasswordButton.setOnClickListener { presenter.onResetPasswordClick() }
        signUpButton.setOnClickListener { presenter.onSignUpButtonClick() }
        presenter.observeFieldsChanges(
                loginLogin.textChanges().map { it.toString() },
                loginPassword.textChanges().map { it.toString() }
        )
    }

    override fun setLoginButtonEnabled() {
        loginProgressButton.setEnabled()
    }

    override fun setLoginButtonDisabled() {
        loginProgressButton.setDisabled()
    }

    override fun showResetPasswordScreen() {
        activityFragmentManager.changeFragment(ResetPasswordFragment())
    }

    override fun showSignUpScreen() {
        activityFragmentManager.changeFragment(SignUpFragment())
    }

    override fun showMainActivity() {
        activity.finish()
        startActivity<MainActivity>()
    }

    override fun setSignInButtonProcessing() {
        loginProgressButton.setProcessing()
    }

    override fun setSignInButtonProcessingFinished() {
        loginProgressButton.setProcessingFinished()
    }
}