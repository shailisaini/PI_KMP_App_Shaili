package com.pi.ProjectInclusion.android.navigation

import kotlinx.serialization.Serializable

sealed class AppRoute(val route: String) {

    @Serializable
    data object LanguageSelect : AppRoute("language_select")

}