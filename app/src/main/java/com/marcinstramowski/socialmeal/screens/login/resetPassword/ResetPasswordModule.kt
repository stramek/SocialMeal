package com.marcinstramowski.socialmeal.screens.login.resetPassword

import com.marcinstramowski.socialmeal.di.FragmentScoped
import dagger.Binds
import dagger.Module

/**
 * Created by marcinstramowski on 17.12.2017.
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