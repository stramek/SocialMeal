package com.marcinstramowski.socialmeal.account

import android.util.Patterns
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Class that validates data such as password or email
 */
@Singleton class DataValidatorRepo @Inject constructor() : DataValidatorSource {

    companion object {
        private val passwordRegex = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9!@#\$%^&*()_+|]{6,}$"
    }

    override fun isEmailValid(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    override fun isPasswordValid(password: String) = password.matches(Regex(passwordRegex))

}