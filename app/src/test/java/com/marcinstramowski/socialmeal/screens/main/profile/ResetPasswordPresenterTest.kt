package com.marcinstramowski.socialmeal.screens.main.profile

import com.marcinstramowski.socialmeal.api.ServerApi
import com.marcinstramowski.socialmeal.model.profile.FavouriteFoodType
import com.marcinstramowski.socialmeal.model.profile.FoodTypes
import com.marcinstramowski.socialmeal.model.profile.ProfileUpdateRequest
import com.marcinstramowski.socialmeal.model.profile.UserProfile
import com.marcinstramowski.socialmeal.rxSchedulers.TrampolineSchedulerProvider
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

/**
 * [ProfilePresenter] tests
 */
class ResetPasswordPresenterTest {

    private val view = mock<ProfileContract.View>()
    private lateinit var presenter: ProfilePresenter

    private val userApi = mock<ServerApi.UserApi>()

    private val mockUserProfile = UserProfile(
        "first name",
        "surname",
        "thumbnail.png",
        "description",
        4.55,
        listOf(FavouriteFoodType(FoodTypes.ITALIAN))
    )

    private val mockProfileUpdateRequest = ProfileUpdateRequest(
        "first name",
        "surname",
        "description",
        listOf()
    )

    @Before
    fun prepareTest() {
        presenter = ProfilePresenter(view, userApi, TrampolineSchedulerProvider())
    }

    @Test
    fun setUserAvatarOnCreateTest() {

        userApi.stub {
            on { getUserProfile() } doReturn Single.just(mockUserProfile)
        }

        presenter.onCreate()

        verify(view).setUserAvatar(any())
    }

    @Test
    fun acquireUserProfileOnCreateTest() {

        userApi.stub {
            on { getUserProfile() } doReturn Single.just(mockUserProfile)
        }

        presenter.onCreate()

        verify(userApi).getUserProfile()
    }

    @Test
    fun fillUserProfileTestOnAcquireSuccessTest() {

        userApi.stub {
            on { getUserProfile() } doReturn Single.just(mockUserProfile)
        }

        presenter.onCreate()

        verify(view).showUserName(mockUserProfile.firstName)
        verify(view).showUserSurname(mockUserProfile.surname)
        verify(view).showUserRating(mockUserProfile.rating)
        verify(view).showUserDescription(mockUserProfile.description)
        verify(view).showFavouriteFood(mockUserProfile.favouriteFoodType.first().type!!)
    }

    @Test
    fun showErrorMessageOnGetUserProfileExceptionTest() {

        userApi.stub {
            on { getUserProfile() } doReturn Single.error(Throwable())
        }

        presenter.onCreate()

        verify(view).showErrorMessage(any())
    }

    @Test
    fun signOutUserOnSignOutButtonPressedTest() {

        presenter.onSignOutButtonPressed()

        verify(view).signOut()
    }

    @Test
    fun showNotImplementedMessageOnChangePasswordPressedTest() {

        presenter.onChangePasswordPressed()

        verify(view).showNotYetImplementedMessage()
    }

    @Test
    fun showProfileUpdateSuccessMessageTest() {

        userApi.stub {
            on { updateUserProfile(mockProfileUpdateRequest) } doReturn Completable.complete()
        }

        presenter.onSaveProfileButtonPressed(mockProfileUpdateRequest)

        inOrder(view) {
            verify(view).enableSaveButton(false)
            verify(view).enableSaveButton(true)
            verify(view).showProfileUpdateSuccessMessage()
        }
    }

    @Test
    fun showProfileUpdateFailMessageTest() {

        userApi.stub {
            on { updateUserProfile(mockProfileUpdateRequest) } doReturn Completable.error(Throwable())
        }

        presenter.onSaveProfileButtonPressed(mockProfileUpdateRequest)

        verify(view).showErrorMessage(any())
    }
}