package com.marcinstramowski.socialmeal.screens.account.signUp

import android.support.annotation.StringRes
import com.marcinstramowski.socialmeal.model.SignUpFormFields
import com.marcinstramowski.socialmeal.screens.base.BaseContract

/**
 * Contract interfaces between [SignUpFragment] and [SignUpPresenter]
 */
interface SignUpContract {

    interface View : BaseContract.View<Presenter> {

        fun setSignUpButtonProcessing(processing: Boolean)

        fun setSignUpButtonEnabled(enabled: Boolean)

        fun showErrorMessage(@StringRes stringId: Int)

        fun showInvalidEmailMessage(visible: Boolean)

        fun showInvalidPasswordMessage(visible: Boolean)

        fun showPasswordsDontMatchMessage(visible: Boolean)
    }

    interface Presenter : BaseContract.Presenter {

        fun onSignUpButtonClick(fields: SignUpFormFields)

        fun observeFieldsChanges(fields: SignUpFormFields)
    }
}