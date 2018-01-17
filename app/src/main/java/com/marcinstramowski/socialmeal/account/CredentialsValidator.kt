package com.marcinstramowski.socialmeal.account

import org.apache.commons.validator.routines.EmailValidator

/**
 * Util class to check user input validity of fields such as email or password
 */
class CredentialsValidator {

    companion object {

        /**
         * Defines default delay between user wrong data input and showing error
         */
        val ERROR_MESSAGE_DELAY_MS: Long = 500

        private const val PASSWORD_REGEX =
            "^(?=.*[a-zA-Z!@#\$%^&*()_+|])(?=.*[0-9])[a-zA-Z0-9!@#\$%^&*()_+|]{6,}$"

        /**
         * Checks if provided [email] is actually email
         */
        fun isEmailValid(email: String) = EmailValidator.getInstance().isValid(email)

        /**
         * Checks if provided [password] matches requirements of [PASSWORD_REGEX]
         */
        fun isPasswordValid(password: String) = password.matches(Regex(PASSWORD_REGEX))
    }

}