package com.marcinstramowski.socialmeal.screens.main.myEvents

import android.os.Bundle
import com.marcinstramowski.socialmeal.R
import com.marcinstramowski.socialmeal.screens.base.BaseFragment
import javax.inject.Inject

/**
 * Screen that allows user to manage his events
 */
class MyEventsFragment : BaseFragment<MyEventsContract.Presenter>(), MyEventsContract.View {

    @Inject override lateinit var presenter: MyEventsContract.Presenter
    override val contentViewId = R.layout.fragment_my_events

    override fun onCreated(savedInstanceState: Bundle?) {

    }
}