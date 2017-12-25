package com.marcinstramowski.socialmeal.screens.main.searchEvents

import com.marcinstramowski.socialmeal.di.FragmentScoped
import com.marcinstramowski.socialmeal.screens.main.profile.ProfileContract
import dagger.Binds
import dagger.Module

/**
 * Specifies classes delivered by dagger when injecting [ProfileContract] interfaces.
 */
@Module
abstract class SearchModule {

    @FragmentScoped
    @Binds
    abstract fun bindPresenter(presenter: SearchPresenter): SearchContract.Presenter

    @FragmentScoped
    @Binds
    abstract fun bindView(view: SearchFragment): SearchContract.View

}