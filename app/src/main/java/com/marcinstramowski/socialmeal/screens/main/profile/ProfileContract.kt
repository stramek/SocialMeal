package com.marcinstramowski.socialmeal.screens.main.profile

import com.marcinstramowski.socialmeal.screens.base.BaseContract

/**
 * Contract interfaces between [ProfileFragment] and [ProfilePresenter]
 */
interface ProfileContract {
    interface View : BaseContract.View<Presenter> {
        fun setUserAvatar(avatarUrl: String)
        fun signOut()
    }

    interface Presenter : BaseContract.Presenter {
        fun onSignOutButtonPressed()
    }
}