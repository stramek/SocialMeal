package com.marcinstramowski.socialmeal.api

import android.os.Debug
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
 * Created by marcinstramowski on 09.12.2017.
 */
interface ServerApi {

    interface ManagementApi {
        @POST("token/refresh")
        fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest): Single<Token>
    }

    interface UserApi {

    }

    companion object Factory {

        /**
         * prepare service with api calls for logged user
         * need authenticator in case of access token not valid
         * need token handler for automatic header with authentication add
         */
        fun prepareUserService(tokenHandler: AuthHeaderInterceptor, authenticator: ApiAuthenticator): UserApi {
            val userOkHttp = basicOkHttpBuilder()
                    .addInterceptor(tokenHandler)
                    .authenticator(authenticator)
                    .build()
            return fromRetrofit(userOkHttp).create(UserApi::class.java)
        }

        /**
         * prepare service for api calls like login, register and refresh token (management)
         */
        fun prepareManagementService(): ManagementApi {
            val okHttp = basicOkHttpBuilder().build()
            return fromRetrofit(okHttp).create(ManagementApi::class.java)
        }

        private fun basicOkHttpBuilder() = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().setLevel(getLoggingLevel()))

        private fun getLoggingLevel(): HttpLoggingInterceptor.Level {
            return when (BuildConfig.DEBUG) {
                true -> HttpLoggingInterceptor.Level.BODY
                else -> HttpLoggingInterceptor.Level.NONE
            }
        }

        /**
         * simple builder from OkHttpClient parameter
         */
        private fun fromRetrofit(userOkHttp: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create(getMoshiObjectMapper()))
                    .baseUrl(BuildConfig.API_URL)
                    .client(userOkHttp)
                    .build()
        }

        private fun getMoshiObjectMapper() = Moshi.Builder().build()
    }
}