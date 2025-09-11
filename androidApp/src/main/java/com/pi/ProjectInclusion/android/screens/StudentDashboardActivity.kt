package com.pi.ProjectInclusion.android.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.BannerColor03
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.BorderBlue
import com.pi.ProjectInclusion.DARK_BODY_TEXT
import com.pi.ProjectInclusion.DARK_TITLE_TEXT
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.Dark_02
import com.pi.ProjectInclusion.Dark_Selected_BG
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.GrayLight02
import com.pi.ProjectInclusion.PRIMARY_AURO_BLUE
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.PrimaryBlueLt1
import com.pi.ProjectInclusion.Transparent
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.AnimatedRouteHost
import com.pi.ProjectInclusion.android.common_UI.BtnUi
import com.pi.ProjectInclusion.android.navigation.AppRoute
import com.pi.ProjectInclusion.android.screens.Profile.EditProfileScreen1
import com.pi.ProjectInclusion.android.screens.Profile.EditProfileScreen2
import com.pi.ProjectInclusion.android.screens.Profile.TrackRequestScreen
import com.pi.ProjectInclusion.android.screens.Profile.professionals.ProfessionalsEditProfile
import com.pi.ProjectInclusion.android.screens.Profile.specialEdu.SpecialEducatorEditProfile
import com.pi.ProjectInclusion.android.screens.addStudentScreen.AddNewStudentDetailsScreen
import com.pi.ProjectInclusion.android.screens.addStudentScreen.AddNewStudentMoreDetailsScreen
import com.pi.ProjectInclusion.android.screens.Profile.PrivacyPolicy
import com.pi.ProjectInclusion.android.screens.dashboardNavActivity.CertificateListActivity
import com.pi.ProjectInclusion.android.screens.dashboardNavActivity.ChangePasswordActivity
import com.pi.ProjectInclusion.android.screens.dashboardNavActivity.FaqActivity
import com.pi.ProjectInclusion.android.screens.dashboardNavActivity.ZoomMeetingActivity
import com.pi.ProjectInclusion.android.screens.dashboardScreen.DashboardScreen
import com.pi.ProjectInclusion.android.screens.dashboardScreen.LMSCourseHomeScreen
import com.pi.ProjectInclusion.android.screens.dashboardScreen.ViewProfileScreen
import com.pi.ProjectInclusion.android.screens.interventionScreens.InterventionAcceptLevelScreen
import com.pi.ProjectInclusion.android.screens.interventionScreens.InterventionHomeScreen
import com.pi.ProjectInclusion.android.screens.interventionScreens.InterventionStudentDetailsScreen
import com.pi.ProjectInclusion.android.screens.interventionScreens.TeachingPlanScreen
import com.pi.ProjectInclusion.android.screens.interventionScreens.UploadedDocumentsScreen
import com.pi.ProjectInclusion.android.screens.login.EnterUserNameScreen
import com.pi.ProjectInclusion.android.screens.menu.AppBar
import com.pi.ProjectInclusion.android.screens.menu.BottomNavigationBar
import com.pi.ProjectInclusion.android.screens.menu.DrawerBody
import com.pi.ProjectInclusion.android.screens.menu.DrawerHeader
import com.pi.ProjectInclusion.android.screens.menu.MenuItem
import com.pi.ProjectInclusion.android.screens.notification.NotificationScreen
import com.pi.ProjectInclusion.android.screens.screeningScreen.AdvanceScreeningScreen
import com.pi.ProjectInclusion.android.screens.screeningScreen.ProfilerFormPageScreen
import com.pi.ProjectInclusion.android.screens.screeningScreen.ReportAdvanceScreen
import com.pi.ProjectInclusion.android.screens.screeningScreen.ScreeningHomeScreen
import com.pi.ProjectInclusion.android.screens.screeningScreen.ScreeningOneReportScreen
import com.pi.ProjectInclusion.android.screens.screeningScreen.ScreeningOneScreen
import com.pi.ProjectInclusion.android.screens.screeningScreen.ViewScreeningProfileDetailsScreen
import com.pi.ProjectInclusion.android.utils.fontBold
import com.pi.ProjectInclusion.android.utils.fontRegular
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.CommonFunction.NoDataFound
import com.pi.ProjectInclusion.constants.CommonFunction.ShowError
import com.pi.ProjectInclusion.constants.CommonFunction.isNetworkAvailable
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION
import com.pi.ProjectInclusion.constants.ConstantVariables.SELECTED_LANGUAGE_ID
import com.pi.ProjectInclusion.constants.ConstantVariables.TOKEN_PREF_KEY
import com.pi.ProjectInclusion.constants.ConstantVariables.USER_NAME
import com.pi.ProjectInclusion.constants.CustomDialog
import com.pi.ProjectInclusion.contactUsTxt
import com.pi.ProjectInclusion.data.model.authenticationModel.response.GetLanguageListResponse
import com.pi.ProjectInclusion.data.model.profileModel.response.ViewProfileResponse
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import androidx.core.net.toUri
import com.pi.ProjectInclusion.android.common_UI.LogoutDialog
import com.pi.ProjectInclusion.android.screens.registration.EnterUserScreen1

class StudentDashboardActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

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
            var strToken = viewModel.getPrefData(TOKEN_PREF_KEY)
            var logOutSheet by remember { mutableStateOf(false) }
            val backStack = remember { mutableStateListOf(startDestination) }

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

            /*fun navigateBack(toRoute: String? = null) {
                isForward = false
                currentRoute = toRoute ?: AppRoute.LanguageSelect.route
            }*/


            val viewProfile by viewModel.viewUserProfileResponse.collectAsStateWithLifecycle()
            var encryptedUserName = viewModel.getPrefData(USER_NAME)
            logger.d("Profile details on dashboard page for header in drawer :- $encryptedUserName")
            var profileData by remember { mutableStateOf<ViewProfileResponse.ProfileResponse?>(null) }

            LaunchedEffect(Unit) {
                viewModel.getUserProfileViewModel(strToken, encryptedUserName)
            }

            LaunchedEffect(viewProfile) {
                when {
                    viewProfile.isLoading -> {
                        isDialogVisible = true
                    }

                    viewProfile.error.isNotEmpty() -> {
                        logger.d("viewProfileData: ${viewProfile.success}")
                        isDialogVisible = false
                    }

                    viewProfile.success != null -> {
                        logger.d("Profile Data:- ${viewProfile.success}")
                        if (viewProfile.success!!.status == true) {
                            profileData = viewProfile.success!!.response
                            logger.d("Profile Data details : ${viewProfile.success}")
                        } else {
                            context.toast(viewProfile.success!!.message.toString())
                        }
                        isDialogVisible = false
                    }
                }
            }

            ModalNavigationDrawer(
                drawerState = drawerState,
//                gesturesEnabled = false, // prevents swipe
                drawerContent = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(end = 30.dp)
                            .background(Transparent)
                    ) {

                        val scope = rememberCoroutineScope()
                        var isBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
                        var isLanguageBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
                        var isBottomSheetReferVisible by rememberSaveable { mutableStateOf(false) }
                        val sheetState = rememberModalBottomSheetState(
                            skipPartiallyExpanded = true, /*confirmValueChange = { it != SheetValue.Hidden }*/
                        )
                        var logOutSheet by remember { mutableStateOf(false) }

                        BottomSheetContactUsScreen(
                            isBottomSheetVisible = isBottomSheetVisible,
                            sheetState = sheetState,
                            onDismiss = {
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    isBottomSheetVisible = false
                                }
                            })

                        BottomSheetLanguageScreen(
                            isBottomSheetVisible = isLanguageBottomSheetVisible,
                            sheetState = sheetState,
                            onDismiss = {
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    isLanguageBottomSheetVisible = false
                                }
                            },
                            viewModel
                        )

                        BottomSheetReferUIScreen(
                            isBottomSheetVisible = isBottomSheetReferVisible,
                            sheetState = sheetState,
                            onDismiss = {
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    isBottomSheetReferVisible = false
                                }
                            })

                        if (logOutSheet) {
                            LogoutDialog(onDismiss = { logOutSheet = false }, onClick = {
                                logOutSheet = false
                                viewModel.clearPref()
                                context.startActivity(
                                    Intent(
                                        context,
                                        LoginNavigationScreen::class.java
                                    )
                                )
                                (context as? Activity)?.finish()
                            })
                        }

                        DrawerHeader(
                            drawerState, onItemClick = {
                                scope.launch {
                                    drawerState.close()
                                    navigateTo(AppRoute.ProfileScreen.route)
                                }
                            },
                            onEditClick = {
                                scope.launch {
                                    drawerState.close()
                                    navigateTo(AppRoute.EditProfileScreen.route)
                                }
                            }, profileData
                        )

                        DrawerBody(
                            // List of Navigation Drawer
                            items = listOf(
                                MenuItem(
                                    id = AppRoute.CertificateScreen.route,
                                    title = stringResource(R.string.certificate_txt),
                                    contentDescription = IMG_DESCRIPTION,
                                    icon = ImageVector.vectorResource(id = R.drawable.certificate_ic)
                                ),
                                MenuItem(
                                    id = AppRoute.JoinMeetingScreen.route,
                                    title = stringResource(R.string.meeting_txt),
                                    contentDescription = IMG_DESCRIPTION,
                                    icon = ImageVector.vectorResource(id = R.drawable.meeting_ic)
                                ),
                                MenuItem(
                                    id = AppRoute.ChangePasswordScreen.route,
                                    title = stringResource(R.string.change_password),
                                    contentDescription = IMG_DESCRIPTION,
                                    icon = ImageVector.vectorResource(id = R.drawable.password_ic)
                                ),
                                MenuItem(
                                    id = AppRoute.LanguageScreen.route,
                                    title = stringResource(R.string.language_txt),
                                    contentDescription = IMG_DESCRIPTION,
                                    icon = ImageVector.vectorResource(id = R.drawable.language_ic)
                                ),
                                MenuItem(
                                    id = AppRoute.ReferScreen.route,
                                    title = stringResource(R.string.nav_refer_txt),
                                    contentDescription = IMG_DESCRIPTION,
                                    icon = ImageVector.vectorResource(id = R.drawable.refer_ic)
                                ),
                                MenuItem(
                                    id = AppRoute.ContactUsScreen.route,
                                    title = stringResource(R.string.nav_contact),
                                    contentDescription = IMG_DESCRIPTION,
                                    icon = ImageVector.vectorResource(id = R.drawable.contact_ic)
                                ),
                                MenuItem(
                                    id = AppRoute.FaqScreen.route,
                                    title = stringResource(R.string.nav_faq),
                                    contentDescription = IMG_DESCRIPTION,
                                    icon = ImageVector.vectorResource(id = R.drawable.faq_ic)
                                ),
                            ), onItemClick = { itemId ->
                                onMenuItemClick(
                                    itemId,
                                    this@StudentDashboardActivity,
                                    coroutineScope,
                                    openContactUs = { isBottomSheetVisible = true },
                                    openLanguage = { isLanguageBottomSheetVisible = true },
                                    openReferUI = { isBottomSheetReferVisible = true },
                                    drawerState
                                )
                            },
                            onLogout = {
                                logOutSheet = true
                            }
                        )

                        if (logOutSheet) {
                            LogoutDialog(
                                onDismiss = { logOutSheet = false },
                                onClick = {
                                    logOutSheet = false
                                    viewModel.clearPref()
                                    val intent =
                                        Intent(context, LoginNavigationScreen::class.java).apply {
                                            flags =
                                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        }
                                    context.startActivity(intent)
                                    (context as? Activity)?.finish()
                                }
                            )
                        }
                    }
                },
            ) {
                Scaffold(contentWindowInsets = WindowInsets(0, 0, 0, 0), bottomBar = {
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
                                onNavigateTo = { route -> navigateTo(route) })
                        }
                    }
                }, topBar = {
                    if (currentRoute == AppRoute.DashboardScreen.route) {
                        AppBar(
                            isNotification, onNavigationIconClick = {
                                scope.launch { drawerState.open() }
                            }, currentRoute = currentRoute
                        )
                    } else if (currentRoute == AppRoute.ScreeningScreen.route) {
                        AppBar(
                            isNotification, onNavigationIconClick = {
                                scope.launch { drawerState.open() }
                            }, currentRoute = currentRoute
                        )
                    } else if (currentRoute == AppRoute.InterventionScreen.route) {
                        AppBar(
                            isNotification, onNavigationIconClick = {
                                scope.launch { drawerState.open() }
                            }, currentRoute = currentRoute
                        )
                    }
                }, content = { innerPadding ->
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        AnimatedRouteHost(
                            currentRoute = currentRoute, isForward = isForward
                        ) { route ->
                            key(route) { ////  force state reset per route
                                when (route) {
                                    AppRoute.DashboardScreen.route -> DashboardScreen(
                                        onProfile = { navigateTo(AppRoute.EnterUserProfileScreen.route) },
                                    )

                                    AppRoute.EnterUserProfileScreen.route -> EnterUserScreen1(
                                        viewModel = viewModel,
                                        onNextTeacher = { navigateTo(AppRoute.EnterTeacherRegScreen.route) },
                                        onNextSpecialEdu = { navigateTo(AppRoute.SpecialEducatorRegistration.route) },
                                        onNextProfessional = { navigateTo(AppRoute.EnterProfessionalScreen.route) },
                                        onBack = { navigateBack(AppRoute.UserNameScreen.route) },
                                        onBackDashboard = { navigateBack(AppRoute.DashboardScreen.route) }
                                    )

                                    AppRoute.CourseScreen.route -> LMSCourseHomeScreen()

                                    AppRoute.ProfileScreen.route -> ViewProfileScreen(
                                        viewModel = viewModel,
                                        onNext = { navigateTo(AppRoute.EditProfileScreen.route) },
                                        onTrackRequest = { navigateTo(AppRoute.TrackRequestScreen.route) },
                                        onBackLogin = { navigateTo(AppRoute.UserNameScreen.route) },
                                        onBack = {
                                            context.startActivity(
                                                Intent(
                                                    context, StudentDashboardActivity::class.java
                                                )
                                            )
                                            (context as? Activity)?.finish()
                                        })
                                    // for logout & delete account we need to route to login screen
                                    AppRoute.UserNameScreen.route -> EnterUserNameScreen(
                                        viewModel = viewModel,
                                        onNext = { navigateTo(AppRoute.UserPasswordScreen.route) },
                                        onRegister = {
                                            navigateTo(AppRoute.OtpSendVerifyUI.route)
                                        },
                                        onPrivacyPolicy = {
                                            navigateTo(AppRoute.PrivacyPolicyScreen.route)
                                        },
                                        onBack = { navigateBack(AppRoute.UserTypeSelect.route) })

                                    AppRoute.PrivacyPolicyScreen.route -> PrivacyPolicy(
                                        onBack = { navigateBack(AppRoute.UserNameScreen.route) }
                                    )

                                    AppRoute.EditProfileScreen.route -> EditProfileScreen1(
                                        loginViewModel = viewModel,
                                        onNextTeacher = { navigateTo(AppRoute.EditTeacherProfileScreen.route) },
                                        onNextProfessional = { navigateTo(AppRoute.EditProfessionalEditProfile.route) },
                                        onNextSpecialEducator = { navigateTo(AppRoute.EditSpeEduEditProfile.route) },
                                        onBack = { navigateBack(AppRoute.ProfileScreen.route) })

                                    AppRoute.EditTeacherProfileScreen.route -> EditProfileScreen2(
                                        loginViewModel = viewModel,
                                        onNext = {
                                            context.startActivity(
                                                Intent(
                                                    context, StudentDashboardActivity::class.java
                                                )
                                            )
                                            (context as? Activity)?.finish()
                                        },
                                    )
//                                        onBack = { navigateBack(AppRoute.ProfileScreen.route) })

                                    AppRoute.EditProfessionalEditProfile.route -> ProfessionalsEditProfile(
                                        loginViewModel = viewModel,
                                        onNext = {
                                            context.startActivity(
                                                Intent(
                                                    context, StudentDashboardActivity::class.java
                                                )
                                            )
                                            (context as? Activity)?.finish()
                                        },
                                        onBack = { navigateBack(AppRoute.ProfileScreen.route) })

                                    AppRoute.EditSpeEduEditProfile.route -> SpecialEducatorEditProfile(
                                        loginViewModel = viewModel,
                                        onNext = {
                                            context.startActivity(
                                                Intent(
                                                    context, StudentDashboardActivity::class.java
                                                )
                                            )
                                            (context as? Activity)?.finish()
                                        },
                                        onBack = { navigateBack(AppRoute.ProfileScreen.route) })

                                    // Screening
                                    AppRoute.ScreeningScreen.route -> ScreeningHomeScreen(
                                        addStudent = { navigateTo(AppRoute.AddStudentRegister.route) },
                                        screeningOne = { navigateTo(AppRoute.ScreeningOne.route) },
                                        profilerForm = { navigateTo(AppRoute.ProfilerFormPage.route) },
                                        advanceScreening = { navigateTo(AppRoute.AdvanceScreening.route) },
                                        onBack = { navigateBack(AppRoute.DashboardScreen.route) })

                                    AppRoute.ScreeningOne.route -> ScreeningOneScreen(
                                        onNext = { navigateTo(AppRoute.ScreeningOneReport.route) },
                                        onBack = { navigateBack(AppRoute.ScreeningScreen.route) })

                                    AppRoute.TrackRequestScreen.route -> {
                                        TrackRequestScreen(
                                            onNext = {},
                                            onBack = { navigateBack(AppRoute.ProfileScreen.route) })
                                    }

                                    AppRoute.NotificationScreen.route -> {
                                        NotificationScreen(
                                            onNext = {},
                                            onBack = { navigateBack(AppRoute.DashboardScreen.route) })
                                    }

                                    AppRoute.ScreeningOneReport.route -> {
                                        ScreeningOneReportScreen(
                                            showReportScreen = true,
                                            onNext = { navigateTo(AppRoute.ProfilerFormPage.route) },
                                            onNextCongratulate = { navigateTo(AppRoute.AdvanceScreeningReport.route) },
                                            onBack = { navigateBack(AppRoute.ScreeningScreen.route) })
                                    }

                                    AppRoute.ProfilerFormPage.route -> {
                                        ProfilerFormPageScreen(
                                            onNext = { navigateTo(AppRoute.ProfilerFormPage.route) },
                                            onBack = { navigateBack(AppRoute.ScreeningScreen.route) })
                                    }

                                    AppRoute.AdvanceScreening.route -> AdvanceScreeningScreen(
                                        onNext = { navigateTo(AppRoute.AdvanceScreeningReport.route) },
                                        onBack = { navigateBack(AppRoute.ScreeningScreen.route) })

                                    AppRoute.AdvanceScreeningReport.route -> {
                                        ReportAdvanceScreen(
                                            onNext = { navigateTo(AppRoute.InterventionScreen.route) },
                                            onViewProfileDetails = { navigateTo(AppRoute.ViewScreeningProfileDetails.route) },
                                            onBack = { navigateBack(AppRoute.ScreeningScreen.route) })
                                    }

                                    AppRoute.ViewScreeningProfileDetails.route -> {
                                        ViewScreeningProfileDetailsScreen(
                                            onNext = { navigateTo(AppRoute.ScreeningScreen.route) },
                                            onBack = { navigateBack(AppRoute.ScreeningScreen.route) })
                                    }

                                    AppRoute.AddStudentRegister.route -> AddNewStudentDetailsScreen(
                                        onNext = { navigateTo(AppRoute.AddNewStudentMoreDetails.route) },
                                        onBack = { navigateBack(AppRoute.ScreeningScreen.route) })

                                    AppRoute.AddNewStudentMoreDetails.route -> AddNewStudentMoreDetailsScreen(
                                        onNext = { navigateTo(AppRoute.ScreeningScreen.route) },
                                        onBack = { navigateBack(AppRoute.AddStudentRegister.route) })

                                    // This is for intervention
                                    AppRoute.InterventionScreen.route -> InterventionHomeScreen(
                                        onNext = { navigateTo(AppRoute.InterventionStudentDetails.route) },
                                        onBack = { navigateBack(AppRoute.DashboardScreen.route) })

                                    AppRoute.InterventionStudentDetails.route -> InterventionStudentDetailsScreen(
                                        uploadedDocumentScreen = { navigateTo(AppRoute.UploadedDocuments.route) },
                                        acceptLevelScreen = { navigateTo(AppRoute.InterventionAcceptLevel.route) },
                                        onBack = { navigateBack(AppRoute.InterventionScreen.route) })

                                    AppRoute.UploadedDocuments.route -> UploadedDocumentsScreen(
                                        onBack = { navigateBack(AppRoute.InterventionStudentDetails.route) })

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
    openContactUs: () -> Unit,
    openLanguage: () -> Unit,
    openReferUI: () -> Unit,
    drawerState: DrawerState,
) {
    // Handle the click event here
    val context = studentDashboardActivity as Context
    when (itemId) {
        AppRoute.CertificateScreen.route -> {
            startActivity(
                context, Intent(context, CertificateListActivity::class.java), null
            ).apply { (context as? Activity)?.finish() }
        }

        AppRoute.JoinMeetingScreen.route -> {
            startActivity(
                context, Intent(context, ZoomMeetingActivity::class.java), null
            ).apply { (context as? Activity)?.finish() }
        }

        AppRoute.ChangePasswordScreen.route -> {
            startActivity(
                context, Intent(context, ChangePasswordActivity::class.java), null
            ).apply { (context as? Activity)?.finish() }
        }

        AppRoute.LanguageScreen.route -> {
            coroutineScope.launch { drawerState.close() }
            openLanguage()
        }

        AppRoute.ReferScreen.route -> {
            coroutineScope.launch { drawerState.close() }
            openReferUI()
        }

        AppRoute.ContactUsScreen.route -> {
            coroutineScope.launch { drawerState.close() }
            openContactUs()
        }

        AppRoute.FaqScreen.route -> {
            startActivity(
                context, Intent(context, FaqActivity::class.java), null
            ).apply { (context as? Activity)?.finish() }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContactUsScreen(
    isBottomSheetVisible: Boolean,
    sheetState: SheetState,
    onDismiss: () -> Unit,
) {

    val context: Context = LocalContext.current

    if (isBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            containerColor = if (isSystemInDarkTheme()) {
                Dark_01
            } else {
                Color.White
            },
            contentColor = MaterialTheme.colorScheme.onSurface,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            dragHandle = null,
            scrimColor = if (isSystemInDarkTheme()) {
                DARK_TITLE_TEXT.copy(alpha = 0.5f)
            } else {
                Color.Black.copy(alpha = 0.5f)
            },
            windowInsets = WindowInsets.ime
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(vertical = 15.dp, horizontal = 15.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "", style = MaterialTheme.typography.headlineLarge.copy(
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black
                                }, fontSize = 18.sp, fontFamily = FontFamily(
                                    Font(R.font.roboto_bold, FontWeight.Bold)
                                ), textAlign = TextAlign.Start
                            )
                        )

                        Image(
                            painter = painterResource(R.drawable.line),
                            contentDescription = IMG_DESCRIPTION,
                            modifier = Modifier
                                .wrapContentWidth()
                                .align(Alignment.CenterVertically)
                                .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                                .clickable {
                                    onDismiss.invoke()
                                }
                                .clip(RoundedCornerShape(100.dp))
                                .border(
                                    width = 2.dp, color = if (isSystemInDarkTheme()) {
                                        DARK_TITLE_TEXT
                                    } else {
                                        Gray
                                    }, shape = CircleShape
                                ))

                        Text(
                            text = "", style = MaterialTheme.typography.headlineLarge.copy(
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black
                                }, fontSize = 18.sp, fontFamily = FontFamily(
                                    Font(R.font.roboto_bold, FontWeight.Bold)
                                ), textAlign = TextAlign.Start
                            )
                        )
                    }

                    Text(
                        text = stringResource(id = R.string.txt_Contact_us),
                        color = if (isSystemInDarkTheme()) {
                            DARK_BODY_TEXT
                        } else {
                            Gray
                        },
                        fontSize = 12.sp,
                        fontFamily = FontFamily(
                            Font(R.font.roboto_regular, FontWeight.Normal)
                        ),
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 32.dp, start = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.CenterVertically)
                                .padding(start = 8.dp, end = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            IconButton(onClick = {
//                                val phoneNumber = stringResource(R.string.whatsapp_number)
                                val phoneNumber = "+919355902926"
                                val url = "https://wa.me/${phoneNumber.replace("+", "")}"

                                try {
                                    val intent = Intent(Intent.ACTION_VIEW).apply {
                                        data = Uri.parse(url)
                                        setPackage("com.whatsapp")
                                    }
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    val fallbackIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                    context.startActivity(fallbackIntent)
                                }
                            }) {
                                Image(
                                    painter = painterResource(id = R.drawable.whats_app_icon),
                                    contentDescription = "WhatsApp",
                                    modifier = Modifier
                                        .size(75.dp)
                                        .align(Alignment.CenterHorizontally)
                                )
                            }

                            Text(
                                text = "Whatsapp",
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    color = if (isSystemInDarkTheme()) {
                                        DARK_TITLE_TEXT
                                    } else {
                                        contactUsTxt
                                    }, fontSize = 14.sp, fontFamily = FontFamily(
                                        Font(R.font.roboto_medium, FontWeight.Medium)
                                    ), textAlign = TextAlign.Start
                                )
                            )
                        }

                        Column(
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.CenterVertically)
                                .padding(start = 8.dp, end = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            IconButton(onClick = {
//                                val emailid = stringResource(R.string.email_id)
                                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                                    data = Uri.parse("mailto:support.pi@aurosociety.org")
                                    putExtra(Intent.EXTRA_SUBJECT, "Support Request")
                                    putExtra(Intent.EXTRA_TEXT, "Hi Team,\n\n")
                                }

                                try {
                                    context.startActivity(emailIntent)
                                } catch (e: Exception) {
                                    // fallback if no email app
                                    val browserIntent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("mailto:support.pi@aurosociety.org")
                                    )
                                    context.startActivity(browserIntent)
                                }
                            }) {
                                Image(
                                    painter = painterResource(id = R.drawable.gmail_icon),
                                    contentDescription = "Gmail",
                                    modifier = Modifier
                                        .size(75.dp)
                                        .align(Alignment.CenterHorizontally)
                                )
                            }

                            Text(
                                text = "Gmail", style = MaterialTheme.typography.headlineLarge.copy(
                                    color = if (isSystemInDarkTheme()) {
                                        DARK_TITLE_TEXT
                                    } else {
                                        contactUsTxt
                                    }, fontSize = 14.sp, fontFamily = FontFamily(
                                        Font(R.font.roboto_medium, FontWeight.Medium)
                                    ), textAlign = TextAlign.Start
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetLanguageScreen(
    isBottomSheetVisible: Boolean,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    viewModel: LoginViewModel,
) {

    val context: Context = LocalContext.current
    val errColor = PrimaryBlue
    val scrollState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    var noData = stringResource(R.string.txt_oops_no_data_found)
    var isInternetAvailable by remember { mutableStateOf(true) }
    val internetMessage = stringResource(R.string.txt_oops_no_internet)
    var noDataMessage by remember { mutableStateOf(noData) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    val selectedLanguageId = remember { mutableStateOf<String?>(null) }
    val selectedLanguageName = remember { mutableStateOf<String?>(null) }
    val languageData = remember { mutableStateListOf<GetLanguageListResponse.LanguageResponse>() }
    var isDialogVisible by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CustomDialog(
        isVisible = isDialogVisible,
        onDismiss = { isDialogVisible = false },
        message = stringResource(R.string.txt_loading)
    )

    LaunchedEffect(Unit) {
        isInternetAvailable = isNetworkAvailable(context)
        if (!isInternetAvailable) {
            context.toast(noDataMessage)
        } else {
            viewModel.getLanguages()
        }
    }

    LaunchedEffect(uiState) {
        when {
            uiState.isLoading -> {
                languageData.clear()
                isDialogVisible = true
            }

            uiState.error.isNotEmpty() -> {
                languageData.clear()
                logger.d("Error: ${uiState.error}")
                context.toast(uiState.error)
                isDialogVisible = false
                noDataMessage = uiState.error
            }

            uiState.success != null -> {
                val list = uiState.success?.response ?: emptyList()
                logger.d("Languages fetched: ${list.size}")
                if (list.isNotEmpty()) {
                    languageData.clear()
                    languageData.addAll(list)
                } else {
                    logger.d("Languages fetched: 0 (null or empty response)")
                }
                isDialogVisible = false
            }
        }
    }

    if (isBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            containerColor = if (isSystemInDarkTheme()) {
                Dark_01
            } else {
                White
            },
            contentColor = MaterialTheme.colorScheme.onSurface,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            dragHandle = null,
            scrimColor = if (isSystemInDarkTheme()) {
                DARK_TITLE_TEXT.copy(alpha = 0.5f)
            } else {
                Color.Black.copy(alpha = 0.5f)
            },
            windowInsets = WindowInsets.ime
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(vertical = 15.dp, horizontal = 15.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "", style = MaterialTheme.typography.headlineLarge.copy(
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black
                                },
                                fontSize = 18.sp,
                                fontFamily = fontBold,
                                textAlign = TextAlign.Start
                            )
                        )

                        Image(
                            painter = painterResource(R.drawable.line),
                            contentDescription = IMG_DESCRIPTION,
                            modifier = Modifier
                                .wrapContentWidth()
                                .align(Alignment.CenterVertically)
                                .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                                .clickable {
                                    onDismiss.invoke()
                                }
                                .clip(RoundedCornerShape(100.dp))
                                .border(
                                    width = 2.dp, color = if (isSystemInDarkTheme()) {
                                        DARK_TITLE_TEXT
                                    } else {
                                        Gray
                                    }, shape = CircleShape
                                ))

                        Text(
                            text = "", style = MaterialTheme.typography.headlineLarge.copy(
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black
                                },
                                fontSize = 18.sp,
                                fontFamily = fontBold,
                                textAlign = TextAlign.Start
                            )
                        )
                    }

                    Text(
                        text = stringResource(id = R.string.select_language),
                        color = if (isSystemInDarkTheme()) {
                            DARK_BODY_TEXT
                        } else {
                            Black
                        },
                        fontSize = 19.sp,
                        fontFamily = fontBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                            .fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(start = 8.dp, end = 8.dp, top = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (languageData.isNotEmpty()) {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                state = scrollState,
                                modifier = Modifier
                                    .height(350.dp)
                                    .padding(horizontal = 8.dp)
                                    .draggable(
                                        orientation = Orientation.Vertical,
                                        state = rememberDraggableState { delta ->
                                            coroutineScope.launch {
                                                scrollState.scrollBy(-delta)
                                            }
                                        })
                            ) {
                                items(languageData.size) { index ->
                                    ItemLanguageChangeCard(
                                        context,
                                        isSelected = selectedIndex == index,
                                        index,
                                        language = languageData,
                                        onItemClicked = {
                                            selectedIndex =
                                                if (selectedIndex == index) null else index // Toggle selection
                                            selectedLanguageId.value =
                                                languageData[index].id.toString()
                                            selectedLanguageName.value =
                                                languageData[index].name.toString()
                                            viewModel.savePrefData(
                                                SELECTED_LANGUAGE_ID,
                                                languageData[index].id.toString()
                                            )
                                        }
                                    )
                                }
                            }
                        } else {
                            isInternetAvailable = isNetworkAvailable(context)
                            if (!isInternetAvailable) {
                                ShowError(
                                    internetMessage,
                                    errColor,
                                    painterResource(R.drawable.sad_emoji)
                                )
                            } else {
                                NoDataFound(noDataMessage, painterResource(R.drawable.sad_emoji))
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .padding(bottom = 32.dp, start = 16.dp, end = 16.dp, top = 16.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BtnUi(
                            onClick = {
                                onDismiss()
                                logger.d("Selected language Name and Id :- ${selectedLanguageName.value}, ${selectedLanguageId.value}")
                            },
                            title = stringResource(R.string.key_Confirm),
                            enabled = true
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ItemLanguageChangeCard(
    context: Context,
    isSelected: Boolean = true,
    index: Int,
    language: MutableList<GetLanguageListResponse.LanguageResponse>,
    onItemClicked: () -> Unit = {},
) {
    val languageIndex = language[index]
    val errorToast = stringResource(R.string.choose_hindi_english)

    val selectedBorder = if (isSelected) BorderStroke(
        width = 1.dp,
        if (isSystemInDarkTheme()) {
            PRIMARY_AURO_BLUE
        } else {
            BorderBlue
        }
    ) else BorderStroke(
        width = 0.5.dp, if (isSystemInDarkTheme()) {
            Dark_02
        } else {
            GrayLight02
        }
    )
    val backGroundColor = if (isSelected) {
        if (isSystemInDarkTheme()) {
            Dark_Selected_BG
        } else {
            PrimaryBlueLt1
        }
    } else {
        if (isSystemInDarkTheme()) {
            Dark_02
        } else {
            White
        }
    }

    Card(
        modifier = Modifier
            .clickable {
                if (languageIndex.status == 1) {
                    onItemClicked.invoke()
                } else {
                    logger.d("Languages fetched: ${languageIndex.status}")
                    context.toast(errorToast)
                }
            }
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(1.dp),
        colors = CardDefaults.cardColors(
            if (isSystemInDarkTheme()) {
                Dark_02
            } else {
                GrayLight02
            }
        ),
        border = selectedBorder,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = backGroundColor),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 15.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .background(Color.Unspecified)
                        .size(45.dp),
                    contentScale = ContentScale.Fit,
                    painter = if (!languageIndex.langIcon.isNullOrEmpty()) {
                        rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(languageIndex.langIcon)
                                .decoderFactory(SvgDecoder.Factory())
                                .size(Size.ORIGINAL)
                                .placeholder(R.drawable.ic_hindi)
                                .error(R.drawable.ic_hindi)
                                .build()
                        )
                    } else {
                        painterResource(id = R.drawable.ic_hindi)
                    },
                    contentDescription = IMG_DESCRIPTION
                )

                Text(
                    (languageIndex.translatedName ?: languageIndex.name)!!,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = Black,
                    modifier = Modifier
                        .padding(top = 10.dp)
                )

                Text(
                    "${languageIndex.name} ",
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    fontSize = 14.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = Gray,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 5.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetReferUIScreen(
    isBottomSheetVisible: Boolean,
    sheetState: SheetState,
    onDismiss: () -> Unit,
) {
    val context: Context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val shareableStr = stringResource(id = R.string.txt_play_Store_text)
    val playStoreLinkStr = stringResource(id = R.string.txt_playStore_Link)
    val textPlanStr = stringResource(id = R.string.txt_text_plan)
    val copiedStr = stringResource(id = R.string.txt_Text_copied)
    val whatsAppPkgStr = stringResource(id = R.string.txt_whatsAppPkg)
    val drivePkgStr = stringResource(id = R.string.txt_DrivePkg)
    val facebookPkgStr = stringResource(id = R.string.txt_FacebookPck)
    val instagramPkgStr = stringResource(id = R.string.txt_InstagramPck)
    val gmailPkgStr = stringResource(id = R.string.txt_gmailPkg)

    if (isBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            containerColor = if (isSystemInDarkTheme()) {
                Dark_01
            } else {
                White
            },
            contentColor = MaterialTheme.colorScheme.onSurface,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            dragHandle = null,
            scrimColor = if (isSystemInDarkTheme()) {
                DARK_TITLE_TEXT.copy(alpha = 0.5f)
            } else {
                Color.Black.copy(alpha = 0.5f)
            },
            windowInsets = WindowInsets.ime
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(vertical = 16.dp, horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "", style = MaterialTheme.typography.headlineLarge.copy(
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black
                                }, fontSize = 18.sp, fontFamily = FontFamily(
                                    Font(R.font.roboto_bold, FontWeight.Bold)
                                ), textAlign = TextAlign.Start
                            )
                        )

                        Image(
                            painter = painterResource(R.drawable.line),
                            contentDescription = IMG_DESCRIPTION,
                            modifier = Modifier
                                .wrapContentWidth()
                                .align(Alignment.CenterVertically)
                                .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                                .clickable {
                                    onDismiss.invoke()
                                }
                                .clip(RoundedCornerShape(100.dp))
                                .border(
                                    width = 2.dp, color = if (isSystemInDarkTheme()) {
                                        DARK_TITLE_TEXT
                                    } else {
                                        Gray
                                    }, shape = CircleShape
                                ))

                        Text(
                            text = "", style = MaterialTheme.typography.headlineLarge.copy(
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black
                                }, fontSize = 18.sp, fontFamily = FontFamily(
                                    Font(R.font.roboto_bold, FontWeight.Bold)
                                ), textAlign = TextAlign.Start
                            )
                        )
                    }

                    Card(
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .wrapContentHeight(),
                        colors = if (isSystemInDarkTheme()) {
                            CardDefaults.cardColors(BannerColor03)
                        } else {
                            CardDefaults.cardColors(
                                containerColor = BannerColor03,
                                contentColor = BannerColor03,
                                disabledContentColor = BannerColor03,
                                disabledContainerColor = BannerColor03
                            )
                        }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .wrapContentHeight()
                                .background(BannerColor03),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = shareableStr,
                                modifier = Modifier
                                    .width(275.dp)
                                    .padding(start = 8.dp, end = 8.dp),
                                fontFamily = fontRegular,
                                fontSize = 12.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black
                                },
                                textAlign = TextAlign.Start
                            )

                            Image(
                                painter = painterResource(R.drawable.copy_img),
                                contentDescription = IMG_DESCRIPTION,
                                modifier = Modifier
                                    .size(35.dp)
                                    .padding(start = 8.dp, end = 8.dp)
                                    .background(Color.Unspecified)
                                    .clickable {
                                        clipboardManager.setText(AnnotatedString(shareableStr))
                                        context.toast(copiedStr)
                                    })
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 32.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.CenterVertically)
                                .padding(start = 8.dp, end = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            IconButton(onClick = {
                                val appPackageName = context.packageName
                                val appLink = "$playStoreLinkStr$appPackageName"

                                val intent = Intent(Intent.ACTION_SEND).apply {
                                    type = textPlanStr
                                    putExtra(
                                        Intent.EXTRA_TEXT, shareableStr
                                    )
                                    setPackage(whatsAppPkgStr)
                                }

                                try {
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    val fallbackIntent =
                                        Intent(Intent.ACTION_VIEW, appLink.toUri())
                                    context.startActivity(fallbackIntent)
                                }
                            }) {
                                Image(
                                    painter = painterResource(id = R.drawable.whats_app_icon),
                                    contentDescription = IMG_DESCRIPTION,
                                    modifier = Modifier
                                        .size(75.dp)
                                        .align(Alignment.CenterHorizontally)
                                )
                            }

                            Text(
                                text = stringResource(R.string.txt_WhatsApp),
                                fontSize = 12.sp,
                                textAlign = TextAlign.Start,
                                fontFamily = fontRegular,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    contactUsTxt
                                }
                            )
                        }

                        Column(
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.CenterVertically)
                                .padding(start = 8.dp, end = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            IconButton(onClick = {
                                val appPackageName = context.packageName
                                val appLink = "$playStoreLinkStr$appPackageName"
                                val intent = Intent(Intent.ACTION_SEND).apply {
                                    type = textPlanStr
                                    putExtra(
                                        Intent.EXTRA_TEXT, shareableStr
                                    )
                                    `package` = drivePkgStr
                                }
                                val resolveInfo = context.packageManager.resolveActivity(intent, 0)
                                if (resolveInfo != null) {
                                    startActivity(context, intent, null)
                                } else {
                                    val browserIntent =
                                        Intent(Intent.ACTION_VIEW, appLink.toUri())
                                    startActivity(context, browserIntent, null)
                                }
                            }) {
                                Image(
                                    painter = painterResource(id = R.drawable.drive_img),
                                    contentDescription = IMG_DESCRIPTION,
                                    modifier = Modifier
                                        .size(75.dp)
                                        .align(Alignment.CenterHorizontally)
                                )
                            }

                            Text(
                                text = stringResource(R.string.txt_Drive),
                                fontSize = 12.sp,
                                textAlign = TextAlign.Start,
                                fontFamily = fontRegular,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    contactUsTxt
                                }
                            )
                        }

                        Column(
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.CenterVertically)
                                .padding(start = 8.dp, end = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            IconButton(onClick = {
                                val appPackageName = context.packageName
                                val appLink = "$playStoreLinkStr$appPackageName"
                                val intent = Intent(Intent.ACTION_SEND).apply {
                                    type = textPlanStr
                                    putExtra(
                                        Intent.EXTRA_TEXT, shareableStr
                                    )
                                    `package` = facebookPkgStr
                                }
                                val resolveInfo = context.packageManager.resolveActivity(intent, 0)
                                if (resolveInfo != null) {
                                    startActivity(context, intent, null)
                                } else {
                                    val browserIntent =
                                        Intent(Intent.ACTION_VIEW, appLink.toUri())
                                    startActivity(context, browserIntent, null)
                                }
                            }) {
                                Image(
                                    painter = painterResource(id = R.drawable.facebook_img),
                                    contentDescription = IMG_DESCRIPTION,
                                    modifier = Modifier
                                        .size(75.dp)
                                        .align(Alignment.CenterHorizontally)
                                )
                            }

                            Text(
                                text = stringResource(R.string.txt_Facebook),
                                fontSize = 12.sp,
                                textAlign = TextAlign.Start,
                                fontFamily = fontRegular,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    contactUsTxt
                                }
                            )
                        }

                        Column(
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.CenterVertically)
                                .padding(start = 8.dp, end = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            IconButton(onClick = {
                                val appPackageName = context.packageName
                                val appLink = "$playStoreLinkStr$appPackageName"
                                val intent = Intent(Intent.ACTION_SEND).apply {
                                    type = textPlanStr
                                    putExtra(
                                        Intent.EXTRA_TEXT, shareableStr
                                    )
                                    `package` = instagramPkgStr
                                }
                                val resolveInfo = context.packageManager.resolveActivity(intent, 0)
                                if (resolveInfo != null) {
                                    startActivity(context, intent, null)
                                } else {
                                    val browserIntent =
                                        Intent(Intent.ACTION_VIEW, appLink.toUri())
                                    startActivity(context, browserIntent, null)
                                }
                            }) {
                                Image(
                                    painter = painterResource(id = R.drawable.instagram_img),
                                    contentDescription = IMG_DESCRIPTION,
                                    modifier = Modifier
                                        .size(75.dp)
                                        .align(Alignment.CenterHorizontally)
                                )
                            }

                            Text(
                                text = stringResource(R.string.txt_Instagram),
                                fontSize = 12.sp,
                                textAlign = TextAlign.Start,
                                fontFamily = fontRegular,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    contactUsTxt
                                }
                            )
                        }

                        Column(
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.CenterVertically)
                                .padding(start = 8.dp, end = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            IconButton(onClick = {
                                val appPackageName = context.packageName
                                val appLink = "$playStoreLinkStr$appPackageName"
                                val intent = Intent(Intent.ACTION_SEND).apply {
                                    type = textPlanStr
                                    putExtra(
                                        Intent.EXTRA_TEXT, shareableStr
                                    )
                                    `package` = gmailPkgStr
                                }
                                val resolveInfo = context.packageManager.resolveActivity(intent, 0)
                                if (resolveInfo != null) {
                                    startActivity(context, intent, null)
                                } else {
                                    val browserIntent =
                                        Intent(Intent.ACTION_VIEW, appLink.toUri())
                                    startActivity(context, browserIntent, null)
                                }
                            }) {
                                Image(
                                    painter = painterResource(id = R.drawable.gmail_icon),
                                    contentDescription = IMG_DESCRIPTION,
                                    modifier = Modifier
                                        .size(75.dp)
                                        .align(Alignment.CenterHorizontally)
                                )
                            }

                            Text(
                                text = stringResource(R.string.txt_gmail),
                                fontSize = 12.sp,
                                textAlign = TextAlign.Start,
                                fontFamily = fontRegular,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    contactUsTxt
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}