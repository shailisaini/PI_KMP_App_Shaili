package com.pi.ProjectInclusion.android.screens.login

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
import androidx.compose.ui.graphics.Color
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kmptemplate.logger.LoggerProvider
import com.pi.ProjectInclusion.Bg_Gray1
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.DARK_DEFAULT_BUTTON_TEXT
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.LightRed01
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.White
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.BackButtonPress
import com.pi.ProjectInclusion.android.common_UI.BtnUi
import com.pi.ProjectInclusion.android.common_UI.DefaultBackgroundUi
import com.pi.ProjectInclusion.android.common_UI.PasswordTextField
import com.pi.ProjectInclusion.android.common_UI.SurfaceLine
import com.pi.ProjectInclusion.android.navigation.AppRoute
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.BackHandler
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION
import com.pi.ProjectInclusion.constants.CustomDialog
import com.pi.ProjectInclusion.data.model.GetUserTypeResponse
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel

@Composable
fun EnterPasswordScreen(navController: NavHostController, viewModel: LoginViewModel) {
    var isDialogVisible by remember { mutableStateOf(false) }
    val uiState by viewModel.uiStateType.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val userType = remember { mutableStateListOf<GetUserTypeResponse.Data>() }
    CustomDialog(
        isVisible = isDialogVisible,
        onDismiss = { isDialogVisible = false },
        message = stringResource(R.string.txt_loading)
    )
    BackHandler {
        BackButtonPress(navController, AppRoute.UserTypeSelect.route)
    }
    LoggerProvider.logger.d("Screen: " + "EnterUserNameScreen()")
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
                isDialogVisible = false
                uiState.success!!.data.let {
                    userType.clear()
                    userType.addAll(it)
                }
                LoggerProvider.logger.d("Languages fetched: ${uiState.success!!.data}")
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
            PasswordUI(context, navController)
        }
    }
}

@Composable
fun PasswordUI(
    context: Context,
    navController: NavHostController
) {
    val scrollState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    val isInternetAvailable by remember { mutableStateOf(true) }
    var isApiResponded by remember { mutableStateOf(false) }
    val internetMessage by remember { mutableStateOf("") }

    var enterPasswordStr = rememberSaveable { mutableStateOf("") }
    var enterConfirmPasswordStr = rememberSaveable { mutableStateOf("") }
    val enterPassword = stringResource(R.string.txt_Enter_your_password)
    val showPassword = remember { mutableStateOf(false) }

    var isDialogVisible by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    val noDataMessage = stringResource(R.string.txt_oops_no_data_found)
    val invalidMobNo = stringResource(id = R.string.text_enter_no)
//  languageData[LanguageTranslationsResponse.KEY_INVALID_MOBILE_NO_ERROR].toString()
    val txtContinue = stringResource(id = R.string.text_continue)
    val tvPassword = stringResource(id = R.string.txt_password)
    val forgotPassword = stringResource(id = R.string.txt_Forgot_Password_q)

    var mobNo = rememberSaveable { mutableStateOf("") }
    val enterMobile = stringResource(R.string.txt_enter_mobile_no_)
    var showError by remember { mutableStateOf(false) }
    var inValidMobNo by remember { mutableStateOf(false) }

    DefaultBackgroundUi(isShowBackButton = true, onBackButtonClick = {
        BackButtonPress(navController, AppRoute.UserTypeSelect.route)
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
                ) {
                    Spacer(modifier = Modifier.height(10.dp))

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

                    if (inValidMobNo) {
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
                                .fillMaxWidth()
                                .padding(5.dp),
                            fontFamily = fontMedium,
                            fontSize = 14.sp,
                            color = PrimaryBlue,
                            textAlign = TextAlign.End
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        BtnUi(
                            enabled = mobNo.value.length >= 10,
                            title = txtContinue,
                            onClick = {
                                if (mobNo.value.isEmpty()) {
                                    /* scope.launch {
                                         isBottomSheetVisible = true
                                         sheetState.expand()
                                     }*/
                                    context.toast("Please enter mobile number.")
                                    navController.navigate(AppRoute.OtpSendVerifyUI.route)

                                    /*context.startActivity(
                                        Intent(
                                            context, ChangePasswordActivity::class.java
                                        ).apply {
                                            putExtra("", "")
                                        })*/
                                } else {
                                    showError = mobNo.value.isEmpty()
                                    val firstDigitChar = mobNo.value.toString().first()
                                    val firstDigit = firstDigitChar.digitToInt()
                                    if (showError || mobNo.value.length < 10) {
                                        inValidMobNo = true
                                    } else { // if first digit of mobile is less than 6 then error will show
                                        if (firstDigit < 6) {
                                            inValidMobNo = true
                                        } else {
                                            isDialogVisible = true

                                        }
                                    }
                                }
                            },
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            SurfaceLine()
                            Text(
                                modifier = Modifier.padding(horizontal = 15.dp),
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
    PasswordUI(context, navController)
}
