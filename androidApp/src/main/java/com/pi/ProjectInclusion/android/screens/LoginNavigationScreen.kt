package com.pi.ProjectInclusion.android.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pi.ProjectInclusion.android.MyApplicationTheme
import com.pi.ProjectInclusion.android.navigation.AppRoute
import com.pi.ProjectInclusion.android.screens.login.EnterPasswordScreen
import com.pi.ProjectInclusion.android.screens.login.ForgetPasswordScreen
import com.pi.ProjectInclusion.android.screens.login.EnterUserNameScreen
import com.pi.ProjectInclusion.android.screens.login.LanguageScreen
import com.pi.ProjectInclusion.android.screens.login.OtpSendVerifyScreen
import com.pi.ProjectInclusion.android.screens.registration.SetNewPasswordScreen
import com.pi.ProjectInclusion.android.screens.login.UserTypeScreen
import com.pi.ProjectInclusion.android.screens.registration.CreateNewPasswordScreen
import com.pi.ProjectInclusion.android.screens.registration.EnterUserScreen1
import com.pi.ProjectInclusion.android.screens.registration.EnterUserScreen2
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import org.koin.androidx.compose.koinViewModel

class LoginNavigationScreen : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = koinViewModel<LoginViewModel>()
//            val loadScreenName = intent.getStringExtra(screenName.screenName)
//            val startDestination = if (viewModel.getScreenName() == onboarding2) {
            val startDestination = AppRoute.LanguageSelect.route
//            } else if (loadScreenName == onboarding1 || viewModel.getScreenName() == onboarding1) {
//                AppRoute.RegistrationStep1()

            MyApplicationTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = startDestination,
                    modifier = Modifier.background(Color.White)
                ) {
                    composable(AppRoute.LanguageSelect.route) {
                        LanguageScreen(navController, viewModel)
                    }
                    composable(AppRoute.UserTypeSelect.route) {
                        UserTypeScreen(navController, viewModel)
                    }
                    composable(AppRoute.UserNameScreen.route) {
                        EnterUserNameScreen(navController, viewModel)
                    }
                    composable(AppRoute.UserPasswordScreen.route) {
                        EnterPasswordScreen(navController, viewModel)
                    }
                    composable(AppRoute.CreatePasswordScreen.route) {
                        CreateNewPasswordScreen(navController, viewModel)
                    }
                    composable(AppRoute.EnterUserProfileScreen.route) {
                        EnterUserScreen1(navController)
                    }
                    composable(AppRoute.EnterUserProfessionalScreen.route) {
                        EnterUserScreen2(navController)
                    }
                    composable(AppRoute.ForgetPasswordUI.route) {
                        ForgetPasswordScreen(navController, viewModel)
                    }
                    composable(AppRoute.OtpSendVerifyUI.route) {
                        OtpSendVerifyScreen(navController, viewModel)
                    }
                    composable(AppRoute.SetNewPasswordUI.route) {
                        SetNewPasswordScreen(navController, viewModel)
                    }
                }
            }
        }
    }
}