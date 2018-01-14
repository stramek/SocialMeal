package com.marcinstramowski.socialmeal.account

import dagger.Binds
import dagger.Module

/**
 * User data bindings between sources and repos
 */
@Module
interface UserDataModule {

    @Binds
    fun bindUserDataSrouce(repo: UserPrefsRepo): UserPrefsDataSource

}