package com.pi.ProjectInclusion.android.di

import com.pi.ProjectInclusion.AndroidConnectivityObserver
import com.pi.ProjectInclusion.database.AndroidLocalDataSource
import com.pi.ProjectInclusion.database.LocalDataSource
import com.pi.ProjectInclusion.domain.ConnectivityObserver
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidModule = module {
    single<ConnectivityObserver> { AndroidConnectivityObserver(get()) }
    single<LocalDataSource> { AndroidLocalDataSource(androidContext()) }
}