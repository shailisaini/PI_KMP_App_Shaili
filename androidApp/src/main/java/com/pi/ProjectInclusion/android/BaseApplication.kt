package com.pi.ProjectInclusion.android

import android.app.Application
import com.pi.ProjectInclusion.di.dataModule
import com.pi.ProjectInclusion.domain.di.domainModule
import com.pi.ProjectInclusion.ui.di.sharedViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication: Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin{
//            androidLogger(if (BuildConfig.LOG_MODE) Level.ERROR else Level.NONE)
            androidContext(this@BaseApplication)
            modules(dataModule + domainModule + sharedViewModelModule())
        }
    }
}