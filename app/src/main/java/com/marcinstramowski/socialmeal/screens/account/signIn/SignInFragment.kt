package com.marcinstramowski.socialmeal.screens.account.signIn

import android.annotation.SuppressLint
import android.os.Bundle
import com.jakewharton.rxbinding2.widget.textChanges
import com.marcinstramowski.socialmeal.BuildConfig
import com.marcinstramowski.socialmeal.R
import com.marcinstramowski.socialmeal.model.SignInFormFields
import com.marcinstramowski.socialmeal.screens.account.resetPassword.ResetPasswordFragment
import com.marcinstramowski.socialmeal.screens.account.signUp.SignUpFragment
import com.marcinstramowski.socialmeal.screens.base.BaseFragment
import com.marcinstramowski.socialmeal.screens.main.MainActivity
import kotlinx.android.synthetic.main.fragment_sign_in.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

/**
 * Screen that allows user sign in to the application
 */
class SignInFragment : BaseFragment<SignInContract.Presenter>(), SignInContract.View {

    @Inject override lateinit var presenter: SignInContract.Presenter

    override val contentViewId = R.layout.fragment_sign_in

    override fun onCreated(savedInstanceState: Bundle?) {
        signInProgressButton.setOnClickListener { presenter.onSignInButtonClick(collectSignInFormData()) }
        resetPasswordButton.setOnClickListener { presenter.onResetPasswordClick() }
        signUpButton.setOnClickListener { presenter.onSignUpButtonClick() }
        signInFacebookButton.setOnClickListener { presenter.onSignInWithFacebookClick() }
        signInTwitterButton.setOnClickListener { presenter.onSignInWithTwitterClick() }
        signInGoogleButton.setOnClickListener { presenter.onSignInWithGoogleClick() }
        observeFormFields()
        setupDebugFields()
    }

    private fun observeFormFields() {
        presenter.observeFieldsChanges(
                loginLogin.textChanges().map { it.toString() },
                loginPassword.textChanges().map { it.toString() }
        )
    }

    private fun collectSignInFormData(): SignInFormFields {
        return SignInFormFields(
                loginLogin.text.toString().trim(),
                loginPassword.text.toString()
        )
    }

    @SuppressLint("SetTextI18n")
    private fun setupDebugFields() {
        if (BuildConfig.DEBUG) {
            loginLogin.setText("debugLogin")
            loginPassword.setText("debugPassword")
        }
    }

    override fun setSignInButtonEnabled(enabled: Boolean) {
        signInProgressButton.isEnabled = enabled
    }

    override fun showResetPasswordScreen() {
        activityFragmentManager.addFragmentToBackStack(ResetPasswordFragment())
    }

    override fun showSignUpScreen() {
        activityFragmentManager.addFragmentToBackStack(SignUpFragment())
    }

    override fun showMainActivity() {
        activity?.finish()
        startActivity<MainActivity>()
    }

    override fun showErrorMessage(stringId: Int) {
        toast(stringId)
    }

    override fun setSignInButtonProcessing(processing: Boolean) {
        signInProgressButton.setProcessing(processing)
    }
}