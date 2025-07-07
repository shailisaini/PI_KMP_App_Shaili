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