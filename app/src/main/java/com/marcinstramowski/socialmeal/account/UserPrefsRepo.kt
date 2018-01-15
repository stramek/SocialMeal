package com.marcinstramowski.socialmeal.account

import android.content.Context
import android.content.SharedPreferences
import com.marcinstramowski.socialmeal.model.SignInResponse
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Class to manage user preferences data such as access and refresh tokens
 */
@Singleton
class UserPrefsRepo @Inject constructor(val context: Context) : UserPrefsDataSource {

    companion object {
        val PREFS_FILENAME = "com.marcinstramowski.socialmeal.userPrefs"
        val ACCESS_TOKEN = "ACCESS_TOKEN"
        val REFRESH_TOKEN = "REFRESH_TOKEN"
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

    override var refreshToken: String?
        get() = prefs.getString(REFRESH_TOKEN, null)
        set(value) = prefs.edit().putString(REFRESH_TOKEN, value).apply()

    override var accessToken: String?
        get() = prefs.getString(ACCESS_TOKEN, null)
        set(value) = prefs.edit().putString(ACCESS_TOKEN, value).apply()

    override fun saveTokens(response: SignInResponse) {
        accessToken = response.accessToken.token
        refreshToken = response.refreshToken.token
    }

    override fun logoutUser() {
        accessToken = null
        refreshToken = null
    }
}