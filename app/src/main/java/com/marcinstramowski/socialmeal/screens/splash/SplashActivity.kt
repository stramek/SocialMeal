package com.marcinstramowski.socialmeal.screens.splash

import android.os.Bundle
import com.marcinstramowski.socialmeal.screens.account.AccountActivity
import dagger.android.support.DaggerAppCompatActivity
import org.jetbrains.anko.intentFor

/**
 * Initial activity after application is launched.
 */
class SplashActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(intentFor<AccountActivity>().putExtras(this.intent))
        finish()
    }
}