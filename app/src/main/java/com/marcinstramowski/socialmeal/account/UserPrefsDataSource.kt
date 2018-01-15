package com.marcinstramowski.socialmeal.account

import com.marcinstramowski.socialmeal.model.SignInResponse

/**
 * Source interface for [UserAccountManager]
 */
interface UserPrefsDataSource {
    var accessToken : String?
    var refreshToken : String?

    fun saveTokens(response: SignInResponse)
    fun logoutUser()
}