package com.pi.ProjectInclusion.android.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import com.pi.ProjectInclusion.android.MyApplicationTheme
import com.pi.ProjectInclusion.android.navigation.AppRoute
import com.pi.ProjectInclusion.android.screens.dashboardScreen.DashboardScreen
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.pi.ProjectInclusion.constants.ConstantVariables.ROUTE
import com.pi.ProjectInclusion.constants.ConstantVariables.SPLASH_KEY

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
          /*  val viewModel = koinViewModel<LoginViewModel>()
            val navController = rememberNavController()*/
            val context = LocalContext.current
            val (currentRoute, setRoute) = remember { mutableStateOf(SPLASH_KEY) }
            var isForward by remember { mutableStateOf(true) }  // is screen moving forward

            fun navigateTo(route: String) {
                isForward = route != SPLASH_KEY // You can define logic to detect back
                setRoute(route)
            }

            MyApplicationTheme {
                AnimatedContent(
                    targetState = currentRoute,
                    label = ROUTE,
                    transitionSpec = {
                        val duration = 400
                        if (isForward) {
                            (slideInHorizontally(
                                animationSpec = tween(duration),
                                initialOffsetX = { it }
                            ) + fadeIn(animationSpec = tween(duration))) with
                                    (slideOutHorizontally(
                                        animationSpec = tween(duration),
                                        targetOffsetX = { -it }
                                    ) + fadeOut(animationSpec = tween(duration)))
                        } else {
                            (slideInHorizontally(
                                animationSpec = tween(duration),
                                initialOffsetX = { -it }
                            ) + fadeIn(animationSpec = tween(duration))) with
                                    (slideOutHorizontally(
                                        animationSpec = tween(duration),
                                        targetOffsetX = { it }
                                    ) + fadeOut(animationSpec = tween(duration)))
                        }.using(SizeTransform(clip = false))
                    }
                ) { route ->
                    when (route) {
                        SPLASH_KEY -> SplashScreen {
                            // Shaili
//                            navigateTo(AppRoute.LanguageSelect.route)

                            // for abhishek & aashish
                            context.startActivity(
                                Intent(context, StudentDashboardActivity::class.java)
                            )
                            (context as? Activity)?.finish()
                        }

                        AppRoute.LanguageSelect.route -> {
                            LaunchedEffect(Unit) {
                                delay(300) // optional smooth transition
                                startActivity(
                                    Intent(
                                        this@SplashActivity,
                                        LoginNavigationScreen::class.java
                                    )
                                ).also { finish() }
                            }
                        }

                        AppRoute.StudentDashboardActivity.route ->{
                            context.startActivity(
                                Intent(context, StudentDashboardActivity::class.java)
                            )
                            (context as? Activity)?.finish()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SplashScreen(onDone: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(3000) // simulate loading
        onDone()
    }
}