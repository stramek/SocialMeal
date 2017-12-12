package com.marcinstramowski.socialmeal.api.auth

import com.marcinstramowski.socialmeal.account.UserAccountManager
import com.marcinstramowski.socialmeal.account.UserPrefsDataSource
import com.marcinstramowski.socialmeal.api.ServerApi
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.HttpException
import com.marcinstramowski.socialmeal.api.auth.AuthHeaderInterceptor.Companion.AUTHORIZATION_HEADER
import com.marcinstramowski.socialmeal.api.auth.model.RefreshTokenRequest
import javax.inject.Inject

/**
 * Created by marcinstramowski on 09.12.2017.
 */
class ApiAuthenticator @Inject constructor(
        val userPrefs: UserPrefsDataSource,
        val userAccountManager: UserAccountManager,
        var api: ServerApi.ManagementApi
) : Authenticator {

    /**
     * process request that returns 401
     */
    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = userPrefs.refreshToken
        if (refreshToken == null || refreshToken.isBlank()) {
            userAccountManager.logoutUser()
            return null
        } else try {
            userPrefs.accessToken = refreshAccessToken(refreshToken)
            return response.request().newBuilder()
                    .header(AUTHORIZATION_HEADER, "Bearer " + userPrefs.accessToken)
                    .build()
        } catch (e: Exception) {
            if (e is HttpException && e.code() == 400) {
                userAccountManager.logoutUser()
            }
            return null
        }
    }

    /**
     * call request for refresh access token
     */
    fun refreshAccessToken(refreshToken: String) = api.refreshToken(RefreshTokenRequest(refreshToken)).blockingGet().token
}