package com.marcinstramowski.socialmeal.screens.login.signUp

import android.os.Bundle
import com.marcinstramowski.socialmeal.R
import com.marcinstramowski.socialmeal.screens.base.BaseFragment
import com.marcinstramowski.socialmeal.screens.login.signIn.SignInContract
import javax.inject.Inject

/**
 * Created by marcinstramowski on 17.12.2017.
 */
class SignUpFragment : BaseFragment<SignUpContract.Presenter>(), SignUpContract.View {

    @Inject override lateinit var presenter: SignUpContract.Presenter

    override val contentViewId = R.layout.fragment_sign_up

    override fun onCreated(savedInstanceState: Bundle?) {

    }
}