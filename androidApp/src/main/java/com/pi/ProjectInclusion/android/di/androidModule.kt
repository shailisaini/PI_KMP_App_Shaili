package com.pi.ProjectInclusion.android.di

import com.pi.ProjectInclusion.database.AndroidLocalDataSource
import com.pi.ProjectInclusion.database.LocalDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidModule = module {
    single<LocalDataSource> { AndroidLocalDataSource(androidContext()) }
}