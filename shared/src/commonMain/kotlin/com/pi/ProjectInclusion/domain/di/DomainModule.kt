package com.pi.ProjectInclusion.domain.di

import com.pi.ProjectInclusion.domain.useCases.AuthenticationUsesCases
import com.pi.ProjectInclusion.domain.useCases.DashboardUsesCases
import org.koin.dsl.module

val domainModule = module {
    factory { AuthenticationUsesCases(get()) }
    factory { DashboardUsesCases(get()) }
}