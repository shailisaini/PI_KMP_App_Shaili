@file:OptIn(ExperimentalMaterial3Api::class)

package com.pi.ProjectInclusion.android.screens.dashboardScreen

import android.Manifest
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kmptemplate.logger.AppLoggerImpl
import com.pi.ProjectInclusion.Bg_Gray1
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.DarkBlue
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.Dark_03
import com.pi.ProjectInclusion.GrayLight02
import com.pi.ProjectInclusion.LightText
import com.pi.ProjectInclusion.OrangeSubTitle
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.PrimaryBlue3
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.BackButtonPress
import com.pi.ProjectInclusion.android.common_UI.DetailsNoImgBackgroundUi
import com.pi.ProjectInclusion.android.common_UI.ProfileWithProgress
import com.pi.ProjectInclusion.android.common_UI.TextWithIconOnLeft
import com.pi.ProjectInclusion.android.navigation.AppRoute
import com.pi.ProjectInclusion.android.utils.fontBold
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.fontRegular
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION
import com.pi.ProjectInclusion.constants.CustomDialog
import com.pi.ProjectInclusion.data.model.GetLanguageListResponse

@Composable
fun ViewProfileScreen(navController: NavHostController) {

    var isDialogVisible by remember { mutableStateOf(false) }

    val logger = AppLoggerImpl()
    val query by rememberSaveable {
        mutableStateOf("")
    }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    val scrollState = rememberLazyGridState()
    val context = LocalContext.current
    val languageData = remember { mutableStateListOf<GetLanguageListResponse.Data.Result>() }
    CustomDialog(
        isVisible = isDialogVisible,
        onDismiss = { isDialogVisible = false },
        message = "Loading your data..."
    )
    val selectedLanguage = remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()
    var hasCameraPermission by remember { mutableStateOf(false) }

    var showDialog by remember { mutableStateOf(false) }
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
        }
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
            ProfileViewUI(context, navController)
        }
    }
}

@Composable
fun ProfileViewUI(
    context: Context,
    navController: NavHostController
) {
    val scrollState = rememberScrollState()
    var isApiResponded by remember { mutableStateOf(false) }
    val internetMessage by remember { mutableStateOf("") }

    var isDialogVisible by remember { mutableStateOf(false) }
    val noDataMessage = stringResource(R.string.txt_oops_no_data_found)
    val invalidMobNo = stringResource(id = R.string.text_enter_no)
//  languageData[LanguageTranslationsResponse.KEY_INVALID_MOBILE_NO_ERROR].toString()
    val txtContinue = stringResource(id = R.string.text_continue)
    val tvMobNo = stringResource(id = R.string.text_mobile_no_user)

    var mobNo = rememberSaveable { mutableStateOf("") }
    var firstName = rememberSaveable { mutableStateOf("") }
    val textNameEg = stringResource(R.string.txt_eg_first_name)
    var showSheetMenu by remember { mutableStateOf(false) }

    if (showSheetMenu) {
        ProfileBottomSheetMenu() {
            showSheetMenu = false
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
            BackButtonPress(navController, AppRoute.InterventionAcceptLevel.route)
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
                    ProfileWithProgress(image = "", progress = 0.8f)

                    Text(
                        text = textNameEg,
                        fontSize = 19.sp,
                        fontFamily = fontBold,
                        color = Black,
                        modifier = Modifier
                            .padding(top = 10.dp)
                    )

                    Text(
                        stringResource(R.string.txt_teacher),
                        textAlign = TextAlign.Center,
                        fontSize = 13.sp,
                        fontFamily = fontMedium,
                        color = OrangeSubTitle,
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                    )

                    Button(
                        onClick = {
                            navController.navigate(AppRoute.EditProfileScreen.route)
                        }, modifier = Modifier
                            .wrapContentSize()
                            .clip(RoundedCornerShape(4.dp)),

                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = White,
                            contentColor = PrimaryBlue
                        ),
                        border = BorderStroke(1.dp, color = PrimaryBlue)
                    ) {
                        TextWithIconOnLeft(
                            text = stringResource(R.string.edit_profile),
                            icon = ImageVector.vectorResource(id = R.drawable.ic_edit_profile),
                            textColor = Black,
                            iconColor = Color.Unspecified,
                            onClick = {
                                navController.navigate(AppRoute.EditProfileScreen.route)
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
                                    text = stringResource(R.string.txt_eg_whatsapp_name),
                                    icon = ImageVector.vectorResource(id = R.drawable.calendar_blue),
                                    textColor = DarkBlue,
                                    iconColor = Color.Unspecified,
                                    onClick = {

                                    })
                                Spacer(modifier = Modifier.height(4.dp))
                                TextWithIconOnLeft(
                                    moreSpace = true,
                                    text = stringResource(R.string.txt_eg_whatsapp_name),
                                    icon = ImageVector.vectorResource(id = R.drawable.ic_call_blue),
                                    textColor = DarkBlue,
                                    iconColor = Color.Unspecified,
                                    onClick = {

                                    })
                                Spacer(modifier = Modifier.height(4.dp))
                                TextWithIconOnLeft(
                                    moreSpace = true,
                                    text = stringResource(R.string.txt_eg_whatsapp_name),
                                    icon = ImageVector.vectorResource(id = R.drawable.ic_whatsapp_blue),
                                    textColor = DarkBlue,
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
                                    stringResource(R.string.text_add),
                                    textAlign = TextAlign.End,
                                    fontSize = 14.sp,
                                    fontFamily = fontMedium,
                                    color = DarkBlue
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
                                    stringResource(R.string.text_add),
                                    textAlign = TextAlign.End,
                                    fontSize = 15.sp,
                                    fontFamily = fontMedium,
                                    color = DarkBlue
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
                                    stringResource(R.string.text_add),
                                    textAlign = TextAlign.End,
                                    fontSize = 15.sp,
                                    fontFamily = fontMedium,
                                    color = DarkBlue
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
                                    stringResource(R.string.text_add),
                                    textAlign = TextAlign.End,
                                    fontSize = 15.sp,
                                    fontFamily = fontMedium,
                                    color = DarkBlue
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
                                    stringResource(R.string.text_add),
                                    textAlign = TextAlign.End,
                                    fontSize = 15.sp,
                                    fontFamily = fontMedium,
                                    color = DarkBlue
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
                                    onClick = { }, modifier = Modifier
                                        .wrapContentSize()
                                        .clip(RoundedCornerShape(4.dp)),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = White,
                                        contentColor = PrimaryBlue
                                    ),
                                    border = BorderStroke(1.dp, color = PrimaryBlue)
                                ) {
                                    Text(
                                        stringResource(R.string.txt_change_request),
                                        textAlign = TextAlign.Start,
                                        fontSize = 13.sp,
                                        fontFamily = fontMedium,
                                        color = PrimaryBlue
                                    )
                                }
                            }
                        }
                    }
                }
            }
        })
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ProfileUI() {
    val navController = rememberNavController()
    val context = LocalContext.current
    ProfileViewUI(context, navController)
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileBottomSheetMenu(
    onDismiss: () -> Unit = {},
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val colors = MaterialTheme.colorScheme

    var logOutSheet by remember { mutableStateOf(false) }
    // show logout sheet while click on logout
    if (logOutSheet) {
//        LogoutBottomSheetMenu(viewModel, languageData, colors) {
//            logOutSheet = false
//        }
    }
    var confirmDeleteState by remember { mutableStateOf(false) }
    // show logout sheet while click on logout
    if (confirmDeleteState) {
        /* ConfirmationDeleteAccountBottomSheet(viewModel, languageData, isDialogVisible, colors) {
             confirmDeleteState = false
         }*/
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
                    confirmDeleteState = true
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
                    confirmDeleteState = true
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