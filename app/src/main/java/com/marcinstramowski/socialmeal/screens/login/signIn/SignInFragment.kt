package com.marcinstramowski.socialmeal.screens.login.signIn

import android.os.Bundle
import android.support.transition.Explode
import android.transition.Fade
import com.marcinstramowski.socialmeal.R
import com.marcinstramowski.socialmeal.screens.base.BaseFragment
import com.marcinstramowski.socialmeal.screens.login.resetPassword.ResetPasswordFragment
import com.marcinstramowski.socialmeal.screens.login.signUp.SignUpFragment
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