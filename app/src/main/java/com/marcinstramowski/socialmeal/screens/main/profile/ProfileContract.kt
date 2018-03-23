package com.marcinstramowski.socialmeal.screens.main.profile

import android.support.annotation.StringRes
import com.marcinstramowski.socialmeal.model.profile.FoodTypes
import com.marcinstramowski.socialmeal.model.profile.ProfileUpdateRequest
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

        fun showUserDescription(description: String)

        fun showErrorMessage(@StringRes messageId: Int)

        fun enableSaveButton(enabled: Boolean)

        fun showProfileUpdateSuccessMessage()

        fun showFavouriteFood(favouriteFoodType: FoodTypes)

        fun showNotYetImplementedMessage()
    }

    interface Presenter : BaseContract.Presenter {

        fun onSignOutButtonPressed()

        fun onChangePasswordPressed()

        fun onSaveProfileButtonPressed(profileUpdateRequest: ProfileUpdateRequest)
    }
}