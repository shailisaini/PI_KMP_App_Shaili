package com.pi.ProjectInclusion.android.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.kmptemplate.logger.LoggerProvider
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.DARK_BODY_TEXT
import com.pi.ProjectInclusion.DARK_TITLE_TEXT
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.INVITE_LIGHT_01
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.BtnUi
import com.pi.ProjectInclusion.android.common_UI.CustomProgressBar
import com.pi.ProjectInclusion.android.common_UI.DefaultBackgroundUi
import com.pi.ProjectInclusion.android.common_UI.OtpInputField
import com.pi.ProjectInclusion.android.common_UI.TextWithIconOnLeft
import com.pi.ProjectInclusion.android.navigation.AppRoute
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.CustomDialog
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import kotlinx.coroutines.delay

@Composable
fun OtpSendVerifyScreen(
    onNext: () -> Unit,
    onBack: () -> Unit,
    viewModel: LoginViewModel
) {
    val sendOtpState by viewModel.uiStateSendOtpResponse.collectAsStateWithLifecycle()
    logger.d("Screen: " + "OtpSendVerifyScreen()")

    var otpValue by remember { mutableStateOf("") }
    var phoneNo by remember { mutableStateOf("") }
    var inValidOTP by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    val invalidOtpText = stringResource(R.string.txt_Enter_valid_OTP)
    var isDialogVisible by remember { mutableStateOf(false) }
    var isFinished by remember { mutableStateOf(false) }
    val mobNo = viewModel.mobileNumber
    logger.d("User Data fetched: $mobNo")

    CustomDialog(
        isVisible = isDialogVisible,
        onDismiss = { isDialogVisible = false },
        message = stringResource(R.string.txt_loading)
    )

    LaunchedEffect(sendOtpState) {
//        viewModel.getOTPViewModel(otpValue)

        when {
            sendOtpState.isLoading -> {
                isDialogVisible = true
            }

            sendOtpState.error.isNotEmpty() -> {
                LoggerProvider.logger.d("Error: ${sendOtpState.error}")
                isDialogVisible = false
            }

            sendOtpState.success != null -> {
//                val list = sendOtpState.success?.response ?: emptyList()
//                LoggerProvider.logger.d("Languages fetched: ${list.size}")

                isDialogVisible = false
            }
        }
    }

    DefaultBackgroundUi(isShowBackButton = true, onBackButtonClick = {
        onBack()
    }, content = {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.txt_OTP_Sent),
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .fillMaxWidth(),
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_TITLE_TEXT
                    } else {
                        Black
                    }
                )

                Text(
                    text = stringResource(R.string.txt_OTP_received_phone),
                    modifier = Modifier.padding(top = 8.dp, start = 10.dp, end = 10.dp),
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_BODY_TEXT
                    } else {
                        Gray
                    }
                )

                otpValue = otpTextField()

                if (isFinished) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding()
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextWithIconOnLeft(
                            text = stringResource(R.string.txt_Resend_OTP),
                            icon = ImageVector.vectorResource(id = R.drawable.message_square),
                            textColor = if (isSystemInDarkTheme()) {
                                INVITE_LIGHT_01
                            } else {
                                PrimaryBlue
                            },
                            iconColor = if (isSystemInDarkTheme()) {
                                INVITE_LIGHT_01
                            } else {
                                PrimaryBlue
                            },
                            modifier = Modifier.padding(top = 16.dp),
                            onClick = {
                                isFinished = false
//                            loginViewModel.setGradeReportPage("")
//                            loginViewModel.setSelectedTabId(0)
//                            loginViewModel.setGradeId("")
//                            navHostController.navigate(AppRoute.TeacherQuizReports.route)
                            })

                        Spacer(modifier = Modifier.weight(1f))

                        TextWithIconOnLeft(
                            text = stringResource(R.string.txt_OTP_call),
                            icon = ImageVector.vectorResource(id = R.drawable.call_on_otp),
                            textColor = if (isSystemInDarkTheme()) {
                                INVITE_LIGHT_01
                            } else {
                                PrimaryBlue
                            },
                            iconColor = if (isSystemInDarkTheme()) {
                                INVITE_LIGHT_01
                            } else {
                                PrimaryBlue
                            },
                            modifier = Modifier.padding(top = 16.dp),
                            onClick = {
                                isFinished = false
//                            loginViewModel.setGradeReportPage("")
//                            loginViewModel.setSelectedTabId(0)
//                            loginViewModel.setGradeId("")
//                            navHostController.navigate(AppRoute.TeacherQuizReports.route)
                            })
                    }
                } else {
                    CountdownTimer(
                        totalTime = 30_000L, // 30 seconds
                        interval = 1_000L,
                        onFinish = { isFinished = true }
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(bottom = 16.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        color = if (isSystemInDarkTheme()) {
                            Dark_01
                        } else {
                            Color.White
                        }
                    ),
                contentAlignment = Alignment.CenterEnd
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    var countNumber by remember { mutableStateOf<Int?>(null) }
                    if (otpValue.length == 6) {
                        countNumber = 1
                    } else {
                        countNumber = null
                    }

                    BtnUi(
                        stringResource(R.string.txt_Verify_proceed),
                        onClick = {
//                            navController.navigate(AppRoute.SetNewPasswordUI.route)
                            onNext()

                            showError = otpValue.isEmpty()
                            inValidOTP = showError || otpValue.length < 6
                            if (!inValidOTP) {
                                if (phoneNo != null) {
                                    isDialogVisible = true
//                                    viewModel.verifyOtp(otp, phoneNo.encryptAES().toString())
                                }
                            }
                        },
                        countNumber != null
                    )
                }
            }
        }
    }
    )
}

@Composable
fun otpTextField(): String {
    var otpValue by remember { mutableStateOf("") }
    var isOtpFilled by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    // 6 OTP field in common
    OtpInputField(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .focusRequester(focusRequester),
        otpText = otpValue,
        shouldCursorBlink = false,
        onOtpModified = { value, otpFilled ->
            otpValue = value
            isOtpFilled = otpFilled
            if (otpFilled) {
                keyboardController?.hide()
            }
        })
    return otpValue
}

@Composable
fun CountdownTimer(
    totalTime: Long = 60_000L, // 60 seconds
    interval: Long = 1_000L,   // 1 second interval
    onFinish: () -> Unit = {},
) {
    var timeLeft by remember { mutableStateOf(totalTime) }

    LaunchedEffect(key1 = timeLeft) {
        if (timeLeft > 0L) {
            delay(interval)
            timeLeft -= interval
        } else {
            onFinish()
        }
    }

    val seconds = (timeLeft / 1000).toInt()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        ProfileProgress(
            textStr = stringResource(R.string.txt_Resend_otp_in),
            progress = seconds.toFloat(),
        )
    }
}

@Composable
private fun ProfileProgress(
    textStr: String,
    progress: Float = 0f,
) {
    Row(
        modifier = Modifier
            .wrapContentWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterVertically)
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = if (isSystemInDarkTheme()) {
                                DARK_TITLE_TEXT
                            } else {
                                Gray
                            }
                        )
                    ) {
                        append(textStr)
                    }
                },
                fontFamily = FontFamily(
                    Font(R.font.roboto_semi_bold, FontWeight.SemiBold)
                ),
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
            )
        }

        CustomProgressBar(percentage = progress, modifier = Modifier.size(35.dp))
    }
}