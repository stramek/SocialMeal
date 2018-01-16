package com.marcinstramowski.socialmeal.model

/**
 * Sign in form fields data
 */
data class SignInFormFields(
        val email: String,
        val password: String
) {
    /**
     * Checks if [email], [password] are not blank
     */
    fun fieldsNotBlank() = email.isNotBlank() && password.isNotBlank()
}