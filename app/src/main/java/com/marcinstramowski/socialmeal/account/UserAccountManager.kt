package com.marcinstramowski.socialmeal.account

import javax.inject.Inject
import javax.inject.Singleton

/**
 * User management helper class
 */
@Singleton
class UserAccountManager @Inject constructor(private val userPrefs: UserPrefsDataSource) {

    /**
     * Voids user access tokens
     */
    fun logoutUser() {
        userPrefs.accessToken = null
        userPrefs.refreshToken = null
    }

    /**
     *  Determines if application should omit login screen and take user straight to [MainActivity]
     */
    fun shouldAutoLogUser() = userPrefs.refreshToken != null && userPrefs.rememberMe

    /**
     * Determines if user it logged to the application
     */
    fun isUserLogged() = userPrefs.refreshToken != null
}