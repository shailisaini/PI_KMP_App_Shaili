package com.pi.ProjectInclusion.android.screens

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pi.ProjectInclusion.android.MyApplicationTheme
import com.pi.ProjectInclusion.android.navigation.AppRoute
import com.pi.ProjectInclusion.di.dataModule
import com.pi.ProjectInclusion.domain.di.domainModule
import com.pi.ProjectInclusion.ui.di.sharedViewModelModule
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import kotlinx.coroutines.delay
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.startKoin

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
                        LanguageScreen(navController, viewModel)
                    }

                    composable(AppRoute.ForgetPasswordUI.route) {
                        ForgetPasswordScreen(navController, viewModel)
                    }

                    composable(AppRoute.OtpSendVerifyUI.route) {
                        OtpSendVerifyScreen(navController, viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(Unit) {
        delay(2000) // simulate loading
        navController.navigate(AppRoute.LanguageSelect.route) {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Splash Screen", fontSize = 24.sp)
    }
}