package com.pi.ProjectInclusion.android.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.pi.ProjectInclusion.android.R

import com.pi.ProjectInclusion.Transparent
import com.pi.ProjectInclusion.android.MyApplicationTheme
import com.pi.ProjectInclusion.android.navigation.AppRoute
import com.pi.ProjectInclusion.android.screens.dashboardScreen.AddStudentRegisterScreen
import com.pi.ProjectInclusion.android.screens.dashboardScreen.DashboardScreen
import com.pi.ProjectInclusion.android.screens.menu.AppBar
import com.pi.ProjectInclusion.android.screens.menu.BottomNavigationBar
import com.pi.ProjectInclusion.android.screens.menu.DrawerBody
import com.pi.ProjectInclusion.android.screens.menu.DrawerHeader
import com.pi.ProjectInclusion.android.screens.menu.MenuItem
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class StudentDashboardActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
          //  MyApplicationTheme {

                val colors = MaterialTheme.colorScheme
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination?.route
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                val bottomSheetState = rememberStandardBottomSheetState(
                    skipHiddenState = false  // Allow transitioning to hidden state
                )
                val bottomSheetScaffoldState =
                    rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)
                val coroutineScope = rememberCoroutineScope()
                val screenHeight = LocalConfiguration.current.screenHeightDp.dp
                val halfScreenHeight = screenHeight / 2
                var sheetPeekHeight by remember { mutableStateOf(0.dp) }
                var isDialogVisible by remember { mutableStateOf(false) }

                BottomSheetScaffold(
                    scaffoldState = bottomSheetScaffoldState, sheetContent = {


                    }, sheetPeekHeight = sheetPeekHeight
                )
                {
                    ModalNavigationDrawer(
                        drawerState = drawerState,
                        drawerContent = {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(end = 30.dp)
                                    .background(Transparent)
                            )
                            {
                                DrawerHeader(drawerState, onItemClick = {
                                    navController.navigate(AppRoute.DashboardScreen.route) //AddStudentRegisterScreen
                                })
                                DrawerBody(
                                    // List of Navigation Drawer
                                    items = listOf(
                                        MenuItem(
                                            id = AppRoute.CourseScreen.route,
                                            title = "Certificate",
                                            contentDescription = "Go to auth",
                                            icon = ImageVector.vectorResource(id = R.drawable.certificate_ic)
                                        ),
                                        MenuItem(
                                            id = AppRoute.ScreeningScreen.route,
                                            title = "Meeting",
                                            contentDescription = "Add new student",
                                            icon = ImageVector.vectorResource(id = R.drawable.meeting_ic)
                                        ),
                                        MenuItem(
                                            id = AppRoute.InterventionScreen.route,
                                            title = "Change Password",
                                            contentDescription = "Switch User Profile",
                                            icon = ImageVector.vectorResource(id = R.drawable.password_ic)
                                        ),
                                        MenuItem(
                                            id = AppRoute.InterventionScreen.route,
                                            title = "Language",
                                            contentDescription = "Switch User Profile",
                                            icon = ImageVector.vectorResource(id = R.drawable.language_ic)
                                        ),
                                        MenuItem(
                                            id = AppRoute.InterventionScreen.route,
                                            title = "Refer",
                                            contentDescription = "Switch User Profile",
                                            icon = ImageVector.vectorResource(id = R.drawable.refer_ic)
                                        ),
                                        MenuItem(
                                            id = AppRoute.InterventionScreen.route,
                                            title = "Change Password",
                                            contentDescription = "Switch User Profile",
                                            icon = ImageVector.vectorResource(id = R.drawable.password_ic)
                                        ),
                                        MenuItem(
                                            id = AppRoute.InterventionScreen.route,
                                            title = "Contact us",
                                            contentDescription = "Switch User Profile",
                                            icon = ImageVector.vectorResource(id = R.drawable.contact_ic)
                                        ),
                                        MenuItem(
                                            id = AppRoute.InterventionScreen.route,
                                            title = "F.A.Q",
                                            contentDescription = "Switch User Profile",
                                            icon = ImageVector.vectorResource(id = R.drawable.faq_ic)
                                        ),
                                    ), onItemClick = { itemId ->
                                        onMenuItemClick(
                                            itemId,
                                            navController,
                                            this@StudentDashboardActivity,
                                            bottomSheetScaffoldState,
                                            coroutineScope,
                                            halfScreenHeight,
                                            sheetPeekHeight
                                        ) {
                                            sheetPeekHeight = it
                                            scope.launch {
                                                drawerState.close()
                                            }
                                        }
                                    })
                            }
                        },
                    )
                    {
                        Scaffold(bottomBar = {
                            if (currentDestination in listOf(
                                    AppRoute.DashboardScreen.route,
                                    AppRoute.CourseScreen.route,
                                    AppRoute.ScreeningScreen.route,
                                    AppRoute.InterventionScreen.route
                                )
                            )
                            {
                                Box(
                                    Modifier
                                        .fillMaxWidth()
                                        .zIndex(1f)
                                        .shadow(elevation = 8.dp)
                                ) {
                                    BottomNavigationBar(
                                        navController = navController,
                                        currentDestination
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

                        }, content = {
                        innerPadding ->
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .zIndex(1f)
                            )
                            {

                                NavHost(
                                    navController = navController,
                                    startDestination = AppRoute.DashboardScreen.route, //AppRoute.DashboardScreen.route,
                                ) {
                                    composable(AppRoute.DashboardScreen.route) {
                                        DashboardScreen(navController)

                                    }
//                                    composable(
//                                        AppRoute.DashboardScreen(-1).route,
//                                        arguments = listOf(navArgument("index") {
//                                            type = NavType.IntType
//                                        })
//                                    ) { backStackEntry ->
//                                        val index = backStackEntry.arguments?.getInt("index")!!
//
//                                    }
                                    composable(AppRoute.ScreeningScreen.route) {
                                        AddStudentRegisterScreen(navController)
                                    }
                                    composable(AppRoute.CourseScreen.route) {
                                        DashboardScreen(navController)
                                    }
                                }
                            }
                        })
                    }
                }

//                NavHost(
//                    navController = navController,
//                    startDestination = "splash"
//                ) {
//
//
//                }
           // }



            }
        }
    }



    // Function to handle item clicks
    @OptIn(ExperimentalMaterial3Api::class)
    fun onMenuItemClick(
        itemId: String,
        navController: NavHostController,
        studentDashboardActivity: StudentDashboardActivity,
        bottomSheetScaffoldState: BottomSheetScaffoldState,
        coroutineScope: CoroutineScope,
        halfScreenHeight: Dp,
        currentPeekHeight: Dp,
        updatePeekHeight: (Dp) -> Unit,
    ) {
        // Handle the click event here
        val context = studentDashboardActivity as Context
        when (itemId) {
            AppRoute.CourseScreen.route -> {
                Log.e("TAG", "Calling   " + "StudentAuthenticationActivity")
                startActivity(
                    context,
                    Intent(context, StudentDashboardActivity::class.java),
                    null
                ).apply { (context as? Activity)?.finish() }
            }

            AppRoute.CourseScreen.route -> {
                startActivity(
                    context,
                    Intent(context, StudentDashboardActivity::class.java),
                    null
                ).apply { (context as? Activity)?.finish() }
            }


        }
    }



    @Preview(showBackground = true)
    @Composable
    fun DashboardPreview() {
        MyApplicationTheme {  }
    }


