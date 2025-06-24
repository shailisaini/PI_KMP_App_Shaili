package com.pi.ProjectInclusion.android.navigation

sealed class AppRoute(val route: String) {
    data object LanguageSelect : AppRoute("LanguageSelect")
}