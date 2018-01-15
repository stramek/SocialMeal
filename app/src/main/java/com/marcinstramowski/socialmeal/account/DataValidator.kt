package com.marcinstramowski.socialmeal.account

import org.apache.commons.validator.routines.EmailValidator
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Class that validates data such as password or email
 */
@Singleton class DataValidator @Inject constructor() {

    companion object {
        private val passwordRegex = "^(?=.*[a-zA-Z!@#\$%^&*()_+|])(?=.*[0-9])[a-zA-Z0-9!@#\$%^&*()_+|]{6,}$"
    }

    fun isEmailValid(email: String) = EmailValidator.getInstance().isValid(email)

    fun isPasswordValid(password: String) = password.matches(Regex(passwordRegex))

}