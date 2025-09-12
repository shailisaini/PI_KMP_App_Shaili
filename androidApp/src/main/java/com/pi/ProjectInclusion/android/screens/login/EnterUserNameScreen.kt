package com.pi.ProjectInclusion.android.screens.login

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kmptemplate.logger.LoggerProvider
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.Bg_Gray1
import com.pi.ProjectInclusion.DARK_DEFAULT_BUTTON_TEXT
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.LightRed01
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.AESEncryption.encryptAES
import com.pi.ProjectInclusion.android.common_UI.AccountRecoverDialog
import com.pi.ProjectInclusion.android.common_UI.BtnUi
import com.pi.ProjectInclusion.android.common_UI.ChooseOneBottomSheet
import com.pi.ProjectInclusion.android.common_UI.CommonAlertDialog
import com.pi.ProjectInclusion.android.common_UI.DefaultBackgroundUi
import com.pi.ProjectInclusion.android.common_UI.TermsAndPrivacyText
import com.pi.ProjectInclusion.android.common_UI.UserNameTextField
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.BackHandler
import com.pi.ProjectInclusion.constants.CommonFunction.isNetworkAvailable
import com.pi.ProjectInclusion.constants.ConstantVariables.USER_EXIST
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION
import com.pi.ProjectInclusion.constants.ConstantVariables.IS_COMING_FROM
import com.pi.ProjectInclusion.constants.ConstantVariables.NEW_USER
import com.pi.ProjectInclusion.constants.ConstantVariables.PRIVACY_POLICY
import com.pi.ProjectInclusion.constants.ConstantVariables.REGISTER_NEW
import com.pi.ProjectInclusion.constants.ConstantVariables.SELECTED_LANGUAGE_ID
import com.pi.ProjectInclusion.constants.ConstantVariables.SUCCESS
import com.pi.ProjectInclusion.constants.ConstantVariables.TERMS_CONDITION
import com.pi.ProjectInclusion.constants.ConstantVariables.USER_MOBILE_NO
import com.pi.ProjectInclusion.constants.ConstantVariables.USER_TYPE_ID
import com.pi.ProjectInclusion.constants.CustomDialog
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel

@Composable
fun EnterUserNameScreen(
    viewModel: LoginViewModel,
    onNext: () -> Unit,
    onRegister: () -> Unit,
    onPrivacyPolicy: () -> Unit,
    onBack: () -> Unit,
) {
    val context = LocalContext.current
    BackHandler {
        onBack()
    }

    logger.d("Screen: " + "EnterUserNameScreen()")

    Surface(
        modifier = Modifier.fillMaxWidth(), color = White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Bg_Gray1),
            verticalArrangement = Arrangement.Top
        ) {
            LoginUI(
                viewModel = viewModel,
                context,
                onNext = onNext,
                onBack = onBack,
                onRegister = onRegister,
                onPrivacyPolicy = onPrivacyPolicy
            )
        }
    }
}

@Composable
fun LoginUI(
    viewModel: LoginViewModel,
    context: Context,
    onBack: () -> Unit,
    onRegister: () -> Unit,
    onPrivacyPolicy: () -> Unit,
    onNext: () -> Unit,
) {
    val colors = MaterialTheme.colorScheme
    var isDialogVisible by remember { mutableStateOf(false) }
    var noData = stringResource(R.string.txt_oops_no_data_found)
    var isInternetAvailable by remember { mutableStateOf(true) }
    val internetMessage = stringResource(R.string.txt_oops_no_internet)
    val invalidMobNo = stringResource(id = R.string.txt_enter_valid_mob_user)

    val txtContinue = stringResource(id = R.string.text_continue)
    val tvMobNo = stringResource(id = R.string.text_mobile_no_user)

    var userName = rememberSaveable { mutableStateOf("") }
    val enterMobile = stringResource(R.string.txt_enter_mobile_no_)
    var showError by remember { mutableStateOf(false) }
    var inValidMobNo by remember { mutableStateOf(false) }
    var isApiCalled by remember { mutableStateOf(false) }
    var confirmRecoverState by remember { mutableStateOf(false) }
    var isNewUserError by remember { mutableStateOf(false) }
    var showOtpBottomSheet by remember { mutableStateOf(false) }

    // user Type Id
    var userTypeId = viewModel.getPrefData(USER_TYPE_ID)
    var languageId = viewModel.getPrefData(SELECTED_LANGUAGE_ID)
    var recoverMessage = ""
    val encryptedPhoneNo = userName.value.encryptAES().toString().trim()
    var sendOtpViaCall by remember { mutableStateOf(false) }
    var sendOtpViaWhatsApp by remember { mutableStateOf(false) }
    var errorResponse = stringResource(R.string.key_error_response)
    val sendOtpState by viewModel.uiStateSendOtpResponse.collectAsStateWithLifecycle()

    CustomDialog(
        isVisible = isDialogVisible,
        onDismiss = { isDialogVisible = false },
        message = stringResource(R.string.txt_loading)
    )

    if (isApiCalled) {
        // saving userName & mobile no as a local variable in view Model
        viewModel.saveUserName(userName.value)

        LoggerProvider.logger.d("ValidateUserParams: ${userName.value} .. $encryptedPhoneNo")
        LaunchedEffect(Unit) {
            isInternetAvailable = isNetworkAvailable(context)
            if (!isInternetAvailable) {
                context.toast(internetMessage)
            } else {
                viewModel.getValidateUser(encryptedPhoneNo, userTypeId)
            }
        }

        val validateUserState by viewModel.validateUserResponse.collectAsStateWithLifecycle()
        LaunchedEffect(validateUserState) {
            when {
                validateUserState.isLoading -> {
                    isDialogVisible = true
                }

                validateUserState.error.isNotEmpty() -> {
                    isDialogVisible = false
                    LoggerProvider.logger.d("Error: ${validateUserState.error}")
                    context.toast(validateUserState.error)
                }

                validateUserState.success != null -> {
                    if (validateUserState.success!!.status == true) {
                        var apiResponse = validateUserState.success!!.response
                        LoggerProvider.logger.d("User ValidateResponse: ${validateUserState.success!!.response}")
                        if (apiResponse?.message == NEW_USER) {
                            if (userName.value.length == 10 && userName.value.all { char -> char.isDigit() }) {
                                // if new User & has mobile no only then register
                                isNewUserError = false
                                showOtpBottomSheet = true

                            } else {
//                                    show error message of only digits
                                showOtpBottomSheet = false
                                isNewUserError = true
                            }
                        } else if (apiResponse?.message == USER_EXIST) {
                            // if login with password
                            viewModel.savePrefData(
                                USER_MOBILE_NO, apiResponse.user!!.mobile.toString()
                            )
                            onNext()
                        } else {
                            // if account deactivated
                            confirmRecoverState = true
                            recoverMessage =
                                validateUserState.success?.response?.daysLeft.toString()
                        }
                    } else {
                        context.toast(validateUserState.success!!.message.toString())
                    }
                    isDialogVisible = false
                    isApiCalled = false
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
            isInternetAvailable = isNetworkAvailable(context)
            if (!isInternetAvailable) {
                context.toast(internetMessage)
            } else {
                viewModel.getOTPWhatsappViewModel(encryptedPhoneNo)
            }
        }
    }

    // dialog to show otp on whatsapp or call
    if (showOtpBottomSheet) {
        ChooseOneBottomSheet(onCallClick = {
            sendOtpViaCall = true
        }, onWhatsappClick = {
            sendOtpViaWhatsApp = true
        }, onDismiss = {
            showOtpBottomSheet = false
        })
    }

    // Error dialog
    if (isNewUserError) {
        CommonAlertDialog(
            alertMessage = stringResource(R.string.key_phn_error), onDismiss = {
                isNewUserError = false
            })
    }

    // recover dialog
    if (confirmRecoverState) {
        AccountRecoverDialog(msg = recoverMessage, onRestore = {
            confirmRecoverState = false
            showOtpBottomSheet = true
//                viewModel.getOTPViewModel(userName.value)
        }, onDismiss = {
            confirmRecoverState = false
        })
    }

    LaunchedEffect(sendOtpState) {
        when {
            sendOtpState.isLoading -> {
                isDialogVisible = true
            }

            sendOtpState.error.isNotEmpty() -> {
                logger.d("Error: ${sendOtpState.error}")
                isDialogVisible = false
            }

            sendOtpState.success != null -> {
                isDialogVisible = false
                if (sendOtpState.success!!.status == true) {
                    if (sendOtpState.success!!.response?.message == SUCCESS) {
                        viewModel.savePrefData(USER_MOBILE_NO, encryptedPhoneNo)
                        viewModel.savePrefData(IS_COMING_FROM, REGISTER_NEW)
                        logger.d("OtpSendVerify:Back to UserName ->"+ sendOtpState.success!!.response?.message)
                        onRegister() // Go to OTP Verify screen
                    } else {
                        val errorMessage =
                            sendOtpState.success!!.response?.message ?: sendOtpState.success?.error
                            ?: sendOtpState.success?.exception ?: errorResponse
                        context.toast(errorMessage)
                    }
                } else {
                    val errorMessage = sendOtpState.success?.message ?: sendOtpState.success?.error
                    ?: sendOtpState.success?.exception ?: errorResponse

                    context.toast(errorMessage)
                }
                sendOtpViaWhatsApp = false
                sendOtpViaCall = false
            }
        }
    }

    DefaultBackgroundUi(isShowBackButton = true, onBackButtonClick = {
        onBack()
    }, content = {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
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
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            tvMobNo,
                            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                            fontFamily = fontMedium,
                            fontSize = 14.sp,
                            color = if (isSystemInDarkTheme()) {
                                DARK_DEFAULT_BUTTON_TEXT
                            } else {
                                Gray
                            }
                        )

                        UserNameTextField(
                            isIcon = false,
                            icon = null,
                            colors = colors,
                            number = userName,
                            trueFalse = true,
                            hint = enterMobile.toString()
                        )

                        if (inValidMobNo) {
                            Text(
                                invalidMobNo.toString(),
                                color = LightRed01,
                                modifier = Modifier.padding(5.dp),
                                fontSize = 10.sp
                            )
                        }

                    Spacer(modifier = Modifier.height(15.dp))
                    BtnUi(
                        enabled = userName.value.isNotEmpty() && userName.value.length >= 6,
                        title = txtContinue,
                        onClick = {
                            if (userName.value.isNotEmpty() || userName.value.length >= 6) {
                                if (showError || userName.value.length < 6) {
                                    inValidMobNo = true
                                } else {
                                    isInternetAvailable = isNetworkAvailable(context)
                                    // if first digit of mobile is less than 6 then error will show
                                    if (!isInternetAvailable) {
                                        context.toast(internetMessage)
                                    } else {
                                        // call Api
                                        isDialogVisible = true
                                        isApiCalled = true
                                    }
                                }
                            }
                        },
                    )
                }
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    TermsAndPrivacyText(onTermsClick = {
                        onPrivacyPolicy()
                        viewModel.savePrefData(IS_COMING_FROM, TERMS_CONDITION)
                    }, onPrivacyClick = {
                        onPrivacyPolicy()
                        viewModel.savePrefData(IS_COMING_FROM, PRIVACY_POLICY)
                    })
                }
            }
        }
    })
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun LoginScreen() {
    val context = LocalContext.current
    val onNext: () -> Unit = {}
    val onRegister: () -> Unit = {}
    val onBack: () -> Unit = {}
//    LoginUI(context, onBack, onNext,onRegister)
}