package com.pi.ProjectInclusion.android.screens.dashboardNavActivity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.DARK_BODY_TEXT
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.GrayLight01
import com.pi.ProjectInclusion.PRIMARY_AURO_BLUE
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.White
import com.pi.ProjectInclusion.android.MyApplicationTheme
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.DetailsNoImgBackgroundUi
import com.pi.ProjectInclusion.android.screens.StudentDashboardActivity
import com.pi.ProjectInclusion.android.screens.menu.TabItem
import com.pi.ProjectInclusion.android.utils.fontSemiBold
import com.pi.ProjectInclusion.constants.BackHandler

class ZoomMeetingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            logger.d("Screen: " + "ZoomMeetingListScreen()")
            val context = LocalContext.current

            BackHandler {
                startActivity(
                    context, Intent(context, StudentDashboardActivity::class.java), null
                ).apply { (context as? Activity)?.finish() }
            }

            MyApplicationTheme {
                logger.d("Screen: " + "ZoomMeetingActivity()")
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize()
                        .background(
                            color = if (isSystemInDarkTheme()) {
                                Dark_01
                            } else {
                                White
                            }
                        ), verticalArrangement = Arrangement.Top
                ) {
                    ShowZoomMeetingData(context)
                }
            }
        }
    }
}

@Composable
private fun ShowZoomMeetingData(context: Context) {

    BackHandler {
        startActivity(
            context, Intent(context, StudentDashboardActivity::class.java), null
        ).apply { (context as? Activity)?.finish() }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .padding(top = 5.dp)
            .background(
                color = if (isSystemInDarkTheme()) {
                    Dark_01
                } else {
                    White
                }
            ), verticalArrangement = Arrangement.Top
    ) {
        DetailsNoImgBackgroundUi(
            backgroundColor = if (isSystemInDarkTheme()) {
                Dark_01
            } else {
                White
            },
            textColor = Black,
            pageTitle = stringResource(R.string.meeting_txt),
            moreInfoIcon = painterResource(id = R.drawable.vertical_dot),
            isShowBackButton = true,
            isShowMoreInfo = false,
            onBackButtonClick = {
                startActivity(
                    context, Intent(context, StudentDashboardActivity::class.java), null
                ).apply { (context as? Activity)?.finish() }
            },
            onMoreInfoClick = {},
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(
                            if (isSystemInDarkTheme()) {
                                Dark_01
                            } else {
                                White
                            }
                        )
                ) {
                    SectionDivider()

                    val tabItems = listOf(
                        TabItem(
                            title = stringResource(id = R.string.key_upcoming)
                        ), TabItem(
                            title = stringResource(id = R.string.key_past)
                        )
                    )
                    var selectedTabIndex by remember { mutableIntStateOf(0) }
                    val pagerState = rememberPagerState { tabItems.size }

                    LaunchedEffect(selectedTabIndex) {
                        pagerState.animateScrollToPage(selectedTabIndex)
                    }

                    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
                        if (!pagerState.isScrollInProgress) {
                            selectedTabIndex = pagerState.currentPage
                            println("selected Tab Index :- $selectedTabIndex")
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxSize()
                            .background(
                                if (isSystemInDarkTheme()) {
                                    Dark_01
                                } else {
                                    White
                                }
                            )
                    ) {
                        TabRow(
                            selectedTabIndex = selectedTabIndex, Modifier.padding(0.dp)
                        ) {
                            tabItems.forEachIndexed { index, tabItem ->
                                Tab(
                                    selected = index == selectedTabIndex,
                                    onClick = { selectedTabIndex = index },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            if (isSystemInDarkTheme()) {
                                                Dark_01
                                            } else {
                                                White
                                            }
                                        ),
                                    text = {
                                        Text(
                                            text = tabItem.title,
                                            color = if (index == selectedTabIndex) {
                                                if (isSystemInDarkTheme()) {
                                                    PRIMARY_AURO_BLUE
                                                } else {
                                                    PrimaryBlue
                                                }
                                            } else {
                                                if (isSystemInDarkTheme()) {
                                                    DARK_BODY_TEXT
                                                } else {
                                                    GrayLight01
                                                }
                                            },
                                            fontFamily = fontSemiBold,
                                            fontSize = 14.sp,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    })
                            }
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxSize()
                                .background(
                                    if (isSystemInDarkTheme()) {
                                        Dark_01
                                    } else {
                                        White
                                    }
                                )
                        ) {
                            HorizontalPager(
                                state = pagerState,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxSize()
                                    .background(
                                        if (isSystemInDarkTheme()) {
                                            Dark_01
                                        } else {
                                            White
                                        }
                                    ),
                                userScrollEnabled = false,
                            ) { index ->
                                when (index) {
                                    0 -> {
                                        AllUpcomingMeetingScreening()
                                    }

                                    1 -> {
                                        AllPastMeetingScreening()
                                    }
                                }
                            }
                        }
                    }
                }
            })
    }
}

@Composable
fun AllUpcomingMeetingScreening() {
    println("Not yet implemented")
}

@Composable
fun AllPastMeetingScreening() {
    println("Not yet implemented")
}