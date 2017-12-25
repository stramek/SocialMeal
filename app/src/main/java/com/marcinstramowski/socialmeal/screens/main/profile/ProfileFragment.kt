package com.marcinstramowski.socialmeal.screens.main.profile

import android.os.Bundle
import com.marcinstramowski.socialmeal.R
import com.marcinstramowski.socialmeal.screens.base.BaseFragment
import javax.inject.Inject

/**
 * Screen that allows user view and manage his profile
 */
class ProfileFragment : BaseFragment<ProfileContract.Presenter>(), ProfileContract.View {

    @Inject override lateinit var presenter: ProfileContract.Presenter
    override val contentViewId = R.layout.fragment_profile

    override fun onCreated(savedInstanceState: Bundle?) {

    }
}