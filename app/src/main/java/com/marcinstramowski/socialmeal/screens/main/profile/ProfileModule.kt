package com.marcinstramowski.socialmeal.screens.main.profile

import com.marcinstramowski.socialmeal.di.FragmentScoped
import dagger.Binds
import dagger.Module

/**
 * Specifies classes delivered by dagger when injecting [ProfileContract] interfaces.
 */
@Module
abstract class ProfileModule {

    @FragmentScoped
    @Binds
    abstract fun bindPresenter(presenter: ProfilePresenter): ProfileContract.Presenter

    @FragmentScoped
    @Binds
    abstract fun bindView(view: ProfileFragment): ProfileContract.View

}