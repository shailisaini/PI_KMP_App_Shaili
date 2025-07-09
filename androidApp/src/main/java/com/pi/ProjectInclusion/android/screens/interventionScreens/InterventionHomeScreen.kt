@file:OptIn(ExperimentalMaterial3Api::class)

package com.pi.ProjectInclusion.android.screens.interventionScreens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.DARK_BODY_TEXT
import com.pi.ProjectInclusion.DARK_TITLE_TEXT
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.Dark_02
import com.pi.ProjectInclusion.GrayLight01
import com.pi.ProjectInclusion.GrayLight02
import com.pi.ProjectInclusion.PRIMARY_AURO_BLUE
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.White
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.screens.menu.TabItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InterventionHomeScreen(navHostController: NavHostController) {

    val tabItems = listOf(
        TabItem(
            title = stringResource(id = R.string.txt_Pending_1)
        ), TabItem(
            title = stringResource(id = R.string.txt_In_progress_1)
        ), TabItem(
            title = stringResource(id = R.string.txt_Completed)
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
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                if (isSystemInDarkTheme()) {
                    Dark_01
                } else {
                    White
                }
            )
    ) {
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex, edgePadding = 0.dp
        ) {
            tabItems.forEachIndexed { index, tabItem ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = { selectedTabIndex = index },
                    modifier = Modifier.background(
                        if (isSystemInDarkTheme()) {
                            Dark_01
                        } else {
                            White
                        }
                    ),
                    text = {
                        Text(
                            text = tabItem.title,
                            modifier = Modifier.padding(vertical = 5.dp),
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
                            fontFamily = FontFamily(
                                Font(R.font.inter_semi_bold, FontWeight.SemiBold)
                            ),
                            fontSize = 14.sp
                        )
                    })
            }
        }

        Column(
            modifier = Modifier
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
                        PendingIntervention()
                    }

                    1 -> {
                        InProgressIntervention()
                    }

                    else -> {
                        CompletedIntervention()
                    }
                }
            }
        }
    }
}

@Composable
fun PendingIntervention() {
    val interventionListData = listOf(
        InterventionData(
            "Abhisheki Muthuswami",
            "Class 6",
            "Start Intervention",
            painterResource(id = R.drawable.dialog_bg)
        ),
        InterventionData(
            "Abhishek Muthut",
            "Class 5",
            "Start Intervention",
            painterResource(id = R.drawable.dialog_bg)
        ),
        InterventionData(
            "Abhi Kothari",
            "Class 4",
            "Start Intervention",
            painterResource(id = R.drawable.dialog_bg)
        ),
        InterventionData(
            "Anup Dev",
            "Class 3",
            "Start Intervention",
            painterResource(id = R.drawable.dialog_bg)
        ),
        InterventionData(
            "Hare Krishna",
            "Class 2",
            "Start Intervention",
            painterResource(id = R.drawable.dialog_bg)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 16.dp)
            .background(
                if (isSystemInDarkTheme()) {
                    Dark_01
                } else {
                    White
                }
            )
    ) {
        LazyColumn {
            items(interventionListData) { interventionData ->
                InterventionDataUI(interventionData)
            }
        }
    }
}

@Composable
fun InterventionDataUI(interData: InterventionData) {
    val selectedBorder = BorderStroke(
        width = 0.5.dp, if (isSystemInDarkTheme()) {
            Dark_02
        } else {
            GrayLight02
        }
    )

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = if (isSystemInDarkTheme()) {
            CardDefaults.cardColors(Dark_02)
        } else {
            CardDefaults.cardColors(
                containerColor = White,
                contentColor = White,
                disabledContentColor = White,
                disabledContainerColor = White
            )
        },
        border = selectedBorder
    ) {
        Column(
            modifier = Modifier.padding(
                start = 5.dp, end = 5.dp, top = 5.dp, bottom = 5.dp
            ), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.padding(horizontal = 5.dp)) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .padding(horizontal = 5.dp, vertical = 10.dp)
                        .width(100.dp)
                        .wrapContentHeight()
                        .weight(1f),
                    colors = if (isSystemInDarkTheme()) {
                        CardDefaults.cardColors(Dark_01)
                    } else {
                        CardDefaults.cardColors(
                            containerColor = White,
                            contentColor = White,
                            disabledContentColor = White,
                            disabledContainerColor = White
                        )
                    },
                    border = selectedBorder,
                ) {
                    Box(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .background(Color.Unspecified)
                    ) {
                        Image(
                            painter = interData.image,
                            contentDescription = "logo",
                            modifier = Modifier
                                .wrapContentHeight()
                                .fillMaxWidth()
                                .background(Color.Unspecified),
                            contentScale = ContentScale.Crop
                        )

                        Image(
                            painter = painterResource(R.drawable.okay_check_img),
                            contentDescription = "logo",
                            modifier = Modifier
                                .height(75.dp)
                                .fillMaxWidth()
                                .background(Color.Unspecified)
                                .align(Alignment.BottomCenter),
                        )
                    }
                }

                Column(
                    modifier = Modifier.padding(
                        start = 5.dp, end = 5.dp, top = 5.dp, bottom = 5.dp
                    ), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = interData.name,
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(start = 8.dp, end = 8.dp),
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = if (isSystemInDarkTheme()) {
                            DARK_TITLE_TEXT
                        } else {
                            Black
                        },
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = interData.grade,
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(start = 8.dp, end = 8.dp),
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = if (isSystemInDarkTheme()) {
                            DARK_TITLE_TEXT
                        } else {
                            Black
                        },
                        textAlign = TextAlign.Center
                    )
                }
            }

            Row(modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(
                    text = interData.interventionStatus,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_TITLE_TEXT
                    } else {
                        Black
                    },
                    textAlign = TextAlign.Center
                )

                Image(
                    painter = painterResource(R.drawable.okay_check_img),
                    contentDescription = "logo",
                    modifier = Modifier
                        .height(75.dp)
                        .fillMaxWidth()
                        .background(Color.Unspecified)
                )
            }
        }
    }
}

@Composable
fun InProgressIntervention() {
    println("Not yet implemented:- InProgressIntervention()")
}

@Composable
fun CompletedIntervention() {
    println("Not yet implemented:- CompletedIntervention()")
}

@Preview(showBackground = true)
@Composable
fun InterventionHomeScreenPreview() {
    val navController = rememberNavController()
    InterventionHomeScreen(navController)
}

data class InterventionData(
    var name: String,
    var grade: String,
    var interventionStatus: String,
    var image: Painter,
)
