package com.marcinstramowski.socialmeal.screens.splash

import android.content.Intent
import android.os.Bundle
import com.marcinstramowski.socialmeal.screens.login.LoginActivity
import com.marcinstramowski.socialmeal.screens.main.MainActivity
import dagger.android.support.DaggerAppCompatActivity
import org.jetbrains.anko.intentFor
import javax.inject.Inject

/**
 * Created by marcinstramowski on 09.12.2017.
 */
class SplashActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(intentFor<LoginActivity>().putExtras(this.intent))
        finish()
    }
}