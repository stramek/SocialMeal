package com.marcinstramowski.socialmeal.api

import com.marcinstramowski.socialmeal.api.auth.ApiAuthenticator
import com.marcinstramowski.socialmeal.api.auth.AuthHeaderInterceptor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * This is a Dagger module. We use this to bind our Application class as a Context in the AppComponent
 * By using Dagger Android we do not need to pass our Application instance to any module,
 * we simply need to expose our Application as Context.
 * One of the advantages of Dagger.Android is that your
 * Application & Activities are provided into your graph for you.
 * [ ].
 */
@Module
class ApiModule {

    @Singleton
    @Provides
    internal fun provideManagementApi(): ServerApi.ManagementApi {
        return ServerApi.Factory.prepareManagementService()
    }

    @Singleton
    @Provides
    internal fun provideSocialMealApi(
        interceptor: AuthHeaderInterceptor, authenticator: ApiAuthenticator
    ): ServerApi.UserApi {
        return ServerApi.Factory.prepareUserService(interceptor, authenticator)
    }
}