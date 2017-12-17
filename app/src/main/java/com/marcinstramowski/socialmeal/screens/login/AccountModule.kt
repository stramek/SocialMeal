package com.marcinstramowski.socialmeal.screens.login

import com.marcinstramowski.socialmeal.di.ActivityScoped
import dagger.Binds
import dagger.Module

/**
 * Created by marcinstramowski on 09.12.2017.
 */
/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * [TasksPresenter].
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
