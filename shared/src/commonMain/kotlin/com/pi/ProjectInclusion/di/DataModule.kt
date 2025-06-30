package com.pi.ProjectInclusion.di

import com.pi.ProjectInclusion.data.model.GetLanguageListResponse
import com.pi.ProjectInclusion.data.remote.ApiService
import com.pi.ProjectInclusion.data.remote.KtorClient
import com.pi.ProjectInclusion.data.repository.LanguageRepoImpl
import com.pi.ProjectInclusion.domain.repository.LanguageRepository
import org.koin.dsl.module

val dataModule = module {
    factory { ApiService(KtorClient.client) }
    factory<LanguageRepository> { LanguageRepoImpl(get()) }
}