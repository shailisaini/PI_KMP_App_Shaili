package com.pi.ProjectInclusion.android.screens.login

import android.content.Context
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
import androidx.compose.runtime.rememberCoroutineScope
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
import com.pi.ProjectInclusion.android.common_UI.AESEncryption.decrypt
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
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.BackHandler
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION
import com.pi.ProjectInclusion.constants.ConstantVariables.SUCCESS
import com.pi.ProjectInclusion.constants.CustomDialog
import com.pi.ProjectInclusion.data.model.AuthenticationModel.GetUserTypeResponse
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun EnterPasswordScreen(
    viewModel: LoginViewModel,
    onNext: (String) -> Unit, //OtpSendVerifyUI
    onBack: () -> Unit,
    isForgetPassword: () -> Unit,
    mobileNo: String = "8851291824" // keeping it static wil change after login API
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
    LaunchedEffect(Unit) {
        viewModel.getUserType()
    }

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
                onNext = { onNext("") },
                onBack = onBack,
                isForgetPassword = isForgetPassword,
                viewModel = viewModel,
                mobileNo = mobileNo
            )
        }
    }
}

@Composable
fun PasswordUI(
    context: Context,
    onBack: () -> Unit,
    mobileNo: String = "",
    viewModel: LoginViewModel,
    isForgetPassword: () -> Unit,
    onNext: (String) -> Unit,    // OtpSendVerifyUI
) {
    val sendOtpState by viewModel.uiStateSendOtpResponse.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val isInternetAvailable by remember { mutableStateOf(true) }
    var isApiResponded by remember { mutableStateOf(false) }
    val internetMessage by remember { mutableStateOf("") }
    var showBottomSheet by remember { mutableStateOf(false) }
    var enterPasswordStr = rememberSaveable { mutableStateOf("") }
    var enterConfirmPasswordStr = rememberSaveable { mutableStateOf("") }
    val enterPassword = stringResource(R.string.txt_Enter_your_password)
    val showPassword = remember { mutableStateOf(false) }

    val mobNo = viewModel.mobileNumber

    val isEncrypted = remember { isEncrypted(viewModel.getUserEmail()) }
    if (isEncrypted) {
        decrypt(viewModel.getUserEmail())
    } else {
        viewModel.getUserEmail()
    }

    val encryptedPhoneNo = mobNo?.encryptAES().toString().trim()

    var isDialogVisible by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    val noDataMessage = stringResource(R.string.txt_oops_no_data_found)
    val invalidMobNo = stringResource(id = R.string.txt_Please_enter_password_)
//  languageData[LanguageTranslationsResponse.KEY_INVALID_MOBILE_NO_ERROR].toString()
    val txtContinue = stringResource(id = R.string.txt_login)
    val tvPassword = stringResource(id = R.string.txt_password)
    val forgotPassword = stringResource(id = R.string.txt_Forgot_Password_q)

    var passwordText = rememberSaveable { mutableStateOf("") }
    val enterMobile = stringResource(R.string.txt_enter_mobile_no_)
    var isValidMobNo by remember { mutableStateOf(false) }
    var sendOtpViaCall by remember { mutableStateOf(false) }
    var sendOtpViaWhatsApp by remember { mutableStateOf(false) }


    if (sendOtpViaCall) {
        // api for otp on call
        LaunchedEffect(Unit) {
            viewModel.getOTPViewModel(mobileNo)
            sendOtpViaCall = false
        }
    }
    if (sendOtpViaWhatsApp) {
        // api for otp on Whatsapp
        LaunchedEffect(Unit) {
            viewModel.getOTPWhatsappViewModel(mobileNo)
            sendOtpViaWhatsApp = false
        }
    }

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
                    onNext(mobileNo)
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
                        password = enterPasswordStr,
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
                                    } else { // if first digit of mobile is less than 6 then error will show
//                                        isDialogVisible = true
                                        onNext("")

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
    val onNext: (String) -> Unit = {}
    val onBack: () -> Unit = {}
    val isForgetPassword: () -> Unit = {}
    val viewModel: LoginViewModel =  koinViewModel()
    PasswordUI(context, onNext = { onNext("") }, onBack = onBack, isForgetPassword= isForgetPassword,viewModel = viewModel, mobileNo = "")
}
