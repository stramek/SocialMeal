package com.marcinstramowski.socialmeal.account

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by marcinstramowski on 09.12.2017.
 */
@Singleton
class UserAccountManager @Inject constructor(private val userPrefs: UserPrefsDataSource) {

    fun logoutUser() {
        userPrefs.accessToken = null
        userPrefs.refreshToken = null
        userPrefs.uniqueUserId = null
    }

    fun shouldAutoLogUser() = userPrefs.refreshToken != null && userPrefs.rememberMe

    fun isUserLogged() = userPrefs.uniqueUserId != null

    fun isRefreshTokenValid() = userPrefs.refreshToken != null
}