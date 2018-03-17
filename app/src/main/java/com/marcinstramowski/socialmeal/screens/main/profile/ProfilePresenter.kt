package com.marcinstramowski.socialmeal.screens.main.profile

import com.marcinstramowski.socialmeal.api.ServerApi
import javax.inject.Inject

/**
 * Profile screen logic
 */
class ProfilePresenter @Inject constructor(
    private val view: ProfileContract.View,
    private val userApi: ServerApi.UserApi
    ) : ProfileContract.Presenter {

    override fun onCreate() {
        view.setUserAvatar("www.asd/sample_image.png")
    }

    override fun onDestroy() {

    }

    override fun onSignOutButtonPressed() {
        view.signOut()
    }
}