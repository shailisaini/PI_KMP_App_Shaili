package com.pi.ProjectInclusion.android.screens.screeningScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.ScreeningDetailsBackgroundUi

@Composable
fun ProfilerFormPageScreen(onNext: () -> Unit, onBack: () -> Unit) {

    logger.d("Screen: " + "ProfilerFormPageScreen()")

    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    ScreeningDetailsBackgroundUi(
        stringResource(R.string.txt_Profiler_Form),
        stringResource(R.string.txt_Student_Name),
        isShowBackButton = true,
        isShowMoreInfo = true,
        onBackButtonClick = {
            onBack()
        },
        onMoreInfoClick = {
            showDialog = true
        },
        content = {

        }
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProfilerFormPagePreview() {
    val onNext: () -> Unit = {}
    val onBack: () -> Unit = {}
    ProfilerFormPageScreen(onNext, onBack)
}