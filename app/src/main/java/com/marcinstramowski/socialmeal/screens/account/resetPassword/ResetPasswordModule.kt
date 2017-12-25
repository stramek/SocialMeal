package com.marcinstramowski.socialmeal.screens.account.resetPassword

import com.marcinstramowski.socialmeal.di.FragmentScoped
import dagger.Binds
import dagger.Module

/**
 * Specifies classes delivered by dagger when injecting [ResetPasswordContract] interfaces.
 */
@Module
abstract class ResetPasswordModule {

    @FragmentScoped
    @Binds
    abstract fun bindPresenter(presenter: ResetPasswordPresenter): ResetPasswordContract.Presenter

    @FragmentScoped
    @Binds
    abstract fun bindView(view: ResetPasswordFragment): ResetPasswordContract.View
}