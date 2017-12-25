package com.marcinstramowski.socialmeal.screens.main.searchEvents

import android.os.Bundle
import com.marcinstramowski.socialmeal.R
import com.marcinstramowski.socialmeal.screens.base.BaseFragment
import javax.inject.Inject

/**
 * Screen that allows user to search and sign for other people events
 */
class SearchFragment : BaseFragment<SearchContract.Presenter>(), SearchContract.View {

    @Inject override lateinit var presenter: SearchContract.Presenter
    override val contentViewId = R.layout.fragment_search

    override fun onCreated(savedInstanceState: Bundle?) {

    }
}