package com.pi.ProjectInclusion.android.screens.Profile

import android.content.Context
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.LightGreen06
import com.pi.ProjectInclusion.LightYellow02
import com.pi.ProjectInclusion.OrangeSubTitle
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.RedBgColor
import com.pi.ProjectInclusion.RedLogout
import com.pi.ProjectInclusion.Transparent
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.DetailsNoImgBackgroundUi
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.fontRegular
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.BackHandler
import com.pi.ProjectInclusion.constants.CommonFunction.NoDataFound
import com.pi.ProjectInclusion.constants.CommonFunction.ShowError
import com.pi.ProjectInclusion.constants.CommonFunction.isNetworkAvailable
import com.pi.ProjectInclusion.constants.ConstantVariables.TOKEN_PREF_KEY
import com.pi.ProjectInclusion.constants.CustomDialog
import com.pi.ProjectInclusion.data.model.authenticationModel.response.UserTrackRequestResponse
import com.pi.ProjectInclusion.ui.viewModel.DashboardViewModel
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TrackRequestScreen(onNext: () -> Unit, onBack: () -> Unit) {

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

    logger.d("Screen: " + "TrackRequestScreen()")

    Surface(
        modifier = Modifier.fillMaxWidth(), color = White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = White),
            verticalArrangement = Arrangement.Top
        ) {
            TrackRequest(context, onNext = onNext, onBack = onBack)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackRequest(
    context: Context,
    onBack: () -> Unit,
    onNext: () -> Unit,
) {

    val viewModel: DashboardViewModel = koinViewModel()
    val loginViewModel: LoginViewModel = koinViewModel()
    val requestState by viewModel.trackRequestResponse.collectAsStateWithLifecycle()
    var isDialogVisible by remember { mutableStateOf(false) }
    var requestData by remember { mutableStateOf(mutableListOf<UserTrackRequestResponse.UserTrackResponse>()) }
    var isInternetAvailable by remember { mutableStateOf(false) }
    val internetMessage = stringResource(R.string.txt_oops_no_internet)
    var strToken = loginViewModel.getPrefData(TOKEN_PREF_KEY)

    CustomDialog(
        isVisible = isDialogVisible,
        onDismiss = { isDialogVisible = false },
        message = stringResource(R.string.txt_loading)
    )

    LaunchedEffect(Unit) {
        isInternetAvailable = isNetworkAvailable(context)
        if (!isInternetAvailable) {
            context.toast(internetMessage)
        } else {
            isDialogVisible = true
            viewModel.getTrackRequest(strToken)
        }
    }

    LaunchedEffect(requestState) {
        when {
            requestState.isLoading -> {
                isDialogVisible = true
            }

            requestState.error.isNotEmpty() -> {
                logger.d("User track request Error: ${requestState.error}")
                isDialogVisible = false
                requestData.clear()
                viewModel.resetState(viewModel.trackRequest)
            }

            requestState.success != null -> {
                logger.d("User track request Response :- ${requestState.success!!.response}")
                if (requestState.success!!.response != null) {
                    isDialogVisible = false
                    requestData.clear()
                    if (requestState.success!!.response?.size != 0) {
                        requestData =
                            requestState.success!!.response as MutableList<UserTrackRequestResponse.UserTrackResponse>
                        println("User track request Data :- $requestData")
                    } else {
                        context.toast(requestState.success!!.message.toString())
                    }
                } else {
                    isDialogVisible = false
                    context.toast(requestState.success!!.message.toString())
                }
                viewModel.resetState(viewModel.trackRequest)
            }
        }
    }

    DetailsNoImgBackgroundUi(
        backgroundColor = White,
        textColor = Black,
        pageTitle = stringResource(R.string.track_request),
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
            )

            TrackListScreen(requestData)
        })
}

@Composable
fun TrackListScreen(requestData: MutableList<UserTrackRequestResponse.UserTrackResponse>) {

    val context = LocalContext.current
    val errColor = PrimaryBlue
    var noData = stringResource(R.string.txt_oops_no_data_found)
    var isInternetAvailable by remember { mutableStateOf(true) }
    val internetMessage = stringResource(R.string.txt_oops_no_internet)
    var noDataMessage by remember { mutableStateOf(noData) }

    if (requestData.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(requestData) { item ->
                TrackListCard(item = item)
            }
        }
    } else {
        isInternetAvailable = isNetworkAvailable(context)
        if (!isInternetAvailable) {
            ShowError(internetMessage, errColor, painterResource(R.drawable.sad_emoji))
        } else {
            NoDataFound(noDataMessage, painterResource(R.drawable.sad_emoji))
        }
    }
}

@Composable
private fun TrackListCard(item: UserTrackRequestResponse.UserTrackResponse) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.Center, modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.requestTypeName.toString(),
                    fontSize = 16.sp,
                    fontFamily = fontMedium,
                    color = PrimaryBlue
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "15 Apr 2025",
                    fontSize = 13.sp,
                    fontFamily = fontRegular,
                    color = Color.Gray
                )
            }

            Box(
                modifier = Modifier
                    .background(
                        when (item.requestStatusName.toString()) {
                            "Approved" -> {
                                LightGreen06
                            }

                            "Pending" -> {
                                RedBgColor
                            }

                            "In Review" -> {
                                OrangeSubTitle
                            }

                            else -> {
                                RedLogout
                            }
                        }, shape = RoundedCornerShape(50)
                    )
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.requestStatusName.toString(),
                    color = White,
                    fontSize = 13.sp,
                    fontFamily = fontRegular,
                )
            }
        }

        if (item.requestStatusName.equals("Rejected")) {
            Card(
                shape = RoundedCornerShape(4.dp),
                colors = CardDefaults.cardColors(containerColor = LightYellow02),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    modifier = Modifier.padding(start = 12.dp, top = 4.dp, bottom = 6.dp),
                    text = stringResource(R.string.txt_reason) + " " + "Document unclear",
                    color = Black,
                    fontFamily = fontRegular,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun TrackRequest() {
    val context = LocalContext.current
    val onNext: () -> Unit = {}
    val onBack: () -> Unit = {}
    TrackRequest(context, onNext, onBack)
}