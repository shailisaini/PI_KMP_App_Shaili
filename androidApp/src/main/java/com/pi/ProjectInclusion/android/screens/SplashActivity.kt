package com.pi.ProjectInclusion.android.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pi.ProjectInclusion.android.MyApplicationTheme
import com.pi.ProjectInclusion.android.navigation.AppRoute
import com.pi.ProjectInclusion.android.screens.login.LanguageScreen
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel = koinViewModel<LoginViewModel>()
            MyApplicationTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "splash"
                ) {
                    composable("splash") {
                        SplashScreen(navController)
                    }
                    composable(AppRoute.LanguageSelect.route) {
                        startActivity(
                            Intent(
                                this@SplashActivity,
                                LoginNavigationScreen::class.java
                            )
                        ).also { finish() }
                    }

                    composable(AppRoute.ForgetPasswordUI.route) {
                        ForgetPasswordScreen(navController, viewModel)
                    }

                    composable(AppRoute.OtpSendVerifyUI.route) {
                        OtpSendVerifyScreen(navController, viewModel)
                    }
//                    composable(AppRoute.StudentDashboardActivity.route) {
//                        StudentDashboardActivity()
//                    }
                }
            }
        }
    }
}

@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(Unit) {
        delay(3500) // simulate loading
        navController.navigate(AppRoute.LanguageSelect.route) {
       // navController.navigate(AppRoute.StudentDashboardActivity.route) {
            popUpTo("splash") { inclusive = true }
        }
    }
}