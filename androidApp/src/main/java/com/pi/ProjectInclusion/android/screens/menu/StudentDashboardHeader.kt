package com.pi.ProjectInclusion.android.screens.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.Transparent
import com.pi.ProjectInclusion.White
import com.pi.ProjectInclusion.android.MyApplicationTheme
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.navigation.AppRoute
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenDashboardHeader(
    onNavigationIconClick: () -> Unit = {},
    navHostController: NavHostController,
) {


    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .clipToBounds()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_hindi),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
        )
        TopAppBar(
            title = {

            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryBlue),
            modifier = Modifier
                .background(Transparent)
                .zIndex(1f),
            navigationIcon = {
                IconButton(onClick = onNavigationIconClick) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Toggle drawer",
                        tint = White
                    )
                }
            }
        )
    }
}

@Composable
fun AppBar(
    onNavigationIconClick: () -> Unit = {},
    scope: CoroutineScope = rememberCoroutineScope({ Dispatchers.IO }),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Open),
    currentDestination: String? = "",
    navHostController: NavHostController
) {
    when (currentDestination) {
        AppRoute.DashboardScreen.route -> {
            OpenDashboardHeader(onNavigationIconClick, navHostController)
        }

        else -> {
            when (currentDestination) {
                AppRoute.CourseScreen.route -> {

                }

                AppRoute.ScreeningScreen.route -> {
                    TopBarPassport(scope, drawerState)
                }

                else -> {
                    /* TopBarWallet(scope, drawerState, viewHistoryClickable = {
                         walletViewHistoryClickable.invoke()
                     })*/
                }
            }
        }
    }
}




@Preview
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBarWallet(openMenu: () -> Unit = {}) {
    TopAppBar(title = {
        Row(
            modifier = Modifier.height(80.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Certificate",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.clickable {

                },
                text = "Meeting",
                fontSize = 15.sp,
                color = PrimaryBlue,
                fontWeight = FontWeight.Bold
            )
        }
    },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
        modifier = Modifier
            .background(Transparent)
            .zIndex(1f),
        navigationIcon = {
            IconButton(onClick = {
//                scope.launch {
//                    drawerState.open()
//                }
            }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Toggle drawer",
                    tint = Black
                )
            }
        }
    )
}

@Preview
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBarPassport(
    scope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Open)
) {
    TopAppBar(title = {

    },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
        modifier = Modifier
            .background(Transparent)
            .zIndex(1f),
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    drawerState.open()
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Toggle drawer",
                    tint = Black
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    MyApplicationTheme {  }
}