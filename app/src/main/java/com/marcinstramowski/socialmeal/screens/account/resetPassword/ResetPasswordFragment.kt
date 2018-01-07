package com.marcinstramowski.socialmeal.screens.account.resetPassword

import android.os.Bundle
import com.jakewharton.rxbinding2.widget.textChanges
import com.marcinstramowski.socialmeal.R
import com.marcinstramowski.socialmeal.screens.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_reset_password.*
import javax.inject.Inject

/**
 * Screen that allows user to reset their password
 */
class ResetPasswordFragment : BaseFragment<ResetPasswordContract.Presenter>(), ResetPasswordContract.View {

    @Inject override lateinit var presenter: ResetPasswordContract.Presenter

    override val contentViewId = R.layout.fragment_reset_password

    override fun onCreated(savedInstanceState: Bundle?) {
        presenter.observeFieldsChanges(resetPasswordEmail.textChanges().map { it.toString() })
    }

    override fun setResetButtonEnabled() {
        resetProgressButton.isEnabled = true
    }

    override fun setResetButtonDisabled() {
        resetProgressButton.isEnabled = false
    }
}