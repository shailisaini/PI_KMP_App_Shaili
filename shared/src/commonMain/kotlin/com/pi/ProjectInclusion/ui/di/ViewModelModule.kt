package com.pi.ProjectInclusion.ui.di

import com.pi.ProjectInclusion.domain.useCases.GetLanguageUsesCases
import org.koin.core.module.Module
import org.koin.dsl.module

expect fun sharedViewModelModule() : Module
//fun sharedViewModelModule() = module {
//    factory { GetLanguageUsesCases(get()) }
//}