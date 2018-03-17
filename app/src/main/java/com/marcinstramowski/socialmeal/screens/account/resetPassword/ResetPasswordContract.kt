package com.marcinstramowski.socialmeal.screens.account.resetPassword

import android.support.annotation.StringRes
import com.marcinstramowski.socialmeal.model.resetPassword.ResetPasswordFormFields
import com.marcinstramowski.socialmeal.screens.base.BaseContract
import io.reactivex.Observable

/**
 * Contract interfaces between [ResetPasswordFragment] and [ResetPasswordPresenter]
 */
interface ResetPasswordContract {
    interface View : BaseContract.View<Presenter> {

        fun setResetButtonEnabled(enabled: Boolean)

        fun setResetButtonProcessing(processing: Boolean)

        fun clearEmailField()

        fun showResetPasswordSuccessMessage()

        fun showResetPasswordErrorMessage(@StringRes messageId: Int)

        fun showInvalidEmailMessage(visible: Boolean)
    }

    interface Presenter : BaseContract.Presenter {

        fun observeFieldsChanges(emailObservable: Observable<ResetPasswordFormFields>)

        fun onResetProgressButtonPressed(resetPasswordFormFields: ResetPasswordFormFields)
    }
}