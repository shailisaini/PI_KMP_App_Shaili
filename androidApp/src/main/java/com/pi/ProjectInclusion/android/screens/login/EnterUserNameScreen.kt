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
import com.pi.ProjectInclusion.Bg_Gray1
import com.pi.ProjectInclusion.DARK_DEFAULT_BUTTON_TEXT
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.LightRed01
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.AccountRecoverDialog
import com.pi.ProjectInclusion.android.common_UI.BtnUi
import com.pi.ProjectInclusion.android.common_UI.DefaultBackgroundUi
import com.pi.ProjectInclusion.android.common_UI.MobileTextField
import com.pi.ProjectInclusion.android.common_UI.TermsAndPrivacyText
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.BackHandler
import com.pi.ProjectInclusion.constants.ConstantVariables.USER_EXIST
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION
import com.pi.ProjectInclusion.constants.ConstantVariables.NEW_USER
import com.pi.ProjectInclusion.constants.CustomDialog
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel

@Composable
fun EnterUserNameScreen(
    viewModel: LoginViewModel,
    onNext: () -> Unit,
    onRegister: () -> Unit,
    onBack: () -> Unit
) {
    var isDialogVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current
    CustomDialog(
        isVisible = isDialogVisible,
        onDismiss = { isDialogVisible = false },
        message = stringResource(R.string.txt_loading)
    )
    BackHandler {
        onBack()
    }
    LoggerProvider.logger.d("Screen: " + "EnterUserNameScreen()")

    Surface(
        modifier = Modifier.fillMaxWidth(), color = White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Bg_Gray1),
            verticalArrangement = Arrangement.Top
        ) {
            LoginUI(viewModel = viewModel,context, onNext = onNext, onBack = onBack, onRegister = onRegister)
        }
    }
}

@Composable
fun LoginUI(
    viewModel: LoginViewModel,
    context: Context,
    onBack: () -> Unit,
    onRegister: () -> Unit,
    onNext: () -> Unit,
) {
    val colors = MaterialTheme.colorScheme
    var isDialogVisible by remember { mutableStateOf(false) }
    val internetMessage = stringResource(R.string.txt_oops_no_internet)
    var apiResponse = stringResource(R.string.txt_oops_no_internet)
    val noDataMessage = stringResource(R.string.txt_oops_no_data_found)
    val invalidMobNo = stringResource(id = R.string.text_enter_no)

    val txtContinue = stringResource(id = R.string.text_continue)
    val tvMobNo = stringResource(id = R.string.text_mobile_no_user)

    val uiState by viewModel.validateUserResponse.collectAsStateWithLifecycle()
    var mobNo = rememberSaveable { mutableStateOf("") }
    val enterMobile = stringResource(R.string.txt_enter_mobile_no_)
    var showError by remember { mutableStateOf(false) }
    var inValidMobNo by remember { mutableStateOf(false) }
    var isApiCalled by remember { mutableStateOf(false) }
    var confirmRecoverState by remember { mutableStateOf(false) }

    if (isApiCalled) {
        LaunchedEffect(Unit) {
            viewModel.getValidateUser("9654074714", "3")
        }

        LaunchedEffect(uiState) {
            when {
                uiState.isLoading -> {
                    isDialogVisible = true
                }

                uiState.error.isNotEmpty() -> {
                    isDialogVisible = false
                    LoggerProvider.logger.d("Error: ${uiState.error}")
                    context.toast(uiState.error)
                }

                uiState.success != null -> {
                    isDialogVisible = false
                    apiResponse = uiState.success!!.response?.message.toString()
//                    LoggerProvider.logger.d("User Data fetched: ${uiState.success!!.response}")
                    isApiCalled = false
                    if (apiResponse == NEW_USER) {
                        // if new User
                        onRegister()
                    } else if (apiResponse == USER_EXIST) {
                        // if login with password
                        onNext()
                    } else {
                        // if account deactivated
                        confirmRecoverState = true
                    }
                }
            }
        }
    }

    // recover dialog
    if (confirmRecoverState) {
        AccountRecoverDialog(
            onRestore = {
                confirmRecoverState = false
                viewModel.saveMobileNumber(mobNo.value)
//                LoggerProvider.logger.d("Screen: Moving to$onRegister.route")
                onRegister() // Go to OTP screen
            },
            onDismiss = {
                confirmRecoverState = false
            }
        )
    }

    DefaultBackgroundUi(isShowBackButton = true, onBackButtonClick = {
        onBack()
    }, content = {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
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

                    MobileTextField(
                        isIcon = false,
                        icon = null,
                        colors = colors,
                        number = mobNo,
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
                        enabled = mobNo.value.length >= 10,
                        title = txtContinue,
                        onClick = {
                            if (mobNo.value.isEmpty()) {
                                inValidMobNo = true
                            } else {
                                if (showError || mobNo.value.length < 10) {
                                    inValidMobNo = true
                                } else { // if first digit of mobile is less than 6 then error will show
                                    showError = mobNo.value.isEmpty()
                                    val firstDigitChar = mobNo.value.toString().first()
                                    val firstDigit = firstDigitChar.digitToInt()
                                    if (firstDigit < 6) {
                                        inValidMobNo = true
                                    } else {
                                        isApiCalled = true
                                    }
                                }
                            }
                        },
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        TermsAndPrivacyText(
                            onTermsClick = {

                            },
                            onPrivacyClick = { }
                        )
                    }
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