package com.pi.ProjectInclusion.android.screens.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.LightBlue
import com.pi.ProjectInclusion.Transparent
import com.pi.ProjectInclusion.White
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.navigation.AppRoute
import com.pi.ProjectInclusion.android.utils.fontBold
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun OpenDashboardHeader(
    onNavigationIconClick: () -> Unit = {},
    isNotification: Boolean = true,
    onNotificationClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .background(color = White)
            .fillMaxWidth()
            .clipToBounds()
    ) {
        TopAppBar(
            title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.padding(end = 10.dp)
                            .clickable{
                                onNotificationClick()
                            },
                        painter = if (isNotification) painterResource(R.drawable.ic_notification) else painterResource(
                            R.drawable.ic_bell
                        ),
                        contentDescription = IMG_DESCRIPTION,
                        contentScale = ContentScale.Crop// Clip the image to a circular shape
                    )
                    Image(
                        painter = painterResource(R.drawable.profile_user_icon),
                        contentDescription = IMG_DESCRIPTION,
                        modifier = Modifier
                            .size(36.dp) // Add size modifier to make the image visible
                            .clip(CircleShape) // Add clip modifier to make the image circular
                            .background(shape = CircleShape, color = White)
                            .border( // Add border modifier to make image stand out
                                width = 2.dp, color = LightBlue, shape = CircleShape
                            ),
                        contentScale = ContentScale.Crop// Clip the image to a circular shape
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = White),
            modifier = Modifier
                .background(Transparent)
                .zIndex(1f),
            navigationIcon = {
                IconButton(onClick = onNavigationIconClick) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_menu),
                        contentDescription = "",
                        tint = Black
                    )
                }
            })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenScreeningHeader(
    onNavigationIconClick: () -> Unit,
    hideShowFilter: Boolean,
) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .background(color = White)
            .fillMaxWidth()
            .clipToBounds()
    ) {
        TopAppBar(
            title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (hideShowFilter) stringResource(R.string.nav_screening) else stringResource(
                            R.string.nav_intervention
                        ),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Start,
                        fontFamily = fontBold,
                        color = Black,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(Modifier.weight(1f))

                    Image(
                        modifier = Modifier
                            .padding(
                                end = if (hideShowFilter) {
                                    10.dp
                                } else {
                                    0.dp
                                }
                            )
                            .size(25.dp),
                        painter = painterResource(R.drawable.more_info_black_icon),
                        contentDescription = IMG_DESCRIPTION,
                        contentScale = ContentScale.Crop// Clip the image to a circular shape
                    )

                    if (hideShowFilter) {
                        Image(
                            painter = painterResource(R.drawable.filter_img),
                            contentDescription = IMG_DESCRIPTION,
                            modifier = Modifier.size(25.dp),
                            contentScale = ContentScale.Crop// Clip the image to a circular shape
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = White),
            modifier = Modifier
                .background(Transparent)
                .zIndex(1f),
            navigationIcon = {
                IconButton(onClick = onNavigationIconClick) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_menu),
                        contentDescription = "",
                        tint = Black
                    )
                }
            })
    }
}

@Composable
fun AppBar(
    isNotification: Boolean,
    onNavigationIconClick: () -> Unit = {},
    currentRoute: String? = "",
    onNotificationClick: () -> Unit = {}
) {
    when (currentRoute) {
        AppRoute.DashboardScreen.route -> {
            OpenDashboardHeader(onNavigationIconClick, isNotification,onNotificationClick)
        }

        AppRoute.ScreeningScreen.route -> {
            var hideShowFilter by remember { mutableStateOf(true) }
            OpenScreeningHeader(onNavigationIconClick, hideShowFilter)
        }

        AppRoute.InterventionScreen.route -> {
            var hideShowFilter by remember { mutableStateOf(false) }
            OpenScreeningHeader(onNavigationIconClick, hideShowFilter)
        }

        else -> {

        }
    }
}


@Preview
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBarPassport(
    scope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Open),
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.height(80.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.language_txt),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.profile_user_icon), // Replace with your drawable resource
                    contentDescription = null, // Provide a description for accessibility purposes
                    modifier = Modifier.wrapContentSize()
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
        modifier = Modifier
            .background(White)
            .shadow(10.dp)
            .zIndex(1f),

        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    drawerState.open()
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Menu, contentDescription = "", tint = Black
                )
            }
        })
}