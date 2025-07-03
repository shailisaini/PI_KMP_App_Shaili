package com.pi.ProjectInclusion.android.navigation

import kotlinx.serialization.Serializable

sealed class AppRoute(val route: String) {

    @Serializable
    data object LanguageSelect : AppRoute("language_select")

    data object DashboardScreen : AppRoute("dashboard_screen")

    data object CourseScreen : AppRoute("course_screen")

    data object ScreeningScreen : AppRoute("screening_screen")

    data object InterventionScreen : AppRoute("intervention_screen")

    data object StudentDashboardActivity : AppRoute("student_screen")
}
