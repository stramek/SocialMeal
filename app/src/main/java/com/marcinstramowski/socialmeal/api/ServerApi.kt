package com.marcinstramowski.socialmeal.api

import com.marcinstramowski.socialmeal.BuildConfig
import com.marcinstramowski.socialmeal.api.auth.model.Token
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.*
import com.marcinstramowski.socialmeal.api.auth.ApiAuthenticator
import com.marcinstramowski.socialmeal.api.auth.AuthHeaderInterceptor
import com.marcinstramowski.socialmeal.api.auth.model.RefreshTokenRequest
import com.squareup.moshi.Moshi
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Configuration of API
 */
interface ServerApi {

    /**
     * Contains account management requests to API
     */
    interface ManagementApi {

        @POST("token/refresh")
        fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest): Single<Token>
    }

    /**
     * Contains user data requests to API
     */
    interface UserApi {

    }

    companion object Factory {

        /**
         * Determines api maximum delay in seconds after sending request before returning timeout exception
         */
        private val API_TIMEOUT_DELAY_SECONDS: Long = 5

        /**
         * Prepare service with api calls for logged user.
         * Need authenticator in case of access token not valid.
         * Need token handler for automatic header with authentication token.
         */
        fun prepareUserService(tokenHandler: AuthHeaderInterceptor, authenticator: ApiAuthenticator): UserApi {
            val userOkHttp = basicOkHttpBuilder()
                    .addInterceptor(tokenHandler)
                    .authenticator(authenticator)
                    .build()
            return fromRetrofit(userOkHttp).create(UserApi::class.java)
        }

        /**
         * Prepare service for api calls like login, register and refresh token (management)
         */
        fun prepareManagementService(): ManagementApi {
            val okHttp = basicOkHttpBuilder().build()
            return fromRetrofit(okHttp).create(ManagementApi::class.java)
        }

        /**
         * Basic api OkHttp builder
         */
        private fun basicOkHttpBuilder() = OkHttpClient.Builder()
                .connectTimeout(API_TIMEOUT_DELAY_SECONDS, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().setLevel(getLoggingLevel()))

        /**
         * Provides api logging level. Logs are available only on debug version
         */
        private fun getLoggingLevel(): HttpLoggingInterceptor.Level {
            return when (BuildConfig.DEBUG) {
                true -> HttpLoggingInterceptor.Level.BODY
                else -> HttpLoggingInterceptor.Level.NONE
            }
        }

        /**
         * Simple builder from [OkHttpClient] parameter
         */
        private fun fromRetrofit(userOkHttp: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create(getMoshiObjectMapper()))
                    .baseUrl(BuildConfig.API_URL)
                    .client(userOkHttp)
                    .build()
        }

        /**
         * Builder for Json Moshi parser
         */
        private fun getMoshiObjectMapper() = Moshi.Builder().build()
    }
}