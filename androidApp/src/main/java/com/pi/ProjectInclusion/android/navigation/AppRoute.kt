package com.pi.ProjectInclusion.android.navigation

import kotlinx.serialization.Serializable

sealed class AppRoute(val route: String) {

    @Serializable
    data object LanguageSelect : AppRoute("language_select")
    data object ForgetPasswordUI : AppRoute("forget_password_UI")
    data object OtpSendVerifyUI : AppRoute("otp_send_verify_UI")

}