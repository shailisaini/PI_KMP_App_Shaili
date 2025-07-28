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
    data object EnterUserProfileScreen : AppRoute("user_profile_screen") // registration 1
    data object EnterUserProfessionalScreen : AppRoute("user_professional_screen") // registration 2

    data object DashboardScreen : AppRoute("dashboard_screen")
    data object ProfileScreen : AppRoute("profile_screen")     // user profile after Dashboard
    data object EditProfileScreen : AppRoute("edit_profile_screen")     // edit profile after Dashboard
    data object EditProfileScreen2 : AppRoute("edit_profile_screen_2")     // edit profile after Dashboard

    data object CourseScreen : AppRoute("course_screen")
    data object CertificateScreen : AppRoute("certificate_screen")

    data object ScreeningScreen : AppRoute("screening_screen")
    data object AddStudentRegister : AppRoute("add_student_register")
    data object AddNewStudentMoreDetails : AppRoute("add_new_student_more_details")

    data object InterventionScreen : AppRoute("intervention_screen")
    data object InterventionStudentDetails : AppRoute("intervention_student_details")
    data object InterventionAcceptLevel : AppRoute("intervention_accept_level")
    data object UploadedDocuments : AppRoute("intervention_uploaded_documents")
    data object TeachingPlan : AppRoute("intervention_teaching_plan")

    data object ChangePasswordActivity : AppRoute("change_password_screen")

    data object StudentDashboardActivity : AppRoute("student_screen")
}
