package com.marcinstramowski.socialmeal.screens.login

import android.os.Bundle
import com.marcinstramowski.socialmeal.R
import com.marcinstramowski.socialmeal.screens.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject
import android.graphics.drawable.AnimationDrawable
import com.marcinstramowski.socialmeal.screens.login.signIn.SignInFragment


/**
 * A email screen that offers email via email/password.
 */
class AccountActivity : BaseActivity<AccountContract.Presenter>(), AccountContract.View {

    override val contentViewId = R.layout.activity_login

    @Inject override lateinit var presenter: AccountContract.Presenter

    private lateinit var animationDrawable: AnimationDrawable

    override fun onCreated(savedInstanceState: Bundle?) {
        animationDrawable = container.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(5000)
        savedInstanceState ?: setFragment(SignInFragment())
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

