package com.pi.ProjectInclusion.android.screens.login

import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
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
import com.example.kmptemplate.logger.LoggerProvider
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.DARK_BODY_TEXT
import com.pi.ProjectInclusion.DARK_TITLE_TEXT
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.INVITE_LIGHT_01
import com.pi.ProjectInclusion.LightRed01
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.AESEncryption.encryptAES
import com.pi.ProjectInclusion.android.common_UI.BtnUi
import com.pi.ProjectInclusion.android.common_UI.ChooseOneBottomSheet
import com.pi.ProjectInclusion.android.common_UI.CustomProgressBar
import com.pi.ProjectInclusion.android.common_UI.DefaultBackgroundUi
import com.pi.ProjectInclusion.android.common_UI.EncryptedCommonFunction.isEncryptedPhone
import com.pi.ProjectInclusion.android.common_UI.OtpInputField
import com.pi.ProjectInclusion.android.common_UI.TextWithIconOnLeft
import com.pi.ProjectInclusion.android.screens.StudentDashboardActivity
import com.pi.ProjectInclusion.android.utils.fontBold
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.CommonFunction.isNetworkAvailable
import com.pi.ProjectInclusion.constants.ConstantVariables.IS_COMING_FROM
import com.pi.ProjectInclusion.constants.ConstantVariables.LOGIN_WITH_OTP
import com.pi.ProjectInclusion.constants.ConstantVariables.SELECTED_LANGUAGE_ID
import com.pi.ProjectInclusion.constants.ConstantVariables.SUCCESS
import com.pi.ProjectInclusion.constants.ConstantVariables.USER_MOBILE_NO
import com.pi.ProjectInclusion.constants.ConstantVariables.USER_TYPE_ID
import com.pi.ProjectInclusion.constants.CustomDialog
import com.pi.ProjectInclusion.data.model.authenticationModel.request.LoginWithOtpRequest
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import kotlinx.coroutines.delay

@Composable
fun OtpSendVerifyScreen(
    onNext: () -> Unit,
    onBack: () -> Unit,
    viewModel: LoginViewModel,
) {
    val sendOtpState by viewModel.uiStateSendOtpResponse.collectAsStateWithLifecycle()
    val verifyOtpState by viewModel.verifyLoginResponse.collectAsStateWithLifecycle()
    val loginWithOtp by viewModel.uiStateLoginResponse.collectAsStateWithLifecycle()
    logger.d("Screen: " + "OtpSendVerifyScreen()")

    var otpValue by remember { mutableStateOf("") }
    var phoneNo by remember { mutableStateOf("") }
    var inValidOTP by remember { mutableStateOf(false) }
    var isVerifyOtpApi by remember { mutableStateOf(false) }
    var invalidText by remember { mutableStateOf(R.string.txt_Enter_valid_OTP) }
    var isDialogVisible by remember { mutableStateOf(false) }
    var isFinished by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }
    var sendOtpViaCall by remember { mutableStateOf(false) }
    var sendOtpViaWhatsApp by remember { mutableStateOf(false) }
    var languageId = viewModel.getPrefData(SELECTED_LANGUAGE_ID)
    var userTypeId = viewModel.getPrefData(USER_TYPE_ID)
    val loginSuccess = stringResource(id = R.string.txt_login_success)

    var isInternetAvailable by remember { mutableStateOf(true) }

    val context = LocalContext.current
    isInternetAvailable = isNetworkAvailable(context)

    var encryptedPhoneNo = viewModel.getPrefData(USER_MOBILE_NO)  // encrypted from shared Pref

    val encryptedOtp = otpValue.encryptAES().toString().trim()

    // dialog to show otp on whatsapp or call
    if (showBottomSheet) {
        ChooseOneBottomSheet(onCallClick = {
            isDialogVisible = true
            sendOtpViaCall = true
        }, onWhatsappClick = {
            isDialogVisible = true
            sendOtpViaWhatsApp = true
        }, onDismiss = {
            showBottomSheet = false
        })
    }

    CustomDialog(
        isVisible = isDialogVisible,
        onDismiss = { isDialogVisible = false },
        message = stringResource(R.string.txt_loading)
    )

    logger.d("OtpSendVerify: $languageId .. $userTypeId .. $encryptedPhoneNo")

    // api for otp on call
    if (sendOtpViaCall) {
        LaunchedEffect(Unit) {
            viewModel.getOTPViewModel(encryptedPhoneNo)
            sendOtpViaCall = false
        }
    }
    // api for otp on Whatsapp
    if (sendOtpViaWhatsApp) {
        LaunchedEffect(Unit) {
            viewModel.getOTPWhatsappViewModel(encryptedPhoneNo)
            sendOtpViaWhatsApp = false
        }
    }

    // send otp response
    LaunchedEffect(sendOtpState) {
        when {
            sendOtpState.isLoading -> {
                isDialogVisible = true
            }

            sendOtpState.error.isNotEmpty() -> {
                logger.d("ResendOtp: ${sendOtpState.success}")
                isDialogVisible = false
            }

            sendOtpState.success != null -> {
                logger.d("ResendOtp: ${sendOtpState.success}")
                if (sendOtpState.success!!.status != true) {
                    context.toast(sendOtpState.success!!.response!!.message.toString())
                }
                isDialogVisible = false
            }
        }
    }

    // verify Api request & response
    if (isVerifyOtpApi) {
        isDialogVisible = true
        if (viewModel.getPrefData(IS_COMING_FROM) == LOGIN_WITH_OTP) {
            viewModel.getLoginWithOtpViewModel(
                LoginWithOtpRequest(
                    encryptedPhoneNo,
                    encryptedOtp,
                    userTypeId.toInt()
                )
            )
        } else {
            viewModel.getVerifyOtpViewModel(encryptedPhoneNo, encryptedOtp)
        }

        // verify otp response
        LaunchedEffect(verifyOtpState) {
            when {
                verifyOtpState.isLoading -> {
                    isDialogVisible = true
                }

                verifyOtpState.error.isNotEmpty() -> {
                    logger.d("VerifyOtp Error: ${verifyOtpState.error}")
                    isDialogVisible = false
                }

                verifyOtpState.success != null -> {
                    logger.d("VerifyOtp: ${verifyOtpState.success!!.response!!.message}")
                    if (verifyOtpState.success!!.status == true) {
                        onNext()
                    } else {
                        invalidText = R.string.txt_Enter_valid_OTP
                        inValidOTP = true
                    }

                    isDialogVisible = false
                }
            }
        }

        // Handle login with otp response state
        LaunchedEffect(loginWithOtp) {
            when {
                loginWithOtp.isLoading -> {
                    isDialogVisible = true
                }

                loginWithOtp.error.isNotEmpty() -> {
                    logger.d("Error: ${loginWithOtp.error}")
                    isDialogVisible = false
                }

                loginWithOtp.success != null -> {
                    if (loginWithOtp.success!!.status == true) {
                        context.toast(loginSuccess)
                        context.startActivity(
                            Intent(
                                context,
                                StudentDashboardActivity::class.java
                            )
                        )
                    } else {
//                        invalidText = loginWithOtp.success!!.message.toString()
                        inValidOTP = true
                    }
                    isDialogVisible = false
                }
            }
        }

        isVerifyOtpApi = false
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
                    text = stringResource(R.string.txt_verify_otp),
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

                if (inValidOTP) {
                    Text(
                        invalidText.toString(),
                        color = LightRed01,
                        modifier = Modifier.padding(horizontal = 10.dp),
                        fontSize = 10.sp
                    )
                }

                if (isFinished) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding()
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.txt_Resend_OTP),
                            modifier = Modifier
                                .padding(start = 12.dp, end = 12.dp, top = 16.dp)
                                .fillMaxWidth()
                                .clickable {
                                    isFinished = false
                                    showBottomSheet = true
                                },
                            fontFamily = fontBold,
                            textAlign = TextAlign.Center,
                            fontSize = 15.sp,
                            color = if (isSystemInDarkTheme()) {
                                INVITE_LIGHT_01
                            } else {
                                PrimaryBlue
                            }
                        )
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
                    BtnUi(
                        stringResource(R.string.txt_Verify_proceed),
                        onClick = {
                            if (otpValue.trim().length < 6) {
                                inValidOTP = true
                                invalidText = R.string.txt_Enter_valid_OTP
                            } else {
                                if (!isInternetAvailable) {
                                    inValidOTP = true
                                    invalidText = R.string.txt_oops_no_internet
                                } else {
                                    // call Api
                                    isVerifyOtpApi = true
                                }
                            }
                        },
                        enabled = otpValue.trim().length == 6
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