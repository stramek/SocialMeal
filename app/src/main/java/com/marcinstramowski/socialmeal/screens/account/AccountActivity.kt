package com.marcinstramowski.socialmeal.screens.account

import android.os.Bundle
import com.marcinstramowski.socialmeal.R
import com.marcinstramowski.socialmeal.screens.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject
import android.graphics.drawable.AnimationDrawable
import com.marcinstramowski.socialmeal.screens.account.signIn.SignInFragment


/**
 * Activity with gradient background with container that inflates not logged user views
 * such as [SignInFragment], [SignUpFragment] or [ResetPasswordFragment]
 */
class AccountActivity : BaseActivity<AccountContract.Presenter>(), AccountContract.View {

    companion object {
        val GRADIENT_ENTER_DURATION_MS = 2000
        val GRADIENT_EXIT_DURATION_MS = 5000
    }

    override val contentViewId = R.layout.activity_login

    @Inject override lateinit var presenter: AccountContract.Presenter

    private lateinit var animationDrawable: AnimationDrawable

    override fun onCreated(savedInstanceState: Bundle?) {
        setupBackground()
        savedInstanceState ?: setFragmentNoAnimation(SignInFragment())
    }

    private fun setupBackground() {
        animationDrawable = (container.background as AnimationDrawable).apply {
            setEnterFadeDuration(GRADIENT_ENTER_DURATION_MS)
            setExitFadeDuration(GRADIENT_EXIT_DURATION_MS)
        }
    }

    override fun onResume() {
        super.onResume()
        animationDrawable.start()
    }

    override fun onPause() {
        animationDrawable.stop()
        super.onPause()
    }
}

