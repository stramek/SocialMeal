package com.marcinstramowski.socialmeal.screens.login.signIn

import com.marcinstramowski.socialmeal.di.FragmentScoped
import dagger.Binds
import dagger.Module

/**
 * Created by marcinstramowski on 17.12.2017.
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