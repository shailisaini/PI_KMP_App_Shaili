package com.pi.ProjectInclusion.android.screens.registration

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.kmptemplate.logger.LoggerProvider
import com.pi.ProjectInclusion.Bg_Gray
import com.pi.ProjectInclusion.Bg_Gray1
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.DARK_BODY_TEXT
import com.pi.ProjectInclusion.DARK_DEFAULT_BUTTON_TEXT
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.LightRed01
import com.pi.ProjectInclusion.OrangeSubTitle
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.Transparent
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.CameraGalleryDialog
import com.pi.ProjectInclusion.android.common_UI.CameraPermission
import com.pi.ProjectInclusion.android.common_UI.GenderOption
import com.pi.ProjectInclusion.android.common_UI.MobileTextField
import com.pi.ProjectInclusion.android.common_UI.RegistrationHeader
import com.pi.ProjectInclusion.android.common_UI.SmallBtnUi
import com.pi.ProjectInclusion.android.common_UI.TextFieldWithLeftIcon
import com.pi.ProjectInclusion.android.common_UI.TextViewField
import com.pi.ProjectInclusion.android.common_UI.showDatePickerDialog
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.fontRegular
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.BackHandler
import com.pi.ProjectInclusion.constants.ConstantVariables.ASTRICK
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION
import com.pi.ProjectInclusion.constants.ConstantVariables.KEY_FEMALE
import com.pi.ProjectInclusion.constants.ConstantVariables.KEY_MALE
import com.pi.ProjectInclusion.constants.ConstantVariables.KEY_OTHER
import com.pi.ProjectInclusion.constants.ConstantVariables.USER_TYPE_ID
import com.pi.ProjectInclusion.constants.CustomDialog
import com.pi.ProjectInclusion.data.model.AuthenticationModel.GetUserTypeResponse
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun EnterUserScreen1(
    onNextTeacher: () -> Unit,  //EnterUserProfessionalScreen
    onBack: () -> Unit,  //UserNameScreen
    onNextSpecialEdu: () -> Unit,
    onNextProfessional: () -> Unit,
    viewModel: LoginViewModel
) {
    var isDialogVisible by remember { mutableStateOf(false) }
//    val uiState by viewModel.uiStateType.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val userType = remember { mutableStateListOf<GetUserTypeResponse.UserTypeResponse>() }

    var hasAllPermissions = remember { mutableStateOf(false) }

    CameraPermission(hasAllPermissions, context)

    CustomDialog(
        isVisible = isDialogVisible,
        onDismiss = { isDialogVisible = false },
        message = stringResource(R.string.txt_loading)
    )
    BackHandler {
        onBack()
    }
    LoggerProvider.logger.d("Screen: " + "EnterUserScreen1()")

    // commenting it for now it will use after API

    /*LaunchedEffect(Unit) {
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
    }*/

    Surface(
        modifier = Modifier.fillMaxWidth(), color = White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = White),
            verticalArrangement = Arrangement.Top
        ) {
            ProfileScreenUI(
                context, onBack = onBack,
                onNextTeacher = onNextTeacher,
                onNextProfessional = onNextProfessional,
                onNextSpecialEdu = onNextSpecialEdu,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun ProfileScreenUI(
    context: Context,
    onBack: () -> Unit,
    onNextTeacher: () -> Unit,
    onNextSpecialEdu: () -> Unit,
    onNextProfessional: () -> Unit,
    viewModel: LoginViewModel,
) {
    val colors = MaterialTheme.colorScheme
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val isInternetAvailable by remember { mutableStateOf(true) }
    var isApiResponded by remember { mutableStateOf(false) }
    val internetMessage by remember { mutableStateOf("") }

    var isDialogVisible by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }

    val noDataMessage = stringResource(R.string.txt_oops_no_data_found)
    val invalidMobNo = stringResource(id = R.string.text_enter_no)
//  languageData[LanguageTranslationsResponse.KEY_INVALID_MOBILE_NO_ERROR].toString()
    val txtContinue = stringResource(id = R.string.text_continue)
    val tvMobNo = stringResource(id = R.string.text_mobile_no_user)

    var mobNo = rememberSaveable { mutableStateOf("") }
    var firstName = rememberSaveable { mutableStateOf("") }
    var lastName = rememberSaveable { mutableStateOf("") }
    var whatsappNo = rememberSaveable { mutableStateOf("") }
    var email = rememberSaveable { mutableStateOf("") }
    var textUpload = stringResource(R.string.txt_profile_pic_upload)
    val enterMobile = stringResource(R.string.txt_enter_mobile_no_)
    val textNameEg = stringResource(R.string.txt_eg_first_name)
    val textWhatsappEg = stringResource(R.string.txt_eg_whatsapp_name)
    val textLastNameEg = stringResource(R.string.txt_eg_last_name)
    val textEmailEg = stringResource(R.string.txt_eg_email_name)
    var showError by remember { mutableStateOf(false) }
    var inValidMobNo by remember { mutableStateOf(false) }
    var isAddImageClicked by remember { mutableStateOf(false) }
    var date by remember { mutableStateOf("") }
    var selectedUri = remember { mutableStateOf<Uri?>(null) }
    var hasAllPermissions = remember { mutableStateOf(false) }

    CameraPermission(hasAllPermissions, context)

    if (isAddImageClicked) {
        if (hasAllPermissions.value) {
            CameraGalleryDialog(selectedUri) {
                isAddImageClicked = false
            }
        }
        else{
            context.toast(context.getString(R.string.txt_permission_grant))
            isAddImageClicked = false
        }
    }

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
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RegistrationHeader(
                stringResource(R.string.txt_create_profile), Black,
                stringResource(R.string.txt_step_1), OrangeSubTitle, onBackButtonClick = {
                    onBack()
                })
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .background(color = Bg_Gray1)
                    .padding(15.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .size(90.dp), // adjust the size as needed,
                        painter =
                            if (selectedUri.value != null) {
                                rememberAsyncImagePainter(
                                    ImageRequest.Builder(LocalContext.current)
                                        .data(selectedUri.value)
                                        .placeholder(R.drawable.profile_user_icon)
                                        .crossfade(true) // Optional: Add a fade transition
                                        .build()
                                )
                            } else {
                                painterResource(id = R.drawable.profile_user_icon)
                            },
                        contentDescription = IMG_DESCRIPTION
                    )
                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                            .wrapContentHeight()
                            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                    ) {
                        Text(
                            text = textUpload,
                            fontSize = 12.sp,
                            fontFamily = fontRegular,
                            color = Black,
                            modifier = Modifier
                                .padding(start = 10.dp, bottom = 5.dp)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = { isAddImageClicked = true }, modifier = Modifier
                                    .wrapContentSize()
                                    .clip(RoundedCornerShape(4.dp)),
                                shape = RoundedCornerShape(4.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = White,
                                    contentColor = PrimaryBlue
                                ),
                                border = BorderStroke(1.dp, color = PrimaryBlue)
                            ) {
                                Text(
                                    if (selectedUri.value != null) {
                                        stringResource(R.string.txt_change_photo)
                                    } else {
                                        stringResource(R.string.txt_add_photo)
                                    },
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .clickable {
                                            isAddImageClicked = true
                                        }
                                        .padding(bottom = 2.dp, top = 2.dp),
                                    fontSize = 12.sp,
                                    fontFamily = fontMedium,
                                    color = PrimaryBlue,
                                    textAlign = TextAlign.Center
                                )
                            }
                            if (selectedUri.value != null) {
                                Image(
                                    modifier = Modifier
                                        .size(35.dp)
                                        .clickable {
                                            selectedUri.value = null
                                        }
                                        .padding(start = 10.dp),
                                    painter = painterResource(id = R.drawable.ic_delete_red),
                                    contentDescription = IMG_DESCRIPTION
                                )
                            }
                        }
                    }
                }
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
                    isIcon = true,
                    icon = ImageVector.vectorResource(id = R.drawable.call_on_otp),
                    colors = colors,
                    number = mobNo,
                    trueFalse = true,
                    hint = enterMobile.toString()
                )

                // first Name
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.txt_first_name))
                        pushStyle(SpanStyle(color = Color.Red))
                        append(ASTRICK)
                        pop()
                    },
                    modifier = Modifier.padding(
                        top = 24.dp,
                        bottom = 10.dp,
                        start = 8.dp, end = 8.dp
                    ),
                    textAlign = TextAlign.Start,
                    fontFamily = fontMedium,
                    fontSize = 14.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_BODY_TEXT
                    } else {
                        Bg_Gray
                    }
                )
                TextViewField(
                    isIcon = false,
                    icon = ImageVector.vectorResource(id = R.drawable.call_on_otp),
                    colors = colors,
                    text = firstName,
                    trueFalse = true,
                    hint = textNameEg.toString()
                )

                // Last Name
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.txt_last_name))
                        pushStyle(SpanStyle(color = Color.Red))
                        append(ASTRICK)
                        pop()
                    },
                    modifier = Modifier.padding(
                        top = 24.dp,
                        bottom = 10.dp,
                        start = 8.dp, end = 8.dp
                    ),
                    textAlign = TextAlign.Start,
                    fontFamily = fontMedium,
                    fontSize = 14.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_BODY_TEXT
                    } else {
                        Bg_Gray
                    }
                )
                TextViewField(
                    isIcon = false,
                    icon = ImageVector.vectorResource(id = R.drawable.call_on_otp),
                    colors = colors,
                    text = lastName,
                    trueFalse = true,
                    hint = textLastNameEg.toString()
                )

                // Date of birth
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.txt_date_of_birth))
                        pushStyle(SpanStyle(color = Color.Red))
                        append(ASTRICK)
                        pop()
                    },
                    modifier = Modifier.padding(
                        top = 24.dp,
                        bottom = 10.dp,
                        start = 8.dp, end = 8.dp
                    ),
                    textAlign = TextAlign.Start,
                    fontFamily = fontMedium,
                    fontSize = 14.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_BODY_TEXT
                    } else {
                        Bg_Gray
                    }
                )
                TextFieldWithLeftIcon(
                    modifier = Modifier.clickable {
                        showDatePickerDialog(context) { year, month, dayOfMonth ->
                            date = "$year-${
                                month.toString().padStart(2, '0')
                            }-${dayOfMonth.toString().padStart(2, '0')}"
                        }
                    },
                    value = remember { mutableStateOf(date) },
                    placeholder = if (date.isEmpty()) stringResource(R.string.select_date_of_birth) else date
                )

                // Gender
                val selectedGender = remember { mutableStateOf("") }
                val genderOptions = listOf(KEY_MALE, KEY_FEMALE, KEY_OTHER)

                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.student_gender))
                        pushStyle(SpanStyle(color = Color.Red))
                        append(ASTRICK)
                        pop()
                    },
                    modifier = Modifier.padding(
                        top = 24.dp,
                        bottom = 10.dp,
                        start = 8.dp, end = 8.dp
                    ),
                    textAlign = TextAlign.Start,
                    fontFamily = fontMedium,
                    fontSize = 14.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_BODY_TEXT
                    } else {
                        Bg_Gray
                    }
                )
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    genderOptions.forEach { gender ->
                        GenderOption(
                            gender = gender,
                            isSelected = selectedGender.value == gender,
                            onSelected = { selectedGender.value = gender }
                        )
                    }
                }

                // whatsapp
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.txt_whatsapp))
                        pushStyle(SpanStyle(color = Color.Red))
                        append(ASTRICK)
                        pop()
                    },
                    modifier = Modifier.padding(
                        top = 24.dp,
                        bottom = 10.dp,
                        start = 8.dp, end = 8.dp
                    ),
                    textAlign = TextAlign.Start,
                    fontFamily = fontMedium,
                    fontSize = 14.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_BODY_TEXT
                    } else {
                        Bg_Gray
                    }
                )
                MobileTextField(
                    isIcon = true,
                    icon = ImageVector.vectorResource(id = R.drawable.ic_whatsapp_otp),
                    colors = colors,
                    number = whatsappNo,
                    trueFalse = true,
                    hint = textWhatsappEg.toString()
                )

                // Email
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.txt_email))
                        pushStyle(SpanStyle(color = Color.Red))
                        append(ASTRICK)
                        pop()
                    },
                    modifier = Modifier.padding(
                        top = 24.dp,
                        bottom = 10.dp,
                        start = 8.dp, end = 8.dp
                    ),
                    textAlign = TextAlign.Start,
                    fontFamily = fontMedium,
                    fontSize = 14.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_BODY_TEXT
                    } else {
                        Bg_Gray
                    }
                )
                TextViewField(
                    isIcon = true,
                    icon = ImageVector.vectorResource(id = R.drawable.call_on_otp),
                    colors = colors,
                    text = email,
                    trueFalse = true,
                    hint = textEmailEg.toString()
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
            }
            Box(
                modifier = Modifier
                    .padding(20.dp)
                    .wrapContentSize(Alignment.Center)
                    .background(
                        color = if (isSystemInDarkTheme()) {
                            Dark_01
                        } else {
                            Transparent
                        }
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = White)
                        .wrapContentHeight(),
                    horizontalAlignment = Alignment.End
                ) {
                    SmallBtnUi(
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
                                        isDialogVisible = true
                                        if (viewModel.getPrefData(USER_TYPE_ID) == "7") {
                                            onNextSpecialEdu()
                                        } else if (viewModel.getPrefData(USER_TYPE_ID) == "8") {
                                            onNextProfessional()
                                        } else if (viewModel.getPrefData(USER_TYPE_ID) == "3") {
                                            // teacher
                                            onNextTeacher()
                                        } else {
                                            // reviewer
                                        }
                                    }
                                }
                            }
                        },
                    )
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun UserProfileUI() {
    val context = LocalContext.current
    val onNext: () -> Unit = {}
    val onBack: () -> Unit = {}
    val speEdu: () -> Unit = {}
    val profession: () -> Unit = {}
    val viewModel: LoginViewModel = koinViewModel()
    ProfileScreenUI(context, onNext, onBack, profession, speEdu, viewModel)
}