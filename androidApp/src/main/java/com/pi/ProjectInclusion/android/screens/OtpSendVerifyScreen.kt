package com.pi.ProjectInclusion.android.screens

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.DARK_BODY_TEXT
import com.pi.ProjectInclusion.DARK_TITLE_TEXT
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.INVITE_LIGHT_01
import com.pi.ProjectInclusion.PRIMARY_AURO_BLUE
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.BtnUi
import com.pi.ProjectInclusion.android.common_UI.DefaultBackgroundUi
import com.pi.ProjectInclusion.android.common_UI.OtpInputField
import com.pi.ProjectInclusion.android.common_UI.TextWithIconOnLeft
import com.pi.ProjectInclusion.android.navigation.AppRoute
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel

@Composable
fun OtpSendVerifyScreen(navController: NavHostController, viewModel: LoginViewModel) {

    var otpValue by remember { mutableStateOf("") }
    var phoneNo by remember { mutableStateOf("") }
    val colors = MaterialTheme.colorScheme
    var inValidOTP by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    val invalidOtpText = remember { mutableStateOf("Please Enter valid OTP") }
    var isDialogVisible by remember { mutableStateOf(false) }

    DefaultBackgroundUi(isShowBackButton = true, onBackButtonClick = {
        navController.popBackStack()
        navController.navigate(AppRoute.LanguageSelect.route)
    }, content = {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "OTP Sent",
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
                    text = "Please enter the OTP received on phone number XXXXXX9168",
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextWithIconOnLeft(
                        text = "Resend OTP",
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
//                            loginViewModel.setGradeReportPage("")
//                            loginViewModel.setSelectedTabId(0)
//                            loginViewModel.setGradeId("")
//                            navHostController.navigate(AppRoute.TeacherQuizReports.route)
                        })

                    Spacer(modifier = Modifier.weight(1f))

                    TextWithIconOnLeft(
                        text = "OTP on call",
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
//                            loginViewModel.setGradeReportPage("")
//                            loginViewModel.setSelectedTabId(0)
//                            loginViewModel.setGradeId("")
//                            navHostController.navigate(AppRoute.TeacherQuizReports.route)
                        })
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(bottom = 32.dp)
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
                        .padding(horizontal = 10.dp, vertical = 24.dp)
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
                        "Verify & proceed",
                        onClick = {
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