package com.pi.ProjectInclusion.android.screens.screeningScreen

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.CardColor01
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.GrayLight04
import com.pi.ProjectInclusion.HeaderColor01
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.White
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.BtnWithRightIconUi
import com.pi.ProjectInclusion.android.utils.fontBold
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.fontRegular
import com.pi.ProjectInclusion.constants.CommonFunction.LoginScreenTitle


@Composable
fun ScreeningOneReportScreen(
    showReportScreen: Boolean,
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
            stringResource(R.string.txt_Screening_1_Report),
            White,
            White,
            if (isSystemInDarkTheme()) {
                PrimaryBlue
            } else {
                PrimaryBlue
            },
            stringResource(R.string.txt_Student_Name)
        )

        // Conditional content
        if (showReportScreen) {
            ReportScreenContent(onNext)
        } else {
            CongratulationsScreen("Rahul")
        }

    }
}

@Composable
fun ReportScreenContent(onNext: () -> Unit = {}) {

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

            ReportCard(
                title = stringResource(R.string.nav_refer_txt),
                text = stringResource(R.string.txt_Based_observation_SAMAN_difficulty),
                titleColor = HeaderColor01
            )

            ReportCard(
                title = stringResource(R.string.txt_Recommendation),
                text = stringResource(R.string.txt_Based_observation_SAMAN_difficulty),
                titleColor = HeaderColor01
            )

            Spacer(modifier = Modifier.height(16.dp))

            DomainSection(stringResource(R.string.txt_Auditory_Domain_HI))

            ReportCard(
                title = stringResource(R.string.txt_Condition),
                text = stringResource(R.string.txt_Based_observation_SAMAN_difficulty),
                titleColor = HeaderColor01
            )
        }

        BottomUI(onNext)
    }
}

@Composable
fun CongratulationsScreen(name: String) {
    val compositionState = rememberLottieComposition(
        spec = LottieCompositionSpec.Asset("Confetti.json")
    )
    val composition = compositionState.value


    val progressState = animateLottieCompositionAsState(
        composition = compositionState.value,
        iterations = LottieConstants.IterateForever
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // 🎉 Background Lottie
//        LottieAnimation(
//            composition = composition,
//            progress = progress,
//            modifier = Modifier
//                .fillMaxSize()
//                .zIndex(0f)
//        )

        // Foreground UI
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
                .zIndex(1f), // Bring UI above animation
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.sad_emoji),
                contentDescription = "Icon",
                modifier = Modifier.size(60.dp),
                tint = Color.Unspecified
            )

            Text(
                text = name + stringResource(R.string.head_leave),
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(top = 16.dp, start = 8.dp, end = 8.dp),
                fontFamily = fontBold,
                fontSize = 19.sp,
                color = Black,
                textAlign = TextAlign.Center
            )

            Text(
                text = stringResource(R.string.body_leave),
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(top = 3.dp, start = 8.dp, end = 8.dp),
                fontFamily = fontRegular,
                fontSize = 15.sp,
                color = GrayLight04,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            BtnWithRightIconUi(
                onClick = {
                    println("testing =>")
                },
                title = "Continue",
                enabled = true
            )
        }
    }
}


@Composable
fun ReportCard(title: String, text: String, titleColor: Color) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 4.dp)
            .wrapContentHeight(),
        colors = if (isSystemInDarkTheme()) {
            CardDefaults.cardColors(CardColor01)
        } else {
            CardDefaults.cardColors(
                containerColor = CardColor01,
                contentColor = CardColor01,
                disabledContentColor = CardColor01,
                disabledContainerColor = CardColor01
            )
        }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = title, color = titleColor, fontFamily = fontMedium, fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = text, fontSize = 14.sp, fontFamily = fontRegular, color = Black
            )
        }
    }
}

@Composable
fun DomainSection(title: String) {
    Text(
        text = title,
        fontSize = 16.sp,
        fontFamily = fontBold,
        color = PrimaryBlue,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun BottomUI(onNext: () -> Unit = {}) {
    Surface(
        color = Color.White, tonalElevation = 4.dp, shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp
                )
        ) {
            Text(
                text = stringResource(R.string.txt_Let_fill_profiler_Form_understand),
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                fontFamily = fontMedium,
                color = Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 24.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = White)
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.txt_No_I_will_do_it_later),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontFamily = fontMedium,
                    color = PrimaryBlue,
                )

                Spacer(modifier = Modifier.width(24.dp))

                BtnWithRightIconUi(
                    onClick = {
                        onNext()
                    }, title = stringResource(R.string.txt_Profiler_Form), enabled = true
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ScreeningOneReportScreenPreview() {
    val onNext: () -> Unit = {}
    val onBack: () -> Unit = {}
    ScreeningOneReportScreen(true, onNext, onBack)
}