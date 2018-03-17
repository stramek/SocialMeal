package com.marcinstramowski.socialmeal.screens.main.profile

import android.support.annotation.StringRes
import com.marcinstramowski.socialmeal.screens.base.BaseContract

/**
 * Contract interfaces between [ProfileFragment] and [ProfilePresenter]
 */
interface ProfileContract {
    interface View : BaseContract.View<Presenter> {

        fun setUserAvatar(avatarUrl: String)

        fun signOut()

        fun showUserName(name: String)

        fun showUserSurname(surname: String)

        fun showUserRating(rating: Double)

        fun showProfileAcquireError(@StringRes messageId: Int)
    }

    interface Presenter : BaseContract.Presenter {

        fun onSignOutButtonPressed()
    }
}