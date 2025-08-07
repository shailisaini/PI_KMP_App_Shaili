package com.pi.ProjectInclusion.android.screens.screeningScreen

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.BorderBlue
import com.pi.ProjectInclusion.CardColor01
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.HeaderColor01
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.White
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.BtnWithRightIconUi
import com.pi.ProjectInclusion.android.utils.fontBold
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.fontRegular
import com.pi.ProjectInclusion.constants.CommonFunction.LoginScreenTitle
import com.pi.ProjectInclusion.LightPurple04
import com.pi.ProjectInclusion.PrimaryBlueLt1
import com.pi.ProjectInclusion.android.common_UI.MobileTextField
import com.pi.ProjectInclusion.android.utils.toast


@Composable
fun ReportAdvanceScreen(
    onNext: () -> Unit,
    onBack: () -> Unit,
) {

    logger.d("Screen: " + "ScreeningOneReportScreen()")


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
        LoginScreenTitle(
            stringResource(R.string.txt_adv_screen_report),
            White,
            LightPurple04,
            if (isSystemInDarkTheme()) {
                PrimaryBlue
            } else {
                PrimaryBlue
            },
            stringResource(R.string.txt_Student_Name)
        )

        ReportScreenContent(onNext,onBack)


    }
}

@Composable
private fun ReportScreenContent(onNext: () -> Unit = {},onBack: () -> Unit) {

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 8.dp)
        ) {
            DomainSection(stringResource(R.string.txt_Visual_Domain_VI))

            ReportCard(
                title = stringResource(R.string.txt_Condition),
                text = stringResource(R.string.txt_Based_observation_SAMAN_difficulty),
                titleColor = HeaderColor01
            )


            Spacer(modifier = Modifier.height(16.dp))

            DomainSection(stringResource(R.string.txt_Auditory_Domain_HI))

            ReportCard(

                title = stringResource(R.string.txt_Condition),
                text = stringResource(R.string.txt_adv_message),
                titleColor = HeaderColor01
            )
        }

        BottomUI(onNext, onBack)
    }
}

@Composable
private fun ReportCard(title: String, text: String, titleColor: Color) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 4.dp)
            .wrapContentHeight(),
        colors = if (isSystemInDarkTheme()) {
            CardDefaults.cardColors(PrimaryBlueLt1)
        } else {
            CardDefaults.cardColors(
                containerColor = PrimaryBlueLt1,
                contentColor = PrimaryBlueLt1,
                disabledContentColor = PrimaryBlueLt1,
                disabledContainerColor = PrimaryBlueLt1
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = PrimaryBlueLt1)
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(16 .dp),
                text = text,
                fontSize = 17.sp,
                fontFamily = fontMedium,
                color = Black
            )
            Spacer(
                    modifier = Modifier
                        .width(24.dp)
                        .padding(top = 16.dp)
                    )

            Text(
                modifier = Modifier
                    .padding(16 .dp)
                    .clickable{

                    },
                text = stringResource(R.string.btn_profiler_Form),
                textAlign = TextAlign.Center,
                fontSize = 21.sp,
                fontFamily = fontMedium,
                color = BorderBlue,
            )

        }
    }
}

@Composable
private fun DomainSection(title: String) {
    Text(
        text = title,
        fontSize = 16.sp,
        fontFamily = fontBold,
        color = PrimaryBlue,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
private fun BottomUI(onNext: () -> Unit = {},onBack: () -> Unit) {
    Surface(
        color = Color.White, tonalElevation = 4.dp, shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp, end = 16.dp, top = 36.dp, bottom = 16.dp
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = White)
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                BtnWithRightIconUi(
                    onClick = {
                        onNext()
                    }, title = stringResource(R.string.txt_Start_Intervention), enabled = true
                )
                Spacer(
                    modifier = Modifier
                        .width(54.dp)
                        .padding(top = 16.dp)
                )

                Text(
                    modifier = Modifier
                        .clickable{
                            onBack()
                        },
                    text = stringResource(R.string.txt_No_I_will_do_it_later),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontFamily = fontMedium,
                    color = BorderBlue,
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ReportAdvanceScreenPreview() {
    val onNext: () -> Unit = {}
    val onBack: () -> Unit = {}
    ReportAdvanceScreen(onNext, onBack)
}