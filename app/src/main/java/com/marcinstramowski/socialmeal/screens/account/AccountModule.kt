package com.marcinstramowski.socialmeal.screens.account

import com.marcinstramowski.socialmeal.di.ActivityScoped
import dagger.Binds
import dagger.Module

/**
 * Specifies classes delivered by dagger when injecting [AccountContract] interfaces.
 */
@Module
abstract class AccountModule {

    @ActivityScoped
    @Binds
    abstract fun bindAccountPresenter(presenter: AccountPresenter): AccountContract.Presenter

    @ActivityScoped
    @Binds
    abstract fun bindAccountView(view: AccountActivity): AccountContract.View
}
