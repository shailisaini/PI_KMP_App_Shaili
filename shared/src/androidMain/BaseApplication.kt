package com.pi.ProjectInclusion.android

import android.app.Application


class BaseApplication: Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger(if (BuildConfig.LOG_MODE) Level.ERROR else Level.NONE)
            androidContext(this@BaseApplication)
            modules(dataModule + domainModule + viewModelModules)
        }
    }
}