package com.marcinstramowski.socialmeal.account

/**
 * Source interface for [DataValidatorRepo]
 */
interface DataValidatorSource {

    fun isEmailValid(email: String): Boolean

    fun isPasswordValid(password: String): Boolean

}