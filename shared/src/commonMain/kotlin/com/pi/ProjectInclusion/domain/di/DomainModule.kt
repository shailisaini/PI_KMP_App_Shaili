package com.pi.ProjectInclusion.domain.di

import com.pi.ProjectInclusion.data.remote.ApiService
import com.pi.ProjectInclusion.data.remote.KtorClient
import com.pi.ProjectInclusion.data.repository.LanguageRepoImpl
import com.pi.ProjectInclusion.domain.repository.LanguageRepository
import com.pi.ProjectInclusion.domain.useCases.GetLanguageUsesCases
import org.koin.dsl.module

val domainModule = module {
    factory { GetLanguageUsesCases(get()) }
}