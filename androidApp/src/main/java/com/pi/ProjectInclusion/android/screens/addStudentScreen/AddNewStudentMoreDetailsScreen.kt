package com.pi.ProjectInclusion.android.screens.addStudentScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.GrayLight02
import com.pi.ProjectInclusion.OrangeSubTitle
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.BackButtonPress
import com.pi.ProjectInclusion.android.common_UI.DetailsNoImgBackgroundUi
import com.pi.ProjectInclusion.android.navigation.AppRoute
import com.pi.ProjectInclusion.android.utils.fontMedium

@Composable
fun AddNewStudentMoreDetailsScreen(navHostController: NavHostController) {

    val scrollState = rememberScrollState()

    DetailsNoImgBackgroundUi(
        backgroundColor = White,
        textColor = Black,
        pageTitle = stringResource(R.string.txt_Add_student_screening),
        moreInfoIcon = painterResource(id = R.drawable.vertical_dot),
        isShowBackButton = true,
        isShowMoreInfo = false,
        onBackButtonClick = {
            BackButtonPress(navHostController, AppRoute.ScreeningScreen.route)
        },
        onMoreInfoClick = {},
        content = {
            Divider(
                color = GrayLight02,
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 0.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = if (isSystemInDarkTheme()) {
                            Dark_01
                        } else {
                            White
                        }
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.txt_More_About_Student),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start,
                        fontFamily = fontMedium,
                        color = OrangeSubTitle,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                }
            }
        })
}