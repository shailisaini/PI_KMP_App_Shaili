package com.pi.ProjectInclusion.android.screens.registration.professionals

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
import com.pi.ProjectInclusion.android.common_UI.TextViewField
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
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ProfessionListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.QualificationListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SchoolByUdiseCodeResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SchoolListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SpecializationListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.StateListResponse
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.ext.clearQuotes

@Composable
fun ProfessionalsRegistrationScreen(
    onNext: () -> Unit,
    onBack: () -> Unit,
    viewModel: LoginViewModel,
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
            ProfessionalScreenUI(context, onBack = onBack, onNext = onNext, viewModel = viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessionalScreenUI(
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
    var isUdiseDetails by remember { mutableStateOf(false) }
    val invalidUdiseNo = stringResource(id = R.string.text_enter_udise)
    val txtContinue = stringResource(id = R.string.text_continue)
    val tvUdise = stringResource(id = R.string.txt_udise_number)
    val enterUdise = stringResource(R.string.txt_enter_udise)
    val enterHereText = stringResource(R.string.enter_here)
    var msgState = stringResource(R.string.key_select_state)
    var msgDistrict = stringResource(R.string.key_select_district)
    var msgBlock = stringResource(R.string.key_select_block)
    var msgSchool = stringResource(R.string.key_select_school)
    var msgProfession = stringResource(R.string.key_select_profession)
    var msgQualification = stringResource(R.string.key_select_qualification)
    var msgSpecialization = stringResource(R.string.key_select_specialization)
    var msgCRRN = stringResource(R.string.key_CRR_No)

    var udiseNo = rememberSaveable { mutableStateOf("") }
    val crrText = rememberSaveable { mutableStateOf("") }

    var showError by remember { mutableStateOf(false) }
    var inValidUdiseNo by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    var isBottomSheetStateVisible by rememberSaveable { mutableStateOf(false) }
    var isBottomSheetDistrictVisible by rememberSaveable { mutableStateOf(false) }
    var isBottomSheetBlockVisible by rememberSaveable { mutableStateOf(false) }
    var isBottomSheetSchoolVisible by rememberSaveable { mutableStateOf(false) }
    var isBottomSheetProfessionVisible by rememberSaveable { mutableStateOf(false) }
    var isBottomSheetQualificationVisible by rememberSaveable { mutableStateOf(false) }
    var isBottomSheetSpecializationVisible by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true, confirmValueChange = { it != SheetValue.Hidden })

    var selectedState by remember { mutableStateOf("") }
    var selectedDistrict by remember { mutableStateOf("") }
    var selectedBlock by remember { mutableStateOf("") }
    var selectedSchool by remember { mutableStateOf("") }
    var selectedProfession by remember { mutableStateOf("") }
    var selectedQualification by remember { mutableStateOf("") }
    var selectedSpecialization by remember { mutableStateOf("") }
    val allStatesState by viewModel.allStatesResponse.collectAsStateWithLifecycle()
    val allDistrictsState by viewModel.allDistrictsResponse.collectAsStateWithLifecycle()
    val allBlocksState by viewModel.allBlocksResponse.collectAsStateWithLifecycle()
    val allSchoolsState by viewModel.allSchoolsResponse.collectAsStateWithLifecycle()
    val allUdiseState by viewModel.allUdiseCodeResponse.collectAsStateWithLifecycle()
    val allProfessionState by viewModel.professionListResponse.collectAsStateWithLifecycle()
    val allQualificationState by viewModel.qualificationListResponse.collectAsStateWithLifecycle()
    val allSpecializationState by viewModel.specializationListResponse.collectAsStateWithLifecycle()
    val professionalProfileState by viewModel.professionalProfileResponse.collectAsStateWithLifecycle()
    var allState = remember { mutableStateListOf<StateListResponse>() }
    var allDistricts = remember { mutableStateListOf<DistrictListResponse>() }
    var allBlocks = remember { mutableStateListOf<BlockListResponse>() }
    var allSchools = remember { mutableStateListOf<SchoolListResponse.SchoolResponse>() }
    var allUdiseDetails =
        remember { mutableStateListOf<SchoolByUdiseCodeResponse.UdiseCodeResponse>() }
    var allProfession = remember { mutableStateListOf<ProfessionListResponse>() }
    var allQualification = remember { mutableStateListOf<QualificationListResponse>() }
    var allSpecialization = remember { mutableStateListOf<SpecializationListResponse>() }

    var stateSelectedId = remember { mutableIntStateOf(-1) }
    var districtSelectedId = remember { mutableIntStateOf(-1) }
    var blockSelectedId = remember { mutableIntStateOf(-1) }
    var schoolSelectedId = remember { mutableIntStateOf(-1) }
    var professionId = remember { mutableIntStateOf(-1) }
    var qualificationId = remember { mutableIntStateOf(-1) }
    var specializationId = remember { mutableIntStateOf(-1) }
    val strToken =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6IjR5QW9PaGdVQnJyOUVkdXVvbHFvSVE9PSIsInN1YiI6IjEiLCJpYXQiOjE3NTU3NzQ4NDcsImV4cCI6MTc1NTg2MTI0N30.bjqUtT6SSrMRpNO4EiLgh6VhnJp54deOPvQBrjzbTGo"

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

    LaunchedEffect(allProfessionState) {
        when {
            allProfessionState.isLoading -> {
                isDialogVisible = true
            }

            allProfessionState.error.isNotEmpty() -> {
                logger.d("All Profession error : ${allProfessionState.success}")
                isDialogVisible = false
            }

            allProfessionState.success != null -> {
                logger.d("All Profession response : ${allProfessionState.success}")
                if (allProfessionState.success?.size != 0) {
                    allProfessionState.success!!.let {
                        it.let { it5 ->
                            allProfession.addAll(
                                it5.toList()
                            )
                        }
                    }
                    println("All Profession list data :- $allProfession")
                    isUdiseDetails = true
                }
                isDialogVisible = false
            }
        }
    }

    LaunchedEffect(allQualificationState) {
        when {
            allQualificationState.isLoading -> {
                isDialogVisible = true
            }

            allQualificationState.error.isNotEmpty() -> {
                logger.d("All Qualification error : ${allQualificationState.success}")
                isDialogVisible = false
            }

            allQualificationState.success != null -> {
                logger.d("All Qualification response : ${allQualificationState.success}")
                if (allQualificationState.success?.size != 0) {
                    allQualificationState.success!!.let {
                        it.let { it5 ->
                            allQualification.addAll(
                                it5.toList()
                            )
                        }
                    }
                    println("All Qualification list data :- $allQualification")
                    isUdiseDetails = true
                }
                isDialogVisible = false
            }
        }
    }

    LaunchedEffect(allSpecializationState) {
        when {
            allSpecializationState.isLoading -> {
                isDialogVisible = true
            }

            allSpecializationState.error.isNotEmpty() -> {
                logger.d("All Specialization error : ${allSpecializationState.success}")
                isDialogVisible = false
            }

            allSpecializationState.success != null -> {
                logger.d("All Specialization response : ${allSpecializationState.success}")
                if (allSpecializationState.success?.size != 0) {
                    allSpecializationState.success!!.let {
                        it.let { it5 ->
                            allSpecialization.addAll(
                                it5.toList()
                            )
                        }
                    }
                    println("All Specialization list data :- $allSpecialization")
                    isUdiseDetails = true
                }
                isDialogVisible = false
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
                    text = tvUdise,
                    modifier = Modifier.padding(
                        top = 10.dp, bottom = 10.dp, start = 8.dp, end = 8.dp
                    ),
                    fontFamily = fontMedium,
                    fontSize = 15.sp,
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
                            hint = enterUdise.toString()
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
                    fontSize = 15.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_BODY_TEXT
                    } else {
                        Bg_Gray
                    }
                )

                DropdownMenuUi(
                    options = listOf(),
                    onItemSelected = { },
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
                            isBottomSheetStateVisible = true
                            sheetState.show()
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
                    fontSize = 15.sp,
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
                    fontSize = 15.sp,
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
                    fontSize = 15.sp,
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
                            viewModel.getAllProfessionRepo()
                        }
                    },
                    allSchools.map { it.name }.toList() as List<String>
                )

                // Profession
                Text(
                    text = stringResource(R.string.txt_profession),
                    modifier = Modifier.padding(
                        top = 24.dp, bottom = 10.dp, start = 8.dp, end = 8.dp
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

                DropdownMenuUi(
                    options = listOf(),
                    onItemSelected = {},
                    modifier = Modifier.clickable {},
                    placeholder = if (selectedProfession.isNotEmpty()) {
                        selectedProfession.toString()
                    } else {
                        stringResource(R.string.choose_option)
                    },
                    onClick = {
                        scope.launch {
                            isBottomSheetProfessionVisible = true // true under development code
                            sheetState.expand()
                        }
                    })

                SchoolListBottomSheet(
                    isBottomSheetVisible = isBottomSheetProfessionVisible,
                    sheetState = sheetState,
                    onDismiss = {
                        scope.launch { sheetState.hide() }
                            .invokeOnCompletion { isBottomSheetProfessionVisible = false }
                    },
                    onDecline = {},
                    onTextSelected = { profession ->
                        selectedProfession = profession
                        allProfession.find { it.name == profession }?.id?.let {
                            professionId.intValue = it
                            viewModel.getAllQualificationRepo(it)
                        }
                    },
                    allProfession.map { it.name }.toList() as List<String>
                )

                // Qualification
                Text(
                    text = stringResource(R.string.txt_qualification),
                    modifier = Modifier.padding(
                        top = 24.dp, bottom = 10.dp, start = 8.dp, end = 8.dp
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

                DropdownMenuUi(
                    options = listOf(),
                    onItemSelected = {},
                    modifier = Modifier.clickable {},
                    placeholder = if (selectedQualification.isNotEmpty()) {
                        selectedQualification.toString()
                    } else {
                        stringResource(R.string.choose_option)
                    },
                    onClick = {
                        scope.launch {
                            isBottomSheetQualificationVisible = true // true under development code
                            sheetState.expand()
                        }
                    })

                SchoolListBottomSheet(
                    isBottomSheetVisible = isBottomSheetQualificationVisible,
                    sheetState = sheetState,
                    onDismiss = {
                        scope.launch { sheetState.hide() }
                            .invokeOnCompletion { isBottomSheetQualificationVisible = false }
                    },
                    onDecline = {},
                    onTextSelected = { qualification ->
                        selectedQualification = qualification
                        allQualification.find { it.name == qualification }?.id?.let {
                            qualificationId.intValue = it
                            viewModel.getAllSpecializationRepo(professionId.intValue, it)
                        }
                    },
                    allQualification.map { it.name }.toList() as List<String>
                )

                // Specialization
                Text(
                    text = stringResource(R.string.txt_specialization),
                    modifier = Modifier.padding(
                        top = 24.dp, bottom = 10.dp, start = 8.dp, end = 8.dp
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

                DropdownMenuUi(
                    options = listOf(),
                    onItemSelected = {},
                    modifier = Modifier.clickable {},
                    placeholder = if (selectedSpecialization.isNotEmpty()) {
                        selectedSpecialization.toString()
                    } else {
                        stringResource(R.string.choose_option)
                    },
                    onClick = {
                        scope.launch {
                            isBottomSheetSpecializationVisible = true
                            sheetState.expand()
                        }
                    })

                SchoolListBottomSheet(
                    isBottomSheetVisible = isBottomSheetSpecializationVisible,
                    sheetState = sheetState,
                    onDismiss = {
                        scope.launch { sheetState.hide() }
                            .invokeOnCompletion { isBottomSheetSpecializationVisible = false }
                    },
                    onDecline = {},
                    onTextSelected = { specialization ->
                        selectedSpecialization = specialization
                        allSpecialization.find { it.name == specialization }?.id?.let {
                            specializationId.intValue = it
                        }
                    },
                    allSpecialization.map { it.name }.toList() as List<String>
                )

                // Crr No.
                Text(
                    text = stringResource(R.string.txt_crrNo),
                    modifier = Modifier.padding(
                        top = 24.dp, bottom = 10.dp, start = 8.dp, end = 8.dp
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
                    text = crrText,
                    trueFalse = true,
                    hint = enterHereText.toString()
                )
                Spacer(modifier = Modifier.height(15.dp))

                if (inValidUdiseNo) {
                    Text(
                        invalidUdiseNo.toString(),
                        color = LightRed01,
                        modifier = Modifier.padding(5.dp),
                        fontSize = 10.sp
                    )
                }
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
                            } else if (selectedProfession.isEmpty() || professionId.intValue == -1) {
                                context.toast(msgProfession)
                            } else if (selectedQualification.isEmpty() || qualificationId.intValue == -1) {
                                context.toast(msgQualification)
                            } else if (selectedSpecialization.isEmpty() || specializationId.intValue == -1) {
                                context.toast(msgSpecialization)
                            } else if (crrText.value.isEmpty()) {
                                context.toast(msgCRRN)
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
                                            schoolSelectedId.intValue,
                                            0,
                                            professionId.intValue,
                                            qualificationId.intValue,
                                            specializationId.intValue,
                                            crrText.value.toString()
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
    ProfessionalScreenUI(context, onNext, onBack, viewModel = viewModel)
}