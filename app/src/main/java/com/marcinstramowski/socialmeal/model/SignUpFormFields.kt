package com.marcinstramowski.socialmeal.model

import com.marcinstramowski.socialmeal.account.CredentialsValidator

/**
 * Class representing sign up form fields
 * Contains useful methods to check if content provided by user is valid such as [passwordsMatch] or [fieldsValid]
 *
 * Validation of [email] and [password] depends on [CredentialsValidator] class
 */
data class SignUpFormFields(
        val firstName: String,
        val surname: String,
        val email: String,
        val password: String,
        val confirmPassword: String
) {
    /**
     * Checks if [firstName], [surname], [email], [password], [confirmPassword] are not blank
     */
    fun fieldsNotBlank() = firstName.isNotBlank() && surname.isNotBlank() && email.isNotBlank()
            && password.isNotBlank() && confirmPassword.isNotBlank()

    /**
     * Checks if [password] and [confirmPassword] matches
     */
    fun passwordsMatch() = password == confirmPassword

    /**
     * Checks if [password] and [confirmPassword] don't match
     */
    fun passwordsNotMatch() = !passwordsMatch()

    /**
     * Checks if [email] meets requirements of [CredentialsValidator.isEmailValid]
     */
    fun emailValid() = CredentialsValidator.isEmailValid(email)

    /**
     * Checks if [email] don't meet requirements of [CredentialsValidator.isEmailValid]
     */
    fun emailNotValid() = !emailValid()

    /**
     * Checks if [password] meets requirements of [CredentialsValidator.isPasswordValid]
     */
    fun passwordValid() = CredentialsValidator.isPasswordValid(password)

    /**
     * Checks if [password] don't meet requirements of [CredentialsValidator.isPasswordValid]
     */
    fun passwordNotValid() = !passwordValid()

    /**
     * Checks if provided fields by user are valid to enable registration process
     */
    fun fieldsValid() = fieldsNotBlank() && passwordsMatch() && passwordValid() && emailValid()
}