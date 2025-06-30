package com.pi.ProjectInclusion.ui.di

import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.module.Module
import org.koin.dsl.module

private val ViewModelModule = module{
single{
    LoginViewModel(get())
}
}
actual fun sharedViewModelModule(): Module = ViewModelModule

object ProvideViewModel : KoinComponent{
    fun getLoginViewModel() = get<LoginViewModel>()
}