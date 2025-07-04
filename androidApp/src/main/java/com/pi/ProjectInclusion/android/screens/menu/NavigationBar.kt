package com.pi.ProjectInclusion.android.screens.menu

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Canvas
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.Transparent
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.GrayLight01
import com.pi.ProjectInclusion.GrayLight02
import com.pi.ProjectInclusion.GreenDark01
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.White
import com.pi.ProjectInclusion.android.MyApplicationTheme
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.navigation.AppRoute


@Composable
fun DrawerHeader(
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Open),
    onItemClick: () -> Unit = {},
) {
    val colors = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .fillMaxWidth()/* .clickable {
                onItemClick.invoke()
            }*/

            .wrapContentHeight()
            .padding(end = 50.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .height(220.dp)

                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF2C3EA2), Color(0xFF101942))
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
                    .clickable { onItemClick.invoke() },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Box(
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp, top = 30.dp)

                )
                {
                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.Center)

                    ) {
                        Image(
                            painter = painterResource(R.drawable.profile_user_icon),
                            contentDescription = "logo",
                            modifier = Modifier
                                .clip(CircleShape) // Add clip modifier to make the image circular
                                .background(shape = CircleShape, color = Transparent)
                                .border( // Add border modifier to make image stand out
                                    width = 1.dp, color = GreenDark01, shape = CircleShape
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
                    var textName: String = ""
                    var textEmail: String = ""
                    textName = "Aman Sharma"

                    //  email
                    textEmail = "Teacher"
                    Text(
                        text = textName, style = MaterialTheme.typography.bodyMedium.copy(
                            /* color = Black,*/  color = colors.onSurface,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(
                        text = textEmail, style = MaterialTheme.typography.bodyMedium.copy(
                            color = Gray, fontSize = 12.sp, fontWeight = FontWeight.Normal
                        )
                    )
                }

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp,bottom = 20.dp, top = 20.dp)
                    .height(35.dp)

            ) {
                Card(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight(),
                    shape = RoundedCornerShape(4.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                    // Do not set containerColor, let the Box inside handle the gradient
                ){
                    Text ( modifier = Modifier.wrapContentWidth()
                        .padding(10.dp),
                        text = "Edit Profile",
                        color = Black,
                        fontSize = 12.sp// Make sure text is readable on gradient
                    )
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

    LazyColumn(
        modifier
            .padding(end = 50.dp)
            .zIndex(1f)
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
                    .padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 15.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = "",
                    tint = Color.Unspecified,
                )
                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = item.title,
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                        .weight(1f),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = Black,
                        fontSize = 14.sp,
                    ),
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.arrow_nxt_ic),
                    contentDescription = "",
                    tint = Color.Unspecified
                )
            }
        }
    }
}

//@Preview
@Composable
fun BottomNavigationBar(
    navController: NavHostController = rememberNavController(),
    currentDestination: String? = "",
) {
    val colors = MaterialTheme.colorScheme
    val item = listOf(
        BottomNavigationItems(
            appRoute = AppRoute.DashboardScreen.route,
            title = "Home",
            selectedIcon = ImageVector.vectorResource(id = R.drawable.home_icon),
            unSelectedIcon = ImageVector.vectorResource(id = R.drawable.active_home_ic)
        ), BottomNavigationItems(
            appRoute = AppRoute.CourseScreen.route,
            title = "Courses",
            selectedIcon = ImageVector.vectorResource(id = R.drawable.course_icon),
            unSelectedIcon = ImageVector.vectorResource(id = R.drawable.course_icon)
        ), BottomNavigationItems(
            appRoute = AppRoute.ScreeningScreen.route,
            title = "Screening",
            selectedIcon = ImageVector.vectorResource(id = R.drawable.screening_icon),
            unSelectedIcon = ImageVector.vectorResource(id = R.drawable.screening_icon)
        ), BottomNavigationItems(
            appRoute = AppRoute.InterventionScreen.route,
            title = "Intervention",
            selectedIcon = ImageVector.vectorResource(id = R.drawable.intervention_icon),
            unSelectedIcon = ImageVector.vectorResource(id = R.drawable.intervention_icon)
        )
    )

    val local = LocalContext.current
    val activeColor = PrimaryBlue
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
                    Log.d("SelectedScreen: ", "" + screen.appRoute)
                    when (screen.appRoute) {
                        AppRoute.DashboardScreen.route -> Icon(
                            imageVector = screen.selectedIcon,
                            contentDescription = null,
                            tint = if (currentDestination == screen.appRoute) activeColor else inactiveColor,
                            modifier = Modifier.size(30.dp)
                        )

                        AppRoute.CourseScreen.route -> Icon(
                            imageVector = screen.selectedIcon,
                            contentDescription = null,
                            tint = if (currentDestination == screen.appRoute) activeColor else inactiveColor,
                            modifier = Modifier.size(30.dp)
                        )

                        AppRoute.ScreeningScreen.route -> Icon(
                            imageVector = screen.selectedIcon,
                            contentDescription = null,
                            tint = if (currentDestination == screen.appRoute) activeColor else inactiveColor,
                            modifier = Modifier.size(30.dp)
                        )

                        AppRoute.InterventionScreen.route -> Icon(
                            imageVector = screen.selectedIcon,
                            contentDescription = null,
                            tint = if (currentDestination == screen.appRoute) activeColor else inactiveColor,
                            modifier = Modifier.size(30.dp)
                        )



                        else -> {
                            Icon(
                                imageVector = screen.selectedIcon,
                                contentDescription = null,
                                tint = if (currentDestination == screen.appRoute) activeColor else inactiveColor,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }, selected = currentDestination == screen.appRoute, onClick = {
                    if (screen.appRoute == "student_wallet") {
                        navController.navigate(screen.appRoute)

                    } else {
                        navController.navigate(screen.appRoute)
                    }
                }, label = {
                    Text(
                        text = screen.title,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.SemiBold,
                        color = if (currentDestination == screen.appRoute) activeColor else inactiveColor,
                        fontSize = 10.sp
                    )
                }, colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent // Removes background color when selected
                )/* selectedColor = activeColor,
                 unselectedContentColor = inactiveColor*/
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationPreview() {
    MyApplicationTheme {  }
}