package com.pi.ProjectInclusion.ui.di

import org.koin.core.module.Module

expect fun sharedViewModelModule() : Module
//fun sharedViewModelModule() = module {
//    factory { GetLanguageUsesCases(get()) }
//}