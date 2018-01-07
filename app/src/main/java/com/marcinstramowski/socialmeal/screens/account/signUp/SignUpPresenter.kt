package com.marcinstramowski.socialmeal.screens.account.signUp

import android.util.Patterns
import com.github.ajalt.timberkt.Timber
import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import com.jakewharton.rxbinding2.widget.checkedChanges
import com.jakewharton.rxbinding2.widget.textChanges
import com.marcinstramowski.socialmeal.R
import com.marcinstramowski.socialmeal.api.ServerApi
import com.marcinstramowski.socialmeal.model.SignUpFormFields
import com.marcinstramowski.socialmeal.model.SignUpRequest
import com.marcinstramowski.socialmeal.utils.DeviceInfo
import com.marcinstramowski.socialmeal.utils.NetworkErrorMessageBuilder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Sign in screen logic
 */
class SignUpPresenter @Inject constructor(
        private val view: SignUpContract.View,
        private val userApi: ServerApi.ManagementApi,
        private val deviceInfo: DeviceInfo
) : SignUpContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate() {
    }

    override fun onDestroy() {

    }

    override fun onSignUpButtonClick(fields: SignUpFormFields) {
        compositeDisposable.add(
                userApi.signUp(SignUpRequest(fields.firstname, fields.surname, fields.email, fields.password))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { view.setSignUpButtonProcessing(true) }
                        .doAfterTerminate { view.setSignUpButtonProcessing(false) }
                        .subscribe(
                                { i { "Registration success" } },
                                { error ->
                                    view.showErrorMessage(getNetworkErrorMessage(error))
                                    e(error)
                                }
                        )
        )
    }

    private fun getNetworkErrorMessage(error: Throwable): Int {
        return NetworkErrorMessageBuilder(error)
                .addHttpErrorMessage(400, R.string.email_is_taken)
                .getMessageStringId()
    }

    override fun observeFieldsChanges(fields: SignUpFormFields) {
        val (name, surname, email, password, confirmPassword) = fields

        val fieldsNotEmpty = name.isNotBlank() && surname.isNotBlank() && email.isNotBlank() && password.isNotBlank()
        val passwordsMatch = password == confirmPassword
        val passwordValid = isPasswordValid(password)
        val emailValid = isEmailValid(email)
        val fieldsValid = fieldsNotEmpty && passwordsMatch && passwordValid && emailValid

        view.showPasswordsDontMatchMessage(confirmPassword.isNotBlank() && !passwordsMatch)
        view.showInvalidPasswordMessage(!passwordValid && password.isNotBlank())
        view.showInvalidEmailMessage(!emailValid && email.isNotBlank())
        view.setSignUpButtonEnabled(fieldsValid)
    }

    private fun isEmailValid(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()


    private fun isPasswordValid(password: String): Boolean {
        val regexStr = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9!@#\$%^&*()_+|]{6,}$"
        return password.matches(Regex(regexStr))
    }
}