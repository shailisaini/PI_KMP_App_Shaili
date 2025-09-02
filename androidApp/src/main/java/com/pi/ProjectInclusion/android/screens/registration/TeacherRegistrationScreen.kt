package com.pi.ProjectInclusion.android.screens.registration

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.kmptemplate.logger.LoggerProvider.logger
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
import com.pi.ProjectInclusion.android.common_UI.DropdownMenuUi
import com.pi.ProjectInclusion.android.common_UI.RegistrationHeader
import com.pi.ProjectInclusion.android.common_UI.SchoolListBottomSheet
import com.pi.ProjectInclusion.android.common_UI.SmallBtnUi
import com.pi.ProjectInclusion.android.common_UI.UdiseTextField
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.BackHandler
import com.pi.ProjectInclusion.constants.ConstantVariables.ASTRICK
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION
import com.pi.ProjectInclusion.constants.CustomDialog
import com.pi.ProjectInclusion.data.model.authenticationModel.request.ProfessionalProfileRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.response.BlockListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.DistrictListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SchoolByUdiseCodeResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SchoolListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.StateListResponse
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.ext.clearQuotes

@Composable
fun TeacherRegistrationScreen(
    viewModel: LoginViewModel,
    onNext: () -> Unit, // Dashboard
    onBack: () -> Unit,
) {
    val context = LocalContext.current
    BackHandler {
        onBack()
    }

    logger.d("Screen: " + "EnterUserScreen2()")

    Surface(
        modifier = Modifier.fillMaxWidth(), color = White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = White),
            verticalArrangement = Arrangement.Top
        ) {
            TeacherScreenUI(context, onBack = onBack, onNext = onNext, viewModel = viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherScreenUI(
    context: Context,
    onNext: () -> Unit,
    onBack: () -> Unit,
    viewModel: LoginViewModel,
) {
    val colors = MaterialTheme.colorScheme
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val isInternetAvailable by remember { mutableStateOf(true) }
    var isApiResponded by remember { mutableStateOf(false) }
    val internetMessage by remember { mutableStateOf("") }

    var isDialogVisible by remember { mutableStateOf(false) }
    var isUdiseDetails by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }

    val noDataMessage = stringResource(R.string.txt_oops_no_data_found)
    val invalidUdise = stringResource(id = R.string.text_enter_udise)
//  languageData[LanguageTranslationsResponse.KEY_INVALID_MOBILE_NO_ERROR].toString()
    val txtContinue = stringResource(id = R.string.text_continue)
    val tvUdise = stringResource(id = R.string.txt_udise_number)
    var udiseNo = rememberSaveable { mutableStateOf("") }
    var firstName = rememberSaveable { mutableStateOf("") }
    var lastName = rememberSaveable { mutableStateOf("") }
    var whatsappNo = rememberSaveable { mutableStateOf("") }
    var email = rememberSaveable { mutableStateOf("") }
    var textUpload = stringResource(R.string.txt_profile_pic_upload)
    val enterUdiseCode = stringResource(R.string.txt_udise_number)
    val textNameEg = stringResource(R.string.txt_eg_first_name)
    val textWhatsappEg = stringResource(R.string.txt_eg_whatsapp_name)
    val textLastNameEg = stringResource(R.string.txt_eg_last_name)
    val textEmailEg = stringResource(R.string.txt_eg_email_name)
    var showError by remember { mutableStateOf(false) }
    var inValidUdiseNo by remember { mutableStateOf(false) }
    var date by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    var isBottomSheetStateVisible by rememberSaveable { mutableStateOf(false) }
    var isBottomSheetDistrictVisible by rememberSaveable { mutableStateOf(false) }
    var isBottomSheetBlockVisible by rememberSaveable { mutableStateOf(false) }
    var isBottomSheetSchoolVisible by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true, confirmValueChange = { it != SheetValue.Hidden })

    var selectedState by remember { mutableStateOf("") }
    var selectedDistrict by remember { mutableStateOf("") }
    var selectedBlock by remember { mutableStateOf("") }
    var selectedSchool by remember { mutableStateOf("") }
    val allStatesState by viewModel.allStatesResponse.collectAsStateWithLifecycle()
    val allDistrictsState by viewModel.allDistrictsResponse.collectAsStateWithLifecycle()
    val allBlocksState by viewModel.allBlocksResponse.collectAsStateWithLifecycle()
    val allSchoolsState by viewModel.allSchoolsResponse.collectAsStateWithLifecycle()
    val allUdiseState by viewModel.allUdiseCodeResponse.collectAsStateWithLifecycle()
    val professionalProfileState by viewModel.professionalProfileResponse.collectAsStateWithLifecycle()
    var allState = remember { mutableStateListOf<StateListResponse>() }
    var allDistricts = remember { mutableStateListOf<DistrictListResponse>() }
    var allBlocks = remember { mutableStateListOf<BlockListResponse>() }
    var allSchools = remember { mutableStateListOf<SchoolListResponse.SchoolResponse>() }
    var allUdiseDetails =
        remember { mutableStateListOf<SchoolByUdiseCodeResponse.UdiseCodeResponse>() }

    var stateSelectedId = remember { mutableIntStateOf(-1) }
    var districtSelectedId = remember { mutableIntStateOf(-1) }
    var blockSelectedId = remember { mutableIntStateOf(-1) }
    var schoolSelectedId = remember { mutableIntStateOf(-1) }
    val strToken =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6IjR5QW9PaGdVQnJyOUVkdXVvbHFvSVE9PSIsInN1YiI6IjEiLCJpYXQiOjE3NTU3NzQ4NDcsImV4cCI6MTc1NTg2MTI0N30.bjqUtT6SSrMRpNO4EiLgh6VhnJp54deOPvQBrjzbTGo"
    var msgState = stringResource(R.string.key_select_state)
    var msgDistrict = stringResource(R.string.key_select_district)
    var msgBlock = stringResource(R.string.key_select_block)
    var msgSchool = stringResource(R.string.key_select_school)

    LaunchedEffect(Unit) {
        viewModel.getAllStateList()
    }

    LaunchedEffect(allStatesState) {
        when {
            allStatesState.isLoading -> {
                isDialogVisible = true
            }

            allStatesState.error.isNotEmpty() -> {
                logger.d("All state error : ${allStatesState.success}")
                isDialogVisible = false
            }

            allStatesState.success != null -> {
                logger.d("All state response : ${allStatesState.success}")
                if (allStatesState.success?.size != 0) {
                    allDistricts.clear()
                    allStatesState.success.let {
                        it.let { it1 -> allState.addAll(it1!!.toList()) }
                    }
                    println("All states list data :- $allState")
                }
                isDialogVisible = false
            }
        }
    }

    LaunchedEffect(allDistrictsState) {
        when {
            allDistrictsState.isLoading -> {
                isDialogVisible = true
            }

            allDistrictsState.error.isNotEmpty() -> {
                logger.d("All district error : ${allDistrictsState.success}")
                isDialogVisible = false
            }

            allDistrictsState.success != null -> {
                logger.d("All district response : ${allDistrictsState.success}")
                if (allDistrictsState.success?.size != 0) {
                    allBlocks.clear()
                    allDistrictsState.success.let {
                        it.let { it2 -> allDistricts.addAll(it2!!.toList()) }
                    }
                    println("All district list data :- $allDistricts")
                }
                isDialogVisible = false
            }
        }
    }

    LaunchedEffect(allBlocksState) {
        when {
            allBlocksState.isLoading -> {
                isDialogVisible = true
            }

            allBlocksState.error.isNotEmpty() -> {
                logger.d("All Blocks error : ${allBlocksState.success}")
                isDialogVisible = false
            }

            allBlocksState.success != null -> {
                logger.d("All Blocks response : ${allBlocksState.success}")
                if (allBlocksState.success?.size != 0) {
                    allSchools.clear()
                    allBlocksState.success.let {
                        it.let { it3 -> allBlocks.addAll(it3!!.toList()) }
                    }
                    println("All Blocks list data :- $allBlocks")
                }
                isDialogVisible = false
            }
        }
    }

    LaunchedEffect(allSchoolsState) {
        when {
            allSchoolsState.isLoading -> {
                isDialogVisible = true
            }

            allSchoolsState.error.isNotEmpty() -> {
                logger.d("All Schools error : ${allSchoolsState.success}")
                isDialogVisible = false
            }

            allSchoolsState.success != null -> {
                logger.d("All Schools response : ${allSchoolsState.success}")
                if (allSchoolsState.success?.status == 1) {
                    allSchoolsState.success!!.response.let {
                        it.let { it4 -> allSchools.addAll(it4!!.toList()) }
                    }
                    println("All Schools list data :- $allSchools")
                }
                isDialogVisible = false
            }
        }
    }

    LaunchedEffect(allUdiseState) {
        when {
            allUdiseState.isLoading -> {
                isDialogVisible = true
            }

            allUdiseState.error.isNotEmpty() -> {
                logger.d("All Udise error : ${allUdiseState.success}")
                isDialogVisible = false
            }

            allUdiseState.success != null -> {
                logger.d("All Udise response : ${allUdiseState.success}")
                if (allUdiseState.success?.status == 1) {
                    allUdiseState.success!!.response.let {
                        it.let { it5 ->
                            allUdiseDetails.addAll(
                                it5!!.toList()
                            )
                        }
                    }
                    selectedState = allUdiseDetails[0].stateName.toString()
                    stateSelectedId.intValue = allUdiseDetails[0].stateId!!

                    selectedDistrict = allUdiseDetails[0].districtName.toString()
                    districtSelectedId.intValue = allUdiseDetails[0].districtId!!

                    selectedBlock = allUdiseDetails[0].blockName.toString()
                    blockSelectedId.intValue = allUdiseDetails[0].blockId!!

                    selectedSchool = allUdiseDetails[0].schoolName.toString()
                    schoolSelectedId.intValue = allUdiseDetails[0].schoolId!!

                    println("All Udise list data :- $allUdiseDetails")
                    isUdiseDetails = true
                    isDialogVisible = false
                }
            }
        }
    }

    LaunchedEffect(professionalProfileState) {
        when {
            professionalProfileState.isLoading -> {
                isDialogVisible = true
            }

            professionalProfileState.error.isNotEmpty() -> {
                logger.d("All professional error : ${professionalProfileState.success}")
                isDialogVisible = false
            }

            professionalProfileState.success != null -> {
                logger.d("All professional response : ${professionalProfileState.success}")
                if (professionalProfileState.success?.statusCode == 200) {
                    println("All professional data :- ${professionalProfileState.success?.message}")
                    onNext()
                }
                isDialogVisible = false
            }
        }
    }

    CustomDialog(
        isVisible = isDialogVisible,
        onDismiss = { isDialogVisible = false },
        message = stringResource(R.string.txt_loading)
    )

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
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RegistrationHeader(
                stringResource(R.string.txt_create_profile),
                Black,
                stringResource(R.string.txt_step_2),
                OrangeSubTitle,
                onBackButtonClick = {
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
                    fontSize = 14.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_DEFAULT_BUTTON_TEXT
                    } else {
                        Gray
                    }
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        UdiseTextField(
                            isIcon = false,
                            icon = ImageVector.vectorResource(id = R.drawable.call_on_otp),
                            colors = colors,
                            number = udiseNo,
                            enable = true,
                            hint = enterUdiseCode.toString()
                        )
                    }

                    Card(
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .width(80.dp)
                            .height(55.dp)
                            .align(Alignment.CenterVertically)
                            .padding(start = 8.dp),
                        elevation = CardDefaults.cardElevation(1.dp),
                        colors = if (isSystemInDarkTheme()) {
                            CardDefaults.cardColors(PrimaryBlue)
                        } else {
                            CardDefaults.cardColors(
                                containerColor = PrimaryBlue,
                                contentColor = PrimaryBlue,
                                disabledContentColor = PrimaryBlue,
                                disabledContainerColor = PrimaryBlue
                            )
                        }
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            if (isUdiseDetails) {
                                Text(
                                    text = stringResource(R.string.key_Reset),
                                    textAlign = TextAlign.Center,
                                    fontFamily = fontMedium,
                                    fontSize = 14.sp,
                                    color = White,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth()
                                        .fillMaxHeight()
                                        .clickable {
                                            udiseNo.value.clearQuotes()
                                            isUdiseDetails = false
                                            selectedState = ""
                                            stateSelectedId.intValue = -1

                                            selectedDistrict = ""
                                            districtSelectedId.intValue = -1

                                            selectedBlock = ""
                                            blockSelectedId.intValue = -1

                                            selectedSchool = ""
                                            schoolSelectedId.intValue = -1
                                        })
                            } else {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.search_icon),
                                    tint = White,
                                    contentDescription = IMG_DESCRIPTION,
                                    modifier = Modifier
                                        .background(Color.Unspecified)
                                        .size(60.dp)
                                        .align(Alignment.CenterHorizontally)
                                        .padding(8.dp)
                                        .clickable {
                                            viewModel.getAllDetailsByUdiseId(udiseNo.value.toString())
                                        })
                            }
                        }
                    }
                }

                // State
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.txt_state))
                        pushStyle(SpanStyle(color = Color.Red))
                        append(ASTRICK)
                        pop()
                    },
                    modifier = Modifier.padding(
                        top = 24.dp, bottom = 10.dp, start = 8.dp, end = 8.dp
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

                DropdownMenuUi(
                    options = listOf(),
                    onItemSelected = {},
                    modifier = Modifier.clickable {
                        logger.d("Clicked state Id :- ${allState.size}")
                    },
                    placeholder = if (selectedState.isNotEmpty()) {
                        selectedState.toString()
                    } else {
                        stringResource(R.string.choose_option)
                    },
                    onClick = {
                        scope.launch {
                            isBottomSheetStateVisible = true // true under development code
                            sheetState.expand()
                        }
                    })

                SchoolListBottomSheet(
                    isBottomSheetVisible = isBottomSheetStateVisible,
                    sheetState = sheetState,
                    onDismiss = {
                        scope.launch { sheetState.hide() }
                            .invokeOnCompletion { isBottomSheetStateVisible = false }
                    },
                    onDecline = {},
                    onTextSelected = { state ->
                        selectedState = state.toString()
                        allState.find { it.name == state }?.id?.let {
                            stateSelectedId.intValue = it
                            viewModel.getAllDistrictByStateId(it)
                        }
                    },
                    allState.map { it.name }.toMutableList() as List<String>
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
                        top = 24.dp, bottom = 10.dp, start = 8.dp, end = 8.dp
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

                DropdownMenuUi(
                    options = listOf(),
                    onItemSelected = {},
                    modifier = Modifier.clickable {},
                    placeholder = if (selectedDistrict.isNotEmpty()) {
                        selectedDistrict.toString()
                    } else {
                        stringResource(R.string.choose_option)
                    },
                    onClick = {
                        scope.launch {
                            isBottomSheetDistrictVisible = true // true under development code
                            sheetState.expand()
                        }
                    })

                SchoolListBottomSheet(
                    isBottomSheetVisible = isBottomSheetDistrictVisible,
                    sheetState = sheetState,
                    onDismiss = {
                        scope.launch { sheetState.hide() }
                            .invokeOnCompletion { isBottomSheetDistrictVisible = false }
                    },
                    onDecline = {},
                    onTextSelected = { districts ->
                        selectedDistrict = districts
                        allDistricts.find { it.name == districts }?.id?.let {
                            districtSelectedId.intValue = it
                            viewModel.getAllBlockByDistrictId(it)
                        }
                    },
                    allDistricts.map { it.name }.toList() as List<String>
                )

                // Block
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.txt_block_zone))
                        pushStyle(SpanStyle(color = Color.Red))
                        append(ASTRICK)
                        pop()
                    },
                    modifier = Modifier.padding(
                        top = 24.dp, bottom = 10.dp, start = 8.dp, end = 8.dp
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

                DropdownMenuUi(
                    options = listOf(),
                    onItemSelected = {},
                    modifier = Modifier.clickable {},
                    placeholder = if (selectedBlock.isNotEmpty()) {
                        selectedBlock.toString()
                    } else {
                        stringResource(R.string.choose_option)
                    },
                    onClick = {
                        scope.launch {
                            isBottomSheetBlockVisible = true // true under development code
                            sheetState.expand()
                        }
                    })

                SchoolListBottomSheet(
                    isBottomSheetVisible = isBottomSheetBlockVisible,
                    sheetState = sheetState,
                    onDismiss = {
                        scope.launch { sheetState.hide() }
                            .invokeOnCompletion { isBottomSheetBlockVisible = false }
                    },
                    onDecline = {},
                    onTextSelected = { block ->
                        selectedBlock = block
                        allBlocks.find { it.name == block }?.id?.let {
                            blockSelectedId.intValue = it
                            viewModel.getAllSchoolsByBlockId(it)
                        }
                    },
                    allBlocks.map { it.name }.toList() as List<String>
                )

                // School
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.txt_school_name))
                        pushStyle(SpanStyle(color = Color.Red))
                        append(ASTRICK)
                        pop()
                    },
                    modifier = Modifier.padding(
                        top = 24.dp, bottom = 10.dp, start = 8.dp, end = 8.dp
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
                DropdownMenuUi(
                    options = listOf(),
                    onItemSelected = {},
                    modifier = Modifier.clickable {},
                    placeholder = if (selectedSchool.isNotEmpty()) {
                        selectedSchool.toString()
                    } else {
                        stringResource(R.string.choose_option)
                    },
                    onClick = {
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
                    onTextSelected = { school ->
                        selectedSchool = school
                        allSchools.find { it.name == school }?.id?.let {
                            schoolSelectedId.intValue = it
                        }
                    },
                    allSchools.map { it.name }.toList() as List<String>
                )

                if (inValidUdiseNo) {
                    Text(
                        invalidUdise.toString(),
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
                        .wrapContentHeight(), horizontalAlignment = Alignment.End
                ) {
                    SmallBtnUi(
                        enabled = udiseNo.value.length >= 11,
                        title = txtContinue,
                        onClick = {
                            if (udiseNo.value.isEmpty()) {
                                inValidUdiseNo = true
                            } else if (selectedState.isEmpty() || stateSelectedId.intValue == -1) {
                                context.toast(msgState)
                            } else if (selectedDistrict.isEmpty() || districtSelectedId.intValue == -1) {
                                context.toast(msgDistrict)
                            } else if (selectedBlock.isEmpty() || blockSelectedId.intValue == -1) {
                                context.toast(msgBlock)
                            } else if (selectedSchool.isEmpty() || schoolSelectedId.intValue == -1) {
                                context.toast(msgSchool)
                            } else {
                                if (showError || udiseNo.value.length < 11) {
                                    inValidUdiseNo = true
                                } else {
                                    showError = udiseNo.value.isEmpty()
                                    val firstDigitChar = udiseNo.value.toString().first()
                                    val firstDigit = firstDigitChar.digitToInt()
                                    if (firstDigit < 6) {
                                        inValidUdiseNo = true
                                    } else {
                                        isDialogVisible = true
                                        val professionalProfileRequest = ProfessionalProfileRequest(
                                            udiseNo.value.toString(),
                                            stateSelectedId.intValue,
                                            districtSelectedId.intValue,
                                            blockSelectedId.intValue,
                                            schoolSelectedId.intValue
                                        )
                                        viewModel.createProfessionalProfileRepo(
                                            professionalProfileRequest, strToken
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
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun UserProfile2UI() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val onNext: () -> Unit = {}
    val onBack: () -> Unit = {}
    val viewModel: LoginViewModel = koinViewModel()
    TeacherScreenUI(context, onNext, onBack, viewModel)
}