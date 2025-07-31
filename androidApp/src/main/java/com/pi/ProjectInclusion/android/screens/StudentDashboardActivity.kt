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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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
import com.pi.ProjectInclusion.android.screens.screeningScreen.ScreeningOneScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.logger.Logger

class StudentDashboardActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val colors = MaterialTheme.colorScheme
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination?.route
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val bottomSheetState = rememberStandardBottomSheetState(
                skipHiddenState = false  // Allow transitioning to hidden state
            )
            val coroutineScope = rememberCoroutineScope()
            val screenHeight = LocalConfiguration.current.screenHeightDp.dp
            val halfScreenHeight = screenHeight / 2
            var sheetPeekHeight by remember { mutableStateOf(0.dp) }
            var isDialogVisible by remember { mutableStateOf(false) }
            val startDestination = AppRoute.DashboardScreen.route
            var currentRoute by remember { mutableStateOf(startDestination) }
            var isForward by remember { mutableStateOf(true) }
            val context = LocalContext.current

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
                        DrawerHeader(drawerState, onItemClick = {
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
                                    id = AppRoute.ScreeningScreen.route,
                                    title = stringResource(R.string.meeting_txt),
                                    contentDescription = "",
                                    icon = ImageVector.vectorResource(id = R.drawable.meeting_ic)
                                ),
                                MenuItem(
                                    id = AppRoute.InterventionScreen.route,
                                    title = stringResource(R.string.change_password),
                                    contentDescription = "",
                                    icon = ImageVector.vectorResource(id = R.drawable.password_ic)
                                ),
                                MenuItem(
                                    id = AppRoute.InterventionScreen.route,
                                    title = stringResource(R.string.language_txt),
                                    contentDescription = "",
                                    icon = ImageVector.vectorResource(id = R.drawable.language_ic)
                                ),
                                MenuItem(
                                    id = AppRoute.InterventionScreen.route,
                                    title = stringResource(R.string.nav_refer_txt),
                                    contentDescription = "",
                                    icon = ImageVector.vectorResource(id = R.drawable.refer_ic)
                                ),
                                MenuItem(
                                    id = AppRoute.InterventionScreen.route,
                                    title = stringResource(R.string.change_password),
                                    contentDescription = "",
                                    icon = ImageVector.vectorResource(id = R.drawable.password_ic)
                                ),
                                MenuItem(
                                    id = AppRoute.InterventionScreen.route,
                                    title = stringResource(R.string.nav_contact),
                                    contentDescription = "",
                                    icon = ImageVector.vectorResource(id = R.drawable.contact_ic)
                                ),
                                MenuItem(
                                    id = AppRoute.InterventionScreen.route,
                                    title = stringResource(R.string.nav_faq),
                                    contentDescription = "",
                                    icon = ImageVector.vectorResource(id = R.drawable.faq_ic)
                                ),
                            ), onItemClick = { itemId ->
                                onMenuItemClick(
                                    itemId,
                                    navController,
                                    this@StudentDashboardActivity,
                                    coroutineScope,
                                )
                            })
                    }
                },
            ) {
                Scaffold(bottomBar = {
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
                    if (currentDestination == AppRoute.DashboardScreen.route) {
                        AppBar(
                            onNavigationIconClick = {
                                scope.launch { drawerState.open() }
                            }, scope, drawerState, currentDestination, navController
                        )
                    }
                }, content = { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .zIndex(1f)
                    ) {
                        /*NavHost(
                            navController = navController,
                            startDestination = AppRoute.DashboardScreen.route, //AppRoute.DashboardScreen.route,
                        ) {
                            composable(AppRoute.DashboardScreen.route) {
                                DashboardScreen()
                            }
                            composable(AppRoute.ProfileScreen.route) {
                                ViewProfileScreen(navController)
                            }
                            composable(AppRoute.EditProfileScreen.route) {
                                EditProfileScreen1(navController)
                            }
                            composable(AppRoute.EditProfileScreen2.route) {
                                EditProfileScreen2(navController)
                            }

                            composable(AppRoute.CourseScreen.route) {
                                DashboardScreen()
                            }

                            // This is use for screening
                            composable(AppRoute.ScreeningScreen.route) {
                                ScreeningHomeScreen(navController)
                            }

                            composable(AppRoute.ScreeningOne.route) {
                                ScreeningOneScreen(navController)
                            }

                            composable(AppRoute.AddStudentRegister.route) {
                                AddNewStudentDetailsScreen(navController)
                            }

                            composable(AppRoute.AddNewStudentMoreDetails.route) {
                                AddNewStudentMoreDetailsScreen(navController)
                            }

                            // This is use for intervention
                            composable(AppRoute.InterventionScreen.route) {
                                InterventionHomeScreen(navController)
                            }

                            composable(AppRoute.InterventionStudentDetails.route) {
                                InterventionStudentDetailsScreen(navController)
                            }

                            composable(AppRoute.InterventionAcceptLevel.route) {
                                InterventionAcceptLevelScreen(navController)
                            }

                            composable(AppRoute.UploadedDocuments.route) {
                                UploadedDocumentsScreen(navController)
                            }

                            composable(AppRoute.TeachingPlan.route) {
                                TeachingPlanScreen(navController)
                            }
                        }*/
                        AnimatedRouteHost(
                            currentRoute = currentRoute,
                            isForward = isForward
                        ) { route ->
                            when (route) {
                                AppRoute.DashboardScreen.route -> DashboardScreen()
                                AppRoute.ProfileScreen.route -> ViewProfileScreen(
                                    onNext = { navigateTo(AppRoute.EditProfileScreen.route) },
                                    onBack = {
                                        context.startActivity(
                                            Intent(context, StudentDashboardActivity::class.java)
                                        )
                                        (context as? Activity)?.finish()
                                    }
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
                                    /*onNext = { navigateTo(AppRoute.EditProfileScreen2.route) },*/
                                    onBack = { navigateBack(AppRoute.ProfileScreen.route) }
                                )

                                AppRoute.ScreeningOne.route -> ScreeningOneScreen(
                                    onNext = { navigateTo(AppRoute.AddStudentRegister.route) },
                                    onBack = { navigateBack(AppRoute.ScreeningScreen.route) }
                                )

                                AppRoute.AddStudentRegister.route -> AddNewStudentDetailsScreen(
                                    addNewStudentMore = { navigateTo(AppRoute.AddNewStudentMoreDetails.route) },
                                    onNext = { navigateTo(AppRoute.AddStudentRegister.route) },
                                    onBack = { navigateBack(AppRoute.ScreeningScreen.route) }
                                )

                                AppRoute.AddNewStudentMoreDetails.route -> AddNewStudentMoreDetailsScreen(
                                    addStudentReg = { navigateTo(AppRoute.AddStudentRegister.route) },
                                    onNext = { navigateTo(AppRoute.ScreeningScreen.route) },
                                    onBack = { navigateBack(AppRoute.ScreeningScreen.route) }
                                )

                                // This is for intervention
                               /* composable(AppRoute.InterventionScreen.route) {
                                    InterventionHomeScreen(navController)
                                }

                                        composable(AppRoute.InterventionStudentDetails.route) {
                                    InterventionStudentDetailsScreen(navController)
                                }

                                        composable(AppRoute.InterventionAcceptLevel.route) {
                                    InterventionAcceptLevelScreen(navController)
                                }

                                        composable(AppRoute.UploadedDocuments.route) {
                                    UploadedDocumentsScreen(navController)
                                }

                                        composable(AppRoute.TeachingPlan.route) {
                                    TeachingPlanScreen(navController)
                                }*/
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
    navController: NavHostController,
    studentDashboardActivity: StudentDashboardActivity,
    coroutineScope: CoroutineScope
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
            LoggerProvider.logger.d("Screen: CertificateListActivity()")
            startActivity(
                context, Intent(context, CertificateListActivity::class.java), null
            ).apply { (context as? Activity)?.finish() }
        }
    }
}