package com.pi.ProjectInclusion.android.screens.addStudentScreen

import android.Manifest
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.Bg_Gray
import com.pi.ProjectInclusion.Bg_Gray2
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.DARK_BODY_TEXT
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.Dark_03
import com.pi.ProjectInclusion.GrayLight02
import com.pi.ProjectInclusion.OrangeSubTitle
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.Transparent
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.BackButtonPress
import com.pi.ProjectInclusion.android.common_UI.DetailsNoImgBackgroundUi
import com.pi.ProjectInclusion.android.common_UI.DropdownMenuUi
import com.pi.ProjectInclusion.android.common_UI.GenderOption
import com.pi.ProjectInclusion.android.common_UI.SchoolListBottomSheet
import com.pi.ProjectInclusion.android.common_UI.SmallBtnUi
import com.pi.ProjectInclusion.android.common_UI.TextFieldWithRightIcon
import com.pi.ProjectInclusion.android.common_UI.TextViewField
import com.pi.ProjectInclusion.android.common_UI.showDatePickerDialog
import com.pi.ProjectInclusion.android.navigation.AppRoute
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.fontRegular
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.ConstantVariables.ASTRICK
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION
import com.pi.ProjectInclusion.constants.ConstantVariables.KEY_FEMALE
import com.pi.ProjectInclusion.constants.ConstantVariables.KEY_MALE
import com.pi.ProjectInclusion.constants.ConstantVariables.KEY_OTHER
import com.pi.ProjectInclusion.constants.CustomDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewStudentDetailsScreen(navHostController: NavHostController) {

    var isDialogVisible by remember { mutableStateOf(false) }
    val colors = MaterialTheme.colorScheme
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    var studentsName = rememberSaveable { mutableStateOf("") }
    var fathersName = rememberSaveable { mutableStateOf("") }
    val textNameEg = stringResource(R.string.txt_Student_Name)
    val textFatherNameEg = stringResource(R.string.f_name)
    val classLoggerMsg = stringResource(R.string.txt_class_name)
    val schoolLoggerMsg = stringResource(R.string.txt_school_name)
    val selectedGender = remember { mutableStateOf("") }
    val genderOptions = listOf(KEY_MALE, KEY_FEMALE, KEY_OTHER)
    var date by remember { mutableStateOf("") }
    var selectedSchool = stringResource(R.string.choose_option)
    var selectedClass = stringResource(R.string.choose_option)
    var schoolName by remember { mutableStateOf("") }
    var className by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    var isBottomSheetSchoolVisible by rememberSaveable { mutableStateOf(false) }
    var isBottomSheetClassVisible by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { it != SheetValue.Hidden })
    val schoolList = listOf(
        "Kendriya Vidhyalya 1, Delhi",
        "Kendriya Vidhyalya 2, Noida",
        "Kendriya Vidhyalya 3, Haryana",
        "Kendriya Vidhyalya 4, Lucknow"
    )
    val classList = listOf(
        "Grade 1",
        "Grade 2",
        "Grade 3",
        "Grade 4"
    )

    val nameMsg = stringResource(R.string.txt_enter_your_name_msg)
    val genderMsg = stringResource(R.string.txt_select_your_gender_msg)
    val dobMsg = stringResource(R.string.txt_select_your_dob_msg)
    val fNameMsg = stringResource(R.string.txt_enter_your_F_name_msg)
    val schoolMsg = stringResource(R.string.txt_select_your_school_msg)
    val classMsg = stringResource(R.string.txt_select_your_class_msg)

    CustomDialog(
        isVisible = isDialogVisible,
        onDismiss = { isDialogVisible = false },
        message = "Loading your data..."
    )

    var hasCameraPermission by remember { mutableStateOf(false) }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        hasCameraPermission = isGranted
    }
    if (hasCameraPermission) {

    } else {
        try {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        } catch (exc: Exception) {
            // Handle exception
            println("Handle exception :- ${exc.message}")
        }
    }

    DetailsNoImgBackgroundUi(
        backgroundColor = White,
        textColor = Black,
        pageTitle = stringResource(R.string.txt_Add_student_screening),
        moreInfoIcon = painterResource(id = R.drawable.vertical_dot),
        isShowBackButton = true,
        isShowMoreInfo = false,
        onBackButtonClick = {
            BackButtonPress(navHostController, AppRoute.ScreeningScreen.route)
        },
        onMoreInfoClick = {},
        content = {
            Divider(
                color = GrayLight02,
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 0.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = if (isSystemInDarkTheme()) {
                            Dark_01
                        } else {
                            White
                        }
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.txt_Students_Details),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start,
                        fontFamily = fontMedium,
                        color = OrangeSubTitle,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp),
                            colors = if (isSystemInDarkTheme()) {
                                CardDefaults.cardColors(Dark_03)
                            } else {
                                CardDefaults.cardColors(
                                    containerColor = Bg_Gray2,
                                    contentColor = Bg_Gray2,
                                    disabledContentColor = Bg_Gray2,
                                    disabledContainerColor = Bg_Gray2
                                )
                            }
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(100.dp)
                                    .background(Color.Unspecified)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.user_img),
                                    contentDescription = IMG_DESCRIPTION,
                                    modifier = Modifier
                                        .width(52.dp)
                                        .height(52.dp)
                                        .align(Alignment.Center)
                                        .background(Color.Unspecified),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }

                        Column(
                            modifier = Modifier
                                .wrapContentSize()
                                .wrapContentHeight()
                                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp),
                        ) {
                            Text(
                                text = stringResource(R.string.txt_Upload_profile_photo),
                                fontSize = 12.sp,
                                fontFamily = fontRegular,
                                color = Black,
                                modifier = Modifier
                                    .padding(start = 8.dp, bottom = 8.dp)
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp, bottom = 8.dp)
                                    .wrapContentHeight(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(
                                    onClick = { }, modifier = Modifier
                                        .wrapContentSize()
                                        .clip(RoundedCornerShape(8.dp)),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = White,
                                        contentColor = PrimaryBlue
                                    ),
                                    border = BorderStroke(1.dp, color = PrimaryBlue)
                                ) {
                                    Text(
                                        stringResource(R.string.txt_add_photo),
                                        modifier = Modifier
                                            .wrapContentSize()
                                            .padding(bottom = 2.dp, top = 2.dp),
                                        fontSize = 14.sp,
                                        fontFamily = fontMedium,
                                        color = PrimaryBlue,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(R.string.txt_Student_Name))
                            pushStyle(SpanStyle(color = Color.Red))
                            append(ASTRICK)
                            pop()
                        },
                        modifier = Modifier
                            .padding(
                                top = 16.dp,
                                bottom = 8.dp, end = 8.dp
                            )
                            .fillMaxWidth(),
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
                        text = studentsName,
                        trueFalse = true,
                        hint = textNameEg.toString()
                    )

                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(R.string.student_gender))
                            pushStyle(SpanStyle(color = Color.Red))
                            append(ASTRICK)
                            pop()
                        },
                        modifier = Modifier
                            .padding(
                                top = 16.dp,
                                bottom = 8.dp, end = 8.dp
                            )
                            .fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        fontFamily = fontMedium,
                        fontSize = 14.sp,
                        color = if (isSystemInDarkTheme()) {
                            DARK_BODY_TEXT
                        } else {
                            Bg_Gray
                        }
                    )

                    Row(horizontalArrangement = Arrangement.Start) {
                        genderOptions.forEach { gender ->
                            GenderOption(
                                gender = gender,
                                isSelected = selectedGender.value == gender,
                                onSelected = {
                                    selectedGender.value = gender
                                }
                            )
                        }
                    }

                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(R.string.txt_date_of_birth))
                            pushStyle(SpanStyle(color = Color.Red))
                            append(ASTRICK)
                            pop()
                        },
                        modifier = Modifier
                            .padding(
                                top = 16.dp,
                                bottom = 8.dp, end = 8.dp
                            )
                            .fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        fontFamily = fontMedium,
                        fontSize = 14.sp,
                        color = if (isSystemInDarkTheme()) {
                            DARK_BODY_TEXT
                        } else {
                            Bg_Gray
                        }
                    )

                    TextFieldWithRightIcon(
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
                            append(stringResource(R.string.f_name))
                            pushStyle(SpanStyle(color = Color.Red))
                            append(ASTRICK)
                            pop()
                        },
                        modifier = Modifier
                            .padding(
                                top = 16.dp,
                                bottom = 8.dp, end = 8.dp
                            )
                            .fillMaxWidth(),
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
                        text = fathersName,
                        trueFalse = true,
                        hint = textFatherNameEg.toString()
                    )

                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(R.string.txt_School))
                            pushStyle(SpanStyle(color = Color.Red))
                            append(ASTRICK)
                            pop()
                        },
                        modifier = Modifier
                            .padding(
                                top = 16.dp,
                                bottom = 8.dp, end = 8.dp
                            )
                            .fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        fontFamily = fontMedium,
                        fontSize = 14.sp,
                        color = if (isSystemInDarkTheme()) {
                            DARK_BODY_TEXT
                        } else {
                            Bg_Gray
                        }
                    )

                    DropdownMenuUi(listOf(), onItemSelected = {}, modifier = Modifier.clickable {
                        logger.e(schoolLoggerMsg)
                    }, placeholder = selectedSchool, onClick = {
                        scope.launch {
                            isBottomSheetSchoolVisible = true // true under development code
                            sheetState.expand()
                        }
                    })

                    SchoolListBottomSheet(
                        isBottomSheetVisible = isBottomSheetSchoolVisible,
                        sheetState = sheetState,
                        onDismiss = {
                            scope.launch { sheetState.hide() }
                                .invokeOnCompletion { isBottomSheetSchoolVisible = false }
                        },
                        onDecline = {},
                        onTextSelected = { it ->
                            selectedSchool = it
                            schoolName = selectedSchool
                        },
                        schoolList.toList()
                    )

                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(R.string.student_class))
                            pushStyle(SpanStyle(color = Color.Red))
                            append(ASTRICK)
                            pop()
                        },
                        modifier = Modifier
                            .padding(
                                top = 16.dp,
                                bottom = 8.dp, end = 8.dp
                            )
                            .fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        fontFamily = fontMedium,
                        fontSize = 14.sp,
                        color = if (isSystemInDarkTheme()) {
                            DARK_BODY_TEXT
                        } else {
                            Bg_Gray
                        }
                    )

                    DropdownMenuUi(listOf(), onItemSelected = {}, modifier = Modifier.clickable {
                        logger.e(classLoggerMsg)
                    }, placeholder = selectedClass, onClick = {
                        scope.launch {
                            isBottomSheetClassVisible = true // true under development code
                            sheetState.expand()
                        }
                    })

                    SchoolListBottomSheet(
                        isBottomSheetVisible = isBottomSheetClassVisible,
                        sheetState = sheetState,
                        onDismiss = {
                            scope.launch { sheetState.hide() }
                                .invokeOnCompletion { isBottomSheetClassVisible = false }
                        },
                        onDecline = {

                        },
                        onTextSelected = { it ->
                            selectedClass = it
                            className = selectedClass
                        },
                        classList.toList()
                    )

                    Box(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
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
                                enabled = true,
                                title = stringResource(R.string.string_next),
                                onClick = {
                                    if (studentsName.value.toString().isEmpty()) {
                                        context.toast(nameMsg)
                                    } else if (selectedGender.value.toString().isEmpty()) {
                                        context.toast(genderMsg)
                                    } else if (date.isEmpty()) {
                                        context.toast(dobMsg)
                                    } else if (fathersName.value.toString().isEmpty()) {
                                        context.toast(fNameMsg)
                                    } else if (schoolName.toString().isEmpty()) {
                                        context.toast(schoolMsg)
                                    } else if (className.toString().isEmpty()) {
                                        context.toast(classMsg)
                                    } else {
                                        navHostController.popBackStack()
                                        navHostController.navigate(AppRoute.AddNewStudentMoreDetails.route)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}