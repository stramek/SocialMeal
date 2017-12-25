package com.marcinstramowski.socialmeal.screens.account.signUp

import android.os.Bundle
import com.marcinstramowski.socialmeal.R
import com.marcinstramowski.socialmeal.screens.base.BaseFragment
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