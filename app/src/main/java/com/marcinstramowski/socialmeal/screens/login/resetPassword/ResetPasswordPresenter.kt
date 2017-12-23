package com.marcinstramowski.socialmeal.screens.login.resetPassword

import com.github.ajalt.timberkt.e
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Reset password screen logic
 */
class ResetPasswordPresenter @Inject constructor(
        private val view: ResetPasswordContract.View
) : ResetPasswordContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        compositeDisposable.clear()
    }

    override fun observeFieldsChanges(emailField: Observable<String>) {
        compositeDisposable.add(emailField
                .map { it.isNotEmpty() }
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { fieldsFilled ->
                            when (fieldsFilled) {
                                true -> view.setResetButtonEnabled()
                                false -> view.setResetButtonDisabled()
                            }
                        },
                        { error -> e(error) }
                )
        )
    }
}