package com.marcinstramowski.socialmeal.screens.account.resetPassword

import com.marcinstramowski.socialmeal.account.CredentialsValidator.Companion.ERROR_MESSAGE_DELAY_MS
import com.marcinstramowski.socialmeal.api.ServerApi
import com.marcinstramowski.socialmeal.model.ResetPasswordFormFields
import com.marcinstramowski.socialmeal.rxSchedulers.TestSchedulerProvider
import com.marcinstramowski.socialmeal.rxSchedulers.TrampolineSchedulerProvider
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

/**
 * [ResetPasswordPresenter] tests
 */
class ResetPasswordPresenterTest {

    private val view = mock<ResetPasswordContract.View>()
    private lateinit var presenter: ResetPasswordPresenter

    private val managementApi = mock<ServerApi.ManagementApi>()

    private val sampleResetPasswordFields = ResetPasswordFormFields("test@test.pl")

    private var scheduler = TestScheduler()

    @Before
    fun prepareTest() {
        presenter = ResetPasswordPresenter(view, managementApi, TrampolineSchedulerProvider())
    }

    @Test
    fun testResetPasswordSuccessCalledAfterRequest() {

        managementApi.stub {
            on { resetPassword(any()) } doReturn Completable.complete()
        }

        presenter.onResetProgressButtonPressed(sampleResetPasswordFields)

        inOrder(view) {
            verify(view).setResetButtonProcessing(true)
            verify(view).showResetPasswordSuccessMessage()
            verify(view).setResetButtonProcessing(false)
        }
    }

    @Test
    fun testResetPasswordFailAfterRequest() {

        managementApi.stub {
            on { resetPassword(any()) } doReturn Completable.error(Throwable())
        }

        presenter.onResetProgressButtonPressed(sampleResetPasswordFields)

        inOrder(view) {
            verify(view).setResetButtonProcessing(true)
            verify(view).showResetPasswordErrorMessage(any())
            verify(view).setResetButtonProcessing(false)
        }
    }

    @Test
    fun disableResetButtonWhenFieldsAreEmpty() {
        presenter.observeFieldsChanges(Observable.just(ResetPasswordFormFields("")))
        presenter.observeFieldsChanges(Observable.just(ResetPasswordFormFields("test@test.pl")))

        inOrder(view) {
            verify(view).setResetButtonEnabled(false)
            verify(view).setResetButtonEnabled(true)
        }
    }

    @Test
    fun disableResetButtonWhenFieldsAreInvalid() {
        presenter.observeFieldsChanges(Observable.just(ResetPasswordFormFields("test@")))
        presenter.observeFieldsChanges(Observable.just(ResetPasswordFormFields("test@test.pl")))

        inOrder(view) {
            verify(view).setResetButtonEnabled(false)
            verify(view).setResetButtonEnabled(true)
        }
    }

    @Test
    fun showErrorMessageWhenEmailIsNotValid() {
        presenter.schedulers = TestSchedulerProvider(scheduler)

        var showEmailInvalidCounter = 0
        var hideEmailInvalidCounter = 0

        presenter.observeFieldsChanges(Observable.just(ResetPasswordFormFields("test@")))
        scheduler.advanceTimeBy(ERROR_MESSAGE_DELAY_MS, TimeUnit.MILLISECONDS)

        verify(view, times(hideEmailInvalidCounter)).showInvalidEmailMessage(visible = false)
        verify(view, times(++showEmailInvalidCounter)).showInvalidEmailMessage(visible = true)

        presenter.observeFieldsChanges(Observable.just(ResetPasswordFormFields("test@gmail.com")))
        scheduler.advanceTimeBy(ERROR_MESSAGE_DELAY_MS, TimeUnit.MILLISECONDS)

        verify(view, times(++hideEmailInvalidCounter)).showInvalidEmailMessage(visible = false)
        verify(view, times(showEmailInvalidCounter)).showInvalidEmailMessage(visible = true)
    }
}