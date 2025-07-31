package com.pi.ProjectInclusion.android.screens.screeningScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.BannerColor03
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.BlueBackground3
import com.pi.ProjectInclusion.DARK_BODY_TEXT
import com.pi.ProjectInclusion.DARK_TITLE_TEXT
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.Dark_02
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.GrayLight01
import com.pi.ProjectInclusion.GrayLight02
import com.pi.ProjectInclusion.LightGreen06
import com.pi.ProjectInclusion.LightOrange2
import com.pi.ProjectInclusion.PRIMARY_AURO_BLUE
import com.pi.ProjectInclusion.Pink80
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.PrimaryBlue3
import com.pi.ProjectInclusion.SemiTransparent
import com.pi.ProjectInclusion.White
import com.pi.ProjectInclusion.Yellow
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.BtnUi
import com.pi.ProjectInclusion.android.common_UI.BtnWithRightIconUi
import com.pi.ProjectInclusion.android.common_UI.CustomHorizontalProgressBar
import com.pi.ProjectInclusion.android.common_UI.YesBtnUi
import com.pi.ProjectInclusion.android.screens.menu.TabItem
import com.pi.ProjectInclusion.android.utils.fontBold
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.fontRegular
import com.pi.ProjectInclusion.android.utils.fontSemiBold
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScreeningHomeScreen(addStudent: () -> Unit, onBack: () -> Unit, screeningOne: () -> Unit) {

    logger.d("Screen: " + "ScreeningHomeScreen()")

    val selectedBorder = BorderStroke(
        width = 0.5.dp, if (isSystemInDarkTheme()) {
            BlueBackground3
        } else {
            BlueBackground3
        }
    )

    val tabItems = listOf(
        TabItem(
            title = stringResource(id = R.string.txt_All_screening)
        ), TabItem(
            title = stringResource(id = R.string.txt_screening_1)
        ), TabItem(
            title = stringResource(id = R.string.txt_Profiler_0)
        ), TabItem(
            title = stringResource(id = R.string.txt_Advance_Screening)
        )
    )
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState { tabItems.size }
    var showDialog by remember { mutableStateOf(true) }
    var showTermDialog by remember { mutableStateOf(false) }

    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
            println("selected Tab Index :- $selectedTabIndex")
        }
    }

    // This function will be change according to recommend
    if (showDialog) {
        ScreeningIntroDialog {
            showDialog = false
        }
    }

    if (showTermDialog) {
        ScreeningTermsDialog(addStudent) {
            showTermDialog = false
        }
    }

    Box(
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
        // This is used for add new students while come for first time screening
       /* Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    if (isSystemInDarkTheme()) {
                        Dark_01
                    } else {
                        White
                    }
                ), verticalArrangement = Arrangement.Center
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                    .wrapContentHeight(),
                colors = if (isSystemInDarkTheme()) {
                    CardDefaults.cardColors(BlueBackground3)
                } else {
                    CardDefaults.cardColors(
                        containerColor = BlueBackground3,
                        contentColor = BlueBackground3,
                        disabledContentColor = BlueBackground3,
                        disabledContainerColor = BlueBackground3
                    )
                },
                border = selectedBorder
            ) {
                Column(
                    modifier = Modifier.padding(
                        start = 5.dp, end = 5.dp, top = 5.dp, bottom = 5.dp
                    ), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.txt_add_Student_Screening),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 16.dp, start = 8.dp, end = 8.dp),
                        fontFamily = fontBold,
                        fontSize = 16.sp,
                        color = if (isSystemInDarkTheme()) {
                            DARK_TITLE_TEXT
                        } else {
                            Black
                        },
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = stringResource(R.string.txt_No_Student_been_added),
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

                    Spacer(modifier = Modifier.height(8.dp))

                    Column(
                        modifier = Modifier
                            .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
                            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        YesBtnUi(
                            onClick = {
                                showTermDialog = true
                            },
                            title = stringResource(R.string.txt_Add_Student),
                            modifier = Modifier.width(165.dp),
                            enabled = true
                        )
                    }
                }
            }
        }*/

        // This is used for tab and student list for all, screening, profiler and advanced screening
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
                                text = tabItem.title, color = if (index == selectedTabIndex) {
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
                                }, fontFamily = fontSemiBold, fontSize = 14.sp
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
                    userScrollEnabled = true,
                ) { index ->
                    when (index) {
                        0 -> {
                            AllScreening()
                        }

                        1 -> {
                            ScreeningFirst(screeningOne = screeningOne)
                        }

                        2 -> {
                            ProfilerScreening()
                        }

                        else -> {
                            AdvanceScreening()
                        }
                    }
                }
            }
        }

        // This is used for add new students for screening
        if (selectedTabIndex == 1) {
            FloatingActionButton(
                onClick = {
                    showTermDialog = true
                },
                containerColor = LightOrange2,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(),
                modifier = Modifier
                    .align(alignment = Alignment.BottomCenter)
                    .padding(bottom = 100.dp)
                    .size(45.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.add_plus_img),
                    contentDescription = IMG_DESCRIPTION,
                    modifier = Modifier.background(Color.Unspecified),
                )
            }
        }
    }
}

@Composable
fun AllScreening() {
    println("Not yet implemented :- AllScreening")
}

@Composable
fun ScreeningFirst(screeningOne: () -> Unit) {
    val screeningListData = listOf(
        ScreeningData(
            stringResource(R.string.txt_Abhisheki_Muthuswami),
            stringResource(R.string.txt_Class_6),
            stringResource(R.string.txt_Start_screening),
            stringResource(R.string.txt_Continue_with_screening_1),
            stringResource(R.string.txt_Completed_screening),
            stringResource(R.string.txt_Inprogress),
            6,
            painterResource(id = R.drawable.dummy_image)
        ), ScreeningData(
            stringResource(R.string.txt_Abhi_Kothari),
            stringResource(R.string.txt_Class_4),
            stringResource(R.string.txt_Start_screening),
            stringResource(R.string.txt_Continue_with_screening_1),
            stringResource(R.string.txt_Completed_screening),
            stringResource(R.string.txt_Inprogress),
            4,
            painterResource(id = R.drawable.dummy_image)
        ), ScreeningData(
            stringResource(R.string.txt_Hare_Krishna),
            stringResource(R.string.txt_Class_2),
            stringResource(R.string.txt_Start_screening),
            stringResource(R.string.txt_Continue_with_screening_1),
            stringResource(R.string.txt_Completed_screening),
            stringResource(R.string.txt_Inprogress),
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
            items(screeningListData) { screeningData ->
                ScreeningFirstDataUI(screeningData, screeningOne)
            }
        }
    }
}

@Composable
fun ScreeningFirstDataUI(
    screeningData: ScreeningData,
    screeningOne: () -> Unit,
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
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 6.dp),
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
                                    painter = screeningData.image,
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
                                .weight(1f),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = screeningData.name,
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
                                text = screeningData.grade,
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
                                    text = screeningData.inProgressAchieved,
                                    modifier = Modifier.wrapContentWidth(),
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
                                    text = "${screeningData.inProgressNum}%",
                                    modifier = Modifier.wrapContentWidth(),
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
                                    .wrapContentHeight(),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                if (screeningData.inProgressNum != null) {
                                    val num: Float = (screeningData.inProgressNum.toFloat() / 100)
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
                    text = screeningData.interventionContinueStatus,
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
                            screeningOne()
                        })
            }
        }
    }
}

@Composable
fun ProfilerScreening() {
    println("Not yet implemented :- ProfilerScreening")
}

@Composable
fun AdvanceScreening() {
    println("Not yet implemented :- AdvanceScreening")
}

@Composable
fun ScreeningIntroDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Box(
            modifier = Modifier
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
                    .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.txt_Phase_Introduction),
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
                    text = stringResource(R.string.txt_Phase_designed_desc),
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

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontFamily = fontBold)) {
                            append(stringResource(R.string.txt_Screening_Identify) + " ")
                        }
                        withStyle(style = SpanStyle(fontFamily = fontRegular)) {
                            append(stringResource(R.string.txt_Screening_Identify_student))
                        }
                    },
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 8.dp),
                    fontSize = 14.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_TITLE_TEXT
                    } else {
                        Gray
                    },
                    textAlign = TextAlign.Center
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontFamily = fontBold)) {
                            append(stringResource(R.string.txt_Profiler_data_analyze_student) + " ")
                        }
                        withStyle(style = SpanStyle(fontFamily = fontRegular)) {
                            append(stringResource(R.string.txt_Profiler_Gather_data_analyze_student))
                        }
                    },
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 8.dp),
                    fontSize = 14.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_TITLE_TEXT
                    } else {
                        Gray
                    },
                    textAlign = TextAlign.Center
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontFamily = fontBold)) {
                            append(stringResource(R.string.txt_Advanced_Screening_Conduct) + " ")
                        }
                        withStyle(style = SpanStyle(fontFamily = fontRegular)) {
                            append(stringResource(R.string.txt_Advanced_Screening_Conduct_detailed))
                        }
                    },
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 8.dp),
                    fontSize = 14.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_TITLE_TEXT
                    } else {
                        Gray
                    },
                    textAlign = TextAlign.Center
                )

                Text(
                    text = stringResource(R.string.txt_developed_reviewed_professionals),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 8.dp),
                    fontSize = 14.sp,
                    fontFamily = fontRegular,
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
                        .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BtnUi(
                        onClick = {
                            onDismiss()
                        }, title = stringResource(R.string.txt_Got_It), enabled = true
                    )
                }
            }
        }
    }
}

@Composable
fun ScreeningTermsDialog(addStudent: () -> Unit, onDismiss: () -> Unit) {

    var isCheckedTermsAccept by remember { mutableStateOf(false) }
    var termsAcceptStr = stringResource(R.string.txt_Please_Check)
    val context = LocalContext.current

    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp), color = if (isSystemInDarkTheme()) {
                    Dark_02
                } else {
                    White
                }, modifier = Modifier.padding(4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.txt_Screening),
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

                    Row(
                        modifier = Modifier
                            .padding(
                                start = 8.dp, end = 8.dp, bottom = 8.dp, top = 8.dp
                            )
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "1.",
                            modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                            textAlign = TextAlign.Start,
                            fontFamily = fontRegular,
                            fontSize = 14.sp,
                            color = if (isSystemInDarkTheme()) {
                                DARK_BODY_TEXT
                            } else {
                                Gray
                            }
                        )

                        Text(
                            text = stringResource(R.string.txt_Screening_checklist_tool),
                            textAlign = TextAlign.Start,
                            fontFamily = fontRegular,
                            fontSize = 14.sp,
                            color = if (isSystemInDarkTheme()) {
                                DARK_BODY_TEXT
                            } else {
                                Gray
                            }
                        )
                    }

                    Row(
                        modifier = Modifier
                            .padding(
                                start = 8.dp, end = 8.dp, bottom = 8.dp, top = 8.dp
                            )
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "2.",
                            modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                            textAlign = TextAlign.Start,
                            fontFamily = fontRegular,
                            fontSize = 14.sp,
                            color = if (isSystemInDarkTheme()) {
                                DARK_BODY_TEXT
                            } else {
                                Gray
                            }
                        )

                        Text(
                            text = stringResource(R.string.txt_Screening_identify_challenges),
                            textAlign = TextAlign.Start,
                            fontFamily = fontRegular,
                            fontSize = 14.sp,
                            color = if (isSystemInDarkTheme()) {
                                DARK_BODY_TEXT
                            } else {
                                Gray
                            }
                        )
                    }

                    Row(
                        modifier = Modifier
                            .padding(
                                start = 8.dp, end = 8.dp, bottom = 8.dp, top = 8.dp
                            )
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "3.",
                            modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                            textAlign = TextAlign.Start,
                            fontFamily = fontRegular,
                            fontSize = 14.sp,
                            color = if (isSystemInDarkTheme()) {
                                DARK_BODY_TEXT
                            } else {
                                Gray
                            }
                        )

                        Text(
                            text = stringResource(R.string.txt_Screening_statement_challenges),
                            textAlign = TextAlign.Start,
                            fontFamily = fontRegular,
                            fontSize = 14.sp,
                            color = if (isSystemInDarkTheme()) {
                                DARK_BODY_TEXT
                            } else {
                                Gray
                            }
                        )
                    }

                    Row(
                        modifier = Modifier
                            .padding(
                                start = 8.dp, end = 8.dp, bottom = 8.dp, top = 8.dp
                            )
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "4.",
                            modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                            textAlign = TextAlign.Start,
                            fontFamily = fontRegular,
                            fontSize = 14.sp,
                            color = if (isSystemInDarkTheme()) {
                                DARK_BODY_TEXT
                            } else {
                                Gray
                            }
                        )

                        Text(
                            text = stringResource(R.string.txt_Screening_observed_period),
                            textAlign = TextAlign.Start,
                            fontFamily = fontRegular,
                            fontSize = 14.sp,
                            color = if (isSystemInDarkTheme()) {
                                DARK_BODY_TEXT
                            } else {
                                Gray
                            }
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(
                            start = 12.dp, end = 8.dp, bottom = 16.dp, top = 16.dp
                        )
                    ) {
                        Checkbox(
                            checked = isCheckedTermsAccept, onCheckedChange = {
                                isCheckedTermsAccept = it
                            }, // Disabled for display-only
                            colors = if (isSystemInDarkTheme()) {
                                CheckboxDefaults.colors(
                                    checkedColor = PrimaryBlue,     // Light purple-gray
                                    uncheckedColor = Gray,   // Same for unchecked
                                    checkmarkColor = White
                                )
                            } else {
                                CheckboxDefaults.colors(
                                    checkedColor = PrimaryBlue,     // Light purple-gray
                                    uncheckedColor = Gray,   // Same for unchecked
                                    checkmarkColor = White
                                )
                            }, modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = stringResource(R.string.txt_I_hereby_mentioned),
                            modifier = Modifier.padding(start = 4.dp, end = 8.dp),
                            textAlign = TextAlign.Start,
                            fontFamily = fontMedium,
                            fontSize = 14.sp,
                            color = if (isSystemInDarkTheme()) {
                                DARK_TITLE_TEXT
                            } else {
                                Black
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        modifier = Modifier
                            .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
                            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BtnWithRightIconUi(
                            onClick = {
                                if (isCheckedTermsAccept == false) {
                                    context.toast(termsAcceptStr)
                                } else {
                                    addStudent()
                                    onDismiss()
                                }
                            }, title = stringResource(R.string.txt_Get_Started), enabled = true
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(32.dp)
                    .border(BorderStroke(1.dp, Pink80), CircleShape)
                    .background(
                        White, CircleShape
                    )
                    .clickable {
                        onDismiss.invoke()
                    }) {

                IconButton(onClick = onDismiss) {
                    Image(
                        painter = painterResource(id = R.drawable.round_close_img),
                        contentDescription = IMG_DESCRIPTION,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

data class ScreeningData(
    var name: String,
    var grade: String,
    var interventionStartStatus: String,
    var interventionContinueStatus: String,
    var interventionCompletedStatus: String,
    var inProgressAchieved: String,
    var inProgressNum: Int,
    var image: Painter,
)

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ScreeningHomeScreenPreview() {
    val addStudent: () -> Unit = {}
    val onBack: () -> Unit = {}
    val screeningOne: () -> Unit = {}
    ScreeningHomeScreen(addStudent, onBack, screeningOne)
}