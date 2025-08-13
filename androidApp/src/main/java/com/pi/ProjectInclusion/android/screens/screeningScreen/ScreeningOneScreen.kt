package com.pi.ProjectInclusion.android.screens.screeningScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.Dark_02
import com.pi.ProjectInclusion.Dark_03
import com.pi.ProjectInclusion.GrayLight06
import com.pi.ProjectInclusion.LightOrange2
import com.pi.ProjectInclusion.LightPurple05
import com.pi.ProjectInclusion.LightRed03
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.White
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.CustomHorizontalProgressBar
import com.pi.ProjectInclusion.android.common_UI.ScreeningDetailsBackgroundUi
import com.pi.ProjectInclusion.android.common_UI.SmallBtnUi
import com.pi.ProjectInclusion.android.common_UI.YesNoBtnUi
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.fontRegular
import com.pi.ProjectInclusion.constants.BackHandler


@Composable
fun ScreeningOneScreen(onNext: () -> Unit, onBack: () -> Unit) {

    logger.d("Screen: " + "ScreeningOneScreen()")

    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    BackHandler {
        onBack()
    }

    // This function will be change according to recommend
    if (showDialog) {
        ScreeningIntroDialog {
            showDialog = false
        }
    }

    val questionListData = listOf(
        ScreeningQuestionData(
            1,
            stringResource(R.string.txt_Question_Constant),
            stringResource(R.string.txt_Yes),
            stringResource(R.string.txt_No)
        ),
        ScreeningQuestionData(
            2,
            stringResource(R.string.txt_Question_Constant),
            stringResource(R.string.txt_Yes),
            stringResource(R.string.txt_No)
        ),
        ScreeningQuestionData(
            3,
            stringResource(R.string.txt_Question_Constant),
            stringResource(R.string.txt_Yes),
            stringResource(R.string.txt_No)
        ),
        /*ScreeningQuestionData(
            4,
            stringResource(R.string.txt_Question_Constant),
            stringResource(R.string.txt_Yes),
            stringResource(R.string.txt_No)
        )*/
    )

    ScreeningDetailsBackgroundUi(
        stringResource(R.string.txt_Screening),
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
                                Dark_03
                            } else {
                                LightRed03
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
                            text = stringResource(R.string.txt_Question),
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

                        Row(
                            modifier = Modifier
                                .wrapContentWidth()
                                .wrapContentHeight(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "01",
                                modifier = Modifier.wrapContentWidth(),
                                fontFamily = fontMedium,
                                fontSize = 16.sp,
                                color = if (isSystemInDarkTheme()) {
                                    PrimaryBlue
                                } else {
                                    PrimaryBlue
                                },
                                textAlign = TextAlign.Start
                            )

                            Text(
                                text = "/42",
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
                    }

                    Column(
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(start = 16.dp, bottom = 8.dp, end = 16.dp)
                            .wrapContentHeight(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        val num: Float = (1.toFloat() / 42)
                        CustomHorizontalProgressBar(
                            num, LightOrange2, GrayLight06
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(start = 8.dp, end = 8.dp, bottom = 16.dp, top = 8.dp)
                        .background(
                            if (isSystemInDarkTheme()) {
                                Dark_01
                            } else {
                                White
                            }
                        )
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(bottom = 64.dp)
                    ) {
                        items(questionListData) { questionData ->
                            ScreeningQuestionDataUI(questionData)
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(start = 8.dp, end = 16.dp, bottom = 16.dp, top = 16.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = White)
                            .wrapContentHeight(), horizontalAlignment = Alignment.End
                    ) {
                        SmallBtnUi(
                            enabled = true,
                            title = stringResource(R.string.txt_submit),
                            onClick = {
                                onNext() // this change according to condition
                            })
                    }
                }
            }
        }
    )
}

@Composable
fun ScreeningQuestionDataUI(questionData: ScreeningQuestionData) {

    val selectedBorder = BorderStroke(
        width = 0.5.dp, if (isSystemInDarkTheme()) {
            Dark_02
        } else {
            LightPurple05
        }
    )
    var trueFalseYes by remember { mutableStateOf(false) }
    var trueFalseNo by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${questionData.queNumber}. ",
            modifier = Modifier.wrapContentWidth(),
            fontFamily = fontRegular,
            fontSize = 14.sp,
            color = if (isSystemInDarkTheme()) {
                if (trueFalseYes || trueFalseNo) {
                    GrayLight06
                } else {
                    Black
                }
            } else {
                if (trueFalseYes || trueFalseNo) {
                    GrayLight06
                } else {
                    Black
                }
            },
            textAlign = TextAlign.Start
        )

        Column(
            modifier = Modifier
                .wrapContentSize()
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                colors = if (isSystemInDarkTheme()) {
                    CardDefaults.cardColors(LightPurple05)
                } else {
                    CardDefaults.cardColors(
                        containerColor = LightPurple05,
                        contentColor = LightPurple05,
                        disabledContentColor = LightPurple05,
                        disabledContainerColor = LightPurple05
                    )
                },
                border = selectedBorder
            ) {
                Text(
                    text = questionData.question,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(8.dp),
                    fontFamily = fontMedium,
                    fontSize = 15.sp,
                    color = if (isSystemInDarkTheme()) {
                        if (trueFalseYes || trueFalseNo) {
                            GrayLight06
                        } else {
                            Black
                        }
                    } else {
                        if (trueFalseYes || trueFalseNo) {
                            GrayLight06
                        } else {
                            Black
                        }
                    },
                    textAlign = TextAlign.Start
                )
            }

            Row(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp, start = 48.dp, end = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                YesNoBtnUi(
                    onClick = {
                        trueFalseYes = true
                        trueFalseNo = false
                    },
                    title = questionData.ansYes,
                    enabled = trueFalseYes
                )

                Spacer(modifier = Modifier.width(16.dp))

                YesNoBtnUi(
                    onClick = {
                        trueFalseNo = true
                        trueFalseYes = false
                    }, title = questionData.ansNo, enabled = trueFalseNo
                )
            }
        }
    }
}

data class ScreeningQuestionData(
    var queNumber: Int,
    var question: String,
    var ansYes: String,
    var ansNo: String,
)

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ScreeningOneScreenPreview() {
    val onNext: () -> Unit = {}
    val onBack: () -> Unit = {}
    ScreeningOneScreen(onNext, onBack)
}