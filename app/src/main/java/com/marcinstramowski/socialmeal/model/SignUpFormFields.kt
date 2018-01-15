package com.marcinstramowski.socialmeal.model

/**
 * Sign up form fields data
 */
data class SignUpFormFields(
        val firstname: String = "",
        val surname: String = "",
        val email: String = "",
        val password: String = "",
        val confirmPassword: String = ""
)