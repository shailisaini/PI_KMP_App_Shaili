package com.pi.ProjectInclusion.android.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.compose.rememberNavController
import com.example.kmptemplate.logger.LoggerProvider
import com.pi.ProjectInclusion.Transparent
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.AnimatedRouteHost
import com.pi.ProjectInclusion.android.navigation.AppRoute
import com.pi.ProjectInclusion.android.screens.Profile.EditProfileScreen1
import com.pi.ProjectInclusion.android.screens.addStudentScreen.AddNewStudentDetailsScreen
import com.pi.ProjectInclusion.android.screens.addStudentScreen.AddNewStudentMoreDetailsScreen
import com.pi.ProjectInclusion.android.screens.Profile.EditProfileScreen2
import com.pi.ProjectInclusion.android.screens.dashboardNavActivity.CertificateListActivity
import com.pi.ProjectInclusion.android.screens.dashboardScreen.DashboardScreen
import com.pi.ProjectInclusion.android.screens.dashboardScreen.ViewProfileScreen
import com.pi.ProjectInclusion.android.screens.interventionScreens.InterventionAcceptLevelScreen
import com.pi.ProjectInclusion.android.screens.interventionScreens.InterventionHomeScreen
import com.pi.ProjectInclusion.android.screens.interventionScreens.InterventionStudentDetailsScreen
import com.pi.ProjectInclusion.android.screens.interventionScreens.TeachingPlanScreen
import com.pi.ProjectInclusion.android.screens.interventionScreens.UploadedDocumentsScreen
import com.pi.ProjectInclusion.android.screens.menu.AppBar
import com.pi.ProjectInclusion.android.screens.menu.BottomNavigationBar
import com.pi.ProjectInclusion.android.screens.menu.DrawerBody
import com.pi.ProjectInclusion.android.screens.menu.DrawerHeader
import com.pi.ProjectInclusion.android.screens.menu.MenuItem
import com.pi.ProjectInclusion.android.screens.screeningScreen.ScreeningHomeScreen
import com.pi.ProjectInclusion.android.screens.screeningScreen.ScreeningOneReportScreen
import com.pi.ProjectInclusion.android.screens.screeningScreen.ScreeningOneScreen
import com.pi.ProjectInclusion.android.screens.dashboardNavActivity.ChangePasswordActivity
import com.pi.ProjectInclusion.android.screens.dashboardNavActivity.FaqActivity
import com.pi.ProjectInclusion.android.screens.login.EnterUserNameScreen
import com.pi.ProjectInclusion.android.screens.screeningScreen.ProfilerFormPageScreen
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

class StudentDashboardActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val colors = MaterialTheme.colorScheme
            val navController = rememberNavController()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val coroutineScope = rememberCoroutineScope()
            var isDialogVisible by remember { mutableStateOf(false) }
            val startDestination = AppRoute.DashboardScreen.route
            var currentRoute by remember { mutableStateOf(startDestination) }
            var isForward by remember { mutableStateOf(true) }
            var isNotification by remember { mutableStateOf(true) }
            val context = LocalContext.current
            val viewModel: LoginViewModel = koinViewModel()

            fun navigateTo(route: String) {
                isForward = true
                currentRoute = route
            }

            fun navigateBack(toRoute: String? = null) {
                isForward = false
                currentRoute = toRoute ?: AppRoute.LanguageSelect.route
            }

            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(end = 30.dp)
                            .background(Transparent)
                    ) {
                        DrawerHeader(
                            drawerState,
                            onItemClick = {
                                scope.launch {
                                    drawerState.close()
                                    navigateTo(AppRoute.ProfileScreen.route)
                                }
                            })
                        DrawerBody(
                            // List of Navigation Drawer
                            items = listOf(
                                MenuItem(
                                    id = AppRoute.CertificateScreen.route,
                                    title = stringResource(R.string.certificate_txt),
                                    contentDescription = "",
                                    icon = ImageVector.vectorResource(id = R.drawable.certificate_ic)
                                ),
                                MenuItem(
                                    id = AppRoute.JoinMeetingScreen.route,
                                    title = stringResource(R.string.meeting_txt),
                                    contentDescription = "",
                                    icon = ImageVector.vectorResource(id = R.drawable.meeting_ic)
                                ),
                                MenuItem(
                                    id = AppRoute.ChangePasswordScreen.route,
                                    title = stringResource(R.string.change_password),
                                    contentDescription = "",
                                    icon = ImageVector.vectorResource(id = R.drawable.password_ic)
                                ),
                                MenuItem(
                                    id = AppRoute.LanguageScreen.route,
                                    title = stringResource(R.string.language_txt),
                                    contentDescription = "",
                                    icon = ImageVector.vectorResource(id = R.drawable.language_ic)
                                ),
                                MenuItem(
                                    id = AppRoute.ReferScreen.route,
                                    title = stringResource(R.string.nav_refer_txt),
                                    contentDescription = "",
                                    icon = ImageVector.vectorResource(id = R.drawable.refer_ic)
                                ),
                                MenuItem(
                                    id = AppRoute.ContactUsScreen.route,
                                    title = stringResource(R.string.nav_contact),
                                    contentDescription = "",
                                    icon = ImageVector.vectorResource(id = R.drawable.contact_ic)
                                ),
                                MenuItem(
                                    id = AppRoute.FaqScreen.route,
                                    title = stringResource(R.string.nav_faq),
                                    contentDescription = "",
                                    icon = ImageVector.vectorResource(id = R.drawable.faq_ic)
                                ),
                            ), onItemClick = { itemId ->
                                onMenuItemClick(
                                    itemId,
                                    this@StudentDashboardActivity,
                                    coroutineScope,
                                )
                            })
                    }
                },
            ) {
                Scaffold(
                    contentWindowInsets = WindowInsets(0, 0, 0, 0),
                    bottomBar = {
                        if (currentRoute in listOf(
                                AppRoute.DashboardScreen.route,
                                AppRoute.CourseScreen.route,
                                AppRoute.ScreeningScreen.route,
                                AppRoute.InterventionScreen.route
                            )
                        ) {
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .zIndex(1f)
                                    .shadow(elevation = 8.dp)
                            ) {
                                BottomNavigationBar(
                                    currentDestination = currentRoute,
                                    onNavigateTo = { route -> navigateTo(route) }
                                )
                            }
                        }
                    }, topBar = {
                        if (currentRoute == AppRoute.DashboardScreen.route) {
                            AppBar(
                                isNotification,
                                onNavigationIconClick = {
                                    scope.launch { drawerState.open() }
                                },
                                currentRoute = currentRoute
                            )
                        }
                    }, content = { innerPadding ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            AnimatedRouteHost(
                                currentRoute = currentRoute,
                                isForward = isForward
                            ) { route ->
                                when (route) {
                                    AppRoute.DashboardScreen.route -> DashboardScreen()
                                    AppRoute.ProfileScreen.route -> ViewProfileScreen(
                                        onNext = { navigateTo(AppRoute.EditProfileScreen.route) },
                                        onBackLogin = { navigateTo(AppRoute.UserNameScreen.route) },
                                        onBack = {
                                            context.startActivity(
                                                Intent(
                                                    context,
                                                    StudentDashboardActivity::class.java
                                                )
                                            )
                                            (context as? Activity)?.finish()
                                        }
                                    )
                                    // for logout & delete account we need to route to login screen
                                    AppRoute.UserNameScreen.route -> EnterUserNameScreen(
                                        viewModel = viewModel,
                                        onNext = { navigateTo(AppRoute.UserPasswordScreen.route) },
                                        onRegister = {
                                            val mobNo = viewModel.mobileNumber
                                            navigateTo(AppRoute.OtpSendVerifyUI.withArgs(mobNo.toString())) },
                                        onBack = { navigateBack(AppRoute.UserTypeSelect.route) }
                                    )

                                    AppRoute.EditProfileScreen.route -> EditProfileScreen1(
                                        onNext = { navigateTo(AppRoute.EditProfileScreen2.route) },
                                        onBack = { navigateBack(AppRoute.ProfileScreen.route) })

                                    AppRoute.EditProfileScreen2.route -> EditProfileScreen2(
                                        onNext = {
                                            context.startActivity(
                                                Intent(
                                                    context,
                                                    StudentDashboardActivity::class.java
                                                )
                                            )
                                            (context as? Activity)?.finish()
                                        },
                                        onBack = { navigateBack(AppRoute.ProfileScreen.route) })

                                    // Screening
                                    AppRoute.ScreeningScreen.route -> ScreeningHomeScreen(
                                        addStudent = { navigateTo(AppRoute.AddStudentRegister.route) },
                                        screeningOne = { navigateTo(AppRoute.ScreeningOne.route) },
                                        onBack = { navigateBack(AppRoute.ProfileScreen.route) }
                                    )

                                    AppRoute.ScreeningOne.route -> ScreeningOneScreen(
                                        onNext = { navigateTo(AppRoute.ScreeningOneReport.route) },
                                        onBack = { navigateBack(AppRoute.ScreeningScreen.route) }
                                    )

                                    AppRoute.ScreeningOneReport.route -> ScreeningOneReportScreen(
                                        showReportScreen = true,
                                        onNext = { navigateTo(AppRoute.ProfilerFormPage.route) }, // this is change according to condition
                                        onBack = { navigateBack(AppRoute.ScreeningScreen.route) }
                                    )

                                    AppRoute.ProfilerFormPage.route -> ProfilerFormPageScreen(
                                        onNext = { }, // this is change according to condition
                                        onBack = { navigateBack(AppRoute.ScreeningScreen.route) }
                                    )

                                    AppRoute.AddStudentRegister.route -> AddNewStudentDetailsScreen(
                                        onNext = { navigateTo(AppRoute.AddNewStudentMoreDetails.route) },
                                        onBack = { navigateBack(AppRoute.ScreeningScreen.route) }
                                    )

                                    AppRoute.AddNewStudentMoreDetails.route -> AddNewStudentMoreDetailsScreen(
                                        onNext = { navigateTo(AppRoute.ScreeningScreen.route) },
                                        onBack = { navigateBack(AppRoute.AddStudentRegister.route) }
                                    )

                                    // This is for intervention
                                    AppRoute.InterventionScreen.route -> InterventionHomeScreen(
                                        onNext = { navigateTo(AppRoute.InterventionStudentDetails.route) },
                                        onBack = { navigateBack(AppRoute.DashboardScreen.route) }
                                    )

                                    AppRoute.InterventionStudentDetails.route -> InterventionStudentDetailsScreen(
                                        uploadedDocumentScreen = { navigateTo(AppRoute.UploadedDocuments.route) },
                                        acceptLevelScreen = { navigateTo(AppRoute.InterventionAcceptLevel.route) },
                                        onBack = { navigateBack(AppRoute.InterventionScreen.route) }
                                    )

                                    AppRoute.UploadedDocuments.route -> UploadedDocumentsScreen(
                                        onBack = { navigateBack(AppRoute.InterventionStudentDetails.route) }
                                    )

                                    AppRoute.InterventionAcceptLevel.route -> InterventionAcceptLevelScreen(
                                        onNext = { navigateTo(AppRoute.TeachingPlan.route) },
                                        onBack = { navigateBack(AppRoute.InterventionStudentDetails.route) })

                                    AppRoute.TeachingPlan.route -> TeachingPlanScreen(onBack = {
                                        navigateBack(
                                            AppRoute.InterventionAcceptLevel.route
                                        )
                                    })
                                }
                            }
                        }
                    })
            }
        }
    }
}

// Function to handle item clicks
@OptIn(ExperimentalMaterial3Api::class)
fun onMenuItemClick(
    itemId: String,
    studentDashboardActivity: StudentDashboardActivity,
    coroutineScope: CoroutineScope,
) {
    // Handle the click event here
    val context = studentDashboardActivity as Context
    when (itemId) {
        AppRoute.CourseScreen.route -> {
            startActivity(
                context, Intent(context, StudentDashboardActivity::class.java), null
            ).apply { (context as? Activity)?.finish() }
        }

        AppRoute.CertificateScreen.route -> {
            startActivity(
                context, Intent(context, CertificateListActivity::class.java), null
            ).apply { (context as? Activity)?.finish() }
        }

        AppRoute.JoinMeetingScreen.route -> {
            LoggerProvider.logger.d("Screen: JoinMeetingActivity()")
        }

        AppRoute.ChangePasswordScreen.route -> {
            startActivity(
                context, Intent(context, ChangePasswordActivity::class.java), null
            ).apply { (context as? Activity)?.finish() }
        }

        AppRoute.LanguageScreen.route -> {
            LoggerProvider.logger.d("Screen: LanguageChangeActivity()")
        }

        AppRoute.ReferScreen.route -> {
            LoggerProvider.logger.d("Screen: Refer dialog open screen")
        }

        AppRoute.ContactUsScreen.route -> {
            LoggerProvider.logger.d("Screen: Contact us dialog screen")
        }

        AppRoute.FaqScreen.route -> {
            startActivity(
                context, Intent(context, FaqActivity::class.java), null
            ).apply { (context as? Activity)?.finish() }
        }
    }
}