package com.pi.ProjectInclusion.android.screens.Profile

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.pi.ProjectInclusion.android.common_UI.DetailsNoImgBackgroundUi
import com.pi.ProjectInclusion.android.common_UI.GenderOption
import com.pi.ProjectInclusion.android.common_UI.MobileTextField
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
import com.pi.ProjectInclusion.constants.CustomDialog
import com.pi.ProjectInclusion.data.model.authenticationModel.request.FirstStepProfileRequest
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream

@Composable
fun EditProfileScreen1(onNext: () -> Unit,  //EditProfileScreen2
                       onBack: () -> Unit,
                       loginViewModel: LoginViewModel) {
    var isDialogVisible by remember { mutableStateOf(false) }
//    val uiState by viewModel.uiStateType.collectAsStateWithLifecycle()
    val context = LocalContext.current


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
    LoggerProvider.logger.d("Screen: " + "EditProfileScreen1()")

    Surface(
        modifier = Modifier.fillMaxWidth(), color = White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = White),
            verticalArrangement = Arrangement.Top
        ) {
            EditProfileScreenUI(context, onBack = onBack, loginViewModel = loginViewModel, onNext = onNext)
        }
    }
}

@Composable
fun EditProfileScreenUI(
    context: Context,
    onBack: () -> Unit,
    loginViewModel: LoginViewModel,
    onNext: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val scrollState = rememberScrollState()
    val isInternetAvailable by remember { mutableStateOf(true) }

    var isDialogVisible by remember { mutableStateOf(false) }
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
    var date by remember { mutableStateOf("") }
    var strToken by remember { mutableStateOf("") }
    var isAddImageClicked by remember { mutableStateOf(false) }

    var selectedUri = remember { mutableStateOf<Uri?>(null) }
    var hasAllPermissions = remember { mutableStateOf(false) }

    // Gender
    val selectedGender = remember { mutableStateOf("") }
    val genderOptions = listOf(KEY_MALE, KEY_FEMALE, KEY_OTHER)
    var byteArray: ByteArray? = null
    var fileName: String? = null
    lateinit var bitmap: Bitmap

    CameraPermission(hasAllPermissions, context)

    if (isAddImageClicked) {
            CameraGalleryDialog(selectedUri) {
                isAddImageClicked = false
            }
    }

    if (selectedUri.value != null){
        try {

            val imagePath = selectedUri.value!!.path
            bitmap = BitmapFactory.decodeFile(imagePath)
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream)
            byteArray = stream.toByteArray()
            //  mBinding.profileImg.setImageBitmap(bitmap)
            fileName =
                imagePath?.substring(imagePath.toString().lastIndexOf("/") + 1)

        } catch (e: Exception) {
            LoggerProvider.logger.d(
                "FileNotConvertedException " + "1.. " +e.message
            )
        }
    }

    DetailsNoImgBackgroundUi(
        backgroundColor = White,
        textColor = Black,
        pageTitle = stringResource(R.string.edit_profile),
//        moreInfoIcon = painterResource(id = R.drawable.vertical_dot),
        isShowBackButton = true,
        isShowMoreInfo = false,
        onBackButtonClick = {
           onBack()
        },
        onMoreInfoClick = {
//            showSheetMenu = true
        },
        content = {
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
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .verticalScroll(scrollState)
                            .background(color = Bg_Gray1)
                            .padding(15.dp)
                    ) {
                        Text(
                            stringResource(R.string.txt_step_1),
                            textAlign = TextAlign.Center,
                            fontSize = 13.sp,
                            fontFamily = fontMedium,
                            color = OrangeSubTitle,
                            modifier = Modifier
                                .padding(start = 10.dp)
                        )
                        Text(
                            stringResource(R.string.txt_basic_information),
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                            fontFamily = fontMedium,
                            color = Black,
                            modifier = Modifier
                                .padding(start = 10.dp)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(top = 15.dp, bottom = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(90.dp), // adjust the size as needed,
                                painter = painterResource(id = R.drawable.profile_user_icon),
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
                            text = if (date.isEmpty()){
                                date
                            }
                            else{""},
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
                                    }
                                    else if (firstName.value.toString().isEmpty()) {
                                        context.toast("Enter first name")
                                    } else if (lastName.value.toString().isEmpty()) {
                                        context.toast("Enter last name")
                                    } else if (date.toString().isEmpty()) {
                                        context.toast("Enter your date of birth")
                                    } else if (selectedGender.value.toString().isEmpty()) {
                                        context.toast("Select your gender")
                                    } else if (whatsappNo.value.toString().isEmpty()) {
                                        context.toast("Enter your whatsApp number")
                                    } else if (email.value.toString().isEmpty()) {
                                        context.toast("Enter your email")
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
                                                val firstStepProfileRequest =
                                                    FirstStepProfileRequest(
                                                        firstName.value.toString(),
                                                        "",
                                                        lastName.value.toString(),
                                                        selectedGender.value.toString(),
                                                        mobNo.value.toString(),
                                                        whatsappNo.value.toString(),
                                                        date.toString(),
                                                        email.value.toString()
                                                    )

//                                               onNext()
                                                    LoggerProvider.logger.d(
                                                        "EditProfile: " + "1.. " + firstName.value + " .. "
                                                                + lastName.value + " .. " + date + " .. " + selectedGender.value + " .. "
                                                    )


                                                    loginViewModel.createFirstStepProfileRepo(
                                                        firstStepProfileRequest,
                                                        strToken,
                                                        byteArray,
                                                        fileName
                                                    )
                                                }
                                            }
                                    }
                                },
                            )
                        }
                    }
                }
            }
        })
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun EditProfileUI() {
    val context = LocalContext.current
    val onNext: () -> Unit = {}
    val onBack: () -> Unit = {}
//    EditProfileScreenUI(context, onNext, onBack)
}