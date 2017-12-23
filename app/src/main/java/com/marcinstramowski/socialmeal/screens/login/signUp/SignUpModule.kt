package com.marcinstramowski.socialmeal.screens.login.signUp

import com.marcinstramowski.socialmeal.di.FragmentScoped
import com.marcinstramowski.socialmeal.screens.login.signIn.SignInContract
import com.marcinstramowski.socialmeal.screens.login.signIn.SignInFragment
import com.marcinstramowski.socialmeal.screens.login.signIn.SignInPresenter
import dagger.Binds
import dagger.Module

/**
 * Specifies classes delivered by dagger when injecting [SignUpContract] interfaces.
 */
@Module
abstract class SignUpModule {

    @FragmentScoped
    @Binds
    abstract fun bindPresenter(presenter: SignUpPresenter): SignUpContract.Presenter

    @FragmentScoped
    @Binds
    abstract fun bindView(view: SignUpFragment): SignUpContract.View

}