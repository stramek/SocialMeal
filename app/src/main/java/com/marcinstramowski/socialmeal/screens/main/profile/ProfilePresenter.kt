package com.marcinstramowski.socialmeal.screens.main.profile

import javax.inject.Inject

/**
 * Profile screen logic
 */
class ProfilePresenter @Inject constructor(
    private val view: ProfileContract.View
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