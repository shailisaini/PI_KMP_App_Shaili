package com.pi.ProjectInclusion.android.navigation

import kotlinx.serialization.Serializable

sealed class AppRoute(val route: String) {

    @Serializable
    data object LanguageSelect : AppRoute("language_select")
    data object UserTypeSelect : AppRoute("user_type")
    data object UserNameScreen : AppRoute("user_name_screen")
    data object UserPasswordScreen : AppRoute("user_password_screen")
    data object CreatePasswordScreen : AppRoute("create_password_screen")
    data object ForgetPasswordUI : AppRoute("forget_password_UI")
    data object OtpSendVerifyUI : AppRoute("otp_send_verify_UI")
    data object SetNewPasswordUI : AppRoute("set_new_password_UI")
    data object EnterUserProfileScreen : AppRoute("user_profile_screen")

    data object DashboardScreen : AppRoute("dashboard_screen")

    data object CourseScreen : AppRoute("course_screen")

    data object ScreeningScreen : AppRoute("screening_screen")

    data object InterventionScreen : AppRoute("intervention_screen")
    data object InterventionStudentDetails : AppRoute("intervention_student_details")

    data object ChangePasswordActivity : AppRoute("change_password_screen")

    data object StudentDashboardActivity : AppRoute("student_screen")


    data object AddStudentRegisterScreen: AppRoute("add_student_register_screen")
}
