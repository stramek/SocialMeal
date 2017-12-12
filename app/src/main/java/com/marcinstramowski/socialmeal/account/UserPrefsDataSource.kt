package com.marcinstramowski.socialmeal.account

/**
 * Created by marcinstramowski on 09.12.2017.
 */
interface UserPrefsDataSource {

    var uniqueUserId: String?
    var accessToken : String?
    var refreshToken : String?
    var rememberMe: Boolean

    fun saveUserUniqueId(userUniqueId: String?)
}