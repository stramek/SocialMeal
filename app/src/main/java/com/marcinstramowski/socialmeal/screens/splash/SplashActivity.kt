package com.marcinstramowski.socialmeal.screens.splash

import android.os.Bundle
import com.marcinstramowski.socialmeal.screens.login.AccountActivity
import dagger.android.support.DaggerAppCompatActivity
import org.jetbrains.anko.intentFor

/**
 * Created by marcinstramowski on 09.12.2017.
 */
class SplashActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(intentFor<AccountActivity>().putExtras(this.intent))
        finish()
    }
}