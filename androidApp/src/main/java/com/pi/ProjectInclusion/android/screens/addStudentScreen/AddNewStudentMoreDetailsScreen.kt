package com.pi.ProjectInclusion.android.screens.addStudentScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.Bg_Gray
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.DARK_BODY_TEXT
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.GrayLight02
import com.pi.ProjectInclusion.OrangeSubTitle
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.Transparent
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.CustomToastMessage
import com.pi.ProjectInclusion.android.common_UI.DetailsNoImgBackgroundUi
import com.pi.ProjectInclusion.android.common_UI.DropdownMenuUi
import com.pi.ProjectInclusion.android.common_UI.MobileTextField
import com.pi.ProjectInclusion.android.common_UI.ResidenceOption
import com.pi.ProjectInclusion.android.common_UI.SchoolListBottomSheet
import com.pi.ProjectInclusion.android.common_UI.SmallBtnUi
import com.pi.ProjectInclusion.android.common_UI.TextViewField
import com.pi.ProjectInclusion.android.common_UI.TextWithIconOnLeft
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.ConstantVariables.ASTRICK
import com.pi.ProjectInclusion.constants.ConstantVariables.RURAL
import com.pi.ProjectInclusion.constants.ConstantVariables.URBAN
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewStudentMoreDetailsScreen(onNext: () -> Unit, onBack: () -> Unit) {

    logger.d("Screen: " + "AddNewStudentMoreDetailsScreen()")

    val colors = MaterialTheme.colorScheme
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    var isBottomSheetFVisible by rememberSaveable { mutableStateOf(false) }
    var isBottomSheetMVisible by rememberSaveable { mutableStateOf(false) }
    var isBottomSheetEdVisible by rememberSaveable { mutableStateOf(false) }
    var isBottomSheetBoardVisible by rememberSaveable { mutableStateOf(false) }
    var isBottomSheetSchoolTypeVisible by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true, confirmValueChange = { it != SheetValue.Hidden })
    var mothersName = rememberSaveable { mutableStateOf("") }
    val textNameEg = stringResource(R.string.m_name)
    var mobNo = rememberSaveable { mutableStateOf("") }
    val enterMobile = stringResource(R.string.enter_here)
    val selectedResidence = remember { mutableStateOf("") }
    val residenceOptions = listOf(URBAN, RURAL)
    var selectedFProfession = stringResource(R.string.choose_option)
    var selectedMProfession = stringResource(R.string.choose_option)
    var loggerProfessionMsg = stringResource(R.string.f_occupation)
    var loggerMProfessionMsg = stringResource(R.string.m_occupation)
    var professionFName by remember { mutableStateOf("") }
    var professionMName by remember { mutableStateOf("") }
    var selectedEducation = stringResource(R.string.choose_option)
    var loggerEducationStsMsg = stringResource(R.string.education_status)
    var educationStsMName by remember { mutableStateOf("") }
    var selectedBoard = stringResource(R.string.choose_option)
    var loggerBoardMsg = stringResource(R.string.student_board)
    var boardName by remember { mutableStateOf("") }
    var selectedSchoolType = stringResource(R.string.choose_option)
    var loggerTypeMsg = stringResource(R.string.type_school)
    var schoolTypeName by remember { mutableStateOf("") }
    val professionList = listOf(
        stringResource(R.string.txt_Government),
        stringResource(R.string.txt_Professional),
        stringResource(R.string.txt_Private),
        stringResource(R.string.txt_Business)
    )
    val educationList = listOf(
        stringResource(R.string.txt_Continuous_Education),
        stringResource(R.string.txt_Pending_Education),
        stringResource(R.string.txt_Drop_Education),
    )
    val boardList = listOf(
        stringResource(R.string.txt_CBSE),
        stringResource(R.string.txt_ICSE),
        stringResource(R.string.txt_UP_Board),
    )
    val schoolTypeList = listOf(
        stringResource(R.string.txt_Private),
        stringResource(R.string.txt_Government),
        stringResource(R.string.txt_State_Governments),
    )

    var fatherProfessionMsg = stringResource(R.string.txt_select_your_father_profession_msg)
    var motherNameMsg = stringResource(R.string.txt_enter_your_mother_name_msg)
    var motherProfessionMsg = stringResource(R.string.txt_select_your_mother_profession_msg)
    var parentMobileNoMsg = stringResource(R.string.txt_enter_your_parent_number_msg)
    var residenceMsg = stringResource(R.string.txt_select_your_residence_msg)
    var educationStatusMsg = stringResource(R.string.txt_select_your_education_status_msg)
    var boardMsg = stringResource(R.string.txt_select_your_board_msg)
    var schoolTypeMsg = stringResource(R.string.txt_select_your_school_type_msg)
    var showToast by remember { mutableStateOf(false) }

    DetailsNoImgBackgroundUi(
        backgroundColor = White,
        textColor = Black,
        pageTitle = stringResource(R.string.txt_Add_student_screening),
        moreInfoIcon = painterResource(id = R.drawable.vertical_dot),
        isShowBackButton = true,
        isShowMoreInfo = false,
        onBackButtonClick = {
            onBack()
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
                        text = stringResource(R.string.txt_More_About_Student),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start,
                        fontFamily = fontMedium,
                        color = OrangeSubTitle,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )

                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(R.string.f_occupation))
                            pushStyle(SpanStyle(color = Color.Red))
                            append(ASTRICK)
                            pop()
                        },
                        modifier = Modifier
                            .padding(
                                top = 16.dp, bottom = 8.dp, end = 8.dp
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
                        logger.e(loggerProfessionMsg)
                    }, placeholder = selectedFProfession, onClick = {
                        scope.launch {
                            isBottomSheetFVisible = true // true under development code
                            sheetState.expand()
                        }
                    })

                    SchoolListBottomSheet(
                        isBottomSheetVisible = isBottomSheetFVisible,
                        sheetState = sheetState,
                        onDismiss = {
                            scope.launch { sheetState.hide() }
                                .invokeOnCompletion { isBottomSheetFVisible = false }
                        },
                        onDecline = {},
                        onTextSelected = { it ->
                            selectedFProfession = it
                            professionFName = selectedFProfession
                        },
                        professionList.toList()
                    )

                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(R.string.txt_Student_Name))
                            pushStyle(SpanStyle(color = Color.Red))
                            append(ASTRICK)
                            pop()
                        },
                        modifier = Modifier
                            .padding(
                                top = 16.dp, bottom = 8.dp, end = 8.dp
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
                        text = mothersName,
                        trueFalse = true,
                        hint = textNameEg.toString()
                    )

                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(R.string.m_occupation))
                            pushStyle(SpanStyle(color = Color.Red))
                            append(ASTRICK)
                            pop()
                        },
                        modifier = Modifier
                            .padding(
                                top = 16.dp, bottom = 8.dp, end = 8.dp
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
                        logger.e(loggerMProfessionMsg)
                    }, placeholder = selectedMProfession, onClick = {
                        scope.launch {
                            isBottomSheetMVisible = true // true under development code
                            sheetState.expand()
                        }
                    })

                    SchoolListBottomSheet(
                        isBottomSheetVisible = isBottomSheetMVisible,
                        sheetState = sheetState,
                        onDismiss = {
                            scope.launch { sheetState.hide() }
                                .invokeOnCompletion { isBottomSheetMVisible = false }
                        },
                        onDecline = {},
                        onTextSelected = { it ->
                            selectedMProfession = it
                            professionMName = selectedMProfession
                        },
                        professionList.toList()
                    )

                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(R.string.parent_mobile))
                            pushStyle(SpanStyle(color = Color.Red))
                            append(ASTRICK)
                            pop()
                        },
                        modifier = Modifier
                            .padding(
                                top = 16.dp, bottom = 8.dp, end = 8.dp
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

                    MobileTextField(
                        isIcon = false,
                        icon = null,
                        colors = colors,
                        number = mobNo,
                        trueFalse = true,
                        hint = enterMobile.toString()
                    )

                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(R.string.select_Residence))
                            pushStyle(SpanStyle(color = Color.Red))
                            append(ASTRICK)
                            pop()
                        },
                        modifier = Modifier
                            .padding(
                                top = 16.dp, bottom = 8.dp, end = 8.dp
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

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Start),
                    ) {
                        Row {
                            residenceOptions.forEach { residence ->
                                ResidenceOption(
                                    residence = residence,
                                    isSelected = selectedResidence.value == residence,
                                    onSelected = { selectedResidence.value = residence })
                            }
                        }
                    }

                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(R.string.education_status))
                            pushStyle(SpanStyle(color = Color.Red))
                            append(ASTRICK)
                            pop()
                        },
                        modifier = Modifier
                            .padding(
                                top = 16.dp, bottom = 8.dp, end = 8.dp
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
                        logger.e(loggerEducationStsMsg)
                    }, placeholder = selectedEducation, onClick = {
                        scope.launch {
                            isBottomSheetEdVisible = true // true under development code
                            sheetState.expand()
                        }
                    })

                    SchoolListBottomSheet(
                        isBottomSheetVisible = isBottomSheetEdVisible,
                        sheetState = sheetState,
                        onDismiss = {
                            scope.launch { sheetState.hide() }
                                .invokeOnCompletion { isBottomSheetEdVisible = false }
                        },
                        onDecline = {},
                        onTextSelected = { it ->
                            selectedEducation = it
                            educationStsMName = selectedEducation
                        },
                        educationList.toList()
                    )

                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(R.string.student_board))
                            pushStyle(SpanStyle(color = Color.Red))
                            append(ASTRICK)
                            pop()
                        },
                        modifier = Modifier
                            .padding(
                                top = 16.dp, bottom = 8.dp, end = 8.dp
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
                        logger.e(loggerBoardMsg)
                    }, placeholder = selectedBoard, onClick = {
                        scope.launch {
                            isBottomSheetBoardVisible = true // true under development code
                            sheetState.expand()
                        }
                    })

                    SchoolListBottomSheet(
                        isBottomSheetVisible = isBottomSheetBoardVisible,
                        sheetState = sheetState,
                        onDismiss = {
                            scope.launch { sheetState.hide() }
                                .invokeOnCompletion { isBottomSheetBoardVisible = false }
                        },
                        onDecline = {

                        },
                        onTextSelected = { it ->
                            selectedBoard = it
                            boardName = selectedBoard
                        },
                        boardList.toList()
                    )

                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(R.string.type_school))
                            pushStyle(SpanStyle(color = Color.Red))
                            append(ASTRICK)
                            pop()
                        },
                        modifier = Modifier
                            .padding(
                                top = 16.dp, bottom = 8.dp, end = 8.dp
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
                        logger.e(loggerTypeMsg)
                    }, placeholder = selectedSchoolType, onClick = {
                        scope.launch {
                            isBottomSheetSchoolTypeVisible = true // true under development code
                            sheetState.expand()
                        }
                    })

                    SchoolListBottomSheet(
                        isBottomSheetVisible = isBottomSheetSchoolTypeVisible,
                        sheetState = sheetState,
                        onDismiss = {
                            scope.launch { sheetState.hide() }
                                .invokeOnCompletion { isBottomSheetSchoolTypeVisible = false }
                        },
                        onDecline = {

                        },
                        onTextSelected = { it ->
                            selectedSchoolType = it
                            schoolTypeName = selectedSchoolType
                        },
                        schoolTypeList.toList()
                    )

                    Box(
                        modifier = Modifier
                            .padding(
                                start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp
                            )
                            .wrapContentSize(Alignment.Center)
                            .background(
                                color = if (isSystemInDarkTheme()) {
                                    Dark_01
                                } else {
                                    Transparent
                                }
                            )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = White)
                                .wrapContentHeight(), verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextWithIconOnLeft(
                                text = stringResource(R.string.txt_Previous),
                                icon = ImageVector.vectorResource(id = R.drawable.left_back_arrow),
                                textColor = PrimaryBlue,
                                iconColor = PrimaryBlue,
                                onClick = {
                                    onBack()
                                },
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            SmallBtnUi(
                                enabled = true,
                                title = stringResource(R.string.add_student),
                                onClick = {
                                    if (professionFName.toString().isEmpty()) {
                                        context.toast(fatherProfessionMsg)
                                    } else if (mothersName.value.toString().isEmpty()) {
                                        context.toast(motherNameMsg)
                                    } else if (professionMName.toString().isEmpty()) {
                                        context.toast(motherProfessionMsg)
                                    } else if (mobNo.value.toString().isEmpty()) {
                                        context.toast(parentMobileNoMsg)
                                    } else if (selectedResidence.value.toString().isEmpty()) {
                                        context.toast(residenceMsg)
                                    } else if (educationStsMName.toString().isEmpty()) {
                                        context.toast(educationStatusMsg)
                                    } else if (boardName.toString().isEmpty()) {
                                        context.toast(boardMsg)
                                    } else if (schoolTypeName.toString().isEmpty()) {
                                        context.toast(schoolTypeMsg)
                                    } else {
                                        showToast = true
                                        onNext()
                                    }
                                })
                        }
                    }
                }
            }
        })

    if (showToast) {
        CustomToastMessage(
            titleStr = stringResource(R.string.txt_Student_added),
            messageStr = stringResource(R.string.txt_Begin_Screening_process),
            visible = showToast,
            onClose = { showToast = false })
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AddNewStudentMoreDetailsScreenPreview() {
    val onNext: () -> Unit = {}
    val onBack: () -> Unit = {}
    AddNewStudentMoreDetailsScreen(onNext, onBack)
}