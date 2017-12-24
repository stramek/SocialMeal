package com.marcinstramowski.socialmeal.screens.login.signUp

import android.util.Patterns
import javax.inject.Inject

/**
 * Sign in screen logic
 */
class SignUpPresenter @Inject constructor(
        private val view: SignUpContract.View
) : SignUpContract.Presenter {

    override fun onCreate() {
    }

    override fun onDestroy() {

    }

    fun isEmailValid(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isPasswordValid(password: String): Boolean {
        val regexStr = """^(?=.*[a-ząćęłńóśźż])(?=.*[A-ZĄĆĘŁŃÓŚŹŻ])(?=.*\d)(?=.*[!"#$%&'()*+,-./:;<=>?@\[\\\]^_`{|}~])[A-Za-ząćęłńóśźżĄĆĘŁŃÓŚŹŻ\d!"#$%&'()*+,-./:;<=>?@\[\\\]^_`{|}~]{8,}"""
        return password.matches(Regex(regexStr))
    }
}