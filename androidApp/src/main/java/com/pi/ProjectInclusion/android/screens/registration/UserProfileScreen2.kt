@file:Suppress("UNCHECKED_CAST")

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
import androidx.compose.runtime.snapshots.SnapshotStateList
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
import com.pi.ProjectInclusion.android.common_UI.MobileTextField
import com.pi.ProjectInclusion.android.common_UI.RegistrationHeader
import com.pi.ProjectInclusion.android.common_UI.SchoolListBottomSheet
import com.pi.ProjectInclusion.android.common_UI.SmallBtnUi
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.constants.BackHandler
import com.pi.ProjectInclusion.constants.ConstantVariables.ASTRICK
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION
import com.pi.ProjectInclusion.constants.CustomDialog
import com.pi.ProjectInclusion.data.model.authenticationModel.request.ProfessionalProfileRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.response.BlockListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.DistrictListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.GetLanguageListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SchoolByUdiseCodeResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SchoolListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.StateListResponse
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun EnterUserScreen2(
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
            ProfileScreen2UI(context, onBack = onBack, onNext = onNext, viewModel = viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen2UI(
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
    var selectedIndex by remember { mutableStateOf<Int?>(null) }

    val noDataMessage = stringResource(R.string.txt_oops_no_data_found)
    val invalidMobNo = stringResource(id = R.string.text_enter_no)
//  languageData[LanguageTranslationsResponse.KEY_INVALID_MOBILE_NO_ERROR].toString()
    val txtContinue = stringResource(id = R.string.text_continue)
    val tvUdise = stringResource(id = R.string.txt_udise_number)
    val schoolList = remember { mutableStateListOf<GetLanguageListResponse.LanguageResponse>() }
    var mobNo = rememberSaveable { mutableStateOf("") }
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
    var inValidMobNo by remember { mutableStateOf(false) }
    var date by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    var isBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true, confirmValueChange = { it != SheetValue.Hidden })

    var selectedSchool = stringResource(R.string.choose_option)
    val allStatesState by viewModel.allStatesResponse.collectAsStateWithLifecycle()
    val allDistrictsState by viewModel.allDistrictsResponse.collectAsStateWithLifecycle()
    val allBlocksState by viewModel.allBlocksResponse.collectAsStateWithLifecycle()
    val allSchoolsState by viewModel.allSchoolsResponse.collectAsStateWithLifecycle()
    val allUdiseState by viewModel.allUdiseCodeResponse.collectAsStateWithLifecycle()
    val professionalProfileState by viewModel.professionalProfileResponse.collectAsStateWithLifecycle()
    var allState: List<StateListResponse>? = remember { mutableStateListOf<StateListResponse>() }
    var allDistricts: List<DistrictListResponse>? =
        remember { mutableStateListOf<DistrictListResponse>() }
    var allBlocks: List<BlockListResponse>? = remember { mutableStateListOf<BlockListResponse>() }
    var allSchools = remember { mutableStateListOf<SchoolListResponse.SchoolResponse>() }
    var allUdiseDetails =
        remember { mutableStateListOf<SchoolByUdiseCodeResponse.UdiseCodeResponse>() }

    var stateSelectedId = remember { mutableIntStateOf(-1) }
    var districtSelectedId = remember { mutableIntStateOf(-1) }
    var blockSelectedId = remember { mutableIntStateOf(-1) }
    var schoolSelectedId = remember { mutableIntStateOf(-1) }

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
                    allState = allStatesState.success
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
                    allDistricts = allDistrictsState.success
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
                    allBlocks = allBlocksState.success
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
                    allSchools =
                        allSchoolsState.success!!.response as SnapshotStateList<SchoolListResponse.SchoolResponse>
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
                    allUdiseDetails =
                        allUdiseState.success!!.response as SnapshotStateList<SchoolByUdiseCodeResponse.UdiseCodeResponse>
                    println("All Udise list data :- $allUdiseState")
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
                        MobileTextField(
                            isIcon = false,
                            icon = ImageVector.vectorResource(id = R.drawable.call_on_otp),
                            colors = colors,
                            number = mobNo,
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
                                        viewModel.getAllDetailsByUdiseId(mobNo.value.toString())
                                    }
                            )

                            /*Text(
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
                                        mobNo.value.clearQuotes()
                                    }
                            )*/
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
                    allState?.map { it.name }?.toList() as List<String>,
                    onItemSelected = { selected ->
                        allState?.find { it.name == selected }?.id?.let {
                            viewModel.getAllDistrictByStateId(it)
                            stateSelectedId.intValue = it
                        }
                    },
                    modifier = Modifier.clickable {
                        logger.d("Clicked state Id :- ${allState?.size}")
                    },
                    placeholder = selectedSchool,
                    onClick = {
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
                        allState?.find { it.name == selectedSchool }?.id?.let {
                            stateSelectedId.value = it
                        }
                        "State"
                    },
                    allState?.map { it.name }?.toList() as List<String>
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
                    allDistricts?.map { it.name }?.toList() as List<String>,
                    onItemSelected = { selected ->
                        allDistricts?.find { it.name == selected }?.id?.let {
                            viewModel.getAllBlockByDistrictId(it)
                            districtSelectedId.intValue = it
                        }
                    },
                    modifier = Modifier.clickable {},
                    placeholder = selectedSchool,
                    onClick = {
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
                        allDistricts?.find { it.name == selectedSchool }?.id?.let {
                            districtSelectedId.value = it
                        }
                        "District"
                    },
                    allDistricts?.map { it.name }?.toList() as List<String>
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
                    allBlocks?.map { it.name }?.toList() as List<String>,
                    onItemSelected = { selected ->
                        allBlocks?.find { it.name == selected }?.id?.let {
                            viewModel.getAllSchoolsByBlockId(it)
                            blockSelectedId.intValue = it
                        }
                    },
                    modifier = Modifier.clickable {},
                    placeholder = selectedSchool,
                    onClick = {
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
                        allBlocks?.find { it.name == selectedSchool }?.id?.let {
                            blockSelectedId.value = it
                        }
                        "Block"
                    },
                    allBlocks?.map { it.name }?.toList() as List<String>
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
                    allSchools.map { it.name }.toList() as List<String>,
                    onItemSelected = { selected ->
                        allSchools.find { it.name == selected }?.id?.let {
                            schoolSelectedId.intValue = it
                        }
                    },
                    modifier = Modifier.clickable {},
                    placeholder = selectedSchool,
                    onClick = {
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
                        allSchools.find { it.name == selectedSchool }?.id?.let {
                            schoolSelectedId.intValue = it
                        }
                        "School"
                    },
                    allSchools.map { it.name }.toList() as List<String>
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
                        .wrapContentHeight(), horizontalAlignment = Alignment.End
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

                                        val professionalProfileRequest =
                                            ProfessionalProfileRequest(
                                                mobNo.value.toString(),
                                                stateSelectedId.value,
                                                districtSelectedId.value,
                                                blockSelectedId.value,
                                                schoolSelectedId.value
                                            )
                                        viewModel.createProfessionalProfileRepo(
                                            professionalProfileRequest,
                                            ""
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
    ProfileScreen2UI(context, onNext, onBack, viewModel)
}