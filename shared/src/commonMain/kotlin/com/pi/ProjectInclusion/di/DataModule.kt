package com.pi.ProjectInclusion.di

import com.pi.ProjectInclusion.data.remote.ApiService
import com.pi.ProjectInclusion.data.remote.KtorClient
import com.pi.ProjectInclusion.data.repository.AuthenticationRepoImpl
import com.pi.ProjectInclusion.data.repository.DashboardRepoImpl
import com.pi.ProjectInclusion.domain.repository.AuthenticationRepository
import com.pi.ProjectInclusion.domain.repository.DashboardRepository
import org.koin.dsl.module

val dataModule = module {
    factory { ApiService(KtorClient.client) }
    factory<AuthenticationRepository> { AuthenticationRepoImpl(get()) }
    factory<DashboardRepository> { DashboardRepoImpl(get()) }
}