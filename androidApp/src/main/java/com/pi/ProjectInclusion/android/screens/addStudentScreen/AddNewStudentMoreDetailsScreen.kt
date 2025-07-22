package com.pi.ProjectInclusion.android.screens.addStudentScreen

import android.util.Log
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.pi.ProjectInclusion.Bg_Gray
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.DARK_BODY_TEXT
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.GrayLight02
import com.pi.ProjectInclusion.OrangeSubTitle
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.Transparent
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.BackButtonPress
import com.pi.ProjectInclusion.android.common_UI.DetailsNoImgBackgroundUi
import com.pi.ProjectInclusion.android.common_UI.DropdownMenuUi
import com.pi.ProjectInclusion.android.common_UI.GenderOption
import com.pi.ProjectInclusion.android.common_UI.MobileTextField
import com.pi.ProjectInclusion.android.common_UI.ResidenceOption
import com.pi.ProjectInclusion.android.common_UI.SchoolListBottomSheet
import com.pi.ProjectInclusion.android.common_UI.SmallBtnUi
import com.pi.ProjectInclusion.android.common_UI.TextViewField
import com.pi.ProjectInclusion.android.common_UI.TextWithIconOnLeft
import com.pi.ProjectInclusion.android.navigation.AppRoute
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.fontRegular
import com.pi.ProjectInclusion.android.utils.fontSemiBold
import com.pi.ProjectInclusion.constants.ConstantVariables.ASTRICK
import com.pi.ProjectInclusion.constants.ConstantVariables.KEY_FEMALE
import com.pi.ProjectInclusion.constants.ConstantVariables.KEY_MALE
import com.pi.ProjectInclusion.constants.ConstantVariables.KEY_OTHER
import com.pi.ProjectInclusion.constants.ConstantVariables.RURAL
import com.pi.ProjectInclusion.constants.ConstantVariables.URBAN
import com.pi.ProjectInclusion.data.model.GetLanguageListResponse
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewStudentMoreDetailsScreen(navHostController: NavHostController) {

    val colors = MaterialTheme.colorScheme
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    var isBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { it != SheetValue.Hidden })
    val professionList = remember { mutableStateListOf<GetLanguageListResponse.Data.Result>() }
    val educationList = remember { mutableStateListOf<GetLanguageListResponse.Data.Result>() }
    val boardList = remember { mutableStateListOf<GetLanguageListResponse.Data.Result>() }
    var mothersName = rememberSaveable { mutableStateOf("") }
    val textNameEg = stringResource(R.string.m_name)
    var mobNo = rememberSaveable { mutableStateOf("") }
    val enterMobile = stringResource(R.string.enter_here)
    val selectedResidence = remember { mutableStateOf("") }
    val residenceOptions = listOf(URBAN, RURAL)
    var selectedProfession = stringResource(R.string.choose_option)
    var selectedEducation = stringResource(R.string.choose_option)
    var selectedBoard = stringResource(R.string.choose_option)
    var selectedSchoolType = stringResource(R.string.choose_option)

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
                        Log.e("TAG", "Father's Profession ")
                    }, placeholder = selectedProfession, onClick = {
//                            schoolListOpen = true
                        scope.launch {
                            isBottomSheetVisible = true // true under development code
                            sheetState.expand()
                        }
                    })

                    SchoolListBottomSheet(
                        isBottomSheetVisible = isBottomSheetVisible,
                        sheetState = sheetState,
                        onDismiss = {
                            scope.launch { sheetState.hide() }
                                .invokeOnCompletion { isBottomSheetVisible = false }
                        },
                        onDecline = {

                        },
                        onTextSelected = { it ->
                            /* selectedProfession = it
                     schoolList.find { it.name == selectedProfession }?.id?.let {
                         schoolSelectedId.value = it
                     }*/
                            "Profession"
                        },
                        professionList.map { it.name }.toList()
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
                        Log.e("TAG", "Mother's Profession ")
                    }, placeholder = selectedProfession, onClick = {
//                            schoolListOpen = true
                        scope.launch {
                            isBottomSheetVisible = true // true under development code
                            sheetState.expand()
                        }
                    })

                    SchoolListBottomSheet(
                        isBottomSheetVisible = isBottomSheetVisible,
                        sheetState = sheetState,
                        onDismiss = {
                            scope.launch { sheetState.hide() }
                                .invokeOnCompletion { isBottomSheetVisible = false }
                        },
                        onDecline = {

                        },
                        onTextSelected = { it ->
                            /* selectedProfession = it
                     schoolList.find { it.name == selectedProfession }?.id?.let {
                         schoolSelectedId.value = it
                     }*/
                            "Profession"
                        },
                        professionList.map { it.name }.toList()
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
                                    onSelected = { selectedResidence.value = residence }
                                )
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
                        Log.e("TAG", "Education Status")
                    }, placeholder = selectedEducation, onClick = {
//                            schoolListOpen = true
                        scope.launch {
                            isBottomSheetVisible = true // true under development code
                            sheetState.expand()
                        }
                    })

                    SchoolListBottomSheet(
                        isBottomSheetVisible = isBottomSheetVisible,
                        sheetState = sheetState,
                        onDismiss = {
                            scope.launch { sheetState.hide() }
                                .invokeOnCompletion { isBottomSheetVisible = false }
                        },
                        onDecline = {

                        },
                        onTextSelected = { it ->
                            /* selectedEducation = it
                            schoolList.find { it.name == selectedEducation }?.id?.let {
                                schoolSelectedId.value = it
                            } */
                            "Education Status"
                        },
                        educationList.map { it.name }.toList()
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
                        Log.e("TAG", "Board")
                    }, placeholder = selectedBoard, onClick = {
//                            schoolListOpen = true
                        scope.launch {
                            isBottomSheetVisible = true // true under development code
                            sheetState.expand()
                        }
                    })

                    SchoolListBottomSheet(
                        isBottomSheetVisible = isBottomSheetVisible,
                        sheetState = sheetState,
                        onDismiss = {
                            scope.launch { sheetState.hide() }
                                .invokeOnCompletion { isBottomSheetVisible = false }
                        },
                        onDecline = {

                        },
                        onTextSelected = { it ->
                            /* selectedBoard = it
                            schoolList.find { it.name == selectedBoard }?.id?.let {
                                schoolSelectedId.value = it
                            } */
                            "Board"
                        },
                        boardList.map { it.name }.toList()
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
                        Log.e("TAG", "Type of School")
                    }, placeholder = selectedSchoolType, onClick = {
//                            schoolListOpen = true
                        scope.launch {
                            isBottomSheetVisible = true // true under development code
                            sheetState.expand()
                        }
                    })

                    SchoolListBottomSheet(
                        isBottomSheetVisible = isBottomSheetVisible,
                        sheetState = sheetState,
                        onDismiss = {
                            scope.launch { sheetState.hide() }
                                .invokeOnCompletion { isBottomSheetVisible = false }
                        },
                        onDecline = {

                        },
                        onTextSelected = { it ->
                            /* selectedSchoolType = it
                            schoolList.find { it.name == selectedSchoolType }?.id?.let {
                                schoolSelectedId.value = it
                            } */
                            "Type of School"
                        },
                        boardList.map { it.name }.toList()
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
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = White)
                                .wrapContentHeight(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextWithIconOnLeft(
                                text = stringResource(R.string.txt_Previous),
                                icon = ImageVector.vectorResource(id = R.drawable.left_back_arrow),
                                textColor = PrimaryBlue,
                                iconColor = PrimaryBlue,
                                onClick = {
                                    navHostController.popBackStack()
                                    navHostController.navigate(AppRoute.AddStudentRegister.route)
                                },
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            SmallBtnUi(
                                enabled = true,
                                title = stringResource(R.string.add_student),
                                onClick = {
                                    navHostController.popBackStack()
                                    navHostController.navigate(AppRoute.ScreeningScreen.route)
                                }
                            )
                        }
                    }
                }
            }
        })
}