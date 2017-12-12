package com.marcinstramowski.socialmeal.screens.login

import com.marcinstramowski.socialmeal.di.ActivityScoped
import com.marcinstramowski.socialmeal.screens.main.MainContract
import com.marcinstramowski.socialmeal.screens.main.MainPresenter
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
abstract class LoginModule {

    @ActivityScoped
    @Binds
    abstract fun bindLoginPresenter(presenter: LoginPresenter): LoginContract.Presenter

    @ActivityScoped
    @Binds
    abstract fun bindLoginView(view: LoginActivity): LoginContract.View
}
