package com.marcinstramowski.socialmeal.screens.account.signUp

import com.github.ajalt.timberkt.e
import com.marcinstramowski.socialmeal.R
import com.marcinstramowski.socialmeal.account.DataValidator
import com.marcinstramowski.socialmeal.account.UserPrefsDataSource
import com.marcinstramowski.socialmeal.api.ServerApi
import com.marcinstramowski.socialmeal.model.SignInRequest
import com.marcinstramowski.socialmeal.model.SignUpFormFields
import com.marcinstramowski.socialmeal.model.SignUpRequest
import com.marcinstramowski.socialmeal.rxSchedulers.SchedulerProvider
import com.marcinstramowski.socialmeal.utils.DeviceInfo
import com.marcinstramowski.socialmeal.utils.NetworkErrorMessageBuilder
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Sign in screen logic
 */
class SignUpPresenter @Inject constructor(
        private val view: SignUpContract.View,
        private val managementApi: ServerApi.ManagementApi,
        private val deviceInfo: DeviceInfo,
        private val userPrefsDataSource: UserPrefsDataSource,
        private val dataValidator: DataValidator,
        private val schedulers: SchedulerProvider
) : SignUpContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate() {
    }

    override fun onDestroy() {

    }

    override fun onSignUpButtonClick(fields: SignUpFormFields) {
        compositeDisposable.add(
                managementApi.signUp(SignUpRequest(fields.firstname, fields.surname, fields.email, fields.password))
                        .andThen(managementApi.signIn(SignInRequest(fields.email, fields.password,
                                deviceInfo.deviceId, deviceInfo.deviceName)))
                        .subscribeOn(schedulers.io())
                        .observeOn(schedulers.io())
                        .doOnSuccess { userPrefsDataSource.saveTokens(it) }
                        .observeOn(schedulers.ui())
                        .doOnSubscribe { view.setSignUpButtonProcessing(true) }
                        .doAfterTerminate { view.setSignUpButtonProcessing(false) }
                        .subscribe(
                                { view.showMainActivity() },
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
        val passwordValid = dataValidator.isPasswordValid(password)
        val emailValid = dataValidator.isEmailValid(email)
        val fieldsValid = fieldsNotEmpty && passwordsMatch && passwordValid && emailValid

        view.showPasswordsDontMatchMessage(confirmPassword.isNotBlank() && !passwordsMatch)
        view.showInvalidPasswordMessage(!passwordValid && password.isNotBlank())
        view.showInvalidEmailMessage(!emailValid && email.isNotBlank())
        view.setSignUpButtonEnabled(fieldsValid)
    }
}