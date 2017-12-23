package com.marcinstramowski.socialmeal.di

import com.marcinstramowski.socialmeal.screens.login.AccountActivity
import com.marcinstramowski.socialmeal.screens.login.AccountModule
import com.marcinstramowski.socialmeal.screens.main.MainActivity
import com.marcinstramowski.socialmeal.screens.main.MainModule
import com.marcinstramowski.socialmeal.screens.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Defines Activities with its modules to allow Dagger to inject its dependencies
 */
@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector()
    internal abstract fun splashActivity(): SplashActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [(AccountModule::class)])
    internal abstract fun accountActivity(): AccountActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [(MainModule::class)])
    internal abstract fun mainActivity(): MainActivity
}
