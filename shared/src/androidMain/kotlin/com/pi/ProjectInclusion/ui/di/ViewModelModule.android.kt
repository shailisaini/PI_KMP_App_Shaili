package com.pi.ProjectInclusion.ui.di

import com.pi.ProjectInclusion.ui.viewModel.DashboardViewModel
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

private val viewModelModules = module {
    viewModel {
        LoginViewModel(get(), get(), get())
    }

    viewModel {
        DashboardViewModel(get(), get(), get())
    }
}

actual fun sharedViewModelModule(): Module = viewModelModules