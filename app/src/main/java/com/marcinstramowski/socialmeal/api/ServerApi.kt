package com.marcinstramowski.socialmeal.api

import com.google.gson.GsonBuilder
import com.marcinstramowski.socialmeal.BuildConfig
import com.marcinstramowski.socialmeal.api.auth.ApiAuthenticator
import com.marcinstramowski.socialmeal.api.auth.AuthHeaderInterceptor
import com.marcinstramowski.socialmeal.model.*
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

/**
 * Configuration of API
 */
interface ServerApi {

    /**
     * Contains account management requests to API
     */
    interface ManagementApi {

        @POST("account/register")
        fun signUp(@Body signUpRequest: SignUpRequest): Completable

        @POST("account/login")
        fun signIn(@Body signInRequest: SignInRequest): Single<SignInResponse>

        @POST("account/refreshToken")
        fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest): Single<Token>

        @POST("account/forgot-password")
        fun resetPassword(@Body resetPasswordRequest: ResetPasswordRequest): Completable
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
        private const val API_TIMEOUT_DELAY_SECONDS: Long = 5

        /**
         * Prepare service with api calls for logged user.
         * Need authenticator in case of access token not valid.
         * Need token handler for automatic header with authentication token.
         */
        fun prepareUserService(
            tokenHandler: AuthHeaderInterceptor, authenticator: ApiAuthenticator
        ): UserApi {
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
                .addConverterFactory(GsonConverterFactory.create(getGsonObjectMapper()))
                .baseUrl(BuildConfig.API_URL)
                .client(userOkHttp)
                .build()
        }

        /**
         * Builder for Gson parser
         */
        private fun getGsonObjectMapper() = GsonBuilder().create()
    }
}