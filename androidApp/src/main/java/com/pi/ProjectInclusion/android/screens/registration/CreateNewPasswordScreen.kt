package com.pi.ProjectInclusion.android.screens.registration

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.Bg_Gray
import com.pi.ProjectInclusion.Bg_Gray1
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.DARK_BODY_TEXT
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.Dark_02
import com.pi.ProjectInclusion.Dark_Selected_BG
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.GrayLight02
import com.pi.ProjectInclusion.PRIMARY_AURO_BLUE
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.PrimaryBlueLt1
import com.pi.ProjectInclusion.Transparent
import com.pi.ProjectInclusion.White
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.AESEncryption.encryptAES
import com.pi.ProjectInclusion.android.common_UI.BtnUi
import com.pi.ProjectInclusion.android.common_UI.CustomToastMessage
import com.pi.ProjectInclusion.android.common_UI.DefaultBackgroundUi
import com.pi.ProjectInclusion.android.common_UI.EncryptedCommonFunction.encryptedPhoneNo
import com.pi.ProjectInclusion.android.common_UI.EncryptedCommonFunction.isEncryptedPhone
import com.pi.ProjectInclusion.android.common_UI.PasswordTextField
import com.pi.ProjectInclusion.android.common_UI.SnackbarWithProgress
import com.pi.ProjectInclusion.android.common_UI.TextWithIconOnLeft
import com.pi.ProjectInclusion.android.utils.fontRegular
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.BackHandler
import com.pi.ProjectInclusion.constants.CommonFunction.LoginScreenTitle
import com.pi.ProjectInclusion.constants.CommonFunction.NoDataFound
import com.pi.ProjectInclusion.constants.CommonFunction.ShowError
import com.pi.ProjectInclusion.constants.CommonFunction.isNetworkAvailable
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION
import com.pi.ProjectInclusion.constants.ConstantVariables.SELECTED_LANGUAGE_ID
import com.pi.ProjectInclusion.constants.ConstantVariables.TOKEN_PREF_KEY
import com.pi.ProjectInclusion.constants.ConstantVariables.USER_MOBILE_NO
import com.pi.ProjectInclusion.constants.ConstantVariables.USER_TYPE_ID
import com.pi.ProjectInclusion.constants.ConstantVariables.USER_TYPE_NAME
import com.pi.ProjectInclusion.constants.CustomDialog
import com.pi.ProjectInclusion.data.model.authenticationModel.request.CreatePasswordRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.response.GetUserTypeResponse
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern

@Composable
fun CreateNewPasswordScreen(
    onNext: () -> Unit,  //EnterUserProfileScreen
    onBack: () -> Unit,
    otpSendVerify: () -> Unit, // OtpSendVerifyUI
    viewModel: LoginViewModel,
) {

    logger.d("Screen: " + "CreateNewPasswordScreen()")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Bg_Gray1),
        verticalArrangement = Arrangement.Top
    ) {
        DefaultBackgroundUi(isShowBackButton = true, onBackButtonClick = {
            onBack()
        }, content = {
            CreateNewPasswordUI(
                onNext = onNext,
                onBack = onBack,
                otpSendVerify = otpSendVerify,
                viewModel = viewModel
            )
        })
    }
}

@Composable
fun CreateNewPasswordUI(
    onBack: () -> Unit,
    otpSendVerify: () -> Unit,
    onNext: () -> Unit,
    viewModel: LoginViewModel,
) {

    BackHandler {
        onBack()
    }

    val context = LocalContext.current
    var showBottomSheet by remember { mutableStateOf(false) }
    var enterPasswordStr = rememberSaveable { mutableStateOf("") }
    var enterConfirmPasswordStr = rememberSaveable { mutableStateOf("") }
    val enterPassword = stringResource(R.string.txt_Enter_your_password)
    val enterConfirmPassword = stringResource(R.string.txt_Confirm_your_password)
    val txtContinue = stringResource(R.string.key_create_password)
    var showError by remember { mutableStateOf(false) }
    var inValidPassword by remember { mutableStateOf(false) }
    var isDialogVisible by remember { mutableStateOf(false) }
    val showPassword = remember { mutableStateOf(false) }
    val showConfirmPassword = remember { mutableStateOf(false) }
    var showToast by remember { mutableStateOf(false) }
    val txtCharacter = stringResource(R.string.txt_Passwords_must_be_8_16_characters)
    val txtUppercase = stringResource(R.string.txt_Including_one_uppercase_letter)
    val txtAtleastOne = stringResource(R.string.txt_Must_include_at_least_one_number)
    val txtSpecialCharacter = stringResource(R.string.txt_Must_include_one_special_characters)
    val enterPasswordMsgStr = stringResource(R.string.txt_Please_enter_Password)
    val enterConfirmPasswordMsgStr = stringResource(R.string.txt_Please_enter_confirm_Password)
    val enterConfirmPasswordSameMsgStr =
        stringResource(R.string.txt_Please_enter_confirm_Password_same)
    var isInternetAvailable by remember { mutableStateOf(true) }
    val internetMessage = stringResource(R.string.txt_oops_no_internet)

    val minLength = enterConfirmPasswordStr.value.length >= 8 || enterPasswordStr.value.length >= 8
    val hasLetter =
        enterConfirmPasswordStr.value.any { it.isUpperCase() } || enterPasswordStr.value.any { it.isUpperCase() }
    val hasDigit =
        enterConfirmPasswordStr.value.any { it.isDigit() } || enterPasswordStr.value.any { it.isDigit() }
    val hasSymbol = Pattern.compile("[^a-zA-Z0-9]").matcher(enterConfirmPasswordStr.value)
        .find() || Pattern.compile("[^a-zA-Z0-9]").matcher(enterPasswordStr.value).find()

    var mobileNo = viewModel.userNameValue  // will remove this after Api implementation
    val createRegisterPasswordState by viewModel.createRegPasswordResponse.collectAsStateWithLifecycle()
    val encryptedPassword = enterConfirmPasswordStr.value.encryptAES().toString().trim()
    val encryptedUserName = viewModel.userNameValue!!.encryptAES().toString().trim()
    val encryptedMobile = encryptedPhoneNo(mobileNo.toString().trim())
    var languageId = viewModel.getPrefData(SELECTED_LANGUAGE_ID)
    var userTypeId = viewModel.getPrefData(USER_TYPE_ID)
    var strToken = viewModel.getPrefData(TOKEN_PREF_KEY)

    var userTypeName = viewModel.getPrefData(USER_TYPE_NAME)

    LaunchedEffect(createRegisterPasswordState) {
        when {
            createRegisterPasswordState.isLoading -> {
                isDialogVisible = true
            }

            createRegisterPasswordState.error.isNotEmpty() -> {
                logger.d("Create/Register Password Error: ${createRegisterPasswordState.error}")
                isDialogVisible = false
            }

            createRegisterPasswordState.success != null -> {
                logger.d("Create/Register Password Response :- ${createRegisterPasswordState.success!!.response}")
                if (createRegisterPasswordState.success!!.status == true) {
                    if (createRegisterPasswordState.success!!.message != null) {
                        if (createRegisterPasswordState.success!!.message == "Success") {
                            showToast = true
                        } else {
                            context.toast(createRegisterPasswordState.success!!.message!!)
                        }
                    }
                    if (createRegisterPasswordState.success!!.response != null) {
                        viewModel.savePrefData(
                            USER_MOBILE_NO,
                            createRegisterPasswordState.success!!.response?.mobile.toString()
                        )
                    }
                    onNext()
                } else {
                    context.toast(createRegisterPasswordState.success!!.message!!)
                }
                isDialogVisible = false
            }
        }
    }

    if (showBottomSheet) {
        SelectUserBottomSheet(
            viewModel = viewModel, onDismiss = {
                showBottomSheet = false
            })
    }

    if (showToast) {
        SnackbarWithProgress(
            message = stringResource(R.string.key_password_created),
            onDismiss = { showToast = false })
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .fillMaxSize(),

            ) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .fillMaxSize()
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top
            ) {
                LoginScreenTitle(
                    stringResource(R.string.txt_Set_your_Password),
                    Black,
                    Gray,
                    Transparent,
                    stringResource(R.string.txt_set_password_desc)
                )

                Row(
                    modifier = Modifier
                        .padding(start = 5.dp, end = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .background(color = PrimaryBlueLt1)
                            .wrapContentSize(),
                        elevation = CardDefaults.cardElevation(1.dp),
                        colors = CardDefaults.cardColors(
                            if (isSystemInDarkTheme()) {
                                Dark_02
                            } else {
                                PrimaryBlueLt1
                            }
                        ),
                        border = BorderStroke(width = 1.dp, PrimaryBlue),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(vertical = 5.dp, horizontal = 15.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(R.drawable.img_teacher)
                                        .decoderFactory(SvgDecoder.Factory())
                                        .placeholder(R.drawable.img_teacher)
                                        .error(R.drawable.img_teacher).build()
                                ),
                                contentDescription = IMG_DESCRIPTION,
                                modifier = Modifier
                                    .size(50.dp)
                                    .background(Color.Unspecified),
                                contentScale = ContentScale.Crop
                            )

                            Text(
                                text = userTypeName,
                                modifier = Modifier.padding(
                                    top = 5.dp, bottom = 5.dp
                                ),
                                textAlign = TextAlign.Start,
                                fontFamily = fontRegular,
                                fontSize = 10.sp,
                                color = Black
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(R.string.txt_change_user),
                            modifier = Modifier.padding(
                                top = 5.dp, bottom = 5.dp
                            ),
                            textAlign = TextAlign.Start,
                            fontFamily = fontRegular,
                            fontSize = 12.sp,
                            color = Black
                        )
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .wrapContentHeight()
                                .clickable {
                                    showBottomSheet = true
                                },
                            colors = if (isSystemInDarkTheme()) {
                                CardDefaults.cardColors(Dark_01)
                            } else {
                                CardDefaults.cardColors(
                                    containerColor = White,
                                    contentColor = White,
                                    disabledContentColor = White,
                                    disabledContainerColor = White
                                )
                            },
                            border = BorderStroke(0.8.dp, color = PrimaryBlue)
                        ) {
                            TextWithIconOnLeft(
                                text = stringResource(R.string.txt_change),
                                icon = ImageVector.vectorResource(id = R.drawable.ic_refresh),
                                textColor = PrimaryBlue,
                                iconColor = Color.Unspecified,
                                modifier = Modifier.padding(10.dp),
                                onClick = { showBottomSheet = true })
                        }
                    }
                }

                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.txt_New_Password))
                        pushStyle(SpanStyle(color = Color.Red))
                        append("*")
                        pop()
                    },
                    modifier = Modifier.padding(
                        top = 24.dp, bottom = 10.dp, start = 8.dp, end = 8.dp
                    ),
                    textAlign = TextAlign.Start,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_BODY_TEXT
                    } else {
                        Bg_Gray
                    }
                )

                PasswordTextField(
                    password = enterPasswordStr, showPassword = showPassword, hint = enterPassword
                )


                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.txt_Confirm_Password))
                        pushStyle(SpanStyle(color = Color.Red))
                        append("*")
                        pop()
                    },
                    modifier = Modifier.padding(
                        top = 24.dp, bottom = 10.dp, start = 8.dp, end = 8.dp
                    ),
                    textAlign = TextAlign.Start,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_BODY_TEXT
                    } else {
                        Bg_Gray
                    }
                )

                PasswordTextField(
                    password = enterConfirmPasswordStr,
                    showPassword = showConfirmPassword,
                    hint = enterConfirmPassword
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(
                        top = 16.dp, start = 12.dp, end = 12.dp, bottom = 8.dp
                    )
                ) {
                    Checkbox(
                        checked = minLength,
                        onCheckedChange = null,
                        colors = if (isSystemInDarkTheme()) {
                            CheckboxDefaults.colors(
                                checkedColor = Color.Transparent,
                                uncheckedColor = Color.LightGray,
                                checkmarkColor = PrimaryBlue
                            )
                        } else {
                            CheckboxDefaults.colors(
                                checkedColor = Color.Transparent,
                                uncheckedColor = Color.LightGray,
                                checkmarkColor = PrimaryBlue
                            )
                        },
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = txtCharacter,
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                        textAlign = TextAlign.Center,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = if (isSystemInDarkTheme()) {
                            DARK_BODY_TEXT
                        } else {
                            Gray
                        }
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 8.dp)
                ) {
                    Checkbox(
                        checked = hasLetter,
                        onCheckedChange = null,
                        colors = if (isSystemInDarkTheme()) {
                            CheckboxDefaults.colors(
                                checkedColor = Color.Transparent,
                                uncheckedColor = Color.LightGray,
                                checkmarkColor = PrimaryBlue
                            )
                        } else {
                            CheckboxDefaults.colors(
                                checkedColor = Color.Transparent,
                                uncheckedColor = Color.LightGray,
                                checkmarkColor = PrimaryBlue
                            )
                        },
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = txtUppercase,
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                        textAlign = TextAlign.Center,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = if (isSystemInDarkTheme()) {
                            DARK_BODY_TEXT
                        } else {
                            Gray
                        }
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 8.dp)
                ) {
                    Checkbox(
                        checked = hasDigit,
                        onCheckedChange = null,
                        colors = if (isSystemInDarkTheme()) {
                            CheckboxDefaults.colors(
                                checkedColor = Color.Transparent,
                                uncheckedColor = Color.LightGray,
                                checkmarkColor = PrimaryBlue
                            )
                        } else {
                            CheckboxDefaults.colors(
                                checkedColor = Color.Transparent,
                                uncheckedColor = Color.LightGray,
                                checkmarkColor = PrimaryBlue
                            )
                        },
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = txtAtleastOne,
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                        textAlign = TextAlign.Center,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = if (isSystemInDarkTheme()) {
                            DARK_BODY_TEXT
                        } else {
                            Gray
                        }
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 8.dp)
                ) {
                    Checkbox(
                        checked = hasSymbol,
                        onCheckedChange = null,
                        colors = if (isSystemInDarkTheme()) {
                            CheckboxDefaults.colors(
                                checkedColor = Color.Transparent,
                                uncheckedColor = Color.LightGray,
                                checkmarkColor = PrimaryBlue
                            )
                        } else {
                            CheckboxDefaults.colors(
                                checkedColor = Color.Transparent,
                                uncheckedColor = Color.LightGray,
                                checkmarkColor = PrimaryBlue
                            )
                        },
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = txtSpecialCharacter,
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                        textAlign = TextAlign.Center,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = if (isSystemInDarkTheme()) {
                            DARK_BODY_TEXT
                        } else {
                            Gray
                        }
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(bottom = 10.dp)
                    .background(
                        color = if (isSystemInDarkTheme()) {
                            Dark_01
                        } else {
                            Color.White
                        }
                    ),
                contentAlignment = Alignment.BottomCenter,
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                ) {
                    BtnUi(
                        txtContinue, onClick = {
                            if (enterPasswordStr.value.isEmpty()) {
                                context.toast(enterPasswordMsgStr)
                            } else if (enterConfirmPasswordStr.value.isEmpty()) {
                                context.toast(enterConfirmPasswordMsgStr)
                            } else if (enterConfirmPasswordStr.value != enterPasswordStr.value) {
                                context.toast(enterConfirmPasswordSameMsgStr)
                            } else {
                                showError = enterConfirmPasswordStr.value.isEmpty()
                                if (showError || enterConfirmPasswordStr.value.length < 8) {
                                    inValidPassword = true
                                } else {
                                    isInternetAvailable = isNetworkAvailable(context)
                                    if (!isInternetAvailable) {
                                        context.toast(internetMessage)
                                    } else {
                                        isDialogVisible = true
                                        val passwordRequest = CreatePasswordRequest(
                                            encryptedUserName,
                                            encryptedPassword,
                                            encryptedMobile.toString(),
                                            userTypeId.toInt(),
                                            languageId.toInt()
                                        )
                                        viewModel.createRegisterPassword(passwordRequest, strToken)
                                    }
                                }
                            }
                        }, true
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectUserBottomSheet(
    viewModel: LoginViewModel,
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val context = LocalContext.current
    val userType = remember { mutableStateListOf<GetUserTypeResponse.UserTypeResponse>() }
    var isDialogVisible by remember { mutableStateOf(true) }
    var isInternetAvailable by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    val selectedLanguage = remember { mutableStateOf<String?>(null) }

    val internetMessage = stringResource(R.string.txt_oops_no_internet)
    val noDataMessage = stringResource(R.string.txt_oops_no_data_found)

    CustomDialog(
        isVisible = isDialogVisible,
        onDismiss = { isDialogVisible = false },
        message = stringResource(R.string.txt_loading)
    )
    LaunchedEffect(Unit) {
        isInternetAvailable = isNetworkAvailable(context)
        if (!isInternetAvailable) {
            context.toast(internetMessage)
        } else {
            viewModel.getUserType()
        }
    }
    val uiState by viewModel.uiStateType.collectAsStateWithLifecycle()
    LaunchedEffect(uiState) {
        when {
            uiState.isLoading -> {
                isDialogVisible = true
                userType.clear()
            }

            uiState.error.isNotEmpty() -> {
                context.toast(uiState.error)
                userType.clear()
                isDialogVisible = false
            }

            uiState.success != null -> {
                uiState.success!!.let {
                    userType.clear()
                    userType.addAll(it.response!!)
                }
                isDialogVisible = false
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        }, sheetState = sheetState
    ) {
        // Sheet content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, bottom = 20.dp)
        ) {
            val errColor = PrimaryBlue
            val scrollState = rememberLazyGridState()
            Text(
                stringResource(R.string.choose_an_option),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp, start = 10.dp),
                fontSize = 14.sp,
                fontFamily = fontRegular,
                color = Gray,
                textAlign = TextAlign.Start
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, bottom = 20.dp)
            ) {
                if (userType.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()

                    ) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            state = scrollState,
                            contentPadding = PaddingValues(vertical = 15.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp, horizontal = 8.dp)
                                .draggable(
                                    orientation = Orientation.Vertical,
                                    state = rememberDraggableState { delta ->
                                        coroutineScope.launch {
                                            scrollState.scrollBy(-delta)
                                        }
                                    })
                        ) {
                            items(userType.size) { index ->
                                UserTypeCard(
                                    viewModel,
                                    onNext = onDismiss,
                                    context,
                                    isSelected = selectedIndex == index,
                                    index,
                                    userType = userType,
                                    onItemClicked = {
                                        selectedIndex =
                                            if (selectedIndex == index) null else index // Toggle selection
                                        selectedLanguage.value = userType[index].id.toString()
                                    })
                            }
                        }
                    }
                } else {
                    isInternetAvailable = isNetworkAvailable(context)
                    if (!isInternetAvailable) {
                        ShowError(
                            internetMessage, errColor, painterResource(R.drawable.sad_emoji)
                        )
                    } else {
                        NoDataFound(noDataMessage, painterResource(R.drawable.sad_emoji))
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun NewPasswordScreen() {
    val onNext: () -> Unit = {}
    val onBack: () -> Unit = {}
    val isForgetPassword: () -> Unit = {}
//    CreateNewPasswordUI(onNext, onBack, isForgetPassword)
}

@Composable
fun UserTypeCard(
    viewModel: LoginViewModel,
    onNext: () -> Unit,
    context: Context,
    isSelected: Boolean = true,
    index: Int,
    userType: MutableList<GetUserTypeResponse.UserTypeResponse>,
    onItemClicked: () -> Unit = {},
) {
    val userTypeIndex = userType[index]
    val errorToast = stringResource(R.string.select_userType)

    val selectedBorder = if (isSelected) BorderStroke(
        width = 1.dp, if (isSystemInDarkTheme()) {
            PRIMARY_AURO_BLUE
        } else {
            PrimaryBlue
        }
    ) else BorderStroke(
        width = 0.5.dp, if (isSystemInDarkTheme()) {
            Dark_02
        } else {
            GrayLight02
        }
    )
    val backGroundColor = if (isSelected) {
        if (isSystemInDarkTheme()) {
            Dark_Selected_BG
        } else {
            PrimaryBlueLt1
        }
    } else {
        if (isSystemInDarkTheme()) {
            Dark_02
        } else {
            Color.Companion.White
        }
    }

    Card(
        modifier = Modifier
            .clickable {
                // saving User type ID
                viewModel.savePrefData(USER_TYPE_ID, userTypeIndex.id.toString())
                viewModel.savePrefData(USER_TYPE_NAME, userTypeIndex.name.toString())
                onItemClicked.invoke()
                onNext()
            }
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(1.dp),
        colors = CardDefaults.cardColors(
            if (isSystemInDarkTheme()) {
                Dark_02
            } else {
                Color.Companion.White
            }
        ),
        border = selectedBorder,
        shape = RoundedCornerShape(12.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = backGroundColor),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 25.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .background(Color.Unspecified)
                        .size(65.dp),
                    contentScale = ContentScale.Fit,
//                    painter = if (userTypeIndex.isNotEmpty()) {
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current).data(userTypeIndex.icon)
                            .decoderFactory(SvgDecoder.Factory()).size(Size.ORIGINAL)
                            .placeholder(R.drawable.img_teacher).error(R.drawable.img_teacher)
                            .build()
                    ),
                    contentDescription = IMG_DESCRIPTION
                )

                Text(
                    "${userTypeIndex.name} ",
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    fontSize = 14.sp,
                    color = if (isSelected) Black else Gray,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 5.dp)
//                            .heightIn(min = 40.dp)
                )
            }
        }
    }
}