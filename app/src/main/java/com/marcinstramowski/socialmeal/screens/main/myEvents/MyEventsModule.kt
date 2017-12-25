package com.marcinstramowski.socialmeal.screens.main.myEvents

import com.marcinstramowski.socialmeal.di.FragmentScoped
import dagger.Binds
import dagger.Module

/**
 * Specifies classes delivered by dagger when injecting [MyEventsContract] interfaces.
 */
@Module
abstract class MyEventsModule {

    @FragmentScoped
    @Binds
    abstract fun bindPresenter(presenter: MyEventsPresenter): MyEventsContract.Presenter

    @FragmentScoped
    @Binds
    abstract fun bindView(view: MyEventsFragment): MyEventsContract.View

}