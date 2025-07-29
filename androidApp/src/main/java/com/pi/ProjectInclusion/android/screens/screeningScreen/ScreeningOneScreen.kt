package com.pi.ProjectInclusion.android.screens.screeningScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.GrayLight03
import com.pi.ProjectInclusion.LightPurple04
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.White
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.BackButtonPress
import com.pi.ProjectInclusion.android.common_UI.CustomHorizontalProgressBar
import com.pi.ProjectInclusion.android.common_UI.ScreeningDetailsBackgroundUi
import com.pi.ProjectInclusion.android.navigation.AppRoute
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.fontRegular

@Composable
fun ScreeningOneScreen(navHostController: NavHostController) {

    logger.d("Screen: " + "ScreeningOneScreen()")

    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    // This function will be change according to recommend
    if (showDialog) {
        ScreeningIntroDialog {
            showDialog = false
        }
    }

    ScreeningDetailsBackgroundUi(
        stringResource(R.string.txt_Screening),
        stringResource(R.string.txt_Student_Name),
        isShowBackButton = true,
        isShowMoreInfo = true,
        onBackButtonClick = {
            BackButtonPress(navHostController, AppRoute.ScreeningScreen.route)
        },
        onMoreInfoClick = {
            showDialog = true
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        if (isSystemInDarkTheme()) {
                            Dark_01
                        } else {
                            White
                        }
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (isSystemInDarkTheme()) {
                                Dark_01
                            } else {
                                White
                            }
                        )
                        .wrapContentHeight(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 16.dp)
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.txt_Progress_achieved),
                            modifier = Modifier.wrapContentWidth(),
                            fontFamily = fontRegular,
                            fontSize = 12.sp,
                            color = if (isSystemInDarkTheme()) {
                                PrimaryBlue
                            } else {
                                PrimaryBlue
                            },
                            textAlign = TextAlign.Start
                        )

                        Text(
                            text = "40%",
                            modifier = Modifier.wrapContentWidth(),
                            fontFamily = fontMedium,
                            fontSize = 14.sp,
                            color = if (isSystemInDarkTheme()) {
                                PrimaryBlue
                            } else {
                                PrimaryBlue
                            },
                            textAlign = TextAlign.Start
                        )
                    }

                    Column(
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 8.dp, bottom = 2.dp)
                            .wrapContentHeight(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        val num: Float = (40.toFloat() / 100)
                        CustomHorizontalProgressBar(
                            num, LightPurple04, GrayLight03
                        )
                    }
                }
            }
        }
    )
}