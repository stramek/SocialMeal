package com.marcinstramowski.socialmeal.screens.account.signUp

import com.github.ajalt.timberkt.e
import com.marcinstramowski.socialmeal.R
import com.marcinstramowski.socialmeal.account.CredentialsValidator.Companion.ERROR_MESSAGE_DELAY_MS
import com.marcinstramowski.socialmeal.account.UserPrefsDataSource
import com.marcinstramowski.socialmeal.api.ServerApi
import com.marcinstramowski.socialmeal.model.SignInRequest
import com.marcinstramowski.socialmeal.model.SignUpFormFields
import com.marcinstramowski.socialmeal.model.SignUpRequest
import com.marcinstramowski.socialmeal.rxSchedulers.SchedulerProvider
import com.marcinstramowski.socialmeal.utils.DeviceInfo
import com.marcinstramowski.socialmeal.utils.NetworkErrorMessageBuilder
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Sign in screen logic
 */
class SignUpPresenter @Inject constructor(
        private val view: SignUpContract.View,
        private val managementApi: ServerApi.ManagementApi,
        private val deviceInfo: DeviceInfo,
        private val userPrefsDataSource: UserPrefsDataSource,
        var schedulers: SchedulerProvider
) : SignUpContract.Presenter {

    companion object {

    }

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate() {}

    override fun onDestroy() {}

    override fun onSignUpButtonClick(fields: SignUpFormFields) {
        compositeDisposable.add(
                managementApi.signUp(SignUpRequest(fields.firstName, fields.surname, fields.email, fields.password))
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

    override fun observeFieldsChanges(observable: Observable<SignUpFormFields>) {
        compositeDisposable.add(
                observable
                        .doOnNext { registrationFields -> view.setSignUpButtonEnabled(registrationFields.fieldsValid()) }
                        .debounce(ERROR_MESSAGE_DELAY_MS, TimeUnit.MILLISECONDS, schedulers.ui())
                        .subscribe(
                                { registrationFields ->
                                    view.showPasswordsDontMatchMessage(registrationFields.shouldShowPasswordsDontMatchMessage())
                                    view.showInvalidPasswordMessage(registrationFields.shouldShowInvalidPasswordMessage())
                                    view.showInvalidEmailMessage(registrationFields.shouldShowInvalidEmailMessage())
                                },
                                { error -> e(error) }
                        )
        )
    }

    private fun SignUpFormFields.shouldShowPasswordsDontMatchMessage() =
            this.confirmPassword.isNotBlank() && this.passwordsNotMatch()

    private fun SignUpFormFields.shouldShowInvalidPasswordMessage() =
            this.passwordNotValid() && this.password.isNotBlank()

    private fun SignUpFormFields.shouldShowInvalidEmailMessage() =
            this.emailNotValid() && this.email.isNotBlank()
}