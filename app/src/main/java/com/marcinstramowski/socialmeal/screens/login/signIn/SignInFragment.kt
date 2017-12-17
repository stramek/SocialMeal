package com.marcinstramowski.socialmeal.screens.login.signIn

import android.os.Bundle
import com.github.ajalt.timberkt.e
import com.marcinstramowski.socialmeal.R
import com.marcinstramowski.socialmeal.screens.base.BaseFragment
import com.marcinstramowski.socialmeal.screens.login.resetPassword.ResetPasswordFragment
import com.marcinstramowski.socialmeal.screens.login.signUp.SignUpFragment
import com.marcinstramowski.socialmeal.screens.main.MainActivity
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_sign_in.*
import org.jetbrains.anko.support.v4.startActivity
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by marcinstramowski on 17.12.2017.
 */
class SignInFragment : BaseFragment<SignInContract.Presenter>(), SignInContract.View {

    @Inject override lateinit var presenter: SignInContract.Presenter

    override val contentViewId = R.layout.fragment_sign_in

    private val compositeDisposable = CompositeDisposable()

    override fun onCreated(savedInstanceState: Bundle?) {

        loginProgressButton.setOnClickListener {
            compositeDisposable.add(Single.just(1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { loginProgressButton.setProcessing() }
                    .doOnError { loginProgressButton.setProcessingFinished() }
                    .delay(2000, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                    .subscribe(
                            { _ -> goToMainActivity() },
                            { error -> e(error) }
                    )
            )
        }

        forgotPassword.setOnClickListener { activityFragmentManager.changeFragment(ResetPasswordFragment()) }
        register.setOnClickListener { activityFragmentManager.changeFragment(SignUpFragment()) }

    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }

    private fun goToMainActivity() {
        activity.finish()
        startActivity<MainActivity>()
    }
}