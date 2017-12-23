package com.marcinstramowski.socialmeal.screens.login.signUp

import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import com.marcinstramowski.socialmeal.R
import com.marcinstramowski.socialmeal.extensions.setErrorWithImage
import com.marcinstramowski.socialmeal.screens.base.BaseFragment
import com.marcinstramowski.socialmeal.screens.login.signIn.SignInContract
import kotlinx.android.synthetic.main.fragment_sign_up.*
import javax.inject.Inject

/**
 * Screen that allows user sign up to the application
 */
class SignUpFragment : BaseFragment<SignUpContract.Presenter>(), SignUpContract.View {

    @Inject override lateinit var presenter: SignUpContract.Presenter

    override val contentViewId = R.layout.fragment_sign_up


    override fun onCreated(savedInstanceState: Bundle?) {

    }
}