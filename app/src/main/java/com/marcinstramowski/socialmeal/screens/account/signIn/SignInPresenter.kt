package com.marcinstramowski.socialmeal.screens.account.signIn

import com.github.ajalt.timberkt.e
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Sign in screen logic
 */
class SignInPresenter @Inject constructor(
        private val view: SignInContract.View
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
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { fieldsFilled -> view.setSignInButtonEnabled(fieldsFilled) },
                        { error -> e(error) }
                )
        )
    }

    override fun onSignInButtonClick() {
        compositeDisposable.add(Single.just(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.setSignInButtonProcessing(true) }
                .doOnError { view.setSignInButtonProcessing(false) }
                .delay(1000, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(
                        { _ -> view.showMainActivity() },
                        { error -> e(error) }
                )
        )
    }

    override fun onResetPasswordClick() {
        view.showResetPasswordScreen()
    }

    override fun onSignUpButtonClick() {
        view.showSignUpScreen()
    }
}