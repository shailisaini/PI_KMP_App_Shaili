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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.android.MyApplicationTheme
import com.pi.ProjectInclusion.android.navigation.AppRoute
import com.pi.ProjectInclusion.android.screens.Profile.PrivacyPolicy
import com.pi.ProjectInclusion.android.screens.login.EnterPasswordScreen
import com.pi.ProjectInclusion.android.screens.login.EnterUserNameScreen
import com.pi.ProjectInclusion.android.screens.login.ForgetPasswordScreen
import com.pi.ProjectInclusion.android.screens.login.LanguageScreen
import com.pi.ProjectInclusion.android.screens.login.OtpSendVerifyScreen
import com.pi.ProjectInclusion.android.screens.login.UserTypeScreen
import com.pi.ProjectInclusion.android.screens.registration.CreateNewPasswordScreen
import com.pi.ProjectInclusion.android.screens.registration.EnterUserScreen1
import com.pi.ProjectInclusion.android.screens.registration.SetNewPasswordScreen
import com.pi.ProjectInclusion.android.screens.registration.TeacherRegistrationScreen
import com.pi.ProjectInclusion.android.screens.registration.professionals.ProfessionalsRegistrationScreen
import com.pi.ProjectInclusion.android.screens.registration.specialEdu.SpecialEducatorRegistrationScreen
import com.pi.ProjectInclusion.constants.ConstantVariables.TOKEN_PREF_KEY
import com.pi.ProjectInclusion.constants.ConstantVariables.USER_NAME
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import org.koin.androidx.compose.koinViewModel

class LoginNavigationScreen : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val viewModel: LoginViewModel = koinViewModel()

            var startDestination = AppRoute.LanguageSelect.route
            val backStack = remember { mutableStateListOf(startDestination) }

            var encryptedUserName = viewModel.getPrefData(USER_NAME)
            var userToken = viewModel.getPrefData(TOKEN_PREF_KEY)
            if (userToken.isNotEmpty()) {
                logger.d("token:: ${userToken}")
                startDestination = AppRoute.TeacherDashboard.route
            } else {
                startDestination
            }

            var currentRoute by remember { mutableStateOf(startDestination) }
            var isForward by remember { mutableStateOf(true) }

            fun navigateTo(route: String) {
                isForward = true

                // If we are navigating to a route that already exists in stack
                if (backStack.contains(route)) {
                    // Pop everything above it → prevents reusing old forward states
                    while (backStack.isNotEmpty() && backStack.last() != route) {
                        backStack.removeAt(backStack.lastIndex)
                    }
                } else {
                    // Add new route at the top
                    backStack.add(route)
                }

                currentRoute = route
            }

            fun navigateBack(toRoute: String? = null) {
                isForward = false

                if (toRoute != null) {
                    if (backStack.contains(toRoute)) {
                        // Pop until we reach that route
                        while (backStack.isNotEmpty() && backStack.last() != toRoute) {
                            backStack.removeAt(backStack.lastIndex)
                        }
                    } else {
                        // Reset stack to only that route
                        backStack.clear()
                        backStack.add(toRoute)
                    }
                } else {
                    if (backStack.size > 1) {
                        backStack.removeAt(backStack.lastIndex)
                    }
                }

                currentRoute = backStack.last()
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
                            navigateTo(AppRoute.UserTypeSelect.route)
                        }

                        AppRoute.UserTypeSelect.route -> UserTypeScreen(
                            viewModel = viewModel,
                            onNext = {
                                navigateTo(AppRoute.UserNameScreen.route)
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
                            onPrivacyPolicy = {
                                navigateTo(AppRoute.PrivacyPolicyScreen.route)
                            },
                            onBack = { navigateBack(AppRoute.UserTypeSelect.route) }
                        )

                        AppRoute.PrivacyPolicyScreen.route -> PrivacyPolicy(
                            onBack = { navigateBack(AppRoute.UserNameScreen.route) }
                        )

                        AppRoute.OtpSendVerifyUI.route -> OtpSendVerifyScreen(
                            onNext = { navigateTo(AppRoute.SetNewPasswordUI.route) },
                            onNextCreatePass = { navigateTo(AppRoute.CreatePasswordScreen.route) },
                            onBackUserName = { navigateBack(AppRoute.UserNameScreen.route) },
                            onBackPassword = { navigateBack(AppRoute.UserPasswordScreen.route) },
                            onBack = { navigateBack(AppRoute.ForgetPasswordUI.route) },
                            viewModel = viewModel
                        )

                        AppRoute.UserPasswordScreen.route -> EnterPasswordScreen(
                            viewModel = viewModel,
                            onNext = {
                                navigateTo(AppRoute.OtpSendVerifyUI.route)
                            },
                            isForgetPassword = { navigateTo(AppRoute.ForgetPasswordUI.route) },
                            onNextProfile = { navigateTo(AppRoute.EnterUserProfileScreen.route) },
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
                            onNextSpecialEdu = { navigateTo(AppRoute.SpecialEducatorRegistration.route) },
                            onNextProfessional = { navigateTo(AppRoute.EnterProfessionalScreen.route) },
                            onBack = { navigateBack(AppRoute.UserNameScreen.route) },
                            onBackDashboard = { navigateBack(AppRoute.DashboardScreen.route) }
                        )

                        // teacher
                        AppRoute.EnterTeacherRegScreen.route -> TeacherRegistrationScreen(
                            viewModel = viewModel,
                            onNext = {
                                context.startActivity(
                                    Intent(context, StudentDashboardActivity::class.java)
                                )
                                (context as? Activity)?.finish()
                            },
                            onBack = { navigateBack(AppRoute.EnterUserProfileScreen.route) }
                        )

                        // Special Educator
                        AppRoute.SpecialEducatorRegistration.route -> SpecialEducatorRegistrationScreen(
                            viewModel = viewModel,
                            onNext = {
                                context.startActivity(
                                    Intent(context, StudentDashboardActivity::class.java)
                                )
                                (context as? Activity)?.finish()
                            },
                            onBack = { navigateBack(AppRoute.EnterUserProfileScreen.route) }
                        )

                        // Professional
                        AppRoute.EnterProfessionalScreen.route -> ProfessionalsRegistrationScreen(
                            viewModel = viewModel,
                            onNext = {
                                context.startActivity(
                                    Intent(context, StudentDashboardActivity::class.java)
                                )
                                (context as? Activity)?.finish()
                            },
                            onBack = { navigateBack(AppRoute.UserNameScreen.route) }
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
                                navigateBack(AppRoute.ForgetPasswordUI.route)
                            },
                            viewModel = viewModel
                        )

                        AppRoute.TeacherDashboard.route -> {
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