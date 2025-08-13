package com.pi.ProjectInclusion.android.screens.Profile

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.kmptemplate.logger.LoggerProvider
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.Transparent
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.DetailsNoImgBackgroundUi
import com.pi.ProjectInclusion.constants.BackHandler
import com.pi.ProjectInclusion.constants.CustomDialog
import com.pi.ProjectInclusion.data.model.AuthenticationModel.GetUserTypeResponse

@Composable
fun TrackRequestScreen(onNext: () -> Unit,onBack: () -> Unit) {
    var isDialogVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val userType = remember { mutableStateListOf<GetUserTypeResponse.UserTypeResponse>() }
    CustomDialog(
        isVisible = isDialogVisible,
        onDismiss = { isDialogVisible = false },
        message = stringResource(R.string.txt_loading)
    )
    BackHandler {
        onBack()
    }
    LoggerProvider.logger.d("Screen: " + "EnterUserNameScreen()")

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
    onNext: () -> Unit
) {

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
        })
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun TrackRequest() {
    val context = LocalContext.current
    val onNext: () -> Unit = {}
    val onBack: () -> Unit = {}
    TrackRequest(context, onNext, onBack)
}