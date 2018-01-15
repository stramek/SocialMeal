package com.marcinstramowski.socialmeal.di

import android.app.Application
import com.marcinstramowski.socialmeal.SocialMealApplication
import com.marcinstramowski.socialmeal.account.UserDataModule
import com.marcinstramowski.socialmeal.api.ApiModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        ApplicationModule::class,
        ActivityBindingModule::class,
        FragmentBindingModule::class,
        AndroidSupportInjectionModule::class,
        UserDataModule::class,
        ApiModule::class)
)
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(application: SocialMealApplication)

    override fun inject(instance: DaggerApplication)

    // Gives us syntactic sugar. we can then do DaggerAppComponent.builder().application(this).build().inject(this);
    // never having to instantiate any modules or say which module we are passing the application to.
    // Application will just be provided into our app graph now.
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }
}
