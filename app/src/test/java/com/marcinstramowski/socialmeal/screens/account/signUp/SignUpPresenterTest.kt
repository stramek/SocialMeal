package com.marcinstramowski.socialmeal.screens.account.signUp

import com.marcinstramowski.socialmeal.account.DataValidator
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

    val scheduler = TestScheduler()

    private val sampleSignUpFields = SignUpFormFields("name", "surname",
            "email", "pass", "pass")
    private val loginResponse = SignInResponse(Token("accessToken", 0),
            Token("refreshToken", 0))

    @Before
    fun prepareTest() {
        presenter = SignUpPresenter(view, managementApi, deviceInfo, userPrefs, DataValidator(), TrampolineSchedulerProvider())
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

    @Test
    fun disableRegisterButtonWhenEmailIsInvalid() {
        presenter.observeFieldsChanges(signUpFields(email = "test.com")) // not valid
        presenter.observeFieldsChanges(signUpFields(email = "@test.com")) // not valid
        presenter.observeFieldsChanges(signUpFields(email = "a@test.")) // not valid
        presenter.observeFieldsChanges(signUpFields(email = "test.test@")) // not valid
        presenter.observeFieldsChanges(signUpFields(email = "test@test.com")) // valid

        inOrder(view) {
            verify(view, times(4)).setSignUpButtonEnabled(false)
            verify(view, times(1)).setSignUpButtonEnabled(true)
        }
    }

    @Test
    fun disableRegisterButtonWhenPasswordIsInvalid() {
        presenter.observeFieldsChanges(signUpFields(password = "aaaaa", confirmPassword = "aaaaa")) // too short, no number
        presenter.observeFieldsChanges(signUpFields(password = "aaaa1", confirmPassword = "aaaa1")) // too short
        presenter.observeFieldsChanges(signUpFields(password = "11111", confirmPassword = "11111")) // too short, no letter
        presenter.observeFieldsChanges(signUpFields(password = "1111a", confirmPassword = "1111a")) // too short
        presenter.observeFieldsChanges(signUpFields(password = "aaa1!!", confirmPassword = "aaa1!!")) // valid
        presenter.observeFieldsChanges(signUpFields(password = "1aaaaa", confirmPassword = "1aaaaa")) // valid

        inOrder(view) {
            verify(view, times(4)).setSignUpButtonEnabled(false)
            verify(view, times(2)).setSignUpButtonEnabled(true)
        }
    }

    @Test
    fun disableRegisterButtonWhenPasswordsNotMatchIsInvalid() {
        presenter.observeFieldsChanges(signUpFields(password = "aaa1!!", confirmPassword = "aaa1!!!"))
        presenter.observeFieldsChanges(signUpFields(password = "1aaaaa", confirmPassword = "1aaaaaz"))
        presenter.observeFieldsChanges(signUpFields(password = "@1aaaaa", confirmPassword = "1aaaaa"))
        presenter.observeFieldsChanges(signUpFields(password = "1aa@aaa", confirmPassword = "1aa@aaa"))

        inOrder(view) {
            verify(view, times(3)).setSignUpButtonEnabled(false)
            verify(view, times(1)).setSignUpButtonEnabled(true)
        }
    }

    @Test
    fun showErrorMessageWhenPasswordsDontMatch() {
        presenter.observeFieldsChanges(signUpFields(password = "pass", confirmPassword = "pass"))
        presenter.observeFieldsChanges(signUpFields(password = "pass", confirmPassword = ""))
        presenter.observeFieldsChanges(signUpFields(password = "pass", confirmPassword = "pass2"))
        presenter.observeFieldsChanges(signUpFields(password = "", confirmPassword = "pass2"))

        inOrder(view) {
            verify(view, times(2)).showPasswordsDontMatchMessage(visible = false)
            verify(view, times(2)).showPasswordsDontMatchMessage(visible = true)
        }
    }

    @Test
    fun showErrorMessageWhenPasswordIsInvalid() {
        presenter.observeFieldsChanges(signUpFields(password = "aaa"))
        presenter.observeFieldsChanges(signUpFields(password = "aaaaa1"))
        presenter.observeFieldsChanges(signUpFields(password = "")) // don't show message when empty

        inOrder(view) {
            verify(view).showInvalidPasswordMessage(visible = true)
            verify(view, times(2)).showInvalidPasswordMessage(visible = false)
        }
    }

    @Test
    fun showErrorMessageWhenEmailIsInvalid() {
        presenter.observeFieldsChanges(signUpFields(email = "aaa"))
        presenter.observeFieldsChanges(signUpFields(email = "test@gmail.com"))

        inOrder(view) {
            verify(view).showInvalidEmailMessage(visible = true)
            verify(view).showInvalidEmailMessage(visible = false)
        }
    }

    private fun signUpFields(firstName: String = "name",
                       surname: String = "surname",
                       email: String = "test@gmail.com",
                       password: String = "Test123$",
                       confirmPassword: String = "Test123$"
    ) = SignUpFormFields(firstName, surname, email, password, confirmPassword)
}