package com.marcinstramowski.socialmeal.di

import com.marcinstramowski.socialmeal.screens.account.resetPassword.ResetPasswordFragment
import com.marcinstramowski.socialmeal.screens.account.resetPassword.ResetPasswordModule
import com.marcinstramowski.socialmeal.screens.account.signIn.SignInFragment
import com.marcinstramowski.socialmeal.screens.account.signIn.SignInModule
import com.marcinstramowski.socialmeal.screens.account.signUp.SignUpFragment
import com.marcinstramowski.socialmeal.screens.account.signUp.SignUpModule
import com.marcinstramowski.socialmeal.screens.main.myEvents.MyEventsFragment
import com.marcinstramowski.socialmeal.screens.main.myEvents.MyEventsModule
import com.marcinstramowski.socialmeal.screens.main.profile.ProfileFragment
import com.marcinstramowski.socialmeal.screens.main.profile.ProfileModule
import com.marcinstramowski.socialmeal.screens.main.searchEvents.SearchFragment
import com.marcinstramowski.socialmeal.screens.main.searchEvents.SearchModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Defines fragments with its modules to allow Dagger to inject its dependencies
 */
@Module
abstract class FragmentBindingModule {

    @FragmentScoped
    @ContributesAndroidInjector(modules = [(SignInModule::class)])
    internal abstract fun signInFragment(): SignInFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = [(SignUpModule::class)])
    internal abstract fun signUpFragment(): SignUpFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = [(ResetPasswordModule::class)])
    internal abstract fun resetPasswordFragment(): ResetPasswordFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = [(MyEventsModule::class)])
    internal abstract fun myEventsFragment(): MyEventsFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = [(ProfileModule::class)])
    internal abstract fun profileFragment(): ProfileFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = [(SearchModule::class)])
    internal abstract fun searchFragment(): SearchFragment
}