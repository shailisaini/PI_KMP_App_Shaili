package com.pi.ProjectInclusion.android.navigation

import kotlinx.serialization.Serializable

sealed class AppRoute(val route: String) {

    @Serializable
    data object LanguageSelect : AppRoute("language_select")
    data object UserTypeSelect : AppRoute("user_type")
    data object ForgetPasswordUI : AppRoute("forget_password_UI")
    data object OtpSendVerifyUI : AppRoute("otp_send_verify_UI")

    data object DashboardScreen : AppRoute("dashboard_screen")

    data object CourseScreen : AppRoute("course_screen")

    data object ScreeningScreen : AppRoute("screening_screen")

    data object InterventionScreen : AppRoute("intervention_screen")

    data object StudentDashboardActivity : AppRoute("student_screen")

    data object AddStudentRegisterScreen: AppRoute("add_student_register_screen")
}
