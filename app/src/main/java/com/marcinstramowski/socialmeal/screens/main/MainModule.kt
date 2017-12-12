package com.marcinstramowski.socialmeal.screens.main

import com.marcinstramowski.socialmeal.di.ActivityScoped
import com.marcinstramowski.socialmeal.screens.login.LoginActivity
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
abstract class MainModule {

    @ActivityScoped
    @Binds
    abstract fun bindLoginPresenter(presenter: MainPresenter): MainContract.Presenter

    @ActivityScoped
    @Binds
    abstract fun bindLoginView(view: MainActivity): MainContract.View
}
