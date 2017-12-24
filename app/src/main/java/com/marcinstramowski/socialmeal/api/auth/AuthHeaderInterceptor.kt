package com.marcinstramowski.socialmeal.api.auth

import com.marcinstramowski.socialmeal.account.UserPrefsDataSource
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

/**
 * Created by marcinstramowski on 09.12.2017.
 */
class AuthHeaderInterceptor @Inject constructor(private val userPrefs: UserPrefsDataSource) : Interceptor {

    companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return if (isHeaderNeeded(request))
            chain.proceed(request
                    .newBuilder()
                    .addHeader(AUTHORIZATION_HEADER, "Bearer " + userPrefs.accessToken)
                    .build()
            )
        else chain.proceed(request)
    }

    private fun isHeaderNeeded(request: Request) = isAccessToken() && hasNoHeader(request)

    private fun isAccessToken() = !userPrefs.accessToken.isNullOrEmpty()

    private fun hasNoHeader(request: Request) = request.header(AUTHORIZATION_HEADER).isNullOrEmpty()
}