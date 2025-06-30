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

class LoginNavigationScreen : ComponentActivity() {
//    @Inject
//    lateinit var sharedPref: SharedPref
//    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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
//                        LanguageScreen(navController)
                    }
                }
            }
        }
    }
}