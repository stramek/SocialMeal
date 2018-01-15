package com.marcinstramowski.socialmeal.screens.account.signIn

import com.github.ajalt.timberkt.e
import com.marcinstramowski.socialmeal.R
import com.marcinstramowski.socialmeal.account.UserPrefsDataSource
import com.marcinstramowski.socialmeal.api.ServerApi
import com.marcinstramowski.socialmeal.model.SignInFormFields
import com.marcinstramowski.socialmeal.model.SignInRequest
import com.marcinstramowski.socialmeal.rxSchedulers.SchedulerProvider
import com.marcinstramowski.socialmeal.utils.DeviceInfo
import com.marcinstramowski.socialmeal.utils.NetworkErrorMessageBuilder
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

/**
 * Sign in screen logic
 */
class SignInPresenter @Inject constructor(
        private val view: SignInContract.View,
        private val managementApi: ServerApi.ManagementApi,
        private val deviceInfo: DeviceInfo,
        private val userPrefsDataSource: UserPrefsDataSource,
        private val schedulers: SchedulerProvider
) : SignInContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    override fun observeFieldsChanges(emailField: Observable<String>, passwordField: Observable<String>) {
        val isSignInEnabled: Observable<Boolean> = Observable.combineLatest(
                emailField,
                passwordField,
                BiFunction { login, password -> login.isNotEmpty() && password.isNotEmpty() })
        compositeDisposable.add(isSignInEnabled
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                        { fieldsFilled -> view.setSignInButtonEnabled(fieldsFilled) },
                        { error -> e(error) }
                )
        )
    }

    override fun onSignInButtonClick(fields: SignInFormFields) {
        compositeDisposable.add(
                managementApi.signIn(SignInRequest(fields.email, fields.password,
                        deviceInfo.deviceId, deviceInfo.deviceName))
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.io())
                .doOnSuccess { userPrefsDataSource.saveTokens(it) }
                .observeOn(schedulers.ui())
                .doOnSubscribe { view.setSignInButtonProcessing(true) }
                .doAfterTerminate { view.setSignInButtonProcessing(false) }
                .subscribe(
                        { _ -> view.showMainActivity() },
                        { error ->
                            view.showErrorMessage(getNetworkErrorMessage(error))
                            e(error)
                        }
                )
        )
    }

    private fun getNetworkErrorMessage(error: Throwable): Int {
        return NetworkErrorMessageBuilder(error)
                .addHttpErrorMessage(400, R.string.wrong_email_or_password)
                .getMessageStringId()
    }

    override fun onResetPasswordClick() {
        view.showResetPasswordScreen()
    }

    override fun onSignUpButtonClick() {
        view.showSignUpScreen()
    }

    override fun onSignInWithFacebookClick() {
        view.showErrorMessage(R.string.not_implemented_yet)
    }

    override fun onSignInWithTwitterClick() {
        view.showErrorMessage(R.string.not_implemented_yet)
    }

    override fun onSignInWithGoogleClick() {
        view.showErrorMessage(R.string.not_implemented_yet)
    }
}