package com.marcinstramowski.socialmeal

import com.jakewharton.threetenabp.AndroidThreeTen
import com.marcinstramowski.socialmeal.di.DaggerAppComponent
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber
import timber.log.Timber.plant

/**
 * Created by marcinstramowski on 09.12.2017.
 */
class SocialMealApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
        return appComponent
    }

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) return
        LeakCanary.install(this)
        if (BuildConfig.DEBUG) plant(Timber.DebugTree())
        AndroidThreeTen.init(this)
    }

}