package com.marcinstramowski.socialmeal.screens.account.resetPassword

import android.os.Bundle
import com.jakewharton.rxbinding2.widget.textChanges
import com.marcinstramowski.socialmeal.R
import com.marcinstramowski.socialmeal.extensions.setCompatError
import com.marcinstramowski.socialmeal.model.resetPassword.ResetPasswordFormFields
import com.marcinstramowski.socialmeal.screens.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_reset_password.*
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

/**
 * Screen that allows user to reset their password
 */
class ResetPasswordFragment : BaseFragment<ResetPasswordContract.Presenter>(),
    ResetPasswordContract.View {

    @Inject
    override lateinit var presenter: ResetPasswordContract.Presenter

    override val contentViewId = R.layout.fragment_reset_password

    override fun onCreated(savedInstanceState: Bundle?) {
        presenter.observeFieldsChanges(resetPasswordEmail.textChanges().map { collectResetPasswordFields() })
        resetProgressButton.setOnClickListener {
            presenter.onResetProgressButtonPressed(collectResetPasswordFields())
        }
    }

    private fun collectResetPasswordFields() =
        ResetPasswordFormFields(
            resetPasswordEmail.text.toString()
        )

    override fun setResetButtonEnabled(enabled: Boolean) {
        resetProgressButton.isEnabled = enabled
    }

    override fun setResetButtonProcessing(processing: Boolean) {
        resetProgressButton.setProcessing(processing)
    }

    override fun clearEmailField() {
        resetPasswordEmail.setText("")
    }

    override fun showResetPasswordSuccessMessage() {
        toast(getText(R.string.reset_password_success))
    }

    override fun showResetPasswordErrorMessage(messageId: Int) {
        toast(messageId)
    }

    override fun showInvalidEmailMessage(visible: Boolean) {
        resetPasswordEmail.setCompatError(if (visible) R.string.sign_up_invalid_email else null)
    }
}