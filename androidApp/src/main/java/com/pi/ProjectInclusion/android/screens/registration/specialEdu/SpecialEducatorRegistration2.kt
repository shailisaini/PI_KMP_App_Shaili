package com.pi.ProjectInclusion.android.screens.registration.specialEdu

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
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
import com.pi.ProjectInclusion.Transparent
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.DropdownMenuUi
import com.pi.ProjectInclusion.android.common_UI.MobileTextField
import com.pi.ProjectInclusion.android.common_UI.RegistrationHeader
import com.pi.ProjectInclusion.android.common_UI.SchoolListBottomSheet
import com.pi.ProjectInclusion.android.common_UI.SmallBtnUi
import com.pi.ProjectInclusion.android.common_UI.TextViewField
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.BackHandler
import com.pi.ProjectInclusion.constants.ConstantVariables.ASTRICK
import com.pi.ProjectInclusion.constants.CustomDialog
import com.pi.ProjectInclusion.data.model.AuthenticationModel.GetLanguageListResponse
import com.pi.ProjectInclusion.data.model.AuthenticationModel.GetUserTypeResponse
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SpecialEducatorScreen2(
    onNext: () -> Unit, // Dashboard
    onBack: () -> Unit,
    viewModel: LoginViewModel,
) {
    var isDialogVisible by remember { mutableStateOf(false) }
//    val uiState by viewModel.uiStateType.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val userType = remember { mutableStateListOf<GetUserTypeResponse.UserTypeResponse>() }
    CustomDialog(
        isVisible = isDialogVisible,
        onDismiss = { isDialogVisible = false },
        message = stringResource(R.string.txt_loading)
    )
    BackHandler {
//        BackButtonPress(navController, AppRoute.UserTypeSelect.route)
        onBack()
    }
    LoggerProvider.logger.d("Screen: " + "EnterUserScreen2()")

    Surface(
        modifier = Modifier.fillMaxWidth(), color = White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = White),
            verticalArrangement = Arrangement.Top
        ) {
            SpeEducatorScreen2UI(context, onBack = onBack, onNext = onNext, viewModel =viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeEducatorScreen2UI(
    context: Context,
    onNext: () -> Unit,
    onBack: () -> Unit,
    viewModel: LoginViewModel,
) {
    val colors = MaterialTheme.colorScheme
    val scrollState = rememberScrollState()
    var isApiResponded by remember { mutableStateOf(false) }
    val internetMessage by remember { mutableStateOf("") }

    var isDialogVisible by remember { mutableStateOf(false) }

    val invalidMobNo = stringResource(id = R.string.text_enter_no)
    val txtContinue = stringResource(id = R.string.text_continue)
    val tvUdise = stringResource(id = R.string.txt_udise_number)
    val enterMobile = stringResource(R.string.txt_enter_udise)
    val enterHere = stringResource(R.string.enter_here).toString()
    var chooseOpt = stringResource(R.string.choose_option)
    var selectedSchool by remember { mutableStateOf(chooseOpt) }
//    var selectedSchool = stringResource(R.string.choose_option)
    var schoolSelectedId = remember { mutableStateOf("0") }

    val schoolList = remember { mutableStateListOf<GetLanguageListResponse.LanguageResponse>() }

    var mobNo = rememberSaveable { mutableStateOf("") }
    val crrText = rememberSaveable { mutableStateOf("") }

    val enterHereText =remember { mutableStateOf(enterHere) }
    var showError by remember { mutableStateOf(false) }
    var inValidMobNo by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    var isBottomSheetVisible by rememberSaveable { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { it != SheetValue.Hidden })

    LaunchedEffect(Unit) {
        viewModel.getLanguages()
    }

    LaunchedEffect(uiState) {
        when {
            uiState.isLoading -> {
                schoolList.clear()
                isDialogVisible = true
            }

            uiState.error.isNotEmpty() -> {
//                languageData.clear()
                LoggerProvider.logger.d("Error: ${uiState.error}")
                context.toast(uiState.error)
                isDialogVisible = false
            }

            uiState.success != null -> {
                val list = uiState.success?.response ?: emptyList()
                LoggerProvider.logger.d("Languages fetched: ${list.size}")

                if (list.isNotEmpty()) {
                    schoolList.clear()
                    schoolList.addAll(list)
                } else {
                    LoggerProvider.logger.d("Languages fetched: 0 (null or empty response)")
                }
                isDialogVisible = false
            }
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
                stringResource(R.string.txt_step_2), OrangeSubTitle, onBackButtonClick = {
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

                Text(
                    tvUdise,
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                    fontFamily = fontMedium,
                    fontSize = 15.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_DEFAULT_BUTTON_TEXT
                    } else {
                        Gray
                    }
                )

                MobileTextField(
                    isIcon = false,
                    icon = ImageVector.vectorResource(id = R.drawable.call_on_otp),
                    colors = colors,
                    number = mobNo,
                    trueFalse = true,
                    hint = enterMobile.toString()
                )

                // State
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.txt_state))
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
                    fontSize = 15.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_BODY_TEXT
                    } else {
                        Bg_Gray
                    }
                )

                // Dropdown field (no internal menu)
                DropdownMenuUi(
                    options = emptyList(), // not needed since bottom sheet will handle it
                    onItemSelected = {selectedSchool }, // not used
                    placeholder = selectedSchool,
                    onClick = {
                        scope.launch {
                            isBottomSheetVisible = true
                            sheetState.show()
                        }
                    }
                )
                // Bottom sheet for school list
                SchoolListBottomSheet(
                    isBottomSheetVisible = isBottomSheetVisible,
                    sheetState = sheetState,
                    onDismiss = {
                        scope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion { isBottomSheetVisible = false }
                    },
                    onDecline = { /* No decline action */ },
                    onTextSelected = { selectedName ->
                        selectedSchool = selectedName
                        // If you want ID mapping:
                        schoolSelectedId.value = selectedName
//                        schoolSelectedId = schoolList.find { it == selectedName } // Replace with your mapping
                    },
                    schoolList.map { it.name }.toList() as List<String>
                )

                // District
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.txt_district))
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
                    fontSize = 15.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_BODY_TEXT
                    } else {
                        Bg_Gray
                    }
                )
                DropdownMenuUi(listOf(), onItemSelected = {

                }, modifier = Modifier.clickable {
                }, placeholder = selectedSchool, onClick = {
//                            schoolListOpen = true
                    scope.launch {
                        isBottomSheetVisible = true // true under development code
                        sheetState.expand()
                    }
                })
//                SchoolListBottomSheet(
//                    isBottomSheetVisible = isBottomSheetVisible,
//                    sheetState = sheetState,
//                    onDismiss = {
//                        scope.launch { sheetState.hide() }
//                            .invokeOnCompletion { isBottomSheetVisible = false }
//                    },
//                    onDecline = {
//
//                    },
//                    onTextSelected = { it ->
//                        /* selectedSchool = it
//                 schoolList.find { it.name == selectedSchool }?.id?.let {
//                     schoolSelectedId.value = it
//                 }*/
//                        "School"
//                    },
//                    schoolList.map { it.name }.toList() as List<String>
//                )

                // Block
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.txt_block_zone))
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
                    fontSize = 15.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_BODY_TEXT
                    } else {
                        Bg_Gray
                    }
                )
                DropdownMenuUi(listOf(), onItemSelected = {

                }, modifier = Modifier.clickable {
                }, placeholder = selectedSchool, onClick = {
//                            schoolListOpen = true
                    scope.launch {
                        isBottomSheetVisible = true // true under development code
                        sheetState.expand()
                    }
                })
//                SchoolListBottomSheet(
//                    isBottomSheetVisible = isBottomSheetVisible,
//                    sheetState = sheetState,
//                    onDismiss = {
//                        scope.launch { sheetState.hide() }
//                            .invokeOnCompletion { isBottomSheetVisible = false }
//                    },
//                    onDecline = {
//
//                    },
//                    onTextSelected = { it ->
//                        selectedSchool = it
//                 schoolList.find { it.name == selectedSchool }?.id?.let {
//                     schoolSelectedId.value = it
//                 }
//                        "School"
//                    },
//                    schoolList.map { it.name }.toList() as List<String>
//                )

                // School
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.txt_school_name))
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
                    fontSize = 15.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_BODY_TEXT
                    } else {
                        Bg_Gray
                    }
                )
                DropdownMenuUi(listOf(), onItemSelected = {

                }, modifier = Modifier.clickable {
                }, placeholder = selectedSchool, onClick = {
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
                        selectedSchool = it
                 schoolList.find { it.name == selectedSchool }?.id?.let {
                     schoolSelectedId.value = it
                 }
                    },
                    schoolList.map { it.name }.toList() as List<String>
                )

                if (inValidMobNo) {
                    Text(
                        invalidMobNo.toString(),
                        color = LightRed01,
                        modifier = Modifier.padding(5.dp),
                        fontSize = 10.sp
                    )
                }

                Text(
                    text = stringResource(R.string.txt_reason_empty_school),
                    modifier = Modifier.padding(
                        top = 24.dp,
                        bottom = 10.dp,
                        start = 8.dp, end = 8.dp
                    ),
                    textAlign = TextAlign.Start,
                    fontFamily = fontMedium,
                    fontSize = 15.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_BODY_TEXT
                    } else {
                        Bg_Gray
                    }
                )

                DropdownMenuUi(listOf(), onItemSelected = {

                }, modifier = Modifier.clickable {

                }, placeholder = selectedSchool, onClick = {
//                            schoolListOpen = true
                    scope.launch {
                        isBottomSheetVisible = true // true under development code
                        sheetState.expand()
                    }
                })


                // Profession
                Text(
                    text = stringResource(R.string.txt_profession),
                    modifier = Modifier.padding(
                        top = 24.dp,
                        bottom = 10.dp,
                        start = 8.dp, end = 8.dp
                    ),
                    textAlign = TextAlign.Start,
                    fontFamily = fontMedium,
                    fontSize = 15.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_BODY_TEXT
                    } else {
                        Bg_Gray
                    }
                )

                DropdownMenuUi(listOf(), onItemSelected = {

                }, modifier = Modifier.clickable {

                }, placeholder = selectedSchool, onClick = {
//                            schoolListOpen = true
                    scope.launch {
                        isBottomSheetVisible = true // true under development code
                        sheetState.expand()
                    }
                })

                // Qualification
                Text(
                    text = stringResource(R.string.txt_qualification),
                    modifier = Modifier.padding(
                        top = 24.dp,
                        bottom = 10.dp,
                        start = 8.dp, end = 8.dp
                    ),
                    textAlign = TextAlign.Start,
                    fontFamily = fontMedium,
                    fontSize = 15.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_BODY_TEXT
                    } else {
                        Bg_Gray
                    }
                )

                DropdownMenuUi(listOf(), onItemSelected = {

                }, modifier = Modifier.clickable {

                }, placeholder = selectedSchool, onClick = {
//                            schoolListOpen = true
                    scope.launch {
                        isBottomSheetVisible = true // true under development code
                        sheetState.expand()
                    }
                })

                // Specialization
                Text(
                    text = stringResource(R.string.txt_specialization),
                    modifier = Modifier.padding(
                        top = 24.dp,
                        bottom = 10.dp,
                        start = 8.dp, end = 8.dp
                    ),
                    textAlign = TextAlign.Start,
                    fontFamily = fontMedium,
                    fontSize = 15.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_BODY_TEXT
                    } else {
                        Bg_Gray
                    }
                )

                DropdownMenuUi(listOf(), onItemSelected = {

                }, modifier = Modifier.clickable {

                }, placeholder = selectedSchool, onClick = {
//                            schoolListOpen = true
                    scope.launch {
                        isBottomSheetVisible = true // true under development code
                        sheetState.expand()
                    }
                })

                // State
                Text(
                    text = stringResource(R.string.txt_crrNo),
                    modifier = Modifier.padding(
                        top = 24.dp,
                        bottom = 10.dp,
                        start = 8.dp, end = 8.dp
                    ),
                    textAlign = TextAlign.Start,
                    fontFamily = fontMedium,
                    fontSize = 15.sp,
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
                    text = enterHereText,
                    trueFalse = true,
                    hint = enterHereText.toString()
                )
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
                                        onNext()

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
fun UserProfile2UI() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val onNext: () -> Unit = {}
    val onBack: () -> Unit = {}
    val viewModel: LoginViewModel = koinViewModel()
    SpeEducatorScreen2UI(context, onNext, onBack, viewModel = viewModel)
}