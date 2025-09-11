package com.pi.ProjectInclusion.android.screens.notification


import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kmptemplate.logger.LoggerProvider
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.Transparent
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.DetailsNoImgBackgroundUi
import com.pi.ProjectInclusion.constants.BackHandler
import com.pi.ProjectInclusion.constants.CustomDialog
import com.pi.ProjectInclusion.data.model.profileModel.response.dummyTrackList
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.BorderBlue
import com.pi.ProjectInclusion.CancelColor7
import com.pi.ProjectInclusion.GrayLight04
import com.pi.ProjectInclusion.TransferColor7
import com.pi.ProjectInclusion.Yellow1
import com.pi.ProjectInclusion.android.screens.StudentDashboardActivity
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.fontRegular
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.CommonFunction.isNetworkAvailable
import com.pi.ProjectInclusion.constants.ConstantVariables.USER_ID
import com.pi.ProjectInclusion.data.model.authenticationModel.response.NotificationList
import com.pi.ProjectInclusion.ui.viewModel.DashboardViewModel
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun NotificationScreen(
    onNext: () -> Unit,
    onBack: () -> Unit,
    dashboardViewModel: DashboardViewModel,
    ) {
    val loginViewModel: LoginViewModel = koinViewModel()
    var isDialogVisible by remember { mutableStateOf(false) }
    val notificationState by dashboardViewModel.getNotificationResponse.collectAsStateWithLifecycle()
    var isInternetAvailable by remember { mutableStateOf(false) }
    val internetMessage = stringResource(R.string.txt_oops_no_internet)
    var userId = loginViewModel.getPrefData(USER_ID)
    logger.d("NotiScreen $userId")
    val context = LocalContext.current
    var notificationData by remember { mutableStateOf(mutableListOf<NotificationList>()) }

    BackHandler {
        startActivity(
            context, Intent(context, StudentDashboardActivity::class.java), null
        ).apply { (context as? Activity)?.finish() }
    }

    LaunchedEffect(Unit) {
        isInternetAvailable = isNetworkAvailable(context)
        if (!isInternetAvailable) {
            context.toast(internetMessage)
        } else {
            isDialogVisible = true
            dashboardViewModel.getSentNotification(270)
        }
    }

    LaunchedEffect(notificationState) {
        when {
            notificationState.isLoading -> {
                isDialogVisible = true
            }

            notificationState.error.isNotEmpty() -> {
                logger.d("Certificate Error: ${notificationState.error}")
                isDialogVisible = false
            }

            notificationState.success != null -> {
                logger.d("Certificate Response :- ${notificationState.success!!.response}")
                if (notificationState.success!!.response != null) {
                    if (notificationState.success!!.response?.size != 0) {
                        notificationData = notificationState.success!!.response as MutableList<NotificationList>
                        println("Certificate Data :- $notificationData")
                    } else {
                        context.toast(notificationState.success!!.message)
                    }
                } else {
                    context.toast(notificationState.success?.message ?: "")
                }
                isDialogVisible = false
            }
        }
    }

    CustomDialog(
        isVisible = isDialogVisible,
        onDismiss = { isDialogVisible = false },
        message = stringResource(R.string.txt_loading)
    )
    BackHandler {
        onBack()
    }
    LoggerProvider.logger.d("Screen: " + "NotificationScreen()")

    Surface(
        modifier = Modifier.fillMaxWidth(), color = White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = White),
            verticalArrangement = Arrangement.Top
        ) {
            Notifications(context, onNext = onNext, onBack = onBack)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Notifications(
    context: Context,
    onBack: () -> Unit,
    onNext: () -> Unit
) {

    DetailsNoImgBackgroundUi(
        backgroundColor = White,
        textColor = Black,
        pageTitle = stringResource(R.string.notification_screen),
        isShowBackButton = true,
        isShowMoreInfo = false,
        onBackButtonClick = {
            onBack()
        },
        onMoreInfoClick = {
        },
        content = {
            Box(
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .background(
                        color = if (isSystemInDarkTheme()) {
                            Dark_01
                        } else {
                            Transparent
                        }
                    )
            )
            NotificationList()
        })
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun Notifications() {
    val context = LocalContext.current
    val onNext: () -> Unit = {}
    val onBack: () -> Unit = {}
    Notifications(context, onNext, onBack)
}
/* this code will be required during API */


@Composable
fun NotificationList() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(dummyTrackList) { item ->
            if (item.title==stringResource(R.string.txt_case_transfer)){
                StudentTransferRequestCard(
                    date = stringResource(R.string.txt_date),
                    time = stringResource(R.string.txt_time),
                    senderName = stringResource(R.string.user_name),
                    senderSchool = stringResource(R.string.txt_dummy_school),
                    studentImage = R.drawable.profile_user_icon,
                    studentName = stringResource(R.string.txt_dummy_name),
                    studentClass = stringResource(R.string.txt_class_name),
                    onCancel = {},
                    onTransfer = {}
                )
            }
            else{
               NotificationCard(
                   date = stringResource(R.string.txt_date),
                   time = stringResource(R.string.txt_time),
                   senderName = stringResource(R.string.user_name),
                   senderSchool = stringResource(R.string.txt_dummy_school),
                   studentImage = R.drawable.profile_user_icon
               )
            }

        }
    }
}

@Composable
fun StudentTransferRequestCard(
    date: String,
    time: String,
    senderName: String,
    senderSchool: String,
    studentImage: Int,
    studentName: String,
    studentClass: String,
    onCancel: () -> Unit,
    onTransfer: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Divider(
            color = Color.LightGray,
            thickness = 0.7.dp,
            modifier = Modifier.padding(vertical = 6.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 4 .dp, bottom = 6.dp)
        ) {
            Text(
                text = date,
                fontSize = 12.sp,
                fontFamily = fontRegular,
                color = BorderBlue
            )

            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(
                        color = Yellow1
                    )
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = time,
                fontSize = 12.sp,
                fontFamily = fontRegular,
                color = BorderBlue
            )
        }
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Black)) {
                    append(senderName)
                }
                append(" from ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Black)) {
                    append("$senderSchool ")
                }
                append(stringResource(R.string.txt_noti_case_trans))
            },
            fontSize = 14.sp,
            fontFamily = fontRegular,
            color = GrayLight04
        )

        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                .background(White)
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
            ){

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = studentImage),
                        contentDescription = null,
                        modifier = Modifier
                            .size(65.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(start = 4 .dp
                                ),
                            text = studentName,
                            fontSize = 17.sp,
                            fontFamily = fontMedium,
                            color = Black
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 4 .dp
                                ),
                            text = studentClass,
                            fontSize = 15.sp,
                            fontFamily = fontRegular,
                            color = GrayLight04
                        )
                        Row(
                            modifier = Modifier
                                .padding(start = 4.dp, top = 16 .dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedButton(
                                onClick = { onCancel() },
                                modifier = Modifier.height(36.dp),
                                shape = RoundedCornerShape(20.dp),
                                border = BorderStroke(1.dp, Color.Red),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = Color.Red
                                )
                            ) {
                                Text(
                                    text = stringResource(R.string.txt_cancel),
                                    fontSize = 14.sp,
                                    fontFamily = fontMedium,
                                    color = Color.Red
                                )
                            }
                            OutlinedButton(
                                onClick = { onTransfer() },
                                modifier = Modifier.height(36.dp),
                                shape = RoundedCornerShape(20.dp),
                                border = BorderStroke(1.dp, Color(0xFF28A745)),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = CancelColor7
                                )
                            ) {
                                Text(
                                    text = stringResource(R.string.txt_transfer),
                                    fontSize = 14.sp,
                                    fontFamily = fontMedium,
                                    color = TransferColor7
                                )
                            }
                        }
                    }
                }

            }



        }
    }
}

@Composable
fun NotificationCard(
    date: String,
    time: String,
    senderName: String,
    senderSchool: String,
    studentImage: Int,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Divider(
            color = Color.LightGray,
            thickness = 0.7.dp,
            modifier = Modifier.padding(vertical = 6.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 4 .dp, bottom = 6.dp)
        ) {
            Text(
                text = date,
                fontSize = 12.sp,
                fontFamily = fontRegular,
                color = BorderBlue
            )

            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(
                        color = Yellow1
                    )
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = time,
                fontSize = 12.sp,
                fontFamily = fontRegular,
                color = BorderBlue
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                .background(White)
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
            ){

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = studentImage),
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = buildAnnotatedString {

                            append(stringResource(R.string.txt_first_noti)+" ")

                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Black)) {
                                append(senderName)
                            }
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Black)) {
                                append("$senderSchool ")
                            }
                            append(stringResource(R.string.txt_last_noti))
                        },
                        fontSize = 14.sp,
                        fontFamily = fontRegular,
                        color = GrayLight04
                    )

                }

            }



        }
    }
}