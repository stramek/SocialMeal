package com.marcinstramowski.socialmeal.screens.login

import android.os.Bundle
import com.marcinstramowski.socialmeal.R
import com.marcinstramowski.socialmeal.screens.base.BaseActivity
import com.marcinstramowski.socialmeal.screens.main.MainActivity
import com.marcinstramowski.socialmeal.screens.main.MainContract
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject

/**
 * A email screen that offers email via email/password.
 */
class LoginActivity : BaseActivity<LoginContract.Presenter>(), LoginContract.View {

    override val contentViewId = R.layout.activity_login

    @Inject override lateinit var presenter: LoginContract.Presenter

    override fun onCreated(savedInstanceState: Bundle?) {
        login.setOnClickListener {
            finish()
            startActivity<MainActivity>()
        }
    }
}

