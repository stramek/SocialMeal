package com.marcinstramowski.socialmeal.screens.login.signIn

import com.github.ajalt.timberkt.e
import com.marcinstramowski.socialmeal.screens.login.resetPassword.ResetPasswordContract
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_sign_in.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Sign in screen logic
 */
class SignInPresenter @Inject constructor(
        private val view: SignInContract.View
) : SignInContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    override fun onSignInButtonClick() {
        compositeDisposable.add(Single.just(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.setSignInButtonProcessing() }
                .doOnError { view.setSignInButtonProcessingFinished() }
                .delay(2000, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
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