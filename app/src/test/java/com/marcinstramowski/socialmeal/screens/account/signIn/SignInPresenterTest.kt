package com.marcinstramowski.socialmeal.screens.account.signIn

import com.marcinstramowski.socialmeal.account.UserPrefsDataSource
import com.marcinstramowski.socialmeal.api.ServerApi
import com.marcinstramowski.socialmeal.model.SignInFormFields
import com.marcinstramowski.socialmeal.model.SignInResponse
import com.marcinstramowski.socialmeal.model.Token
import com.marcinstramowski.socialmeal.rxSchedulers.TrampolineSchedulerProvider
import com.marcinstramowski.socialmeal.utils.DeviceInfo
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

/**
 * [SignInPresenter] tests
 */
class SignInPresenterTest {

    private val view = mock<SignInContract.View>()
    private lateinit var presenter: SignInPresenter

    private val deviceInfo = DeviceInfo("DeviceId", "DeviceName")
    private val userPrefs = mock<UserPrefsDataSource>()
    private val managementApi = mock<ServerApi.ManagementApi>()

    private val sampleSignInFields = SignInFormFields("email@email.com", "asdasd123")
    private val signInResponse = SignInResponse(Token("accessToken", 0),
            Token("refreshToken", 0))

    @Before
    fun prepareTest() {
        presenter = SignInPresenter(view, managementApi, deviceInfo, userPrefs, TrampolineSchedulerProvider())
    }

    @Test
    fun testSignInSuccessCalledAfterSignInRequest() {

        managementApi.stub {
            on { signIn(any()) } doReturn Single.just(signInResponse)
        }

        presenter.onSignInButtonClick(sampleSignInFields)

        verify(view).setSignInButtonProcessing(true)
        verify(userPrefs).saveTokens(signInResponse)
        verify(view).showMainActivity()
        verify(view).setSignInButtonProcessing(false)

        verify(view, never()).showErrorMessage(any())
    }

    @Test
    fun testSignInErrorCalledAfterSignInRequest() {

        managementApi.stub {
            on { signIn(any()) } doReturn Single.error(Throwable())
        }

        presenter.onSignInButtonClick(sampleSignInFields)

        verify(view).setSignInButtonProcessing(true)
        verify(view).showErrorMessage(any())
        verify(view).setSignInButtonProcessing(false)

        verify(userPrefs, never()).saveTokens(signInResponse)
        verify(view, never()).showMainActivity()

    }

    @Test
    fun testForgotPasswordScreenOpenedAfterButtonClick() {
        presenter.onResetPasswordClick()
        verify(view).showResetPasswordScreen()
    }

    @Test
    fun testSignUpScreenOpenedAfterButtonClick() {
        presenter.onSignUpButtonClick()
        verify(view).showSignUpScreen()
    }

    @Test
    fun testSignInWithFacebookCalledAfterButtonClick() {
        presenter.onSignInWithFacebookClick()
        verify(view).signInWithFacebook()
    }

    @Test
    fun testSignInWithTwitterCalledAfterButtonClick() {
        presenter.onSignInWithTwitterClick()
        verify(view).signInWithTwitter()
    }

    @Test
    fun testSignInWithGoogleCalledAfterButtonClick() {
        presenter.onSignInWithGoogleClick()
        verify(view).signInWithGoogle()
    }

    @Test
    fun testSignInButtonDisabledIfAnyOfDataIsBlank() {
        presenter.observeFieldsChanges(Observable.just(SignInFormFields("", "password123")))
        presenter.observeFieldsChanges(Observable.just(SignInFormFields("email@email.com", "")))
        presenter.observeFieldsChanges(Observable.just(SignInFormFields("email@email.com", "password123")))

        inOrder(view) {
            verify(view, times(2)).setSignInButtonEnabled(false)
            verify(view).setSignInButtonEnabled(true)
        }
    }

}