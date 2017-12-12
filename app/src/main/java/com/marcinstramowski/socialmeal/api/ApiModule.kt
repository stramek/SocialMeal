package com.marcinstramowski.socialmeal.api

import dagger.Module
import dagger.Provides
import com.marcinstramowski.socialmeal.api.auth.ApiAuthenticator
import com.marcinstramowski.socialmeal.api.auth.AuthHeaderInterceptor
import javax.inject.Singleton

/**
 * Created by marcinstramowski on 09.12.2017.
 */
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

    @Provides
    @Singleton
    internal fun provideManagementApi(): ServerApi.ManagementApi {
        return ServerApi.Factory.prepareManagementService()
    }

    @Provides
    @Singleton
    internal fun provideHmoApi(interceptor: AuthHeaderInterceptor, authenticator: ApiAuthenticator): ServerApi.UserApi {
        return ServerApi.Factory.prepareUserService(interceptor, authenticator)
    }
}