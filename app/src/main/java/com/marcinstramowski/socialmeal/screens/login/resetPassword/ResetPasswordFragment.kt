package com.marcinstramowski.socialmeal.screens.login.resetPassword

import android.os.Bundle
import com.marcinstramowski.socialmeal.R
import com.marcinstramowski.socialmeal.screens.base.BaseFragment
import javax.inject.Inject

/**
 * Created by marcinstramowski on 17.12.2017.
 */
class ResetPasswordFragment : BaseFragment<ResetPasswordContract.Presenter>(), ResetPasswordContract.View {

    @Inject override lateinit var presenter: ResetPasswordContract.Presenter

    override val contentViewId = R.layout.fragment_reset_password

    override fun onCreated(savedInstanceState: Bundle?) {

    }

}