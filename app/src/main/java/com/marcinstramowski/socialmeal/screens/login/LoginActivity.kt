package com.marcinstramowski.socialmeal.screens.login

import android.os.Bundle
import com.marcinstramowski.socialmeal.R
import com.marcinstramowski.socialmeal.screens.base.BaseActivity
import com.marcinstramowski.socialmeal.screens.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject
import android.graphics.drawable.AnimationDrawable
import android.view.View
import com.github.ajalt.timberkt.e
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


/**
 * A email screen that offers email via email/password.
 */
class LoginActivity : BaseActivity<LoginContract.Presenter>(), LoginContract.View {

    override val contentViewId = R.layout.activity_login

    @Inject override lateinit var presenter: LoginContract.Presenter

    lateinit var animationDrawable: AnimationDrawable

    override fun onCreated(savedInstanceState: Bundle?) {

        animationDrawable = loginBackground.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(5000)

        loginProgressButton.setOnClickListener {
            Single.just(1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { loginProgressButton.setProcessing() }
                    .doOnError { loginProgressButton.setProcessingFinished() }
                    .delay(2000, TimeUnit.MILLISECONDS)
                    .subscribe(
                            { _ -> goToMainActivity() },
                            { error -> e(error) }
                    )
        }

    }

    fun goToMainActivity() {
        finish()
        startActivity<MainActivity>()
    }

    override fun onResume() {
        super.onResume()
        animationDrawable.start()
    }

    override fun onPause() {
        super.onPause()
        animationDrawable.stop()
    }
}

