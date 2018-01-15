package com.marcinstramowski.socialmeal.screens.account.signIn

import com.marcinstramowski.socialmeal.account.UserPrefsDataSource
import com.marcinstramowski.socialmeal.api.ServerApi
import com.marcinstramowski.socialmeal.model.SignInFormFields
import com.marcinstramowski.socialmeal.model.SignInResponse
import com.marcinstramowski.socialmeal.model.Token
import com.marcinstramowski.socialmeal.rxSchedulers.TrampolineSchedulerProvider
import com.marcinstramowski.socialmeal.utils.DeviceInfo
import com.nhaarman.mockito_kotlin.mock
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
    fun test() {

    }

}