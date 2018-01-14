package com.marcinstramowski.socialmeal.screens.account.signUp

import com.marcinstramowski.socialmeal.account.DataValidatorSource
import com.marcinstramowski.socialmeal.account.UserPrefsDataSource
import com.marcinstramowski.socialmeal.api.ServerApi
import com.marcinstramowski.socialmeal.model.SignInResponse
import com.marcinstramowski.socialmeal.model.SignUpFormFields
import com.marcinstramowski.socialmeal.model.Token
import com.marcinstramowski.socialmeal.rxSchedulers.TrampolineSchedulerProvider
import com.marcinstramowski.socialmeal.utils.DeviceInfo
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test

/**
 * [SignUpPresenter] tests
 */
class SignUpPresenterTest {

    private val view = mock<SignUpContract.View>()
    private lateinit var presenter: SignUpPresenter

    private val deviceInfo = DeviceInfo("DeviceId", "DeviceName")
    private val userPrefs = mock<UserPrefsDataSource>()
    private val managementApi = mock<ServerApi.ManagementApi>()
    private val dataValidator = mock<DataValidatorSource>()

    val scheduler = TestScheduler()

    private val sampleSignUpFields = SignUpFormFields("name", "surname",
            "email", "pass", "pass")
    private val loginResponse = SignInResponse(Token("accessToken", 0),
            Token("refreshToken", 0))

    @Before
    fun prepareTest() {
        presenter = SignUpPresenter(view, managementApi, deviceInfo, userPrefs, dataValidator, TrampolineSchedulerProvider())
    }

    @Test
    fun testRegisterSuccessCalledAfterRegisterRequest() {

        managementApi.stub {
            on { signUp(any()) } doReturn Completable.complete()
            on { signIn(any()) } doReturn Single.just(loginResponse)
        }

        presenter.onSignUpButtonClick(sampleSignUpFields)

        verify(view).setSignUpButtonProcessing(true)
        verify(userPrefs).saveTokens(loginResponse)
        verify(view).showMainActivity()
        verify(view).setSignUpButtonProcessing(false)

        verify(view, never()).showErrorMessage(any())
    }

    @Test
    fun testRegisterErrorCalledAfterRegisterRequest() {

        managementApi.stub {
            on { signUp(any()) } doReturn Completable.error(Throwable())
            on { signIn(any()) } doReturn Single.just(loginResponse)
        }

        presenter.onSignUpButtonClick(sampleSignUpFields)

        verify(view).setSignUpButtonProcessing(true)
        verify(view).showErrorMessage(any())
        verify(view).setSignUpButtonProcessing(false)

        verify(userPrefs, never()).saveTokens(loginResponse)
        verify(view, never()).showMainActivity()
    }

    @Test
    fun testSignUpErrorCalledAfterRegisterRequest() {

        managementApi.stub {
            on { signUp(any()) } doReturn Completable.complete()
            on { signIn(any()) } doReturn Single.error(Throwable())
        }

        presenter.onSignUpButtonClick(sampleSignUpFields)

        verify(view).setSignUpButtonProcessing(true)
        verify(view).showErrorMessage(any())
        verify(view).setSignUpButtonProcessing(false)

        verify(userPrefs, never()).saveTokens(loginResponse)
        verify(view, never()).showMainActivity()
    }

    @Test
    fun disableRegisterButtonWhenFieldsAreEmpty() {

        dataValidator.stub {
            on { isEmailValid(any()) } doReturn true
            on { isPasswordValid(any()) } doReturn true
        }

        presenter.observeFieldsChanges(signUpFields(firstName = ""))
        presenter.observeFieldsChanges(signUpFields(surname = ""))
        presenter.observeFieldsChanges(signUpFields(email = ""))
        presenter.observeFieldsChanges(signUpFields(password = ""))
        presenter.observeFieldsChanges(signUpFields(confirmPassword = ""))
        presenter.observeFieldsChanges(signUpFields("name", "surname",
                "email@gmail.com", "abcd123", "abcd123"))

        inOrder(view) {
            verify(view, times(5)).setSignUpButtonEnabled(false)
            verify(view).setSignUpButtonEnabled(true)
        }
    }

    private fun signUpFields(firstName: String = "name",
                       surname: String = "surname",
                       email: String = "test@gmail.com",
                       password: String = "Test123$",
                       confirmPassword: String = "Test123$"
    ) = SignUpFormFields(firstName, surname, email, password, confirmPassword)
}