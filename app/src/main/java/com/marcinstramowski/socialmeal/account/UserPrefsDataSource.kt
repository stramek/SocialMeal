package com.marcinstramowski.socialmeal.account

/**
 * Source interface for [UserAccountManager]
 */
interface UserPrefsDataSource {
    var accessToken : String?
    var refreshToken : String?
    var rememberMe: Boolean
}