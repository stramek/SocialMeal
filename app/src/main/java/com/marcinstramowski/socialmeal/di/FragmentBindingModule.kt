package com.marcinstramowski.socialmeal.di

import com.marcinstramowski.socialmeal.screens.login.resetPassword.ResetPasswordFragment
import com.marcinstramowski.socialmeal.screens.login.resetPassword.ResetPasswordModule
import com.marcinstramowski.socialmeal.screens.login.signIn.SignInFragment
import com.marcinstramowski.socialmeal.screens.login.signIn.SignInModule
import com.marcinstramowski.socialmeal.screens.login.signUp.SignUpFragment
import com.marcinstramowski.socialmeal.screens.login.signUp.SignUpModule
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

}