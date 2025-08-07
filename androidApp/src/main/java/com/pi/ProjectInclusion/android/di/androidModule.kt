package com.pi.ProjectInclusion.android.di

import com.pi.ProjectInclusion.AndroidConnectivityObserver
import com.pi.ProjectInclusion.database.AndroidLocalDataSource
import com.pi.ProjectInclusion.database.LocalDataSource
import com.pi.ProjectInclusion.domain.ConnectivityObserver
import com.pi.ProjectInclusion.domain.useCases.AuthenticationUsesCases
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidModule = module {
    single<AuthenticationUsesCases> { AuthenticationUsesCases(get()) }
        single<ConnectivityObserver> { AndroidConnectivityObserver(get()) }
    single<LocalDataSource> { AndroidLocalDataSource(androidContext()) }
}