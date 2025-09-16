package com.pi.ProjectInclusion.android.screens.dashboardNavActivity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.BorderBlue
import com.pi.ProjectInclusion.DARK_BODY_TEXT
import com.pi.ProjectInclusion.DARK_TITLE_TEXT
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.Dark_02
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.GrayLight02
import com.pi.ProjectInclusion.GrayLight09
import com.pi.ProjectInclusion.LightGreen06
import com.pi.ProjectInclusion.LightGreen07
import com.pi.ProjectInclusion.LightRed01
import com.pi.ProjectInclusion.LightRed04
import com.pi.ProjectInclusion.PRIMARY_AURO_BLUE
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.PrimaryBlue3
import com.pi.ProjectInclusion.White
import com.pi.ProjectInclusion.Yellow
import com.pi.ProjectInclusion.android.MyApplicationTheme
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.DetailsNoImgBackgroundUi
import com.pi.ProjectInclusion.android.screens.StudentDashboardActivity
import com.pi.ProjectInclusion.android.screens.menu.TabItem
import com.pi.ProjectInclusion.android.utils.fontBold
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.fontRegular
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.BackHandler
import com.pi.ProjectInclusion.constants.CommonFunction.isNetworkAvailable
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION
import com.pi.ProjectInclusion.constants.CustomDialog
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ZoomMeetingListResponse
import com.pi.ProjectInclusion.ui.viewModel.DashboardViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class ZoomMeetingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val viewModel: DashboardViewModel = koinViewModel()

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
                    ShowZoomMeetingData(context, viewModel)
                }
            }
        }
    }
}

@Composable
private fun ShowZoomMeetingData(context: Context, viewModel: DashboardViewModel) {

    var isDialogVisible by remember { mutableStateOf(false) }
    val firstTokenState by viewModel.getZoomMeetingTokenResponse.collectAsStateWithLifecycle()
    val actualTokenState by viewModel.getTokenResponse.collectAsStateWithLifecycle()
    val meetingListState by viewModel.getMeetingListResponse.collectAsStateWithLifecycle()
    val meetingJoinState by viewModel.getMeetingJoinResponse.collectAsStateWithLifecycle()

    var strRefreshToken by remember { mutableStateOf("") }
    var strActualTokenKey by remember { mutableStateOf("") }
    val strBaseKey =
        "Basic RFdUcmNuUDZSUUdiUzhwX3hUbU1Rdzp4M3hlQ2gyZnJMS3FqOGlHYnlUOEo4VkZvdjdSdWNMNQ=="
    val strRefreshKey = "refresh_token"

    var meetingListData by remember { mutableStateOf(mutableListOf<ZoomMeetingListResponse.Meetings>()) }
    var isInternetAvailable by remember { mutableStateOf(false) }
    val internetMessage = stringResource(R.string.txt_oops_no_internet)

    LaunchedEffect(Unit) {
        isInternetAvailable = isNetworkAvailable(context)
        if (!isInternetAvailable) {
            context.toast(internetMessage)
        } else {
            isDialogVisible = true
            viewModel.getRefreshToken()
        }
    }

    LaunchedEffect(firstTokenState) {
        when {
            firstTokenState.isLoading -> {
                isDialogVisible = true
            }

            firstTokenState.error.isNotEmpty() -> {
                logger.d("Refresh token Error: ${firstTokenState.error}")
                isDialogVisible = false
                viewModel.resetState(viewModel.getZoomMeetingToken)
            }

            firstTokenState.success != null -> {
                logger.d("Refresh token Response :- ${firstTokenState.success}")
                if (firstTokenState.success?.refreshToken != null) {
                    if (firstTokenState.success?.refreshToken!!.isNotEmpty()) {
                        strRefreshToken = firstTokenState.success?.refreshToken.toString()
                        println("Refresh token Data :- $strRefreshToken")
                        viewModel.getZoomMeetingsActualToken(
                            strBaseKey, strRefreshKey, strRefreshToken
                        )
                    }
                    viewModel.resetState(viewModel.getZoomMeetingToken)
                } else {
                    isDialogVisible = false
                    logger.d("Refresh token data not found...")
                }
            }
        }
    }

    LaunchedEffect(actualTokenState) {
        when {
            actualTokenState.isLoading -> {
                isDialogVisible = true
            }

            actualTokenState.error.isNotEmpty() -> {
                logger.d("Actual Token  Error: ${actualTokenState.error}")
                isDialogVisible = false
                viewModel.resetState(viewModel.getToken)
            }

            actualTokenState.success != null -> {
                logger.d("Actual Token Response :- ${actualTokenState.success}")
                if (actualTokenState.success?.accessToken != null) {
                    if (actualTokenState.success?.accessToken!!.isNotEmpty()) {
                        strActualTokenKey = actualTokenState.success?.accessToken.toString()
                        println("Actual token Data :- $strActualTokenKey")
                        viewModel.getAllZoomMeetings("bearer $strActualTokenKey")
                    }
                    viewModel.resetState(viewModel.getToken)
                } else {
                    isDialogVisible = false
                    logger.d("Actual token data not found...")
                }
            }
        }
    }

    LaunchedEffect(meetingListState) {
        when {
            meetingListState.isLoading -> {
                isDialogVisible = true
            }

            meetingListState.error.isNotEmpty() -> {
                logger.d("Meeting list error: ${meetingListState.error}")
                isDialogVisible = false
                viewModel.resetState(viewModel.getMeetingList)
            }

            meetingListState.success != null -> {
                logger.d("Meeting list response :- ${meetingListState.success}")
                if (meetingListState.success?.meetings != null) {
                    isDialogVisible = false
                    if (meetingListState.success?.meetings?.size != 0) {
                        meetingListData =
                            meetingListState.success?.meetings as MutableList<ZoomMeetingListResponse.Meetings>
                        println("Meeting list data :- $meetingListData")
                    }
                    viewModel.resetState(viewModel.getMeetingList)
                } else {
                    isDialogVisible = false
                    logger.d("Meeting list data not found...")
                }
            }
        }
    }

    BackHandler {
        startActivity(
            context, Intent(context, StudentDashboardActivity::class.java), null
        ).apply { (context as? Activity)?.finish() }
    }

    CustomDialog(
        isVisible = isDialogVisible,
        onDismiss = { isDialogVisible = false },
        message = stringResource(R.string.txt_loading)
    )

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
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
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
                                                    BorderBlue
                                                }
                                            } else {
                                                if (isSystemInDarkTheme()) {
                                                    DARK_BODY_TEXT
                                                } else {
                                                    GrayLight09
                                                }
                                            },
                                            fontFamily = fontMedium,
                                            fontSize = 15.sp,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    })
                            }
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
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
                                        AllUpcomingMeetingScreening(meetingListData)
                                    }

                                    1 -> {
                                        AllPastMeetingScreening(meetingListData)
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
fun AllUpcomingMeetingScreening(meetingListData: MutableList<ZoomMeetingListResponse.Meetings>) {

    val selectedBorder = BorderStroke(
        width = 0.5.dp, if (isSystemInDarkTheme()) {
            Dark_02
        } else {
            GrayLight02
        }
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(meetingListData) { meetingData ->

                val calender = Calendar.getInstance()
                val day = calender.get(Calendar.DAY_OF_MONTH)
                val dayAfter = calender.get(Calendar.DAY_OF_MONTH) + 1
                val month = calender.get(Calendar.MONTH) + 1
                val year = calender.get(Calendar.YEAR)

                val today = if (month != 10) {
                    "$day-0$month-$year"
                } else {
                    "$day-$month-$year"
                }

                val currentMonth = "$month-$year"

                val afterTwoDays = if (month != 10) {
                    "$dayAfter-0$month-$year"
                } else {
                    "$dayAfter-$month-$year"
                }

                println("Day after tomorrow :- $afterTwoDays")

                if (meetingData.startTime != null) {
                    val meetingDateMonth = formatDateMonth(meetingData.startTime.toString())
                    val meetingDate = formatMonthDate(meetingData.startTime.toString())

                    if (meetingDateMonth.contains(currentMonth)) {
                        if (meetingDate == afterTwoDays || meetingDate == today) {
                            Card(
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                colors = if (isSystemInDarkTheme()) {
                                    CardDefaults.cardColors(Yellow)
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
                                        start = 12.dp, end = 12.dp, top = 12.dp, bottom = 12.dp
                                    ), horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Row(
                                        modifier = Modifier.padding(
                                            horizontal = 4.dp,
                                            vertical = 4.dp
                                        ),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Card(
                                            shape = RoundedCornerShape(25.dp),
                                            colors = if (isSystemInDarkTheme()) {
                                                CardDefaults.cardColors(PrimaryBlue3) //LightGreen07
                                            } else {
                                                CardDefaults.cardColors(
                                                    containerColor = LightRed04,
                                                    contentColor = LightRed04,
                                                    disabledContentColor = LightRed04,
                                                    disabledContainerColor = LightRed04
                                                )
                                            }
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .height(35.dp)
                                                    .width(100.dp),
                                                horizontalArrangement = Arrangement.Center,
                                                verticalAlignment = Alignment.CenterVertically,
                                            ) {
                                                Text(
                                                    text = "•",
                                                    modifier = Modifier
                                                        .padding(start = 8.dp, end = 4.dp)
                                                        .wrapContentWidth(),
                                                    fontFamily = fontMedium,
                                                    fontSize = 18.sp,
                                                    color = if (isSystemInDarkTheme()) {
                                                        LightRed01 // ZoomLightRed
                                                    } else {
                                                        LightRed01 //  ZoomLightRed
                                                    },
                                                    textAlign = TextAlign.Center
                                                )

                                                Text(
                                                    text = stringResource(R.string.key_Live),
                                                    modifier = Modifier
                                                        .padding(start = 4.dp, end = 8.dp)
                                                        .wrapContentWidth(),
                                                    fontFamily = fontMedium,
                                                    fontSize = 13.sp,
                                                    color = if (isSystemInDarkTheme()) {
                                                        LightRed01 // ZoomLightRed
                                                    } else {
                                                        LightRed01//  ZoomLightRed
                                                    },
                                                    textAlign = TextAlign.Start
                                                )
                                            }
                                        }

                                        Spacer(Modifier.weight(1f))

                                        Column(
                                            modifier = Modifier
                                                .padding(
                                                    start = 6.dp, top = 6.dp, bottom = 6.dp
                                                )
                                                .wrapContentWidth(),
                                            horizontalAlignment = Alignment.End,
                                            verticalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = if (meetingData.duration != null) {
                                                    stringResource(R.string.key_duration) + " ${meetingData.duration} " + stringResource(
                                                        R.string.key_mins
                                                    )
                                                } else {
                                                    stringResource(R.string.key_duration_in_minuts)
                                                },
                                                modifier = Modifier
                                                    .wrapContentWidth()
                                                    .padding(start = 8.dp, end = 8.dp),
                                                fontFamily = fontRegular,
                                                fontSize = 13.sp,
                                                color = if (isSystemInDarkTheme()) {
                                                    DARK_TITLE_TEXT
                                                } else {
                                                    GrayLight09
                                                },
                                                textAlign = TextAlign.Start
                                            )
                                        }
                                    }

                                    Row(
                                        modifier = Modifier.padding(
                                            horizontal = 4.dp,
                                            vertical = 4.dp
                                        ),
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.zoom_meeting_img),
                                            contentDescription = IMG_DESCRIPTION,
                                            modifier = Modifier
                                                .size(65.dp)
                                                .background(Color.Unspecified),
                                        )

                                        Column(
                                            modifier = Modifier
                                                .padding(
                                                    start = 6.dp, top = 6.dp, bottom = 6.dp
                                                )
                                                .wrapContentWidth(),
                                            horizontalAlignment = Alignment.End,
                                            verticalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = if (meetingData.topic != null) {
                                                    meetingData.topic.toString()
                                                } else {
                                                    stringResource(R.string.key_Na)
                                                },
                                                modifier = Modifier
                                                    .padding(start = 6.dp, end = 8.dp)
                                                    .fillMaxWidth(),
                                                fontFamily = fontMedium,
                                                fontSize = 15.sp,
                                                color = if (isSystemInDarkTheme()) {
                                                    DARK_TITLE_TEXT
                                                } else {
                                                    Black
                                                },
                                                textAlign = TextAlign.Start
                                            )
                                        }
                                    }

                                    Row(
                                        modifier = Modifier.padding(
                                            horizontal = 4.dp,
                                            vertical = 4.dp
                                        ),
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .padding(
                                                    top = 6.dp, bottom = 6.dp
                                                )
                                                .wrapContentWidth(),
                                            horizontalAlignment = Alignment.Start,
                                            verticalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            if (meetingData.startTime != null) {
                                                val formatted =
                                                    formatDate(meetingData.startTime.toString())
                                                val formattedTime =
                                                    formatTime(meetingData.startTime.toString())

                                                Text(
                                                    text = "$formatted, $formattedTime",
                                                    modifier = Modifier
                                                        .padding(end = 8.dp)
                                                        .wrapContentWidth(),
                                                    fontFamily = fontBold,
                                                    fontSize = 13.sp,
                                                    color = if (isSystemInDarkTheme()) {
                                                        DARK_TITLE_TEXT
                                                    } else {
                                                        GrayLight09
                                                    },
                                                    textAlign = TextAlign.Start
                                                )
                                            } else {
                                                Text(
                                                    text = stringResource(R.string.key_Na),
                                                    modifier = Modifier
                                                        .padding(end = 8.dp)
                                                        .wrapContentWidth(),
                                                    fontFamily = fontBold,
                                                    fontSize = 13.sp,
                                                    color = if (isSystemInDarkTheme()) {
                                                        DARK_TITLE_TEXT
                                                    } else {
                                                        GrayLight09
                                                    },
                                                    textAlign = TextAlign.Start
                                                )
                                            }
                                        }

                                        Spacer(Modifier.weight(1f))

                                        Column(
                                            modifier = Modifier.wrapContentHeight(),
                                            horizontalAlignment = Alignment.End
                                        ) {
                                            Card(
                                                modifier = Modifier
                                                    .width(115.dp)
                                                    .wrapContentHeight()
                                                    .clickable {
                                                        if (meetingDate.contains(today)) {
                                                            println("Clickable for joining meeting")
                                                        } else {
                                                            println("Not clickable for joining meeting")
                                                        }
                                                    },
                                                shape = RoundedCornerShape(8.dp),
                                                colors = if (meetingDate.contains(today)) {
                                                    if (isSystemInDarkTheme()) {
                                                        CardDefaults.cardColors(PrimaryBlue)
                                                    } else {
                                                        CardDefaults.cardColors(PrimaryBlue)
                                                    }
                                                } else {
                                                    if (isSystemInDarkTheme()) {
                                                        CardDefaults.cardColors(
                                                            containerColor = Gray,
                                                            contentColor = Gray,
                                                            disabledContentColor = Gray,
                                                            disabledContainerColor = Gray
                                                        )
                                                    } else {
                                                        CardDefaults.cardColors(
                                                            containerColor = Gray,
                                                            contentColor = Gray,
                                                            disabledContentColor = Gray,
                                                            disabledContainerColor = Gray
                                                        )
                                                    }
                                                }
                                            ) {
                                                Row(
                                                    modifier = Modifier
                                                        .wrapContentWidth()
                                                        .height(36.dp)
                                                        .align(Alignment.CenterHorizontally),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Text(
                                                        text = "Join now",
                                                        modifier = Modifier.wrapContentWidth(),
                                                        fontFamily = fontMedium,
                                                        fontSize = 15.sp,
                                                        color = if (isSystemInDarkTheme()) {
                                                            White
                                                        } else {
                                                            White
                                                        },
                                                        textAlign = TextAlign.Center
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AllPastMeetingScreening(meetingListData: MutableList<ZoomMeetingListResponse.Meetings>) {

    val selectedBorder = BorderStroke(
        width = 0.5.dp, if (isSystemInDarkTheme()) {
            Dark_02
        } else {
            GrayLight02
        }
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(meetingListData) { meetingData ->
                val calender = Calendar.getInstance()
                val day = calender.get(Calendar.DAY_OF_MONTH)
                val dayAfter = calender.get(Calendar.DAY_OF_MONTH) + 1
                val month = calender.get(Calendar.MONTH) + 1
                val year = calender.get(Calendar.YEAR)

                val today = if (month != 10) {
                    "$day-0$month-$year"
                } else {
                    "$day-$month-$year"
                }

                val currentMonth = "$month-$year"

                val afterTwoDays = if (month != 10) {
                    "$dayAfter-0$month-$year"
                } else {
                    "$dayAfter-$month-$year"
                }

                if (meetingData.startTime != null) {
                    val meetingDateMonth = formatDateMonth(meetingData.startTime.toString())
                    val meetingDate = formatMonthDate(meetingData.startTime.toString())
                    if (meetingDateMonth.contains(currentMonth)) {
                        if (meetingDate != afterTwoDays || meetingDate != today) {
                            Card(
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                colors = if (isSystemInDarkTheme()) {
                                    CardDefaults.cardColors(Yellow)
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
                                        start = 12.dp, end = 12.dp, top = 12.dp, bottom = 12.dp
                                    ), horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Row(
                                        modifier = Modifier.padding(
                                            horizontal = 4.dp,
                                            vertical = 4.dp
                                        ),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Card(
                                            shape = RoundedCornerShape(25.dp),
                                            colors = if (isSystemInDarkTheme()) {
                                                CardDefaults.cardColors(LightGreen07) //LightGreen07
                                            } else {
                                                CardDefaults.cardColors(
                                                    containerColor = LightGreen07,
                                                    contentColor = LightGreen07,
                                                    disabledContentColor = LightGreen07,
                                                    disabledContainerColor = LightGreen07
                                                )
                                            }
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .height(35.dp)
                                                    .width(115.dp),
                                                horizontalArrangement = Arrangement.Center,
                                                verticalAlignment = Alignment.CenterVertically,
                                            ) {
                                                Text(
                                                    text = "•",
                                                    modifier = Modifier
                                                        .padding(start = 8.dp, end = 4.dp)
                                                        .wrapContentWidth(),
                                                    fontFamily = fontMedium,
                                                    fontSize = 18.sp,
                                                    color = if (isSystemInDarkTheme()) {
                                                        LightGreen06
                                                    } else {
                                                        LightGreen06
                                                    },
                                                    textAlign = TextAlign.Center
                                                )

                                                Text(
                                                    text = stringResource(R.string.key_Completed),
                                                    modifier = Modifier
                                                        .padding(start = 4.dp, end = 8.dp)
                                                        .wrapContentWidth(),
                                                    fontFamily = fontMedium,
                                                    fontSize = 13.sp,
                                                    color = if (isSystemInDarkTheme()) {
                                                        LightGreen06
                                                    } else {
                                                        LightGreen06
                                                    },
                                                    textAlign = TextAlign.Start
                                                )
                                            }
                                        }

                                        Spacer(Modifier.weight(1f))

                                        Column(
                                            modifier = Modifier
                                                .padding(
                                                    start = 6.dp, top = 6.dp, bottom = 6.dp
                                                )
                                                .wrapContentWidth(),
                                            horizontalAlignment = Alignment.End,
                                            verticalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = if (meetingData.duration != null) {
                                                    stringResource(R.string.key_duration) + " ${meetingData.duration} " + stringResource(
                                                        R.string.key_mins
                                                    )
                                                } else {
                                                    stringResource(R.string.key_duration_in_minuts)
                                                },
                                                modifier = Modifier
                                                    .wrapContentWidth()
                                                    .padding(start = 8.dp, end = 8.dp),
                                                fontFamily = fontRegular,
                                                fontSize = 13.sp,
                                                color = if (isSystemInDarkTheme()) {
                                                    DARK_TITLE_TEXT
                                                } else {
                                                    GrayLight09
                                                },
                                                textAlign = TextAlign.Start
                                            )
                                        }
                                    }

                                    Row(
                                        modifier = Modifier.padding(
                                            horizontal = 4.dp,
                                            vertical = 4.dp
                                        ),
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.zoom_meeting_img),
                                            contentDescription = IMG_DESCRIPTION,
                                            modifier = Modifier
                                                .size(65.dp)
                                                .background(Color.Unspecified),
                                        )

                                        Column(
                                            modifier = Modifier
                                                .padding(
                                                    start = 6.dp, top = 6.dp, bottom = 6.dp
                                                )
                                                .wrapContentWidth(),
                                            horizontalAlignment = Alignment.End,
                                            verticalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = if (meetingData.topic != null) {
                                                    meetingData.topic.toString()
                                                } else {
                                                    stringResource(R.string.key_Na)
                                                },
                                                modifier = Modifier
                                                    .padding(start = 6.dp, end = 8.dp)
                                                    .fillMaxWidth(),
                                                fontFamily = fontMedium,
                                                fontSize = 15.sp,
                                                color = if (isSystemInDarkTheme()) {
                                                    DARK_TITLE_TEXT
                                                } else {
                                                    Black
                                                },
                                                textAlign = TextAlign.Start
                                            )
                                        }
                                    }

                                    Row(
                                        modifier = Modifier.padding(
                                            horizontal = 4.dp,
                                            vertical = 4.dp
                                        ),
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .padding(
                                                    top = 6.dp, bottom = 6.dp
                                                )
                                                .wrapContentWidth(),
                                            horizontalAlignment = Alignment.Start,
                                            verticalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            if (meetingData.startTime != null) {
                                                val formatted =
                                                    formatDate(meetingData.startTime.toString())
                                                val formattedTime =
                                                    formatTime(meetingData.startTime.toString())

                                                Text(
                                                    text = "$formatted, $formattedTime",
                                                    modifier = Modifier
                                                        .padding(end = 8.dp)
                                                        .wrapContentWidth(),
                                                    fontFamily = fontBold,
                                                    fontSize = 13.sp,
                                                    color = if (isSystemInDarkTheme()) {
                                                        DARK_TITLE_TEXT
                                                    } else {
                                                        GrayLight09
                                                    },
                                                    textAlign = TextAlign.Start
                                                )
                                            } else {
                                                Text(
                                                    text = stringResource(R.string.key_Na),
                                                    modifier = Modifier
                                                        .padding(end = 8.dp)
                                                        .wrapContentWidth(),
                                                    fontFamily = fontBold,
                                                    fontSize = 13.sp,
                                                    color = if (isSystemInDarkTheme()) {
                                                        DARK_TITLE_TEXT
                                                    } else {
                                                        GrayLight09
                                                    },
                                                    textAlign = TextAlign.Start
                                                )
                                            }
                                        }

                                        Spacer(Modifier.weight(1f))

                                        Column(
                                            modifier = Modifier.wrapContentHeight(),
                                            horizontalAlignment = Alignment.End
                                        ) {
                                            if (!meetingDate.contains(today)) {
                                                Text(
                                                    text = stringResource(R.string.key_Joined),
                                                    modifier = Modifier
                                                        .padding(start = 8.dp, end = 8.dp)
                                                        .wrapContentWidth(),
                                                    fontFamily = fontBold,
                                                    fontSize = 15.sp,
                                                    color = if (isSystemInDarkTheme()) {
                                                        LightGreen06
                                                    } else {
                                                        LightGreen06
                                                    },
                                                    textAlign = TextAlign.End
                                                )
                                            } else {
                                                Text(
                                                    text = stringResource(R.string.key_Passed),
                                                    modifier = Modifier
                                                        .padding(start = 8.dp, end = 8.dp)
                                                        .wrapContentWidth(),
                                                    fontFamily = fontBold,
                                                    fontSize = 15.sp,
                                                    color = if (isSystemInDarkTheme()) {
                                                        LightRed01
                                                    } else {
                                                        LightRed01
                                                    },
                                                    textAlign = TextAlign.End
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun formatDateMonth(input: String): String {
    val inputFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
    val outputFormatter = DateTimeFormatter.ofPattern("MM-yyyy")
    val parsedDate = ZonedDateTime.parse(input, inputFormatter)
    return parsedDate.format(outputFormatter)
}

fun formatDate(input: String): String {
    val inputFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
    val outputFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy")
    val parsedDate = ZonedDateTime.parse(input, inputFormatter)
    return parsedDate.format(outputFormatter)
}

fun formatMonthDate(input: String): String {
    val inputFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
    val outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val parsedDate = ZonedDateTime.parse(input, inputFormatter)
    return parsedDate.format(outputFormatter)
}

fun formatTime(time: String): String {
    val zonedDateTime = ZonedDateTime.parse(time)
    val formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH)
    val formattedTime = zonedDateTime.format(formatter)
    return formattedTime
}