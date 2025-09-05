package com.pi.ProjectInclusion.android.screens.login

import android.content.Context
import android.content.Intent
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.pi.ProjectInclusion.android.common_UI.AESEncryption.encryptAES
import com.example.kmptemplate.logger.LoggerProvider
import com.pi.ProjectInclusion.Bg_Gray1
import com.pi.ProjectInclusion.DARK_DEFAULT_BUTTON_TEXT
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.LightRed01
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.White
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.BtnUi
import com.pi.ProjectInclusion.android.common_UI.ChooseOneBottomSheet
import com.pi.ProjectInclusion.android.common_UI.DefaultBackgroundUi
import com.pi.ProjectInclusion.android.common_UI.OTPBtnUi
import com.pi.ProjectInclusion.android.common_UI.PasswordTextField
import com.pi.ProjectInclusion.android.common_UI.SurfaceLine
import com.pi.ProjectInclusion.android.screens.StudentDashboardActivity
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.BackHandler
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION
import com.pi.ProjectInclusion.constants.ConstantVariables.IS_COMING_FROM
import com.pi.ProjectInclusion.constants.ConstantVariables.LOGIN_WITH_OTP
import com.pi.ProjectInclusion.constants.ConstantVariables.SELECTED_LANGUAGE_ID
import com.pi.ProjectInclusion.constants.ConstantVariables.SUCCESS
import com.pi.ProjectInclusion.constants.ConstantVariables.TOKEN_PREF_KEY
import com.pi.ProjectInclusion.constants.ConstantVariables.USER_MOBILE_NO
import com.pi.ProjectInclusion.constants.ConstantVariables.USER_NAME
import com.pi.ProjectInclusion.constants.ConstantVariables.USER_TYPE_ID
import com.pi.ProjectInclusion.constants.CustomDialog
import com.pi.ProjectInclusion.data.model.authenticationModel.response.GetUserTypeResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.request.LoginRequest
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun EnterPasswordScreen(
    viewModel: LoginViewModel,
    onNext: () -> Unit, //OtpSendVerifyUI
    onBack: () -> Unit,
    isForgetPassword: () -> Unit
) {
    var isDialogVisible by remember { mutableStateOf(false) }
    val uiState by viewModel.uiStateType.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val userType = remember { mutableStateListOf<GetUserTypeResponse.UserTypeResponse>() }

    CustomDialog(
        isVisible = isDialogVisible,
        onDismiss = { isDialogVisible = false },
        message = stringResource(R.string.txt_loading)
    )
    BackHandler {
        // UserNameScreen
        onBack()
    }
    LoggerProvider.logger.d("Screen: " + "EnterPasswordScreen()")


    LaunchedEffect(uiState) {
        when {
            uiState.isLoading -> {
                userType.clear()
                isDialogVisible = true
            }

            uiState.error.isNotEmpty() -> {
                userType.clear()
                isDialogVisible = false
                LoggerProvider.logger.d("Error: ${uiState.error}")
                context.toast(uiState.error)
            }

            uiState.success != null -> {

                LoggerProvider.logger.d("Languages fetched: ${uiState.success!!.response}")
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxWidth(), color = White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Bg_Gray1),
            verticalArrangement = Arrangement.Top
        ) {
            PasswordUI(
                context,
                onNext = { onNext() },
                onBack = onBack,
                isForgetPassword = isForgetPassword,
                viewModel = viewModel,
            )
        }
    }
}

@Composable
fun PasswordUI(
    context: Context,
    onBack: () -> Unit,
    viewModel: LoginViewModel,
    isForgetPassword: () -> Unit,
    onNext: () -> Unit,    // OtpSendVerifyUI
) {

    val loginResponse by viewModel.uiStateLoginResponse.collectAsStateWithLifecycle()

    val isInternetAvailable by remember { mutableStateOf(true) }
    val internetMessage by remember { mutableStateOf("") }
    var showBottomSheet by remember { mutableStateOf(false) }
    val enterPassword = stringResource(R.string.txt_Enter_your_password)
    val showPassword = remember { mutableStateOf(false) }

    var isDialogVisible by remember { mutableStateOf(false) }
    var invalidMob = stringResource(id = R.string.txt_Please_enter_valid_password_)
    var invalidMobNo by remember { mutableStateOf(invalidMob)}
//  languageData[LanguageTranslationsResponse.KEY_INVALID_MOBILE_NO_ERROR].toString()
    val txtContinue = stringResource(id = R.string.txt_login)
    val tvPassword = stringResource(id = R.string.txt_password)
    val forgotPassword = stringResource(id = R.string.txt_Forgot_Password_q)
    val loginSuccess = stringResource(id = R.string.txt_login_success)

    var passwordText = rememberSaveable { mutableStateOf("") }
    var isValidMobNo by remember { mutableStateOf(false) }
    var sendOtpViaCall by remember { mutableStateOf(false) }
    var loginWithPassword by remember { mutableStateOf(false) }
    var sendOtpViaWhatsApp by remember { mutableStateOf(false) }
    var languageId = viewModel.getPrefData(SELECTED_LANGUAGE_ID)
    var userTypeId = viewModel.getPrefData(USER_TYPE_ID)
    var encryptedPhoneNo = viewModel.getPrefData(USER_MOBILE_NO)  // encrypted from shared Pref
    var userName = viewModel.userNameValue

    val encryptedUserName = userName?.encryptAES().toString().trim()
    val encryptedPassword = passwordText.value.encryptAES().toString().trim()

    if (loginWithPassword) {
        LoggerProvider.logger.d("LoginWithPassword: $languageId .. $userTypeId .. $encryptedPhoneNo")

        LaunchedEffect(Unit) {
        isDialogVisible = true
            viewModel.loginWithPasswordViewModel(
                LoginRequest(
                    encryptedUserName,
                    encryptedPassword,
                    userTypeId.toInt(),
                    languageId.toInt()
                )
            )
        }

        // Handle login response state
        LaunchedEffect(loginResponse) {
            when {
                loginResponse.isLoading -> {
                    isDialogVisible = true
                }

                loginResponse.error.isNotEmpty() -> {
                    loginWithPassword = false
                    LoggerProvider.logger.d("Error: ${loginResponse.error}")
                    isDialogVisible = false
                    isValidMobNo = true
                    invalidMobNo = loginResponse.error.toString()
                }

                loginResponse.success != null -> {
                    loginWithPassword = false
                    if (loginResponse.success!!.status == true){
                        // save token
                        viewModel.savePrefData(TOKEN_PREF_KEY,
                            "Bearer "+loginResponse.success!!.response?.accessToken.toString()
                        )
                        // save UserName
                        viewModel.savePrefData(USER_NAME,
                            loginResponse.success!!.response?.user?.username.toString().trim()
                        )
                        context.toast(loginSuccess)
                        context.startActivity(
                            Intent(
                                context,
                                StudentDashboardActivity::class.java
                            )
                        )
                    }
                    else{
                        isValidMobNo = true
                        invalidMobNo = loginResponse.success!!.message.toString()
                    }
                    isDialogVisible = false
                }
            }
        }
    }

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
    val sendOtpState by viewModel.uiStateSendOtpResponse.collectAsStateWithLifecycle()
    LaunchedEffect(sendOtpState) {
        when {
            sendOtpState.isLoading -> {
                isDialogVisible = true
            }

            sendOtpState.error.isNotEmpty() -> {
                LoggerProvider.logger.d("Error: ${sendOtpState.error}")
                isDialogVisible = false
            }

            sendOtpState.success != null -> {
//                LoggerProvider.logger.d("Languages fetched: ${list.size}")
                if (sendOtpState.success!!.response!!.message.equals(SUCCESS)) {
                    viewModel.savePrefData(IS_COMING_FROM, LOGIN_WITH_OTP)
                    onNext()
                } else {
                    context.toast(sendOtpState.success!!.response!!.message.toString())
                }

                isDialogVisible = false
            }
        }
    }

    // dialog to show otp on whatsapp or call
    if (showBottomSheet) {
        ChooseOneBottomSheet(onCallClick = {
            sendOtpViaCall = true
        }, onWhatsappClick = {
            sendOtpViaWhatsApp = true
        }, onDismiss = {
            showBottomSheet = false
        })
    }

    DefaultBackgroundUi(isShowBackButton = true, onBackButtonClick = {
        onBack()
    }, content = {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
//                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    painter = painterResource(id = R.drawable.login_img),
                    contentDescription = IMG_DESCRIPTION,
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        tvPassword,
                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp),
                        fontFamily = fontMedium,
                        fontSize = 14.sp,
                        color = if (isSystemInDarkTheme()) {
                            DARK_DEFAULT_BUTTON_TEXT
                        } else {
                            Gray
                        }
                    )

                    PasswordTextField(
                        password = passwordText,
                        showPassword = showPassword,
                        hint = enterPassword
                    )

                    if (isValidMobNo) {
                        Text(
                            invalidMobNo.toString(),
                            color = LightRed01,
                            modifier = Modifier.padding(horizontal = 10.dp),
                            fontSize = 10.sp
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .padding(8.dp)
                    ) {
                        Text(
                            forgotPassword,
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(5.dp)
                                .align(Alignment.End)
                                .clickable {
                                    isForgetPassword()
                                },
                            fontFamily = fontMedium,
                            fontSize = 14.sp,
                            color = PrimaryBlue,
                            textAlign = TextAlign.End
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        BtnUi(
                            enabled = passwordText.value.length >= 6,
                            title = txtContinue,
                            onClick = {
                                if (passwordText.value.isEmpty()) {
                                    isValidMobNo = true
                                } else {
                                    if (passwordText.value.length < 6) {
                                        isValidMobNo = true
                                    } else {
                                        loginWithPassword = true
                                    }
                                }
                            },
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            SurfaceLine()

                            Text(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                text = stringResource(R.string.txt_or),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Gray,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Start
                                )
                            )

                            SurfaceLine()
                        }

                        OTPBtnUi(
                            title = stringResource(R.string.txt_login_otp), onClick = {
                                showBottomSheet = true
                            })
                    }
                }
            }
        }
    })
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun LoginPassScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val onNext: () -> Unit = {}
    val onBack: () -> Unit = {}
    val isForgetPassword: () -> Unit = {}
    val viewModel: LoginViewModel = koinViewModel()
    PasswordUI(
        context,
        onNext = { onNext() },
        onBack = onBack, isForgetPassword = isForgetPassword, viewModel = viewModel
    )
}
