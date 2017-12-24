package com.marcinstramowski.socialmeal.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

/**
 * This is a Dagger module. We use this to bind our Application class as a Context in the AppComponent
 * By using Dagger Android we do not need to pass our Application instance to any module,
 * we simply need to expose our Application as Context.
 */
@Module
abstract class ApplicationModule {

    @Binds internal abstract fun bindContext(application: Application): Context
}