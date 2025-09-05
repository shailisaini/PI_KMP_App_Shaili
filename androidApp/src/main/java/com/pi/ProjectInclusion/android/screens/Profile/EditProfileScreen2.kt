package com.pi.ProjectInclusion.android.screens.Profile

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
import com.example.kmptemplate.logger.LoggerProvider
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
import com.pi.ProjectInclusion.android.common_UI.DetailsNoImgBackgroundUi
import com.pi.ProjectInclusion.android.common_UI.DropdownMenuUi
import com.pi.ProjectInclusion.android.common_UI.SchoolListBottomSheet
import com.pi.ProjectInclusion.android.common_UI.SmallBtnUi
import com.pi.ProjectInclusion.android.common_UI.UdiseTextField
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.BackHandler
import com.pi.ProjectInclusion.constants.ConstantVariables.ASTRICK
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION
import com.pi.ProjectInclusion.constants.ConstantVariables.TOKEN_PREF_KEY
import com.pi.ProjectInclusion.constants.ConstantVariables.USER_NAME
import com.pi.ProjectInclusion.constants.CustomDialog
import com.pi.ProjectInclusion.data.model.authenticationModel.request.ProfessionalProfileRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.response.BlockListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.DistrictListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.GetLanguageListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SchoolByUdiseCodeResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SchoolListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.StateListResponse
import com.pi.ProjectInclusion.data.model.profileModel.response.ViewProfileResponse
import com.pi.ProjectInclusion.data.remote.ApiService.Companion.PROFILE_BASE_URL
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import kotlinx.coroutines.launch
import org.koin.ext.clearQuotes

@Composable
fun EditProfileScreen2(
    onNext: () -> Unit,  //EditProfileScreen2
    onBack: () -> Unit,
    loginViewModel: LoginViewModel
) {

    val context = LocalContext.current
    logger.d("Screen: " + "EditProfileScreen2()")

    BackHandler {
        onBack()
    }

    Surface(
        modifier = Modifier.fillMaxWidth(), color = White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = White),
            verticalArrangement = Arrangement.Top
        ) {
            EditProfileScreen2UI(
                context,
                onNext = onNext,
                onBack = onBack,
                loginViewModel = loginViewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen2UI(
    context: Context,
    onBack: () -> Unit,
    onNext: () -> Unit,
    loginViewModel: LoginViewModel
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
    val tvUdise = stringResource(id = R.string.txt_udise_number)
    val stateError = stringResource(id = R.string.txt_select_state)
    val districtError = stringResource(id = R.string.txt_select_district)
    val blockError = stringResource(id = R.string.txt_select_block)
    val schoolError = stringResource(id = R.string.txt_select_school)
    val schoolList = remember { mutableStateListOf<GetLanguageListResponse.LanguageResponse>() }
    var udiseCode = rememberSaveable { mutableStateOf("") }
    val enterUdiseCode = stringResource(R.string.txt_udise_number)
    var isUdiseDetails by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
//    var inValidMobNo by remember { mutableStateOf(false) }
    var selectedState by remember { mutableStateOf("") }
    var selectedDistrict by remember { mutableStateOf("") }
    var selectedBlock by remember { mutableStateOf("") }
    var selectedSchool by remember { mutableStateOf("") }

    var isBottomSheetStateVisible by rememberSaveable { mutableStateOf(false) }
    var isBottomSheetDistrictVisible by rememberSaveable { mutableStateOf(false) }
    var isBottomSheetBlockVisible by rememberSaveable { mutableStateOf(false) }
    var isBottomSheetSchoolVisible by rememberSaveable { mutableStateOf(false) }
    var isUdiseCalled by rememberSaveable { mutableStateOf(false) }
    var isDropDownSelected by rememberSaveable { mutableStateOf(false) }

    val allStatesState by loginViewModel.allStatesResponse.collectAsStateWithLifecycle()
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
    val scope = rememberCoroutineScope()
    var encryptedUserName = loginViewModel.getPrefData(USER_NAME)
    var profileData by remember { mutableStateOf<ViewProfileResponse?>(null) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    var strToken = loginViewModel.getPrefData(TOKEN_PREF_KEY)

    CustomDialog(
        isVisible = isDialogVisible,
        onDismiss = { isDialogVisible = false },
        message = stringResource(R.string.txt_loading)
    )
    LaunchedEffect(Unit) {
        loginViewModel.getUserProfileViewModel(strToken, encryptedUserName)
    }

    val viewProfile by loginViewModel.viewUserProfileResponse.collectAsStateWithLifecycle()
    LaunchedEffect(viewProfile) {
        when {
            viewProfile.isLoading -> {
                isDialogVisible = true
            }

            viewProfile.error.isNotEmpty() -> {
                logger.d("viewProfileData: ${viewProfile.success}")
                isDialogVisible = false
            }

            viewProfile.success != null -> {
                logger.d("viewProfileData: ${viewProfile.success}")
                if (viewProfile.success!!.status == true) {
                    profileData = viewProfile.success!!
                    logger.d("viewProfileData 1: ${viewProfile.success}")
                } else {
                    context.toast(viewProfile.success!!.message.toString())
                }
                isDialogVisible = false
            }
        }
    }

    loginViewModel.getAllStateList()
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
                    allStatesState.success?.let { stateList ->
                        stateList.let { it1 ->
                            allState.clear()
                            allState.addAll(it1.toList())

                            // Find matching state
                            if (!isDropDownSelected) {
                                val matchedState = it1.find { state ->
                                    state.id == profileData?.response?.stateId?.toInt()
                                }

                                matchedState?.let { state ->
                                    selectedState = state.name!! // assuming field is stateName
                                }
                            }
                        }
                    }

                    println("All states list data :- $allState")
                }
                isDialogVisible = false
            }
        }
    }

    if (stateSelectedId.intValue != -1) {
        loginViewModel.getAllDistrictByStateId(stateSelectedId.intValue)
    }
    if (districtSelectedId.intValue != -1) {
        loginViewModel.getAllBlockByDistrictId(districtSelectedId.intValue)
    }
    if (blockSelectedId.intValue != -1) {
        loginViewModel.getAllSchoolsByBlockId(blockSelectedId.intValue)
    }

    val allDistrictsState by loginViewModel.allDistrictsResponse.collectAsStateWithLifecycle()
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
                allDistrictsState.success?.let { districtList ->
                    districtList.let { it1 ->
                        allDistricts.clear()
                        allDistricts.addAll(it1.toList())

                        // Find matching state
                        if (!isDropDownSelected) {
                            val matchedState = it1.find { district ->
                                district.id == profileData?.response?.districtId?.toInt()
                            }

                            matchedState?.let { district ->
                                selectedDistrict = district.name!! // assuming field is stateName
                            }
                        }
                    }
                }
                isDialogVisible = false
            }
        }
    }

    if (stateSelectedId.intValue != -1) {
        loginViewModel.getAllDistrictByStateId(stateSelectedId.intValue)
    }
    val allBlocksState by loginViewModel.allBlocksResponse.collectAsStateWithLifecycle()
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
                    allBlocksState.success?.let { blockList ->
                        blockList.let { it1 ->
                            allBlocks.clear()
                            allBlocks.addAll(it1.toList())

                            // Find matching state
                            if (!isDropDownSelected) {
                                val matchedState = it1.find { block ->
                                    block.id == profileData?.response?.blockId?.toInt()
                                }

                                matchedState?.let { block ->
                                    selectedBlock = block.name!! // assuming field is stateName
                                }
                            }
                        }
                    }
                    println("All Blocks list data :- $allBlocks")
                }
                isDialogVisible = false
            }
        }
    }

    val allSchoolsState by loginViewModel.allSchoolsResponse.collectAsStateWithLifecycle()
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
                    allSchoolsState.success?.response.let { schoolList ->
                        schoolList.let { it1 ->

                            allSchools.addAll(it1!!.toList())

                            // Find matching state
                            if (!isDropDownSelected) {
                                val matchedState = it1.find { school ->
                                    school.id == profileData?.response?.blockId?.toInt()
                                }

                                matchedState?.let { school ->
                                    selectedSchool = school.name!! // assuming field is stateName
                                }
                            }
                        }
                    }
                    println("All Schools list data :- $allSchools")
                }
                isDialogVisible = false
            }
        }

    }

    if (isUdiseCalled) {
        loginViewModel.getAllDetailsByUdiseId(udiseCode.value.toString())
        isDialogVisible = true
        val allUdiseState by loginViewModel.allUdiseCodeResponse.collectAsStateWithLifecycle()
        LaunchedEffect(allUdiseState) {
            when {
                allUdiseState.isLoading -> {
                    isDialogVisible = true
                }

                allUdiseState.error.isNotEmpty() -> {
                    logger.d("All Udise error : ${allUdiseState.success}")
                    isUdiseCalled = false
                    isDialogVisible = false
                }

                allUdiseState.success != null -> {
                    logger.d("All Udise response : ${allUdiseState.success}")
                    isUdiseCalled = false
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
                            stringResource(R.string.txt_step_2),
                            textAlign = TextAlign.Center,
                            fontSize = 13.sp,
                            fontFamily = fontMedium,
                            color = OrangeSubTitle,
                            modifier = Modifier
                                .padding(start = 10.dp)
                        )
                        Text(
                            stringResource(R.string.txt_professional_information),
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                            fontFamily = fontMedium,
                            color = Black,
                            modifier = Modifier
                                .padding(start = 10.dp)
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .verticalScroll(scrollState)
                                .background(color = Bg_Gray1)
                                .padding(vertical = 15.dp)
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
                                        number = udiseCode,
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
                                                        udiseCode.value.clearQuotes()
                                                        isUdiseDetails = false
                                                        // remove values
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
                                                        isDialogVisible = true
                                                        isUdiseCalled = true
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
                                        isBottomSheetStateVisible =
                                            true // true under development code
                                        sheetState.expand()
                                    }
                                })

                            SchoolListBottomSheet(
                                isBottomSheetVisible = isBottomSheetStateVisible,
                                sheetState = sheetState,
                                onDismiss = {
                                    scope.launch { sheetState.hide() }
                                    isBottomSheetStateVisible = false
                                },
                                onDecline = {},
                                onTextSelected = { state ->
                                    selectedState = state.toString()
                                    allState.find { it.name == state }?.id?.let {
                                        stateSelectedId.intValue = it
                                        isDropDownSelected = true
                                        loginViewModel.getAllDistrictByStateId(stateSelectedId.intValue)
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
                                        isBottomSheetDistrictVisible =
                                            true // true under development code
                                        sheetState.expand()
                                    }
                                })

                            SchoolListBottomSheet(
                                isBottomSheetVisible = isBottomSheetDistrictVisible,
                                sheetState = sheetState,
                                onDismiss = {
                                    scope.launch { sheetState.hide() }
                                    isBottomSheetDistrictVisible = false
                                },
                                onDecline = {},
                                onTextSelected = { districts ->
                                    selectedDistrict = districts
                                    allDistricts.find { it.name == districts }?.id?.let {
                                        districtSelectedId.intValue = it
                                        loginViewModel.getAllBlockByDistrictId(it)
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
                                        isBottomSheetBlockVisible =
                                            true // true under development code
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
                                        loginViewModel.getAllSchoolsByBlockId(it)
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
                                onItemSelected = {
                                },
                                modifier = Modifier.clickable {},
                                placeholder = if (selectedSchool.isNotEmpty()) {
                                    selectedSchool.toString()
                                } else {
                                    stringResource(R.string.choose_option)
                                },
                                onClick = {
                                    scope.launch {
                                        isBottomSheetSchoolVisible =
                                            true // true under development code
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

                            Spacer(modifier = Modifier.height(15.dp))
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
                                enabled = schoolSelectedId.intValue != -1, // if school is not empty
                                title = txtContinue,
                                onClick = {

                                    if (schoolSelectedId.intValue == -1) {
                                        context.toast(stateError)
                                    }
                                    if (districtSelectedId.intValue == -1) {
                                        context.toast(districtError)
                                    }
                                    if (blockSelectedId.intValue == -1) {
                                        context.toast(blockError)
                                    }
                                    if (schoolSelectedId.intValue == -1) {
                                        context.toast(schoolError)
                                    } else {
                                        val professionalProfileRequest = ProfessionalProfileRequest(
                                            udiseCode.value.toString(),
                                            stateSelectedId.intValue,
                                            districtSelectedId.intValue,
                                            blockSelectedId.intValue,
                                            schoolSelectedId.intValue,
                                            0,
                                            0,
                                            0,
                                            0,""

                                        )
                                        loginViewModel.createProfessionalProfileRepo(
                                            professionalProfileRequest, strToken
                                        )
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
fun EditUserProfile2UI() {
    val context = LocalContext.current
    val onNext: () -> Unit = {}
    val onBack: () -> Unit = {}
//    EditProfileScreen2UI(context, onNext, onBack)
}