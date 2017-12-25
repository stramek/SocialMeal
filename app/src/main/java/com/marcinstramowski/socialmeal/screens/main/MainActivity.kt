package com.marcinstramowski.socialmeal.screens.main

import android.os.Bundle
import com.marcinstramowski.socialmeal.R
import com.marcinstramowski.socialmeal.screens.base.BaseActivity
import com.marcinstramowski.socialmeal.screens.main.myEvents.MyEventsFragment
import com.marcinstramowski.socialmeal.screens.main.profile.ProfileFragment
import com.marcinstramowski.socialmeal.screens.main.searchEvents.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity<MainContract.Presenter>(), MainContract.View {

    @Inject override lateinit var presenter: MainContract.Presenter
    override val contentViewId = R.layout.activity_main

    override fun onCreated(savedInstanceState: Bundle?) {
        configureBottomNavigationDrawer()
        savedInstanceState ?: setNavigationDrawerDefaultTab()
    }

    private fun configureBottomNavigationDrawer() {
        navigation.setOnNavigationItemSelectedListener { selectedMenuItem ->
            when (selectedMenuItem.itemId) {
                R.id.drawer_search -> {
                    setFragment(SearchFragment())
                    true
                }
                R.id.drawer_events -> {
                    setFragment(MyEventsFragment())
                    true
                }
                R.id.drawer_profile -> {
                    setFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }
    private fun setNavigationDrawerDefaultTab() {
        navigation.selectedItemId = R.id.drawer_search
    }

}
