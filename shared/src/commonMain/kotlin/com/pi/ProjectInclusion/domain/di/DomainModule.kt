package com.pi.ProjectInclusion.domain.di

import com.pi.ProjectInclusion.domain.useCases.GetLanguageUsesCases
import org.koin.dsl.module

val domainModule = module {
    factory { GetLanguageUsesCases(get()) }
}