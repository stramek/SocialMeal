package com.marcinstramowski.socialmeal.screens.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.marcinstramowski.socialmeal.R
import com.marcinstramowski.socialmeal.screens.base.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity<MainContract.Presenter>(), MainContract.View {

    @Inject override lateinit var presenter: MainContract.Presenter
    override val contentViewId = R.layout.activity_main

    override fun onCreated(savedInstanceState: Bundle?) {

    }
}
