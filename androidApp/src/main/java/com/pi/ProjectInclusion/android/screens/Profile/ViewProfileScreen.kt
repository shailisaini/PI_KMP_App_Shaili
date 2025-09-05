package com.pi.ProjectInclusion.android.screens.dashboardScreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.Bg_Gray1
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.BorderBlue
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.Dark_02
import com.pi.ProjectInclusion.Dark_03
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.GrayLight02
import com.pi.ProjectInclusion.GrayLight04
import com.pi.ProjectInclusion.LightText
import com.pi.ProjectInclusion.LightYellow02
import com.pi.ProjectInclusion.OrangeSubTitle
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.PrimaryBlue3
import com.pi.ProjectInclusion.RedBgColor
import com.pi.ProjectInclusion.RedText
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.AESEncryption.decrypt
import com.pi.ProjectInclusion.android.common_UI.AccountDeleteDialog
import com.pi.ProjectInclusion.android.common_UI.BtnUi
import com.pi.ProjectInclusion.android.common_UI.CameraGalleryButtons
import com.pi.ProjectInclusion.android.common_UI.CameraPermission
import com.pi.ProjectInclusion.android.common_UI.DeleteAccountPasswordDialog
import com.pi.ProjectInclusion.android.common_UI.DetailsNoImgBackgroundUi
import com.pi.ProjectInclusion.android.common_UI.LogoutDialog
import com.pi.ProjectInclusion.android.common_UI.ProfileWithProgress
import com.pi.ProjectInclusion.android.common_UI.TextWithIconOnLeft
import com.pi.ProjectInclusion.android.screens.StudentDashboardActivity
import com.pi.ProjectInclusion.android.utils.fontBold
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.fontRegular
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.BackHandler
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION
import com.pi.ProjectInclusion.constants.ConstantVariables.N_A
import com.pi.ProjectInclusion.constants.ConstantVariables.SELECTED_LANGUAGE_ID
import com.pi.ProjectInclusion.constants.ConstantVariables.TOKEN_PREF_KEY
import com.pi.ProjectInclusion.constants.ConstantVariables.USER_NAME
import com.pi.ProjectInclusion.constants.ConstantVariables.USER_TYPE_ID
import com.pi.ProjectInclusion.constants.ConstantVariables.nameChangeDescription
import com.pi.ProjectInclusion.constants.ConstantVariables.nameChangeRequestId
import com.pi.ProjectInclusion.constants.ConstantVariables.schoolChangeDescription
import com.pi.ProjectInclusion.constants.ConstantVariables.schoolChangeRequestId
import com.pi.ProjectInclusion.constants.CustomDialog
import com.pi.ProjectInclusion.data.model.authenticationModel.response.LoginApiResponse
import com.pi.ProjectInclusion.data.model.profileModel.ProfileNameChangeRequest
import com.pi.ProjectInclusion.data.model.profileModel.response.ViewProfileResponse
import com.pi.ProjectInclusion.data.remote.ApiService.Companion.PROFILE_BASE_URL
import com.pi.ProjectInclusion.ui.viewModel.DashboardViewModel
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import org.koin.androidx.compose.koinViewModel
import java.io.ByteArrayOutputStream
import kotlin.Unit

@Composable
fun ViewProfileScreen(
    viewModel: LoginViewModel,
    onNext: () -> Unit,  //EnterUserProfileScreen
    onBackLogin: () -> Unit,
    onBack: () -> Unit,
    onTrackRequest: () -> Unit
) {

    var isDialogVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current
    CustomDialog(
        isVisible = isDialogVisible,
        onDismiss = { isDialogVisible = false },
        message = stringResource(R.string.txt_loading)
    )
    var languageId = viewModel.getPrefData(SELECTED_LANGUAGE_ID)

    var encryptedUserName = viewModel.getPrefData(USER_NAME)
    val profilePic = remember { mutableStateOf("") }

    var strToken = viewModel.getPrefData(TOKEN_PREF_KEY).toString()


    var hasAllPermissions = remember { mutableStateOf(false) }
    CameraPermission(hasAllPermissions, context)
    var profileData by remember { mutableStateOf<ViewProfileResponse?>(null) }

    logger.d("Screen: " + "ViewProfileScreen()"+ strToken)

    LaunchedEffect(Unit) {
        logger.d("viewProfileUI: $languageId  .. $encryptedUserName")
        viewModel.getUserProfileViewModel(strToken, encryptedUserName)
    }

    val viewProfile by viewModel.viewUserProfileResponse.collectAsStateWithLifecycle()
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
                    profilePic.value = PROFILE_BASE_URL + profileData!!.response?.profilepic

//                    context.toast(sendOtpState.success!!.response!!.message.toString())
                } else {
                    context.toast(viewProfile.success!!.message.toString())
                }
                isDialogVisible = false
            }
        }
    }

    BackHandler {
        onBack()
    }

    Surface(
        modifier = Modifier.fillMaxWidth(), color = White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp)
                .background(color = White),
            verticalArrangement = Arrangement.Top
        ) {
            profileData?.let {
                ProfileViewUI(
                    context, onNext = onNext, onBack = onBack, onBackLogin = onBackLogin,
                    onTrackRequest = onTrackRequest, profileData = it, viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun ProfileViewUI(
    context: Context,
    onBack: () -> Unit,
    onBackLogin: () -> Unit,
    onNext: () -> Unit,
    onTrackRequest: () -> Unit,
    profileData: ViewProfileResponse,
    viewModel: LoginViewModel?
) {

    val decryptUserName = decrypt(profileData.response?.username.toString().trim())
    val scrollState = rememberScrollState()
    var showSheetMenu by remember { mutableStateOf(false) }
    var isChangeRequestBottomSheet by remember { mutableStateOf(false) }
    var userTypeId = viewModel?.getPrefData(USER_TYPE_ID)

    if (showSheetMenu) {
        ProfileBottomSheetMenu(viewModel = viewModel, onBackLogin = onBackLogin) {
            showSheetMenu = false
        }
    }

    if (isChangeRequestBottomSheet) {
        ChangeRequestSheet(viewModel = viewModel, onTrackRequest = onTrackRequest) {
            isChangeRequestBottomSheet = false
        }
    }

    DetailsNoImgBackgroundUi(
        backgroundColor = White,
        textColor = Black,
        pageTitle = stringResource(R.string.txt_profile),
        moreInfoIcon = painterResource(id = R.drawable.vertical_dot),
        isShowBackButton = true,
        isShowMoreInfo = true,
        onBackButtonClick = {
            onBack()
        },
        onMoreInfoClick = {
            showSheetMenu = true
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
                    .background(color = Bg_Gray1)
                    .verticalScroll(scrollState)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ProfileWithProgress(
                        image = PROFILE_BASE_URL + profileData.response?.profilepic,
                        progress = 0.8f
                    )

                    Text(
                        text = decryptUserName,
                        fontSize = 19.sp,
                        fontFamily = fontBold,
                        color = Black,
                        modifier = Modifier
                            .padding(top = 10.dp)
                    )

                    Text(
                        if (userTypeId == "7") {
                            stringResource(R.string.txt_special_educator)
                        } else if (userTypeId == "8") {
                            stringResource(R.string.txt_Professional)
                        } else {
                            // teacher
                            stringResource(R.string.txt_teacher)
                        },
                        textAlign = TextAlign.Center,
                        fontSize = 13.sp,
                        fontFamily = fontMedium,
                        color = OrangeSubTitle,
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                    )

                    Button(
                        onClick = {
                            onNext()
                        }, modifier = Modifier
                            .wrapContentSize()
                            .clip(RoundedCornerShape(4.dp)),

                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = White,
                            contentColor = BorderBlue
                        ),
                        border = BorderStroke(1.dp, color = BorderBlue)
                    ) {
                        TextWithIconOnLeft(
                            text = stringResource(R.string.edit_profile),
                            icon = ImageVector.vectorResource(id = R.drawable.ic_edit_profile),
                            textColor = BorderBlue,
                            iconColor = Color.Unspecified,
                            onClick = {
                                onNext()
                            })
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = if (isSystemInDarkTheme()) {
                                Dark_01
                            } else {
                                White
                            }, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        ),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp)
                                .border(
                                    width = 1.dp, color = if (isSystemInDarkTheme()) {
                                        Dark_03
                                    } else {
                                        GrayLight02
                                    }, shape = RoundedCornerShape(8.dp)
                                ),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp)
                            ) {
                                TextWithIconOnLeft(
                                    moreSpace = true,
                                    text = profileData.response?.dob ?: N_A,
                                    icon = ImageVector.vectorResource(id = R.drawable.calendar_blue),
                                    textColor = PrimaryBlue,
                                    iconColor = Color.Unspecified,
                                    onClick = {

                                    })
                                Spacer(modifier = Modifier.height(4.dp))
                                TextWithIconOnLeft(
                                    moreSpace = true,
                                    text = profileData.response?.mobile ?: N_A,
                                    icon = ImageVector.vectorResource(id = R.drawable.ic_call_blue),
                                    textColor = PrimaryBlue,
                                    iconColor = Color.Unspecified,
                                    onClick = {

                                    })
                                Spacer(modifier = Modifier.height(4.dp))
                                TextWithIconOnLeft(
                                    moreSpace = true,
                                    text = profileData.response?.whatsapp ?: N_A,
                                    icon = ImageVector.vectorResource(id = R.drawable.ic_whatsapp_blue),
                                    textColor = PrimaryBlue,
                                    iconColor = Color.Unspecified,
                                    onClick = {

                                    })
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp)
                                .background(color = PrimaryBlue3)
                                .border(
                                    width = 0.3.dp, color = if (isSystemInDarkTheme()) {
                                        Dark_03
                                    } else {
                                        Black
                                    }, shape = RoundedCornerShape(8.dp)
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                stringResource(R.string.txt_complete_email),
                                textAlign = TextAlign.Start,
                                fontSize = 13.sp,
                                fontFamily = fontRegular,
                                color = Black,
                                modifier = Modifier
                                    .padding(15.dp)
                                    .weight(1f)
                            )
                            Text(
                                stringResource(R.string.text_add),
                                textAlign = TextAlign.End,
                                fontSize = 15.sp,
                                fontFamily = fontMedium,
                                color = PrimaryBlue,
                                modifier = Modifier
                                    .padding(15.dp)
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp)
                                .border(
                                    width = 1.dp, color = if (isSystemInDarkTheme()) {
                                        Dark_03
                                    } else {
                                        GrayLight02
                                    }, shape = RoundedCornerShape(8.dp)
                                ),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    stringResource(R.string.txt_udise),
                                    textAlign = TextAlign.Start,
                                    fontSize = 15.sp,
                                    fontFamily = fontMedium,
                                    color = LightText,
                                    modifier = Modifier
                                        .padding(end = 15.dp)
                                        .weight(1f)
                                )
                                Text(
                                    profileData.response?.udicecode ?: N_A,
                                    textAlign = TextAlign.End,
                                    fontSize = 14.sp,
                                    fontFamily = fontMedium,
                                    color = PrimaryBlue
                                )
                            }
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp)
                                .border(
                                    width = 1.dp, color = if (isSystemInDarkTheme()) {
                                        Dark_03
                                    } else {
                                        GrayLight02
                                    }, shape = RoundedCornerShape(8.dp)
                                ),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 15.dp,
                                        end = 15.dp,
                                        top = 15.dp,
                                        bottom = 5.dp
                                    ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    stringResource(R.string.txt_state),
                                    textAlign = TextAlign.Start,
                                    fontSize = 15.sp,
                                    fontFamily = fontMedium,
                                    color = LightText,
                                    modifier = Modifier
                                        .padding(end = 15.dp)
                                        .weight(1f)
                                )
                                Text(
                                    profileData.response?.stateId ?: N_A,
                                    textAlign = TextAlign.End,
                                    fontSize = 15.sp,
                                    fontFamily = fontMedium,
                                    color = PrimaryBlue
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    stringResource(R.string.txt_district),
                                    textAlign = TextAlign.Start,
                                    fontSize = 15.sp,
                                    fontFamily = fontMedium,
                                    color = LightText,
                                    modifier = Modifier
                                        .padding(end = 15.dp)
                                        .weight(1f)
                                )
                                Text(
                                    profileData.response?.districtId ?: N_A,
                                    textAlign = TextAlign.End,
                                    fontSize = 15.sp,
                                    fontFamily = fontMedium,
                                    color = PrimaryBlue
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    stringResource(R.string.txt_block_zone),
                                    textAlign = TextAlign.Start,
                                    fontSize = 15.sp,
                                    fontFamily = fontMedium,
                                    color = LightText,
                                    modifier = Modifier
                                        .padding(end = 15.dp)
                                        .weight(1f)
                                )
                                Text(
                                    profileData.response?.blockId ?: N_A,
                                    textAlign = TextAlign.End,
                                    fontSize = 15.sp,
                                    fontFamily = fontMedium,
                                    color = PrimaryBlue
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 15.dp,
                                        end = 15.dp,
                                        top = 5.dp,
                                        bottom = 15.dp
                                    ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    stringResource(R.string.txt_school_name),
                                    textAlign = TextAlign.Start,
                                    fontSize = 15.sp,
                                    fontFamily = fontMedium,
                                    color = LightText,
                                    modifier = Modifier
                                        .padding(end = 15.dp)
                                        .weight(1f)
                                )
                                Text(
                                    profileData.response?.schoolId ?: N_A,
                                    textAlign = TextAlign.End,
                                    fontSize = 15.sp,
                                    fontFamily = fontMedium,
                                    color = PrimaryBlue
                                )
                            }

                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp)
                                .border(
                                    width = 1.dp, color = if (isSystemInDarkTheme()) {
                                        Dark_03
                                    } else {
                                        GrayLight02
                                    }, shape = RoundedCornerShape(8.dp)
                                ),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp)
                            ) {
                                Text(
                                    stringResource(R.string.txt_update_name_school),
                                    textAlign = TextAlign.Start,
                                    fontSize = 13.sp,
                                    fontFamily = fontRegular,
                                    color = Black,
                                    modifier = Modifier
                                        .padding(5.dp)
                                )
                                Button(
                                    onClick = {
                                        isChangeRequestBottomSheet = true
                                    }, modifier = Modifier
                                        .wrapContentSize()
                                        .clip(RoundedCornerShape(4.dp)),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = White,
                                        contentColor = BorderBlue
                                    ),
                                    border = BorderStroke(1.dp, color = BorderBlue)
                                ) {
                                    Text(
                                        stringResource(R.string.txt_change_request),
                                        textAlign = TextAlign.Start,
                                        fontSize = 13.sp,
                                        fontFamily = fontMedium,
                                        color = BorderBlue
                                    )
                                }
                            }
                        }
                    }
                }
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileBottomSheetMenu(
    viewModel: LoginViewModel?,
    onBackLogin: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    val sheetState = rememberModalBottomSheetState()

    var logOutSheet by remember { mutableStateOf(false) }
    // show logout sheet while click on logout
    if (logOutSheet) {
        LogoutDialog(
            onDismiss = { logOutSheet = false },
            onClick = {
                logOutSheet = false
                onBackLogin() // move to Login Screen
            }
        )
    }
    var confirmDeleteState by remember { mutableStateOf(false) }
    var deleteAccountDialog by remember { mutableStateOf(false) }
    // show logout sheet while click on logout
    if (confirmDeleteState) {
        DeleteAccountPasswordDialog(
            onSubmit = {
                confirmDeleteState = false
                deleteAccountDialog = true
            },
            onDismiss = { confirmDeleteState = false })
    }
    if (deleteAccountDialog) {
        AccountDeleteDialog(
            onDismiss = { deleteAccountDialog = false },
            onClick = {
                deleteAccountDialog = false
                onBackLogin()
            }  // move to Login Screen
        )
    }

    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, bottom = 20.dp)
                .fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.padding(start = 5.dp),
                verticalAlignment = Alignment.CenterVertically // Vertically center items in the Row
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_track),
                    contentDescription = IMG_DESCRIPTION,
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color.Unspecified),
                )

                TextButton(onClick = {

                }) {
                    Text(
                        text = stringResource(R.string.txt_track_request),
                        style = MaterialTheme.typography.headlineLarge.copy(
                            color = Black,
                            fontSize = 16.sp,
                            fontFamily = fontMedium
                        )
                    )
                }
            }

            Row(
                modifier = Modifier.padding(start = 5.dp),
                verticalAlignment = Alignment.CenterVertically // Vertically center items in the Row
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = IMG_DESCRIPTION,
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color.Unspecified),
                )

                TextButton(onClick = {
                    confirmDeleteState = true
                }) {
                    Text(
                        text = stringResource(R.string.txt_deactivate),
                        style = MaterialTheme.typography.headlineLarge.copy(
                            color = Black,
                            fontSize = 16.sp,
                            fontFamily = fontMedium
                        )
                    )
                }
            }

            Row(
                modifier = Modifier.padding(start = 5.dp),
                verticalAlignment = Alignment.CenterVertically // Vertically center items in the Row
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_logout),
                    contentDescription = IMG_DESCRIPTION,
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color.Unspecified),
                )

                TextButton(onClick = {
                    logOutSheet = true
                }) {
                    Text(
                        text = stringResource(R.string.txt_logout),
                        style = MaterialTheme.typography.headlineLarge.copy(
                            color = Black,
                            fontSize = 16.sp,
                            fontFamily = fontMedium
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeRequestSheet(
    viewModel: LoginViewModel?,
    onTrackRequest: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState()
    var isSelectedSchool by remember { mutableStateOf(false) }
    var isSelectedName by remember { mutableStateOf(false) }
    var txtContinue = stringResource(id = R.string.text_continue)
    var uploadShowDialog by remember { mutableStateOf(false) }

    var isDialogVisible = remember { mutableStateOf(false) }
    var isRequestedAlready by remember { mutableStateOf(false) }
    var requestTypeId by remember { mutableStateOf("1") }
    var strToken = viewModel?.getPrefData(TOKEN_PREF_KEY).toString()
    val dashboardViewModel: DashboardViewModel = koinViewModel()

    CustomDialog(
        isVisible = isDialogVisible.value,
        onDismiss = { isDialogVisible.value = false },
        message = stringResource(R.string.txt_loading)
    )

    val requestTrackState by dashboardViewModel.getTrackRequestResponse.collectAsStateWithLifecycle()
    LaunchedEffect(requestTrackState) {
        when {
            requestTrackState.isLoading -> {
                isDialogVisible.value = true
            }

            requestTrackState.error.isNotEmpty() -> {
                logger.d("requestTrackState: ${requestTrackState.success}")
                isDialogVisible.value = false
                isRequestedAlready = false
                uploadShowDialog = false
            }

            requestTrackState.success != null -> {
                logger.d("requestTrackState: ${requestTrackState.success}")
                if (requestTrackState.success!!.status == true) {
                    if (requestTrackState.success!!.response?.already_requested == true) {
                        isRequestedAlready = true
                    } else {
                        uploadShowDialog = true
                    }
                }
                isDialogVisible.value = false
            }
        }
    }

    if (isRequestedAlready) {
        RequestSubmittedDialog(
            stringResource(R.string.txt_request_submitted_already),
            requestTrackState.success!!.response?.message?: stringResource(R.string.txt_submit_review),
            onTrackRequest = onTrackRequest,
        ) {
            isRequestedAlready = false
        }
    }

    if (uploadShowDialog) {
        if (isSelectedSchool) {
            UploadIdDialog(
                strToken,
                isDialogVisible,
                dashboardViewModel = dashboardViewModel,
                viewModel, schoolChangeRequestId, schoolChangeDescription,
                stringResource(R.string.txt_upload_clear_id_school), onTrackRequest = onTrackRequest
            ) {
                uploadShowDialog = false
            }
        } else {
            // name change Track
            NameRequestDialog(
                strToken = strToken,
                isDialogVisible = isDialogVisible,
                dashboardViewModel = dashboardViewModel,
                viewModel = viewModel,
                title = stringResource(R.string.txt_name_change),
                onTrackRequest = onTrackRequest
            ) {
                uploadShowDialog = false
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, bottom = 20.dp)
                .fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.txt_change_request),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = Black,
                        fontSize = 18.sp,
                        fontFamily = fontBold
                    )
                )
                Text(
                    text = stringResource(R.string.txt_change_desc),
                    color = Gray,
                    fontSize = 15.sp,
                    fontFamily = fontRegular
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, bottom = 5.dp, top = 10.dp)
                    .clickable {
                        isSelectedSchool = true
                        requestTypeId = schoolChangeRequestId
                        isSelectedName = false
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    TextWithIconOnLeft(
                        text = stringResource(R.string.txt_school_change),
                        icon = ImageVector.vectorResource(id = R.drawable.ic_school_request),
                        textColor = Black,
                        iconColor = Color.Unspecified,
                        textSize = 17.sp,
                    )
                }

                RadioButton(
                    selected = isSelectedSchool,
                    onClick = {
                        isSelectedSchool = true
                        requestTypeId = schoolChangeRequestId
                        isSelectedName = false
                    },
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, bottom = 10.dp)
                    .clickable {
                        isSelectedSchool = false
                        requestTypeId = nameChangeRequestId
                        isSelectedName = true
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    TextWithIconOnLeft(
                        text = stringResource(R.string.txt_name_change),
                        icon = ImageVector.vectorResource(id = R.drawable.ic_name_request),
                        textColor = Black,
                        iconColor = Color.Unspecified,
                        textSize = 17.sp
                    )
                }

                RadioButton(
                    selected = isSelectedName,
                    onClick = {
                        isSelectedSchool = false
                        requestTypeId = nameChangeRequestId
                        isSelectedName = true
                    }
                )
            }

            BtnUi(
                enabled = isSelectedSchool || isSelectedName,
                title = txtContinue,
                onClick = {
                    if (isSelectedSchool || isSelectedName) {
                        dashboardViewModel.getTrackChangeRequest(strToken, requestTypeId)
                    }
                },
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun UploadIdDialog(
    strToken: String = "",
    isDialogVisible: MutableState<Boolean>,
    dashboardViewModel: DashboardViewModel,
    viewModel: LoginViewModel?,
    requestId: String = "",
    requestDescription: String = "",
    subText: String = "",
    onTrackRequest: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    val context = LocalContext.current
    val dashboardViewModel: DashboardViewModel = koinViewModel()
    val requestChangeState by dashboardViewModel.getChangeRequestResponse.collectAsStateWithLifecycle()

    var emptyDoc = stringResource(R.string.document_empty_error)
    var docError by remember { mutableStateOf(emptyDoc) }

    var byteArray: ByteArray? = null
    var fileName: String? = null
    lateinit var bitmap: Bitmap


    // App version
    var appPackageName: String = ""
    var appVersion: String = ""

    appPackageName = context.packageName
    val pInfo: PackageInfo =
        context.packageManager.getPackageInfo(appPackageName, 0)
    appVersion = pInfo.versionName.toString()

    var isSubmitted by remember { mutableStateOf(false) }

    if (isSubmitted) {
        RequestSubmittedDialog(
            stringResource(R.string.txt_request_submitted),
            stringResource(R.string.txt_submit_review),
            onTrackRequest = onTrackRequest,
        ) {
            isSubmitted = false
        }
    }

    LaunchedEffect(requestChangeState) {
        when {
            requestChangeState.isLoading -> {
                isDialogVisible.value = true
            }

            requestChangeState.error.isNotEmpty() -> {
                logger.d("requestChangeState: ${requestChangeState.success}")
                isDialogVisible.value = false
            }

            requestChangeState.success != null -> {
                logger.d("requestChangeState: ${requestChangeState.success}")
                if (requestChangeState.success!!.status == true) {
                    isSubmitted = true
                    context.toast(requestChangeState.success!!.response.toString())
                } else {
                    context.toast(requestChangeState.success!!.message.toString())
                }
                isDialogVisible.value = false
            }
        }
    }

    Dialog(onDismissRequest = { onDismiss() }) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            var selectedUri = remember { mutableStateOf<Uri?>(null) }

            if (selectedUri.value != null) {
                LaunchedEffect(Unit) {
                    try {
                        val uri = selectedUri.value!!
                        val inputStream = context.contentResolver.openInputStream(uri)

                        bitmap = BitmapFactory.decodeStream(inputStream)
                        inputStream?.close()

                        // Convert to byteArray
                        val stream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream)
                        byteArray = stream.toByteArray()

                        // File name (if available from Uri)
                        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                            if (cursor.moveToFirst()) {
                                fileName = cursor.getString(nameIndex)
                            }
                        }
                        logger.d("Picked image fileName = $fileName, byteArray size = ${byteArray.size}")

                    } catch (e: Exception) {
                        logger.d("FileNotConvertedException: ${e.message}")
                    }
                }
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isSystemInDarkTheme()) Dark_02 else White)
                    .fillMaxWidth()

            ) {
                Column(
                    modifier = Modifier
                        .padding(bottom = 16.dp, start = 8.dp, end = 8.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.txt_upload_id),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 16.dp, start = 8.dp, end = 8.dp),
                        fontFamily = fontBold,
                        fontSize = 18.sp,
                        color = Black,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = subText,
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(start = 8.dp, end = 8.dp),
                        fontFamily = fontRegular,
                        fontSize = 14.sp,
                        color = Gray,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .fillMaxWidth()
                            .background(color = LightYellow02),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.txt_school_id_front),
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(8.dp),
                            fontFamily = fontRegular,
                            fontSize = 13.sp,
                            color = Gray,
                            textAlign = TextAlign.Center
                        )
                    }

                    Box(
                        modifier = Modifier
                            .padding(vertical = 15.dp, horizontal = 5.dp)
                            .fillMaxWidth()
                            .drawDashedBorder(
                                color = LightText,
                                strokeWidth = 1.dp,
                                dashLength = 4.dp,
                                gapLength = 5.dp,
                                cornerRadius = 12.dp
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (selectedUri.value == null) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(vertical = 10.dp)
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_upload_icon),
                                    contentDescription = IMG_DESCRIPTION,
                                    modifier = Modifier.size(60.dp),
                                    tint = Color.Unspecified
                                )

                                Text(
                                    text = stringResource(R.string.txt_choose_to_upload),
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .padding(top = 16.dp, start = 8.dp, end = 8.dp),
                                    fontFamily = fontMedium,
                                    fontSize = 17.sp,
                                    color = Black,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = stringResource(R.string.txt_choose_supported_format),
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .padding(top = 3.dp, start = 8.dp, end = 8.dp),
                                    fontFamily = fontMedium,
                                    fontSize = 13.sp,
                                    color = GrayLight04,
                                    textAlign = TextAlign.Center
                                )

                                // gallery & camera button
                                CameraGalleryButtons { selectedImageUri ->
                                    if (selectedImageUri != null) {
//                                        Logger.d("SelectedURI: $selectedImageUri")
                                        selectedUri.value = selectedImageUri
                                        // Use the URI as needed (e.g., upload, display, etc.)
                                    } else {
//                                        Logger.d("SelectedURI:$selectedImageUri")
                                    }
                                }
                            }
                        } else {
                            Box(
                                modifier = Modifier.padding(10.dp) // Add padding to the outer box
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(vertical = 10.dp)
                                ) {
                                    Image(
                                        painter =
                                            rememberAsyncImagePainter(
                                                ImageRequest.Builder(LocalContext.current)
                                                    .data(selectedUri.value)
                                                    .placeholder(R.drawable.profile_user_icon)
                                                    .crossfade(true) // Optional: Add a fade transition
                                                    .build()
                                            ),
                                        contentDescription = IMG_DESCRIPTION,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 10.dp)
                                            .height(200.dp)
                                    )
                                    TextWithIconOnLeft(
                                        text = stringResource(R.string.txt_remove),
                                        icon = ImageVector.vectorResource(id = R.drawable.ic_delete),
                                        textColor = BorderBlue,
                                        iconColor = BorderBlue,
                                        onClick = {
                                            selectedUri.value = null
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .padding(vertical = 15.dp, horizontal = 10.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = { onDismiss() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(end = 8.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = White,
                                contentColor = BorderBlue
                            ),
                            border = BorderStroke(1.dp, BorderBlue)
                        ) {
                            Text(
                                text = stringResource(R.string.txt_cancel),
                                fontSize = 16.sp,
                                fontFamily = fontMedium,
                                color = BorderBlue
                            )
                        }
                        Button(
                            onClick = {
                                if (selectedUri.value != null) {
                                    isDialogVisible.value = true
                                    dashboardViewModel.getProfileChangeRequest(
                                        ProfileNameChangeRequest(requestId, appVersion, requestDescription),
                                        strToken.toString(), byteArray,
                                        fileName
                                    )
                                } else {
                                    context.toast(docError)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(end = 8.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            shape = RoundedCornerShape(8.dp),
                            colors = if (selectedUri.value != null) {
                                ButtonDefaults.buttonColors(
                                    containerColor = PrimaryBlue,
                                    contentColor = com.pi.ProjectInclusion.White
                                )
                            } else {
                                ButtonDefaults.buttonColors(
                                    containerColor = Gray,
                                    contentColor = White
                                )
                            },
                            enabled = selectedUri.value != null
                        ) {
                            Text(
                                text = stringResource(R.string.txt_submit),
                                fontSize = 16.sp,
                                fontFamily = fontMedium,
                                color = White
                            )
                        }
                    }
                }
            }
        }
    }
}

fun Modifier.drawDashedBorder(
    color: Color,
    strokeWidth: Dp,
    dashLength: Dp,
    gapLength: Dp,
    cornerRadius: Dp = 0.dp
) = this.then(
    Modifier.drawBehind {
        val stroke = Stroke(
            width = strokeWidth.toPx(),
            pathEffect = PathEffect.dashPathEffect(
                floatArrayOf(dashLength.toPx(), gapLength.toPx()), 0f
            )
        )

        val corner = cornerRadius.toPx()

        drawRoundRect(
            color = color,
            topLeft = Offset.Zero,
            size = size,
            cornerRadius = CornerRadius(corner, corner),
            style = stroke
        )
    }
)

@SuppressLint("UnusedBoxWithConstraintsScope")
@Preview
@Composable
fun RequestSubmittedDialog(
    title: String = "",
    subText: String = "",
    onTrackRequest: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    val context = LocalContext.current
    var strTitle = stringResource(R.string.txt_request_submitted_already)

    Dialog(onDismissRequest = { }) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isSystemInDarkTheme()) Dark_02 else White)
                    .fillMaxWidth()

            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 15.dp, horizontal = 8.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_request_submitted),
                        contentDescription = IMG_DESCRIPTION,
                        modifier = Modifier.size(60.dp),
                        tint = Color.Unspecified
                    )

                    Text(
                        text = title,
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 16.dp, start = 8.dp, end = 8.dp),
                        fontFamily = fontBold,
                        fontSize = 19.sp,
                        color = Black,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = subText,
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 3.dp, start = 8.dp, end = 8.dp),
                        fontFamily = fontRegular,
                        fontSize = 15.sp,
                        color = GrayLight04,
                        textAlign = TextAlign.Center
                    )
                    Row(
                        modifier = Modifier
                            .padding(vertical = 15.dp, horizontal = 5.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,

                        ) {
                        Button(
                            onClick = {
                                onDismiss()
                                if (title != strTitle){
                                    context.startActivity(
                                        Intent(
                                            context,
                                            StudentDashboardActivity::class.java
                                        )
                                    )
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(end = 8.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = White,
                                contentColor = BorderBlue
                            ),
                            border = BorderStroke(1.dp, BorderBlue)
                        ) {
                            Text(
                                text = stringResource(R.string.txt_close),
                                fontSize = 16.sp,
                                fontFamily = fontMedium,
                                color = BorderBlue
                            )
                        }
                        // check Status
                        Button(
                            onClick = {
                                onTrackRequest()
                                onDismiss()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(end = 8.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PrimaryBlue,
                                contentColor = White
                            ),
                            border = BorderStroke(1.dp, BorderBlue)
                        ) {
                            Text(
                                text = stringResource(R.string.txt_check_status),
                                fontSize = 16.sp,
                                fontFamily = fontMedium,
                                color = White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NameRequestDialog(
    isDialogVisible: MutableState<Boolean>,
    dashboardViewModel: DashboardViewModel,
    viewModel: LoginViewModel?,
    title: String = "",
    strToken: String = "",
    onTrackRequest: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    var uploadShowDialog by remember { mutableStateOf(false) }
    if (uploadShowDialog) {
        UploadIdDialog(
            strToken = strToken,
            isDialogVisible,
            dashboardViewModel = dashboardViewModel,
            viewModel,
            nameChangeRequestId,
            nameChangeDescription,
            stringResource(R.string.txt_upload_clear_id_school),
            onTrackRequest = onTrackRequest
        ) {
            uploadShowDialog = false
        }
    }

    Dialog(onDismissRequest = { onDismiss() }) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(if (isSystemInDarkTheme()) Dark_02 else White)
                .fillMaxWidth()

        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 15.dp, horizontal = 8.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 16.dp, start = 8.dp, end = 8.dp),
                    fontFamily = fontBold,
                    fontSize = 19.sp,
                    color = Black,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.txt_name_change_desc),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 3.dp, start = 8.dp, end = 8.dp),
                    fontFamily = fontRegular,
                    fontSize = 15.sp,
                    color = Gray,
                    textAlign = TextAlign.Center
                )

                Column(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth()
                        .background(color = RedBgColor)
                        .clip(RoundedCornerShape(8.dp)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.txt_request_note),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(5.dp),
                        fontFamily = fontRegular,
                        fontSize = 12.sp,
                        color = RedText,
                        textAlign = TextAlign.Center
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(vertical = 15.dp, horizontal = 5.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            onDismiss()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(end = 8.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = White,
                            contentColor = PrimaryBlue
                        ),
                        border = BorderStroke(1.dp, BorderBlue)
                    ) {
                        Text(
                            text = stringResource(R.string.txt_close),
                            fontSize = 16.sp,
                            fontFamily = fontMedium,
                            color = BorderBlue,
                        )
                    }

                    // check Status
                    Button(
                        onClick = {
                            uploadShowDialog = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(end = 8.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryBlue,
                            contentColor = White
                        ),
                        border = BorderStroke(1.dp, BorderBlue)
                    ) {
                        Text(
                            text = stringResource(R.string.txt_yes_proceed),
                            fontSize = 16.sp,
                            fontFamily = fontMedium,
                            color = White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}