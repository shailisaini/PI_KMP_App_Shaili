package com.pi.ProjectInclusion.android.screens.menu

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.pi.ProjectInclusion.BannerColor03
import com.pi.ProjectInclusion.Bg_Gray
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.BorderBlue
import com.pi.ProjectInclusion.Transparent
import com.pi.ProjectInclusion.GrayLight01
import com.pi.ProjectInclusion.LightPurple04
import com.pi.ProjectInclusion.MenuSelectedColor
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.RedLogout
import com.pi.ProjectInclusion.White
import com.pi.ProjectInclusion.android.MyApplicationTheme
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.TextWithIconOnLeft
import com.pi.ProjectInclusion.android.common_UI.ThemeSwitch
import com.pi.ProjectInclusion.android.navigation.AppRoute
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.fontRegular
import com.pi.ProjectInclusion.data.model.profileModel.response.ViewProfileResponse

@Composable
fun DrawerHeader(
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Open),
    onItemClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    profileData: ViewProfileResponse.ProfileResponse?,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(end = 50.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .clickable {
                    onItemClick.invoke()
                }
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            BorderBlue, // #2C3EA2
                            PrimaryBlue  // #101942
                        ), start = Offset(0f, Float.POSITIVE_INFINITY), end = Offset(0f, 0f)
                    )
                )) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                Box(
                    modifier = Modifier.padding(5.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.Center)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.profile_user_icon),
                            contentDescription = stringResource(R.string.logo_ic),
                            modifier = Modifier
                                .width(80.dp)
                                .height(80.dp)
                                .clip(CircleShape) // Add clip modifier to make the image circular
                                .background(shape = CircleShape, color = Transparent)
                                .border( // Add border modifier to make image stand out
                                    width = 2.dp, color = White, shape = CircleShape
                                ),
                            contentScale = ContentScale.Crop // Clip the image to a circular shape
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .weight(1f),
                ) {
                    var textName = stringResource(R.string.user_name)
                    var textEmail = stringResource(R.string.user_profile)

                    var fullName = if (profileData != null) {
                        "${profileData.firstname} ${profileData.lastname}"
                    } else {
                        stringResource(R.string.user_name)
                    }

                    Text(
                        text = if (profileData?.firstname != null || profileData?.lastname != null) {
                            if (fullName.length > 20) {
                                fullName.take(20) + "..."
                            } else {
                                fullName
                            }
                        } else {
                            textName
                        }, color = White, fontSize = 19.sp, fontFamily = fontMedium
                    )

                    Text(
                        text = if (profileData?.userTypeName != null) {
                            profileData.userTypeName.toString()
                        } else {
                            textEmail
                        }, color = LightPurple04, fontSize = 13.sp, fontFamily = fontRegular
                    )

                    Column(
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(vertical = 10.dp)
                    ) {
                        Card(
                            modifier = Modifier
                                .wrapContentSize()
                                .border(
                                    width = 1.dp, color = White, shape = RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    onEditClick.invoke()
                                }, colors = CardDefaults.cardColors(
                                containerColor = White.copy(alpha = 0.2f) // Set semi-transparent background here
                            ), shape = RoundedCornerShape(8.dp)
                            // Do not set containerColor, let the Box inside handle the gradient
                        ) {
                            Text(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(10.dp),
                                text = stringResource(R.string.edit_profile),
                                color = White,
                                fontSize = 13.sp// Make sure text is readable on gradient
                            )
                        }
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun DrawerBody(
    items: List<MenuItem> = emptyList(),
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp),
    onItemClick: (String) -> Unit = {},
) {
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .padding(end = 50.dp)
            .zIndex(1f)
            .fillMaxHeight()
            .background(color = White)
    ) {
        LazyColumn(
            modifier
                .padding(vertical = 10.dp)
                .fillMaxHeight()
                .background(
                    color = if (isSystemInDarkTheme()) {
                        colors.background
                    } else {
                        Color.White
                    }
                )
        ) {
            items(items) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onItemClick(item.id)
                        }
                        .padding(start = 20.dp, end = 20.dp, top = 20.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = "",
                        tint = Color.Unspecified,
                    )
                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = item.title,
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                            .weight(1f),
                        color = Black,
                        fontFamily = fontMedium,
                        fontSize = 16.sp,
                    )
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.arrow_nxt_ic),
                        contentDescription = "",
                        tint = Color.Unspecified
                    )
                }
            }

            item {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    var isLight by remember { mutableStateOf(true) }

                    TextWithIconOnLeft(
                        text = stringResource(R.string.txt_change_theme),
                        icon = ImageVector.vectorResource(id = R.drawable.ic_change_theme),
                        textColor = Bg_Gray,
                        iconColor = Color.Unspecified,
                        modifier = Modifier.padding(vertical = 10.dp),
                        onClick = {

                        })
                    ThemeSwitch(isLightSelected = isLight) { selected ->
                        isLight = selected
                    }
                    Surface(
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .height(1.dp)
                            .fillMaxWidth(),
                        color = BannerColor03,
                    ) {

                    }
                    TextWithIconOnLeft(
                        text = stringResource(R.string.txt_logout),
                        icon = ImageVector.vectorResource(id = R.drawable.ic_logout),
                        textColor = RedLogout,
                        iconColor = RedLogout,
                        modifier = Modifier.padding(vertical = 5.dp),
                        onClick = {

                        })
                }
            }
        }
    }
}

@Preview
@Composable
fun BottomNavigationBar(
    onNavigateTo: (String) -> Unit = {},
    currentDestination: String? = "",
) {
    val item = listOf(
        BottomNavigationItems(
            appRoute = AppRoute.DashboardScreen.route,
            title = stringResource(R.string.nav_home),
            selectedIcon = ImageVector.vectorResource(id = R.drawable.active_home_ic),
            unSelectedIcon = ImageVector.vectorResource(id = R.drawable.home_icon)
        ), BottomNavigationItems(
            appRoute = AppRoute.CourseScreen.route,
            title = stringResource(R.string.nav_course),
            selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_selected_course),
            unSelectedIcon = ImageVector.vectorResource(id = R.drawable.course_icon)
        ), BottomNavigationItems(
            appRoute = AppRoute.ScreeningScreen.route,
            title = stringResource(R.string.nav_screening),
            selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_selected_screening),
            unSelectedIcon = ImageVector.vectorResource(id = R.drawable.screening_icon)
        ), BottomNavigationItems(
            appRoute = AppRoute.InterventionScreen.route,
            title = stringResource(R.string.nav_intervention),
            selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_selected_intervention),
            unSelectedIcon = ImageVector.vectorResource(id = R.drawable.intervention_icon)
        )
    )

    val local = LocalContext.current
    val activeColor = BorderBlue
    val inactiveColor = GrayLight01
    val context: Context = LocalContext.current


    NavigationBar(
        modifier = Modifier.padding(horizontal = 1.dp),
        containerColor = if (isSystemInDarkTheme()) {
            White
        } else {
            White
        },
        tonalElevation = 8.dp
    ) {
        item.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Box(
                        modifier = Modifier
                            .background(
                                color = if (currentDestination == screen.appRoute) MenuSelectedColor else Color.Transparent,
                                shape = RoundedCornerShape(70.dp) // curved rectangle
                            )
                            .padding(horizontal = 12.dp), // control size of background
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 5.dp),
                            imageVector = if (currentDestination == screen.appRoute) screen.selectedIcon else screen.unSelectedIcon,
                            contentDescription = null,
                            tint = Color.Unspecified,
                        )
                    }
                }, selected = currentDestination == screen.appRoute, onClick = {
                    if (currentDestination != screen.appRoute) {
                        onNavigateTo(screen.appRoute)
                    }
                }, label = {
                    Text(
                        text = screen.title,
                        fontStyle = FontStyle.Normal,
                        fontFamily = fontMedium,
                        color = if (currentDestination == screen.appRoute) activeColor else inactiveColor,
                        fontSize = 12.sp
                    )
                }, colors = NavigationBarItemDefaults.colors(
                    indicatorColor = White // disable default background
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationPreview() {
    MyApplicationTheme { }
}