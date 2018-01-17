package com.marcinstramowski.socialmeal.screens.account.resetPassword

import com.github.ajalt.timberkt.e
import com.marcinstramowski.socialmeal.R
import com.marcinstramowski.socialmeal.account.CredentialsValidator.Companion.ERROR_MESSAGE_DELAY_MS
import com.marcinstramowski.socialmeal.api.ServerApi
import com.marcinstramowski.socialmeal.model.ResetPasswordFormFields
import com.marcinstramowski.socialmeal.model.ResetPasswordRequest
import com.marcinstramowski.socialmeal.rxSchedulers.SchedulerProvider
import com.marcinstramowski.socialmeal.utils.NetworkErrorMessageBuilder
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Reset password screen logic
 */
class ResetPasswordPresenter @Inject constructor(
    private val view: ResetPasswordContract.View,
    private val managementApi: ServerApi.ManagementApi,
    var schedulers: SchedulerProvider
) : ResetPasswordContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun observeFieldsChanges(emailObservable: Observable<ResetPasswordFormFields>) {
        compositeDisposable.add(emailObservable
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .doOnNext { formFields -> view.setResetButtonEnabled(formFields.emailValid()) }
            .debounce(ERROR_MESSAGE_DELAY_MS, TimeUnit.MILLISECONDS, schedulers.ui())
            .subscribe(
                { formFields -> view.showInvalidEmailMessage(formFields.shouldShowInvalidEmailMessage()) },
                { error -> e(error) }
            )
        )
    }

    override fun onResetProgressButtonPressed(resetPasswordFormFields: ResetPasswordFormFields) {
        compositeDisposable.add(
            managementApi.resetPassword(ResetPasswordRequest(resetPasswordFormFields.email))
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .doOnSubscribe { view.setResetButtonProcessing(true) }
                .doAfterTerminate { view.setResetButtonProcessing(false) }
                .subscribe(
                    { view.showResetPasswordSuccessMessage() },
                    { error ->
                        view.showResetPasswordErrorMessage(getResetPasswordErrorMessage(error))
                        e(error)
                    }
                )
        )
    }

    private fun getResetPasswordErrorMessage(error: Throwable): Int {
        return NetworkErrorMessageBuilder(error)
            .addHttpErrorMessage(400, R.string.reset_password_no_email)
            .getMessageStringId()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    private fun ResetPasswordFormFields.shouldShowInvalidEmailMessage() =
        this.emailNotValid() && this.email.isNotBlank()
}