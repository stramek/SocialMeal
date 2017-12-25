package com.marcinstramowski.socialmeal.screens.main.profile

import javax.inject.Inject

/**
 * Profile screen logic
 */
class ProfilePresenter @Inject constructor(
        private val view: ProfileContract.View
) : ProfileContract.Presenter {

    override fun onCreate() {

    }

    override fun onDestroy() {

    }
}