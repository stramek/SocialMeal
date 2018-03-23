package com.marcinstramowski.socialmeal.screens.main.profile

import com.github.ajalt.timberkt.Timber
import com.marcinstramowski.socialmeal.api.ServerApi
import com.marcinstramowski.socialmeal.model.profile.FoodTypes
import com.marcinstramowski.socialmeal.model.profile.ProfileUpdateRequest
import com.marcinstramowski.socialmeal.rxSchedulers.SchedulerProvider
import com.marcinstramowski.socialmeal.utils.NetworkErrorMessageBuilder
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

/**
 * Profile screen logic
 */
class ProfilePresenter @Inject constructor(
    private val view: ProfileContract.View,
    private val userApi: ServerApi.UserApi,
    private val schedulers: SchedulerProvider
) : ProfileContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate() {
        acquireUserProfile()
        view.setUserAvatar("www.asd/sample_image.png")
    }

    private fun acquireUserProfile() {
        compositeDisposable.add(
            userApi.getUserProfile()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribeBy(
                    onSuccess = { userProfile ->
                        view.showUserName(userProfile.firstName)
                        view.showUserSurname(userProfile.surname)
                        view.showUserRating(userProfile.rating)
                        view.showUserDescription(userProfile.description)
                        view.showFavouriteFood(
                            userProfile.favouriteFoodType.firstOrNull()?.type ?: FoodTypes.UNDEFINED
                        )
                    },
                    onError = { error ->
                        view.showErrorMessage(NetworkErrorMessageBuilder(error).getMessageStringId())
                        Timber.e(error, { "Error occurred when receiving profile!" })
                    }
                )
        )
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }

    override fun onSignOutButtonPressed() {
        view.signOut()
    }

    override fun onSaveProfileButtonPressed(profileUpdateRequest: ProfileUpdateRequest) {
        compositeDisposable.add(
            userApi.updateUserProfile(profileUpdateRequest)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .doOnSubscribe { view.enableSaveButton(false) }
                .doOnTerminate { view.enableSaveButton(true) }
                .subscribeBy(
                    onComplete = { view.showProfileUpdateSuccessMessage() },
                    onError = { error ->
                        view.showErrorMessage(NetworkErrorMessageBuilder(error).getMessageStringId())
                        Timber.e(error, { "Error occurred when updating profile!" })
                    }
                )
        )
    }

    override fun onChangePasswordPressed() {
        view.showNotYetImplementedMessage()
    }
}