package com.pi.ProjectInclusion.android.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.DARK_BODY_TEXT
import com.pi.ProjectInclusion.DARK_TITLE_TEXT
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.Transparent
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.AnimatedRouteHost
import com.pi.ProjectInclusion.android.navigation.AppRoute
import com.pi.ProjectInclusion.android.screens.Profile.EditProfileScreen1
import com.pi.ProjectInclusion.android.screens.Profile.EditProfileScreen2
import com.pi.ProjectInclusion.android.screens.Profile.TrackRequestScreen
import com.pi.ProjectInclusion.android.screens.addStudentScreen.AddNewStudentDetailsScreen
import com.pi.ProjectInclusion.android.screens.addStudentScreen.AddNewStudentMoreDetailsScreen
import com.pi.ProjectInclusion.android.screens.Profile.professionals.ProfessionalsEditProfile
import com.pi.ProjectInclusion.android.screens.Profile.specialEdu.SpecialEducatorEditProfile
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
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION
import com.pi.ProjectInclusion.constants.ConstantVariables.USER_NAME
import com.pi.ProjectInclusion.contactUsTxt
import com.pi.ProjectInclusion.data.model.profileModel.ViewProfileResponse
import com.pi.ProjectInclusion.data.model.profileModel.response.ViewProfileResponse
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

            val viewProfile by viewModel.viewUserProfileResponse.collectAsStateWithLifecycle()
            var encryptedUserName = viewModel.getPrefData(USER_NAME)
            logger.d("Profile details on dashboard page for header in drawer :- $encryptedUserName")
            var profileData by remember { mutableStateOf<ViewProfileResponse.ProfileResponse?>(null) }

            var allUdiseDetails = remember { mutableStateListOf<ViewProfileResponse>() }

            LaunchedEffect(Unit) {
//                viewModel.getUserProfileViewModel(encryptedUserName)
                viewModel.getUserProfileViewModel("lhWmhODMnBvTyxCkajySXQ==")
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

            fun navigateBack(toRoute: String? = null) {
                isForward = false
                currentRoute = toRoute ?: AppRoute.LanguageSelect.route
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
                        val sheetState = rememberModalBottomSheetState(
                            skipPartiallyExpanded = true, /*confirmValueChange = { it != SheetValue.Hidden }*/
                        )

                        BottomSheetContactUsScreen(
                            isBottomSheetVisible = isBottomSheetVisible,
                            sheetState = sheetState,
                            onDismiss = {
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    isBottomSheetVisible = false
                                }
                            })

                        DrawerHeader(
                            drawerState,
                            onItemClick = {
                                scope.launch {
                                    drawerState.close()
                                    navigateTo(AppRoute.ProfileScreen.route)
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
                                    drawerState
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
                        } else if (currentRoute == AppRoute.ScreeningScreen.route) {
                            AppBar(
                                isNotification,
                                onNavigationIconClick = {
                                    scope.launch { drawerState.open() }
                                },
                                currentRoute = currentRoute
                            )
                        } else if (currentRoute == AppRoute.InterventionScreen.route) {
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

                                    AppRoute.CourseScreen.route -> LMSCourseHomeScreen()

                                    AppRoute.ProfileScreen.route -> ViewProfileScreen(
                                        viewModel = viewModel,
                                        onNext = { navigateTo(AppRoute.EditProfileScreen.route) },
                                        onTrackRequest = { navigateTo(AppRoute.TrackRequestScreen.route) },
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
                                            navigateTo(AppRoute.OtpSendVerifyUI.route)
                                        },
                                        onBack = { navigateBack(AppRoute.UserTypeSelect.route) }
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
                                                    context,
                                                    StudentDashboardActivity::class.java
                                                )
                                            )
                                            (context as? Activity)?.finish()
                                        },
                                        onBack = { navigateBack(AppRoute.ProfileScreen.route) })

                                    AppRoute.EditProfessionalEditProfile.route -> ProfessionalsEditProfile(
                                        loginViewModel = viewModel,
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

                                    AppRoute.EditSpeEduEditProfile.route -> SpecialEducatorEditProfile(
                                        loginViewModel = viewModel,
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
                                        profilerForm = { navigateTo(AppRoute.ProfilerFormPage.route) },
                                        advanceScreening = { navigateTo(AppRoute.AdvanceScreening.route) },
                                        onBack = { navigateBack(AppRoute.DashboardScreen.route) }
                                    )

                                    AppRoute.ScreeningOne.route -> ScreeningOneScreen(
                                        onNext = { navigateTo(AppRoute.ScreeningOneReport.route) },
                                        onBack = { navigateBack(AppRoute.ScreeningScreen.route) }
                                    )

                                    AppRoute.TrackRequestScreen.route -> {
                                        TrackRequestScreen(
                                            onNext = {},
                                            onBack = { navigateBack(AppRoute.ProfileScreen.route) }
                                        )
                                    }

                                    AppRoute.NotificationScreen.route -> {
                                        NotificationScreen(
                                            onNext = {},
                                            onBack = { navigateBack(AppRoute.DashboardScreen.route) }
                                        )
                                    }

                                    AppRoute.ScreeningOneReport.route -> {
                                        ScreeningOneReportScreen(
                                            showReportScreen = true,
                                            onNext = { navigateTo(AppRoute.ProfilerFormPage.route) },
                                            onNextCongratulate = { navigateTo(AppRoute.AdvanceScreeningReport.route) },
                                            onBack = { navigateBack(AppRoute.ScreeningScreen.route) }
                                        )
                                    }

                                    AppRoute.ProfilerFormPage.route -> {
                                        ProfilerFormPageScreen(
                                            onNext = { navigateTo(AppRoute.ProfilerFormPage.route) },
                                            onBack = { navigateBack(AppRoute.ScreeningScreen.route) }
                                        )
                                    }

                                    AppRoute.AdvanceScreening.route -> AdvanceScreeningScreen(
                                        onNext = { navigateTo(AppRoute.AdvanceScreeningReport.route) },
                                        onBack = { navigateBack(AppRoute.ScreeningScreen.route) }
                                    )

                                    AppRoute.AdvanceScreeningReport.route -> {
                                        ReportAdvanceScreen(
                                            onNext = { navigateTo(AppRoute.InterventionScreen.route) },
                                            onViewProfileDetails = { navigateTo(AppRoute.ViewScreeningProfileDetails.route) },
                                            onBack = { navigateBack(AppRoute.ScreeningScreen.route) }
                                        )
                                    }

                                    AppRoute.ViewScreeningProfileDetails.route -> {
                                        ViewScreeningProfileDetailsScreen(
                                            onNext = { navigateTo(AppRoute.ScreeningScreen.route) },
                                            onBack = { navigateBack(AppRoute.ScreeningScreen.route) }
                                        )
                                    }

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
    openContactUs: () -> Unit,
    drawerState: DrawerState
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
            logger.d("Screen: LanguageChangeActivity()")
        }

        AppRoute.ReferScreen.route -> {
            logger.d("Screen: Refer dialog open screen")
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
                                    val browserIntent =
                                        Intent(Intent.ACTION_VIEW, Uri.parse("mailto:support.pi@aurosociety.org"))
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