@file:OptIn(ExperimentalMaterial3Api::class)

package com.pi.ProjectInclusion.android.screens.interventionScreens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.pi.ProjectInclusion.BannerColor03
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.DARK_BODY_TEXT
import com.pi.ProjectInclusion.DARK_TITLE_TEXT
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.Dark_02
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.GrayLight01
import com.pi.ProjectInclusion.GrayLight02
import com.pi.ProjectInclusion.LightGreen06
import com.pi.ProjectInclusion.PRIMARY_AURO_BLUE
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.PrimaryBlue3
import com.pi.ProjectInclusion.SemiTransparent
import com.pi.ProjectInclusion.White
import com.pi.ProjectInclusion.Yellow
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.BtnUi
import com.pi.ProjectInclusion.android.common_UI.CustomHorizontalProgressBar
import com.pi.ProjectInclusion.android.navigation.AppRoute
import com.pi.ProjectInclusion.android.screens.menu.TabItem
import com.pi.ProjectInclusion.android.utils.fontBold
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.fontRegular
import com.pi.ProjectInclusion.android.utils.fontSemiBold
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION

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
    var showDialog by remember { mutableStateOf(true) }

    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }
    }

    // This function will be change according to recommend
    if (showDialog) {
        InterventionIntroDialog {
            showDialog = false
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
                            fontSize = 14.sp
                        )
                    })
            }
        }

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
            userScrollEnabled = true,
        ) { index ->
            when (index) {
                0 -> {
                    PendingIntervention(navHostController)
                }

                1 -> {
                    InProgressIntervention(navHostController)
                }

                else -> {
                    CompletedIntervention(navHostController)
                }
            }
        }
    }
}

@Composable
fun InterventionIntroDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    if (isSystemInDarkTheme()) {
                        Dark_02
                    } else {
                        Color.White
                    }
                )
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(bottom = 16.dp, start = 8.dp, end = 8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.txt_Intervention_Introduction),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 16.dp, start = 8.dp, end = 8.dp),
                    fontFamily = fontBold,
                    fontSize = 18.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_TITLE_TEXT
                    } else {
                        Black
                    },
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.txt_Intervention_Introduction_Details),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    fontFamily = fontRegular,
                    fontSize = 14.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_TITLE_TEXT
                    } else {
                        Gray
                    },
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BtnUi(
                        onClick = {
                            onDismiss()
                        },
                        title = stringResource(R.string.text_continue),
                        enabled = true
                    )
                }
            }
        }
    }
}

@Composable
fun PendingIntervention(navHostController: NavHostController) {
    val interventionListData = listOf(
        InterventionData(
            "Abhisheki Muthuswami",
            "Class 6",
            "Start Intervention",
            "Continue Intervention",
            "Completed Intervention",
            "Progress achieved",
            0,
            painterResource(id = R.drawable.dummy_image)
        ),
        InterventionData(
            "Abhi Kothari",
            "Class 4",
            "Start Intervention",
            "Continue Intervention",
            "Completed Intervention",
            "Progress achieved",
            0,
            painterResource(id = R.drawable.dummy_image)
        ),
        InterventionData(
            "Hare Krishna",
            "Class 2",
            "Start Intervention",
            "Continue Intervention",
            "Completed Intervention",
            "Progress achieved",
            0,
            painterResource(id = R.drawable.dummy_image)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 8.dp)
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
                InterventionDataUI(interventionData, navHostController)
            }
        }
    }
}

@Composable
fun InterventionDataUI(interData: InterventionData, navHostController: NavHostController) {
    val selectedBorder = BorderStroke(
        width = 0.5.dp, if (isSystemInDarkTheme()) {
            Dark_02
        } else {
            GrayLight02
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp, bottom = 32.dp)
            .background(
                if (isSystemInDarkTheme()) {
                    Dark_01
                } else {
                    White
                }
            )
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = if (isSystemInDarkTheme()) {
                CardDefaults.cardColors(Yellow)
            } else {
                CardDefaults.cardColors(
                    containerColor = Yellow,
                    contentColor = Yellow,
                    disabledContentColor = Yellow,
                    disabledContainerColor = Yellow
                )
            }
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 3.dp)
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
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 6.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .width(90.dp)
                                .height(90.dp),
                            colors = if (isSystemInDarkTheme()) {
                                CardDefaults.cardColors(Dark_01)
                            } else {
                                CardDefaults.cardColors(
                                    containerColor = White,
                                    contentColor = White,
                                    disabledContentColor = White,
                                    disabledContainerColor = White
                                )
                            }
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(90.dp)
                                    .height(90.dp)
                                    .background(Color.Unspecified)
                            ) {
                                Image(
                                    painter = interData.image,
                                    contentDescription = IMG_DESCRIPTION,
                                    modifier = Modifier
                                        .width(90.dp)
                                        .height(90.dp)
                                        .background(Color.Unspecified),
                                    contentScale = ContentScale.Crop
                                )

                                Row(
                                    modifier = Modifier
                                        .height(30.dp)
                                        .fillMaxWidth()
                                        .background(SemiTransparent)
                                        .align(Alignment.BottomCenter),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.eye_open),
                                        contentDescription = IMG_DESCRIPTION,
                                        modifier = Modifier
                                            .size(30.dp)
                                            .padding(start = 8.dp, end = 4.dp)
                                            .background(Color.Unspecified),
                                    )

                                    Text(
                                        text = stringResource(R.string.txt_Profiler),
                                        modifier = Modifier
                                            .padding(start = 4.dp, end = 8.dp)
                                            .wrapContentWidth(),
                                        fontFamily = fontRegular,
                                        fontSize = 12.sp,
                                        color = if (isSystemInDarkTheme()) {
                                            DARK_TITLE_TEXT
                                        } else {
                                            DARK_TITLE_TEXT
                                        },
                                        textAlign = TextAlign.Start
                                    )
                                }
                            }
                        }

                        Column(
                            modifier = Modifier
                                .padding(
                                    start = 6.dp, end = 6.dp, top = 6.dp, bottom = 6.dp
                                )
                                .weight(1f), horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = interData.name,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp, end = 8.dp),
                                fontFamily = fontMedium,
                                fontSize = 16.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black
                                },
                                textAlign = TextAlign.Start
                            )

                            Text(
                                text = interData.grade,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp, end = 8.dp),
                                fontFamily = fontMedium,
                                fontSize = 12.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black
                                },
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                }
            }
        }

        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = if (isSystemInDarkTheme()) {
                CardDefaults.cardColors(PrimaryBlue3)
            } else {
                CardDefaults.cardColors(
                    containerColor = PrimaryBlue3,
                    contentColor = PrimaryBlue3,
                    disabledContentColor = PrimaryBlue3,
                    disabledContainerColor = PrimaryBlue3
                )
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .clickable {
                        navHostController.navigate(AppRoute.InterventionStudentDetails.route)
                    }
                    .background(PrimaryBlue3),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = interData.interventionStartStatus,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 16.dp, end = 8.dp),
                    fontFamily = fontMedium,
                    fontSize = 12.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_TITLE_TEXT
                    } else {
                        Black
                    },
                    textAlign = TextAlign.Start
                )

                Image(
                    painter = painterResource(R.drawable.round_right_arrow_img),
                    contentDescription = IMG_DESCRIPTION,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(start = 8.dp, end = 16.dp)
                        .background(Color.Unspecified)
                        .clickable {
                            navHostController.navigate(AppRoute.InterventionStudentDetails.route)
                        }
                )
            }
        }
    }
}

@Composable
fun InProgressIntervention(navHostController: NavHostController) {
    val interventionListData = listOf(
        InterventionData(
            "Abhisheki Muthuswami",
            "Class 6",
            "Start Intervention",
            "Continue Intervention",
            "Completed Intervention",
            "Progress achieved",
            6,
            painterResource(id = R.drawable.dummy_image)
        ),
        InterventionData(
            "Abhi Kothari",
            "Class 4",
            "Start Intervention",
            "Continue Intervention",
            "Completed Intervention",
            "Progress achieved",
            4,
            painterResource(id = R.drawable.dummy_image)
        ),
        InterventionData(
            "Hare Krishna",
            "Class 2",
            "Start Intervention",
            "Continue Intervention",
            "Completed Intervention",
            "Progress achieved",
            2,
            painterResource(id = R.drawable.dummy_image)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 8.dp)
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
                InterventionInProgressDataUI(interventionData, navHostController)
            }
        }
    }
}

@Composable
fun InterventionInProgressDataUI(
    inProgress: InterventionData,
    navHostController: NavHostController,
) {
    val selectedBorder = BorderStroke(
        width = 0.5.dp, if (isSystemInDarkTheme()) {
            Dark_02
        } else {
            GrayLight02
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp, bottom = 32.dp)
            .background(
                if (isSystemInDarkTheme()) {
                    Dark_01
                } else {
                    White
                }
            )
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = if (isSystemInDarkTheme()) {
                CardDefaults.cardColors(Yellow)
            } else {
                CardDefaults.cardColors(
                    containerColor = Yellow,
                    contentColor = Yellow,
                    disabledContentColor = Yellow,
                    disabledContainerColor = Yellow
                )
            }
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 3.dp)
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
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 6.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .width(90.dp)
                                .height(90.dp),
                            colors = if (isSystemInDarkTheme()) {
                                CardDefaults.cardColors(Dark_01)
                            } else {
                                CardDefaults.cardColors(
                                    containerColor = White,
                                    contentColor = White,
                                    disabledContentColor = White,
                                    disabledContainerColor = White
                                )
                            }
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(90.dp)
                                    .height(90.dp)
                                    .background(Color.Unspecified)
                            ) {
                                Image(
                                    painter = inProgress.image,
                                    contentDescription = IMG_DESCRIPTION,
                                    modifier = Modifier
                                        .width(90.dp)
                                        .height(90.dp)
                                        .background(Color.Unspecified),
                                    contentScale = ContentScale.Crop
                                )

                                Row(
                                    modifier = Modifier
                                        .height(30.dp)
                                        .fillMaxWidth()
                                        .background(SemiTransparent)
                                        .align(Alignment.BottomCenter),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.eye_open),
                                        contentDescription = IMG_DESCRIPTION,
                                        modifier = Modifier
                                            .size(30.dp)
                                            .padding(start = 8.dp, end = 4.dp)
                                            .background(Color.Unspecified),
                                    )

                                    Text(
                                        text = stringResource(R.string.txt_Profiler),
                                        modifier = Modifier
                                            .padding(start = 4.dp, end = 8.dp)
                                            .wrapContentWidth(),
                                        fontFamily = fontRegular,
                                        fontSize = 12.sp,
                                        color = if (isSystemInDarkTheme()) {
                                            DARK_TITLE_TEXT
                                        } else {
                                            DARK_TITLE_TEXT
                                        },
                                        textAlign = TextAlign.Start
                                    )
                                }
                            }
                        }

                        Column(
                            modifier = Modifier
                                .padding(
                                    start = 6.dp, end = 6.dp, top = 6.dp, bottom = 6.dp
                                )
                                .weight(1f), horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = inProgress.name,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp, end = 8.dp),
                                fontFamily = fontMedium,
                                fontSize = 16.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black
                                },
                                textAlign = TextAlign.Start
                            )

                            Text(
                                text = inProgress.grade,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp, end = 8.dp),
                                fontFamily = fontMedium,
                                fontSize = 12.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black
                                },
                                textAlign = TextAlign.Start
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 2.dp)
                                    .wrapContentHeight(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = inProgress.inProgressAchieved,
                                    modifier = Modifier
                                        .wrapContentWidth(),
                                    fontFamily = fontRegular,
                                    fontSize = 14.sp,
                                    color = if (isSystemInDarkTheme()) {
                                        DARK_TITLE_TEXT
                                    } else {
                                        Black
                                    },
                                    textAlign = TextAlign.Start
                                )

                                Text(
                                    text = "${inProgress.inProgressNum}%",
                                    modifier = Modifier
                                        .wrapContentWidth(),
                                    fontFamily = fontMedium,
                                    fontSize = 14.sp,
                                    color = if (isSystemInDarkTheme()) {
                                        DARK_TITLE_TEXT
                                    } else {
                                        Black
                                    },
                                    textAlign = TextAlign.Start
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
                                    .wrapContentHeight(), horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                if (inProgress.inProgressNum != null) {
                                    val num: Float = (inProgress.inProgressNum.toFloat() / 100)
                                    CustomHorizontalProgressBar(num, LightGreen06, BannerColor03)
                                } else {
                                    CustomHorizontalProgressBar(0.0f, LightGreen06, BannerColor03)
                                }
                            }
                        }
                    }
                }
            }
        }

        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = if (isSystemInDarkTheme()) {
                CardDefaults.cardColors(PrimaryBlue3)
            } else {
                CardDefaults.cardColors(
                    containerColor = PrimaryBlue3,
                    contentColor = PrimaryBlue3,
                    disabledContentColor = PrimaryBlue3,
                    disabledContainerColor = PrimaryBlue3
                )
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .background(PrimaryBlue3),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = inProgress.interventionContinueStatus,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 16.dp, end = 8.dp),
                    fontFamily = fontMedium,
                    fontSize = 12.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_TITLE_TEXT
                    } else {
                        Black
                    },
                    textAlign = TextAlign.Start
                )

                Image(
                    painter = painterResource(R.drawable.round_right_arrow_img),
                    contentDescription = IMG_DESCRIPTION,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(start = 8.dp, end = 16.dp)
                        .background(Color.Unspecified)
                )
            }
        }
    }
}

@Composable
fun CompletedIntervention(navHostController: NavHostController) {
    val interventionListData = listOf(
        InterventionData(
            "Abhisheki Muthuswami",
            "Class 6",
            "Start Intervention",
            "Continue Intervention",
            "Completed Intervention",
            "Progress achieved",
            100,
            painterResource(id = R.drawable.dummy_image)
        ),
        InterventionData(
            "Abhi Kothari",
            "Class 4",
            "Start Intervention",
            "Continue Intervention",
            "Completed Intervention",
            "Progress achieved",
            100,
            painterResource(id = R.drawable.dummy_image)
        ),
        InterventionData(
            "Hare Krishna",
            "Class 2",
            "Start Intervention",
            "Continue Intervention",
            "Continue Intervention",
            "Progress achieved",
            100,
            painterResource(id = R.drawable.dummy_image)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 8.dp)
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
                InterventionCompletedDataUI(interventionData, navHostController)
            }
        }
    }
}

@Composable
fun InterventionCompletedDataUI(completedData: InterventionData, navigation: NavHostController) {
    val selectedBorder = BorderStroke(
        width = 0.5.dp, if (isSystemInDarkTheme()) {
            Dark_02
        } else {
            GrayLight02
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp, bottom = 32.dp)
            .background(
                if (isSystemInDarkTheme()) {
                    Dark_01
                } else {
                    White
                }
            )
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = if (isSystemInDarkTheme()) {
                CardDefaults.cardColors(Yellow)
            } else {
                CardDefaults.cardColors(
                    containerColor = Yellow,
                    contentColor = Yellow,
                    disabledContentColor = Yellow,
                    disabledContainerColor = Yellow
                )
            }
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 3.dp)
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
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 6.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .width(90.dp)
                                .height(100.dp),
                            colors = if (isSystemInDarkTheme()) {
                                CardDefaults.cardColors(Dark_01)
                            } else {
                                CardDefaults.cardColors(
                                    containerColor = White,
                                    contentColor = White,
                                    disabledContentColor = White,
                                    disabledContainerColor = White
                                )
                            }
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(100.dp)
                                    .background(Color.Unspecified)
                            ) {
                                Image(
                                    painter = completedData.image,
                                    contentDescription = IMG_DESCRIPTION,
                                    modifier = Modifier
                                        .width(100.dp)
                                        .height(100.dp)
                                        .background(Color.Unspecified),
                                    contentScale = ContentScale.Crop
                                )

                                Row(
                                    modifier = Modifier
                                        .height(30.dp)
                                        .fillMaxWidth()
                                        .background(SemiTransparent)
                                        .align(Alignment.BottomCenter),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.eye_open),
                                        contentDescription = IMG_DESCRIPTION,
                                        modifier = Modifier
                                            .size(30.dp)
                                            .padding(start = 8.dp, end = 4.dp)
                                            .background(Color.Unspecified),
                                    )

                                    Text(
                                        text = stringResource(R.string.txt_Profiler),
                                        modifier = Modifier
                                            .padding(start = 4.dp, end = 8.dp)
                                            .wrapContentWidth(),
                                        fontFamily = fontRegular,
                                        fontSize = 12.sp,
                                        color = if (isSystemInDarkTheme()) {
                                            DARK_TITLE_TEXT
                                        } else {
                                            DARK_TITLE_TEXT
                                        },
                                        textAlign = TextAlign.Start
                                    )
                                }
                            }
                        }

                        Column(
                            modifier = Modifier
                                .padding(
                                    start = 6.dp, end = 6.dp, top = 6.dp, bottom = 6.dp
                                )
                                .weight(1f), horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = completedData.name,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp, end = 8.dp),
                                fontFamily = fontMedium,
                                fontSize = 16.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black
                                },
                                textAlign = TextAlign.Start
                            )

                            Text(
                                text = completedData.grade,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp, end = 8.dp),
                                fontFamily = fontMedium,
                                fontSize = 12.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black
                                },
                                textAlign = TextAlign.Start
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 2.dp)
                                    .wrapContentHeight(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = completedData.inProgressAchieved,
                                    modifier = Modifier
                                        .wrapContentWidth(),
                                    fontFamily = fontRegular,
                                    fontSize = 14.sp,
                                    color = if (isSystemInDarkTheme()) {
                                        DARK_TITLE_TEXT
                                    } else {
                                        Black
                                    },
                                    textAlign = TextAlign.Start
                                )

                                Text(
                                    text = "${completedData.inProgressNum}%",
                                    modifier = Modifier
                                        .wrapContentWidth(),
                                    fontFamily = fontMedium,
                                    fontSize = 14.sp,
                                    color = if (isSystemInDarkTheme()) {
                                        DARK_TITLE_TEXT
                                    } else {
                                        Black
                                    },
                                    textAlign = TextAlign.Start
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
                                    .wrapContentHeight(), horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                if (completedData.inProgressNum != null) {
                                    val num: Float = (completedData.inProgressNum.toFloat() / 100)
                                    CustomHorizontalProgressBar(num, LightGreen06, BannerColor03)
                                } else {
                                    CustomHorizontalProgressBar(0.0f, LightGreen06, BannerColor03)
                                }
                            }
                        }
                    }
                }
            }
        }

        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = if (isSystemInDarkTheme()) {
                CardDefaults.cardColors(PrimaryBlue3)
            } else {
                CardDefaults.cardColors(
                    containerColor = PrimaryBlue3,
                    contentColor = PrimaryBlue3,
                    disabledContentColor = PrimaryBlue3,
                    disabledContainerColor = PrimaryBlue3
                )
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .background(PrimaryBlue3),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = completedData.interventionCompletedStatus,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 16.dp, end = 8.dp),
                    fontFamily = fontMedium,
                    fontSize = 12.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_TITLE_TEXT
                    } else {
                        Black
                    },
                    textAlign = TextAlign.Start
                )

                Image(
                    painter = painterResource(R.drawable.okay_check_img),
                    contentDescription = IMG_DESCRIPTION,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(start = 8.dp, end = 16.dp)
                        .background(Color.Unspecified)
                )
            }
        }
    }
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
    var interventionStartStatus: String,
    var interventionContinueStatus: String,
    var interventionCompletedStatus: String,
    var inProgressAchieved: String,
    var inProgressNum: Int,
    var image: Painter,
)
