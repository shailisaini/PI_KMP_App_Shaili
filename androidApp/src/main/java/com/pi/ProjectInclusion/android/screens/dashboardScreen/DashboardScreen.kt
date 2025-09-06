package com.pi.ProjectInclusion.android.screens.dashboardScreen

import android.Manifest
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kmptemplate.logger.AppLoggerImpl
import com.pi.ProjectInclusion.Bg1_Gray
import com.pi.ProjectInclusion.Bg_Gray
import com.pi.ProjectInclusion.Bg_Gray1
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.DashboardCard1
import com.pi.ProjectInclusion.DashboardCard2
import com.pi.ProjectInclusion.DashboardCard3
import com.pi.ProjectInclusion.DashboardCard4
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.GreenGradient1
import com.pi.ProjectInclusion.GreenGradient2
import com.pi.ProjectInclusion.LightBlueBox
import com.pi.ProjectInclusion.Transparent
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.utils.fontBold
import com.pi.ProjectInclusion.android.utils.fontRegular
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.ConstantVariables.TOKEN_PREF_KEY
import com.pi.ProjectInclusion.constants.ConstantVariables.USER_NAME
import com.pi.ProjectInclusion.constants.CustomDialog
import com.pi.ProjectInclusion.data.model.profileModel.response.ViewProfileResponse
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {
    var isDialogVisible by remember { mutableStateOf(false) }

    val logger = AppLoggerImpl()
    val query by rememberSaveable { mutableStateOf("") }
    val viewModel: LoginViewModel = koinViewModel()
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    val scrollState = rememberLazyGridState()
    val context = LocalContext.current
//    val languageData = remember { mutableStateListOf<GetLanguageListResponse.Data.Result>() }

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

    var strToken = viewModel.getPrefData(TOKEN_PREF_KEY)
    val viewProfile by viewModel.viewUserProfileResponse.collectAsStateWithLifecycle()
    var encryptedUserName = viewModel.getPrefData(USER_NAME)
    logger.d("Profile details on dashboard page :- $encryptedUserName")
    var profileData by remember { mutableStateOf<ViewProfileResponse.ProfileResponse?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getUserProfileViewModel(strToken, encryptedUserName)
    }

    LaunchedEffect(viewProfile) {
        when {
            viewProfile.isLoading -> {
                isDialogVisible = true
            }

            viewProfile.error.isNotEmpty() -> {
                logger.d("view profile error data: ${viewProfile.success}")
                isDialogVisible = false
            }

            viewProfile.success != null -> {
                logger.d("Profile response Data:- ${viewProfile.success}")
                if (viewProfile.success!!.status == true) {
                    profileData = viewProfile.success!!.response
                    logger.d("Profile Data details : ${viewProfile.success?.response}")
                    if (profileData != null) {
                        viewModel.saveFirstName(profileData?.firstname.toString())
                        viewModel.saveLastName(profileData?.lastname.toString())
                    }
                } else {
                    context.toast(viewProfile.success!!.message.toString())
                }
                isDialogVisible = false
            }
        }
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
        modifier = Modifier
            .wrapContentSize()
            .background(
                color = Bg1_Gray
            )
            .padding(top = 65.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Transparent
                )
                .padding(10.dp),
            // Add horizontal padding,
        )
        {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Column(
                    modifier = Modifier.padding(horizontal = 10.dp),
                )
                {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.txt_hi),
                            fontSize = 28.sp,
                            fontFamily = fontRegular,
                            color = Gray,
                            modifier = Modifier.padding(top = 10.dp)
                        )

                        Text(
                            text = if (profileData != null) {
                                profileData?.firstname.toString()
                            } else {
                                stringResource(R.string.welcome_name)
                            },
                            fontSize = 28.sp,
                            fontFamily = fontBold,
                            color = Black,
                            modifier = Modifier.padding(top = 10.dp, start = 8.dp)
                        )
                    }

                    Text(
                        text = stringResource(R.string.welcome_desc),
                        fontSize = 13.sp,
                        fontFamily = fontRegular,
                        color = Gray,
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                    )

                }
                ItemStudentAchievement(context)
                ItemCard(context)
                ItemReferCard(context)

            }
        }
    }
}

@Composable
fun ItemCard(
    context: Context,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally // Center rows inside the column
    ) {
        Row(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.Absolute.Center // or Arrangement.Start/Center
        )
        {
            Card(
                modifier = Modifier
                    .padding(start = 10.dp, end = 5.dp)
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(12.dp), // optional
                colors = CardDefaults.cardColors(
                    containerColor = DashboardCard1,
                    contentColor = Black
                )
            )
            {
                Column(
                    modifier = Modifier
                        .padding(15.dp)
                        .align(Alignment.CenterHorizontally),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 5.dp, top = 10.dp),
                        text = stringResource(R.string.certificate_txt_value),
                        fontFamily = fontBold,
                        fontSize = 24.sp,
                        color = Black
                    )
                    Text(
                        modifier = Modifier.padding(top = 5.dp, bottom = 10.dp),
                        text = stringResource(R.string.txt_screening_in_progress),
                        fontFamily = fontRegular,
                        fontSize = 13.sp,
                        color = Black
                    )

                }
            }
            Card(
                modifier = Modifier
                    .padding(start = 5.dp, end = 10.dp)
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(12.dp), // optional
                colors = CardDefaults.cardColors(
                    containerColor = DashboardCard2,
                    contentColor = Black
                )
            )
            {
                Column(
                    modifier = Modifier
                        .padding(15.dp)
                        .align(Alignment.CenterHorizontally),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 5.dp, top = 10.dp),
                        text = stringResource(R.string.certificate_txt_value),
                        fontFamily = fontBold,
                        fontSize = 24.sp,
                        color = Black
                    )
                    Text(
                        modifier = Modifier.padding(top = 5.dp, bottom = 10.dp),
                        text = stringResource(R.string.txt_screening_done),
                        fontFamily = fontRegular,
                        fontSize = 13.sp,
                        color = Black
                    )

                }
            }
        }
        Row(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.Absolute.Center // or Arrangement.Start/Center
        )
        {
            Card(
                modifier = Modifier
                    .padding(start = 10.dp, end = 5.dp)
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = DashboardCard3,
                    contentColor = Black
                )
            )
            {
                Column(
                    modifier = Modifier
                        .padding(15.dp)
                        .align(Alignment.CenterHorizontally),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 5.dp, top = 10.dp),
                        text = stringResource(R.string.certificate_txt_value),
                        fontFamily = fontBold,
                        fontSize = 24.sp,
                        color = Black
                    )
                    Text(
                        modifier = Modifier.padding(top = 5.dp, bottom = 10.dp),
                        text = stringResource(R.string.txt_learning_plan),
                        fontFamily = fontRegular,
                        fontSize = 13.sp,
                        color = Black
                    )
                }
            }
            Card(
                modifier = Modifier
                    .padding(start = 5.dp, end = 10.dp)
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(12.dp), // optional
                colors = CardDefaults.cardColors(
                    containerColor = DashboardCard4,
                    contentColor = Black
                )
            )
            {
                Column(
                    modifier = Modifier
                        .padding(15.dp)
                        .align(Alignment.CenterHorizontally),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 5.dp, top = 10.dp),
                        text = stringResource(R.string.certificate_txt_value),
                        fontFamily = fontBold,
                        fontSize = 24.sp,
                        color = Black
                    )
                    Text(
                        modifier = Modifier.padding(top = 5.dp, bottom = 10.dp),
                        text = stringResource(R.string.txt_learning_plan_exe),
                        fontFamily = fontRegular,
                        fontSize = 13.sp,
                        color = Black
                    )
                }
            }
        }
    }
}

@Composable
fun ItemReferCard(context: Context) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .wrapContentHeight()
    )
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        )
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                GreenGradient1,
                                GreenGradient2
                            )
                        )
                    ),

                ) {
                Column(
                    modifier = Modifier
                        .padding(15.dp)
                        .align(Alignment.TopStart)
                ) {
                    Text(
                        text = stringResource(R.string.refer_txt),
                        color = Black,
                        fontFamily = fontBold,
                        fontSize = 17.sp// Make sure text is readable on gradient
                    )
                    Text(
                        text = stringResource(R.string.refer_desc),
                        color = Bg_Gray,
                        fontFamily = fontRegular,
                        fontSize = 13.sp// Make sure text is readable on gradient
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(
                        modifier = Modifier
                            .background(color = White)
                            .wrapContentWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = White,
                            contentColor = Black
                        ),
                        shape = RoundedCornerShape(4.dp),
                        // Do not set containerColor, let the Box inside handle the gradient
                    ) {
                        Text(
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(vertical = 8.dp, horizontal = 10.dp),
                            text = stringResource(R.string.refer_count),
                            color = Black,
                            fontSize = 12.sp// Make sure text is readable on gradient
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.refer_banner),
                        contentDescription = stringResource(R.string.lock_ic),
                        tint = Color.Unspecified// Optional color
                    )
                }
            }
        }
    }
}

@Composable
fun ItemStudentAchievement(context: Context) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
    )
    {
        Card(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            // Do not set containerColor, let the Box inside handle the gradient
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = LightBlueBox
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 15.dp)
                )
                {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .background(color = Bg_Gray1, shape = RoundedCornerShape(size = 10.dp))
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Achievements",
                            fontFamily = fontRegular,
                            fontSize = 15.sp
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(
                                start = 5.dp, end = 5.dp, top = 15.dp
                            )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .background(
                                    color = White,
                                    shape = RoundedCornerShape(size = 8.dp)
                                )
                        ) {
                            Text(
                                modifier = Modifier.padding(
                                    start = 15.dp,
                                    end = 15.dp,
                                    top = 10.dp,
                                    bottom = 5.dp
                                ),
                                text = "4",
                                fontFamily = fontBold,
                                fontSize = 24.sp
                            )
                            Text(
                                modifier = Modifier.padding(
                                    start = 15.dp,
                                    end = 15.dp,
                                    bottom = 10.dp
                                ),
                                text = stringResource(R.string.txt_course_completed),
                                fontFamily = fontRegular,
                                fontSize = 13.sp
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 5.dp)
                                .weight(1f)
                                .background(
                                    color = White,
                                    shape = RoundedCornerShape(size = 8.dp)
                                )

                        ) {
                            Text(
                                modifier = Modifier.padding(
                                    start = 15.dp,
                                    end = 10.dp,
                                    top = 10.dp,
                                    bottom = 5.dp
                                ),
                                text = "4",
                                fontFamily = fontBold,
                                fontSize = 24.sp
                            )
                            Text(
                                modifier = Modifier.padding(
                                    start = 15.dp,
                                    end = 15.dp,
                                    bottom = 10.dp
                                ),
                                text = stringResource(R.string.txt_course_certificates),
                                fontFamily = fontRegular,
                                fontSize = 13.sp
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .background(
                                    color = White,
                                    shape = RoundedCornerShape(size = 8.dp)
                                )
                        ) {
                            Text(
                                modifier = Modifier.padding(
                                    start = 15.dp,
                                    end = 15.dp,
                                    top = 10.dp,
                                    bottom = 5.dp
                                ),
                                text = "4",
                                fontFamily = fontBold,
                                fontSize = 24.sp
                            )
                            Text(
                                modifier = Modifier.padding(
                                    start = 15.dp,
                                    end = 15.dp,
                                    bottom = 10.dp
                                ),
                                text = stringResource(R.string.txt_modules_certificates),
                                fontFamily = fontRegular,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun DashboardScreenUI() {
    DashboardScreen()
}