package com.pi.ProjectInclusion.android.navigation

import kotlinx.serialization.Serializable

sealed class AppRoute(val route: String) {

    @Serializable
    data object Splash : AppRoute("splash")
    data object LanguageSelect : AppRoute("language_select")
    data object UserTypeSelect : AppRoute("user_type")
    data object UserNameScreen : AppRoute("user_name_screen")
    data object UserPasswordScreen : AppRoute("user_password_screen")
    data object CreatePasswordScreen : AppRoute("create_password_screen")
    data object ForgetPasswordUI : AppRoute("forget_password_UI")
//    data object OtpSendVerifyUI : AppRoute("otp_send_verify_UI")

    object OtpSendVerifyUI {
        const val route = "otp_send_verify_UI/{mobNo}"
        fun withArgs(mobNo: String): String { return "otp_send_verify_UI/$mobNo"}
    }

    data object SetNewPasswordUI : AppRoute("set_new_password_UI")
    data object EnterUserProfileScreen : AppRoute("user_profile_screen") // registration 1
    data object EnterTeacherRegScreen : AppRoute("user_professional_screen") // registration 2
    data object SpecialEducatorRegistration : AppRoute("SpecialEducatorRegistration2") // speEducator registration 2
    data object EnterProfessionalScreen : AppRoute("user_professional_screen") // Professional registration 2

    data object DashboardScreen : AppRoute("dashboard_screen")
    data object ProfileScreen : AppRoute("profile_screen")     // user profile after Dashboard
    data object EditProfileScreen : AppRoute("edit_profile_screen")     // edit profile after Dashboard
    data object EditTeacherProfileScreen : AppRoute("edit_profile_screen_2")     // edit profile after Dashboard
    data object EditSpeEduEditProfile : AppRoute("edit_special_edu_edit_profile")     // edit Special Edu profile after Dashboard
    data object EditProfessionalEditProfile : AppRoute("edit_professional_edit_profile_screen")     // edit professional profile after Dashboard

    data object PrivacyPolicyScreen : AppRoute("privacy_policy_screen")

    data object CourseScreen : AppRoute("course_screen")
    data object CertificateScreen : AppRoute("certificate_screen")
    data object JoinMeetingScreen : AppRoute("join_meeting_screen")
    data object ChangePasswordScreen : AppRoute("change_password_screen")
    data object LanguageScreen : AppRoute("language_screen")
    data object ReferScreen : AppRoute("refer_screen")
    data object ContactUsScreen : AppRoute("contact_us_screen")
    data object FaqScreen : AppRoute("f_a_q_screen")

    data object ScreeningScreen : AppRoute("screening_screen")
    data object AddStudentRegister : AppRoute("add_student_register")
    data object AddNewStudentMoreDetails : AppRoute("add_new_student_more_details")
    data object ScreeningOne : AppRoute("screening_one")
    data object ScreeningOneReport : AppRoute("screening_one_report")
    data object AdvanceScreening : AppRoute("advance_screening")
    data object AdvanceScreeningReport : AppRoute("advance_screening_report")
    data object ProfilerFormPage : AppRoute("profiler_form_page")
    data object ViewScreeningProfileDetails : AppRoute("view_screening_profile_details")

    data object InterventionScreen : AppRoute("intervention_screen")
    data object InterventionStudentDetails : AppRoute("intervention_student_details")
    data object InterventionAcceptLevel : AppRoute("intervention_accept_level")
    data object UploadedDocuments : AppRoute("intervention_uploaded_documents")
    data object TeachingPlan : AppRoute("intervention_teaching_plan")

    data object TeacherDashboard : AppRoute("teacher_dashboard")
    data object TrackRequestScreen : AppRoute("track_request_screen")
    data object NotificationScreen : AppRoute("notification_screen")
}
