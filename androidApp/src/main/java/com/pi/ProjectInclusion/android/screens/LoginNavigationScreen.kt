package com.pi.ProjectInclusion.android.screens

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kmptemplate.logger.LoggerProvider
import com.example.kmptemplate.logger.LoggerProvider.logger
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
import com.pi.ProjectInclusion.android.screens.registration.professionals.ProfessionalsRegistration2
import com.pi.ProjectInclusion.android.screens.registration.specialEdu.SpecialEducatorScreen2
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import org.koin.androidx.compose.koinViewModel

class LoginNavigationScreen : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val navController = rememberNavController()
//            val viewModel = koinViewModel<LoginViewModel>()
            val viewModel: LoginViewModel = koinViewModel()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination?.route
//            val loadScreenName = intent.getStringExtra(screenName.screenName)
//            val startDestination = if (viewModel.getScreenName() == onboarding2) {
            val startDestination = AppRoute.EnterUserProfileScreen.route
//            } else if (loadScreenName == onboarding1 || viewModel.getScreenName() == onboarding1) {
//                AppRoute.RegistrationStep1()

            var currentRoute by remember { mutableStateOf(startDestination) }
            var isForward by remember { mutableStateOf(true) }

            fun navigateTo(route: String) {
                isForward = true
                currentRoute = route
            }

            fun navigateBack(toRoute: String? = null) {
                isForward = false
                currentRoute = toRoute ?: AppRoute.LanguageSelect.route
            }

            MyApplicationTheme {
                AnimatedContent(
                    targetState = currentRoute,
                    label = "",
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
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) { route ->
                    when (route) {
                        AppRoute.LanguageSelect.route -> LanguageScreen(
                            viewModel = viewModel
                        ) {
//                            navigateTo(AppRoute.SpecialEducatorRegistration2.route)
                            navigateTo(AppRoute.UserTypeSelect.route)
                        }

                        AppRoute.UserTypeSelect.route -> UserTypeScreen(
                            viewModel = viewModel,
                            onNext = { /*navigateTo(AppRoute.UserNameScreen.route)*/
                                context.startActivity(
                                    Intent(context, StudentDashboardActivity::class.java)
                                )
                                (context as? Activity)?.finish()
                            },
                            onBack = { navigateBack(AppRoute.LanguageSelect.route) }
                        )

                        AppRoute.UserNameScreen.route -> EnterUserNameScreen(
                            viewModel = viewModel,
                            onNext = { navigateTo(AppRoute.UserPasswordScreen.route) },
                            // for register & activate
                            onRegister = {
                                navigateTo(AppRoute.OtpSendVerifyUI.route)
                            },

                            onBack = { navigateBack(AppRoute.UserTypeSelect.route) }
                        )

                        AppRoute.OtpSendVerifyUI.route -> OtpSendVerifyScreen(
                            onNext = { navigateTo(AppRoute.SetNewPasswordUI.route) },
                            onBack = {
                                navigateBack(AppRoute.ForgetPasswordUI.route)
                            },
                            viewModel = viewModel
                        )

                        AppRoute.UserPasswordScreen.route -> EnterPasswordScreen(
                            viewModel = viewModel,
                            onNext = {
                                navigateTo(AppRoute.OtpSendVerifyUI.route)
                            },
                            isForgetPassword = { navigateTo(AppRoute.ForgetPasswordUI.route) },
                            onBack = { navigateBack(AppRoute.UserNameScreen.route) }
                        )

                        AppRoute.CreatePasswordScreen.route -> CreateNewPasswordScreen(
                            viewModel = viewModel,
                            onNext = { navigateTo(AppRoute.EnterUserProfileScreen.route) },
                            onBack = { navigateBack(AppRoute.UserNameScreen.route) },
                            otpSendVerify = {
                                navigateTo(AppRoute.OtpSendVerifyUI.route)
                            }
                        )

                        AppRoute.EnterUserProfileScreen.route -> EnterUserScreen1(
                            viewModel = viewModel,
                            onNextTeacher = { navigateTo(AppRoute.EnterTeacherRegScreen.route) },
                            onNextSpecialEdu = { navigateTo(AppRoute.SpecialEducatorRegistration2.route) },
                            onNextProfessional = { navigateTo(AppRoute.EnterProfessionalScreen2.route) },
                            onBack = { navigateBack(AppRoute.UserNameScreen.route) }
                        )

                        AppRoute.EnterUserProfileScreen.route -> ProfessionalsRegistration2(
                            viewModel = viewModel,
                            onNext = {
                                context.startActivity(
                                    Intent(context, StudentDashboardActivity::class.java)
                                )
                                (context as? Activity)?.finish()
                            },
                            onBack = { navigateBack(AppRoute.UserNameScreen.route) }
                        )

                        AppRoute.EnterTeacherRegScreen.route -> EnterUserScreen2(
                            onNext = {
                                context.startActivity(
                                    Intent(context, StudentDashboardActivity::class.java)
                                )
                                (context as? Activity)?.finish()
                            },
                            onBack = { navigateBack(AppRoute.EnterUserProfileScreen.route) }
                        )

//                        Special Educator
                        AppRoute.SpecialEducatorRegistration2.route -> SpecialEducatorScreen2(
                            viewModel = viewModel,
                            onNext = {
                                context.startActivity(
                                    Intent(context, StudentDashboardActivity::class.java)
                                )
                                (context as? Activity)?.finish()
                            },
                            onBack = { navigateBack(AppRoute.EnterUserProfileScreen.route) }
                        )

                        AppRoute.ForgetPasswordUI.route -> ForgetPasswordScreen(
                            onNext = {
                                navigateTo(AppRoute.OtpSendVerifyUI.route)
                            },
                            onBack = { navigateBack(AppRoute.UserNameScreen.route) },
                            viewModel = viewModel
                        )

                        AppRoute.SetNewPasswordUI.route -> SetNewPasswordScreen(
                            onNext = { navigateTo(AppRoute.UserNameScreen.route) },
                            onBack = {
//                                navigateBack(AppRoute.OtpSendVerifyUI.route)
                            },
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}