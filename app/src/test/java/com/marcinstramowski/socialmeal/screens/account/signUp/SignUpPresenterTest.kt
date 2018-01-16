package com.marcinstramowski.socialmeal.screens.account.signUp

import com.marcinstramowski.socialmeal.account.CredentialsValidator.Companion.ERROR_MESSAGE_DELAY_MS
import com.marcinstramowski.socialmeal.account.UserPrefsDataSource
import com.marcinstramowski.socialmeal.api.ServerApi
import com.marcinstramowski.socialmeal.model.SignInResponse
import com.marcinstramowski.socialmeal.model.SignUpFormFields
import com.marcinstramowski.socialmeal.model.Token
import com.marcinstramowski.socialmeal.rxSchedulers.TestSchedulerProvider
import com.marcinstramowski.socialmeal.rxSchedulers.TrampolineSchedulerProvider
import com.marcinstramowski.socialmeal.utils.DeviceInfo
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

/**
 * [SignUpPresenter] tests
 */
class SignUpPresenterTest {

    private val view = mock<SignUpContract.View>()
    private lateinit var presenter: SignUpPresenter

    private val deviceInfo = DeviceInfo("DeviceId", "DeviceName")
    private val userPrefs = mock<UserPrefsDataSource>()
    private val managementApi = mock<ServerApi.ManagementApi>()

    private val sampleSignUpFields = SignUpFormFields("name", "surname",
            "email", "pass", "pass")
    private val signInResponse = SignInResponse(Token("accessToken", 0),
            Token("refreshToken", 0))

    private var scheduler = TestScheduler()

    @Before
    fun prepareTest() {
        presenter = SignUpPresenter(view, managementApi, deviceInfo, userPrefs, TrampolineSchedulerProvider())
    }

    @Test
    fun testRegisterSuccessCalledAfterSignUpRequest() {

        managementApi.stub {
            on { signUp(any()) } doReturn Completable.complete()
            on { signIn(any()) } doReturn Single.just(signInResponse)
        }

        presenter.onSignUpButtonClick(sampleSignUpFields)

        verify(view).setSignUpButtonProcessing(true)
        verify(userPrefs).saveTokens(signInResponse)
        verify(view).showMainActivity()
        verify(view).setSignUpButtonProcessing(false)

        verify(view, never()).showErrorMessage(any())
    }

    @Test
    fun testRegisterErrorCalledAfterSignUpRequest() {

        managementApi.stub {
            on { signUp(any()) } doReturn Completable.error(Throwable())
            on { signIn(any()) } doReturn Single.just(signInResponse)
        }

        presenter.onSignUpButtonClick(sampleSignUpFields)

        verify(view).setSignUpButtonProcessing(true)
        verify(view).showErrorMessage(any())
        verify(view).setSignUpButtonProcessing(false)

        verify(userPrefs, never()).saveTokens(signInResponse)
        verify(view, never()).showMainActivity()
    }

    @Test
    fun testSignUpErrorCalledAfterSignUpRequest() {

        managementApi.stub {
            on { signUp(any()) } doReturn Completable.complete()
            on { signIn(any()) } doReturn Single.error(Throwable())
        }

        presenter.onSignUpButtonClick(sampleSignUpFields)

        verify(view).setSignUpButtonProcessing(true)
        verify(view).showErrorMessage(any())
        verify(view).setSignUpButtonProcessing(false)

        verify(userPrefs, never()).saveTokens(signInResponse)
        verify(view, never()).showMainActivity()
    }

    @Test
    fun disableRegisterButtonWhenFieldsAreEmpty() {

        presenter.observeFieldsChanges(Observable.just(signUpFields(firstName = "")))
        presenter.observeFieldsChanges(Observable.just(signUpFields(surname = "")))
        presenter.observeFieldsChanges(Observable.just(signUpFields(email = "")))
        presenter.observeFieldsChanges(Observable.just(signUpFields(password = "")))
        presenter.observeFieldsChanges(Observable.just(signUpFields(confirmPassword = "")))
        presenter.observeFieldsChanges(Observable.just(signUpFields("name", "surname",
                "email@gmail.com", "abcd123", "abcd123")))

        inOrder(view) {
            verify(view, times(5)).setSignUpButtonEnabled(false)
            verify(view).setSignUpButtonEnabled(true)
        }
    }

    @Test
    fun disableRegisterButtonWhenEmailIsInvalid() {
        presenter.observeFieldsChanges(Observable.just(signUpFields(email = "test.com"))) // not valid
        presenter.observeFieldsChanges(Observable.just(signUpFields(email = "@test.com"))) // not valid
        presenter.observeFieldsChanges(Observable.just(signUpFields(email = "a@test."))) // not valid
        presenter.observeFieldsChanges(Observable.just(signUpFields(email = "test.test@"))) // not valid
        presenter.observeFieldsChanges(Observable.just(signUpFields(email = "test@test.com"))) // valid

        inOrder(view) {
            verify(view, times(4)).setSignUpButtonEnabled(false)
            verify(view, times(1)).setSignUpButtonEnabled(true)
        }
    }

    @Test
    fun disableRegisterButtonWhenPasswordIsInvalid() {
        presenter.observeFieldsChanges(Observable.just(signUpFields(password = "aaaaa", confirmPassword = "aaaaa"))) // too short, no number
        presenter.observeFieldsChanges(Observable.just(signUpFields(password = "aaaa1", confirmPassword = "aaaa1"))) // too short
        presenter.observeFieldsChanges(Observable.just(signUpFields(password = "11111", confirmPassword = "11111"))) // too short, no letter
        presenter.observeFieldsChanges(Observable.just(signUpFields(password = "1111a", confirmPassword = "1111a"))) // too short
        presenter.observeFieldsChanges(Observable.just(signUpFields(password = "aaa1!!", confirmPassword = "aaa1!!"))) // valid
        presenter.observeFieldsChanges(Observable.just(signUpFields(password = "1aaaaa", confirmPassword = "1aaaaa"))) // valid

        inOrder(view) {
            verify(view, times(4)).setSignUpButtonEnabled(false)
            verify(view, times(2)).setSignUpButtonEnabled(true)
        }
    }

    @Test
    fun disableRegisterButtonWhenPasswordsNotMatchIsInvalid() {
        presenter.observeFieldsChanges(Observable.just(signUpFields(password = "aaa1!!", confirmPassword = "aaa1!!!")))
        presenter.observeFieldsChanges(Observable.just(signUpFields(password = "1aaaaa", confirmPassword = "1aaaaaz")))
        presenter.observeFieldsChanges(Observable.just(signUpFields(password = "@1aaaaa", confirmPassword = "1aaaaa")))
        presenter.observeFieldsChanges(Observable.just(signUpFields(password = "1aa@aaa", confirmPassword = "1aa@aaa")))

        inOrder(view) {
            verify(view, times(3)).setSignUpButtonEnabled(false)
            verify(view, times(1)).setSignUpButtonEnabled(true)
        }
    }

    @Test
    fun showErrorMessageWhenPasswordsDontMatch() {
        presenter.schedulers = TestSchedulerProvider(scheduler)

        var showPasswordsDontMatchCounter = 0
        var hidePasswordsDontMatchCounter = 0

        presenter.observeFieldsChanges(Observable.just(signUpFields(password = "pass", confirmPassword = "pass")))
        scheduler.advanceTimeBy(ERROR_MESSAGE_DELAY_MS, TimeUnit.MILLISECONDS)

        verify(view, times(++hidePasswordsDontMatchCounter)).showPasswordsDontMatchMessage(visible = false)
        verify(view, times(showPasswordsDontMatchCounter)).showPasswordsDontMatchMessage(visible = true)

        presenter.observeFieldsChanges(Observable.just(signUpFields(password = "pass", confirmPassword = "")))
        scheduler.advanceTimeBy(ERROR_MESSAGE_DELAY_MS, TimeUnit.MILLISECONDS)

        verify(view, times(++hidePasswordsDontMatchCounter)).showPasswordsDontMatchMessage(visible = false)
        verify(view, times(showPasswordsDontMatchCounter)).showPasswordsDontMatchMessage(visible = true)

        presenter.observeFieldsChanges(Observable.just(signUpFields(password = "pass", confirmPassword = "pass2")))
        scheduler.advanceTimeBy(ERROR_MESSAGE_DELAY_MS, TimeUnit.MILLISECONDS)

        verify(view, times(hidePasswordsDontMatchCounter)).showPasswordsDontMatchMessage(visible = false)
        verify(view, times(++showPasswordsDontMatchCounter)).showPasswordsDontMatchMessage(visible = true)

        presenter.observeFieldsChanges(Observable.just(signUpFields(password = "", confirmPassword = "pass2")))
        scheduler.advanceTimeBy(ERROR_MESSAGE_DELAY_MS, TimeUnit.MILLISECONDS)

        verify(view, times(hidePasswordsDontMatchCounter)).showPasswordsDontMatchMessage(visible = false)
        verify(view, times(++showPasswordsDontMatchCounter)).showPasswordsDontMatchMessage(visible = true)
    }

    @Test
    fun showErrorMessageWhenPasswordIsInvalid() {
        presenter.schedulers = TestSchedulerProvider(scheduler)

        var showPasswordInvalidCounter = 0
        var hidePasswordInvalidCounter = 0

        presenter.observeFieldsChanges(Observable.just(signUpFields(password = "aaa")))
        scheduler.advanceTimeBy(ERROR_MESSAGE_DELAY_MS, TimeUnit.MILLISECONDS)

        verify(view, times(hidePasswordInvalidCounter)).showInvalidPasswordMessage(visible = false)
        verify(view, times(++showPasswordInvalidCounter)).showInvalidPasswordMessage(visible = true)

        presenter.observeFieldsChanges(Observable.just(signUpFields(password = "aaaaa1")))
        scheduler.advanceTimeBy(ERROR_MESSAGE_DELAY_MS, TimeUnit.MILLISECONDS)

        verify(view, times(++hidePasswordInvalidCounter)).showInvalidPasswordMessage(visible = false)
        verify(view, times(showPasswordInvalidCounter)).showInvalidPasswordMessage(visible = true)

        presenter.observeFieldsChanges(Observable.just(signUpFields(password = ""))) // don't show message when empty
        scheduler.advanceTimeBy(ERROR_MESSAGE_DELAY_MS, TimeUnit.MILLISECONDS)

        verify(view, times(++hidePasswordInvalidCounter)).showInvalidPasswordMessage(visible = false)
        verify(view, times(showPasswordInvalidCounter)).showInvalidPasswordMessage(visible = true)
    }

    @Test
    fun showErrorMessageWhenEmailIsInvalid() {
        presenter.schedulers = TestSchedulerProvider(scheduler)

        var showEmailInvalidCounter = 0
        var hideEmailInvalidCounter = 0

        presenter.observeFieldsChanges(Observable.just(signUpFields(email = "aaa")))
        scheduler.advanceTimeBy(ERROR_MESSAGE_DELAY_MS, TimeUnit.MILLISECONDS)

        verify(view, times(hideEmailInvalidCounter)).showInvalidEmailMessage(visible = false)
        verify(view, times(++showEmailInvalidCounter)).showInvalidEmailMessage(visible = true)

        presenter.observeFieldsChanges(Observable.just(signUpFields(email = "test@gmail.com")))
        scheduler.advanceTimeBy(ERROR_MESSAGE_DELAY_MS, TimeUnit.MILLISECONDS)

        verify(view, times(++hideEmailInvalidCounter)).showInvalidEmailMessage(visible = false)
        verify(view, times(showEmailInvalidCounter)).showInvalidEmailMessage(visible = true)
    }

    private fun signUpFields(firstName: String = "name",
                       surname: String = "surname",
                       email: String = "test@gmail.com",
                       password: String = "Test123$",
                       confirmPassword: String = "Test123$"
    ) = SignUpFormFields(firstName, surname, email, password, confirmPassword)
}