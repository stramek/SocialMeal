package com.marcinstramowski.socialmeal.model

import com.marcinstramowski.socialmeal.account.CredentialsValidator

/**
 * Reset password form fields data
 */
data class ResetPasswordFormFields(
    val email: String
) {

    /**
     * Checkds if reset password form fields are not empty
     */
    fun fieldsNotBlank() = email.isNotBlank()

    /**
     * Checks if [email] meets requirements of [CredentialsValidator.isEmailValid]
     */
    fun emailValid() = CredentialsValidator.isEmailValid(email)

    /**
     * Checks if [email] don't meet requirements of [CredentialsValidator.isEmailValid]
     */
    fun emailNotValid() = !emailValid()
}