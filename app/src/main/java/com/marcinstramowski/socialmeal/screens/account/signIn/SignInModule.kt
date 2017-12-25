package com.marcinstramowski.socialmeal.screens.account.signIn

import com.marcinstramowski.socialmeal.di.FragmentScoped
import dagger.Binds
import dagger.Module

/**
 * Specifies classes delivered by dagger when injecting [SignInContract] interfaces.
 */
@Module
abstract class SignInModule {

    @FragmentScoped
    @Binds
    abstract fun bindPresenter(presenter: SignInPresenter): SignInContract.Presenter

    @FragmentScoped
    @Binds
    abstract fun bindView(view: SignInFragment): SignInContract.View

}