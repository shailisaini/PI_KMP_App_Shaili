package com.pi.ProjectInclusion.android.screens.screeningScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
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
import com.pi.ProjectInclusion.LightPurple04
import com.pi.ProjectInclusion.LightPurple05
import com.pi.ProjectInclusion.LightRed03
import com.pi.ProjectInclusion.LightYellow04
import com.pi.ProjectInclusion.White
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.ScreeningDetailsBackgroundUi
import com.pi.ProjectInclusion.android.common_UI.SelectOptBtnUi
import com.pi.ProjectInclusion.android.common_UI.SmallBtnUi
import com.pi.ProjectInclusion.android.common_UI.YesNoBtnUi
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.fontRegular
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.BackHandler

@Composable
fun ProfilerFormPageScreen(onNext: () -> Unit, onBack: () -> Unit) {

    logger.d("Screen: " + "ProfilerFormPageScreen()")

    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    val steps = listOf(
        stringResource(R.string.key_academic),
        stringResource(R.string.key_interests),
        stringResource(R.string.key_strength),
        stringResource(R.string.key_temperament)
    )
    var stepTitle by remember { mutableStateOf(0) }
    val totalSteps = steps.size

    var answerQ1 by remember { mutableStateOf("") }
    var answerQ2 by remember { mutableStateOf("") }
    var answerQ3 by remember { mutableStateOf("") }
    var answerQ4 by remember { mutableStateOf("") }

    // This function will be change according to recommend
    if (showDialog) {
        ScreeningIntroDialog {
            showDialog = false
        }
    }

    BackHandler {
        onBack()
    }

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
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        steps.forEachIndexed { index, label ->
                            var progress by remember { mutableStateOf(0.65f) } // 65% progress
                            ProgressBarWithText(progress = progress, index, label)
                            if (index < steps.lastIndex) {
                                DashedLine()
                            }
                        }
                    }
                }

                when (stepTitle) {
                    0 -> {
                        AcademicScreenUI(selectedAns = { option ->
                            option.toString()
                            answerQ1 = option.toString()
                        })
                    }

                    1 -> {
                        InterestScreenUI(selectedAns = { option ->
                            option.toString()
                            answerQ2 = option.toString()
                        })
                    }

                    2 -> {
                        StrengthScreenUI(selectedAns = { option ->
                            option.toString()
                            answerQ3 = option.toString()

                        })
                    }

                    3 -> {
                        TemperamentScreenUI(selectedAns = { option ->
                            option.toString()
                            answerQ4 = option.toString()
                        })
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = White)
                            .fillMaxHeight(), horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        SmallBtnUi(
                            enabled = true,
                            title = if (stepTitle < totalSteps - 1) {
                                stringResource(R.string.string_next)
                            } else {
                                stringResource(R.string.txt_Save_Continue)
                            },
                            onClick = {
                                if (stepTitle < totalSteps - 1) {
                                    stepTitle++
                                } else {
                                    context.toast("It will Submit all data here")
                                }
                            })
                    }
                }
            }
        }
    )
}

@Composable
fun AcademicScreenUI(selectedAns: (String) -> Unit) {
    val questionListData = listOf(
        ProfilerQuestionData(
            1,
            stringResource(R.string.txt_Select_statement_from_following),
            stringResource(R.string.txt_Does_not_participate),
            stringResource(R.string.txt_Below_average),
            stringResource(R.string.txt_Average),
            stringResource(R.string.txt_Above_Average)
        ),
        ProfilerQuestionData(
            2,
            stringResource(R.string.txt_Choose_multiple_from_following),
            stringResource(R.string.txt_Does_not_participate),
            stringResource(R.string.txt_Below_average),
            stringResource(R.string.txt_Average),
            stringResource(R.string.txt_Above_Average)
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 16.dp)
    ) {
        items(questionListData) { questionData ->
            ProfilerQuestionDataUI(questionData, selectedAns)
        }
    }
}

@Composable
fun InterestScreenUI(selectedAns: (String) -> Unit) {
    val questionListData = listOf(
        ProfilerQuestionData(
            1,
            stringResource(R.string.txt_Select_statement_from_following),
            stringResource(R.string.txt_Does_not_participate),
            stringResource(R.string.txt_Below_average),
            stringResource(R.string.txt_Average),
            stringResource(R.string.txt_Above_Average)
        ),
        ProfilerQuestionData(
            2,
            stringResource(R.string.txt_Choose_multiple_from_following),
            stringResource(R.string.txt_Does_not_participate),
            stringResource(R.string.txt_Below_average),
            stringResource(R.string.txt_Average),
            stringResource(R.string.txt_Above_Average)
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 16.dp)
    ) {
        items(questionListData) { questionData ->
            ProfilerQuestionDataUI(questionData, selectedAns)
        }
    }
}

@Composable
fun StrengthScreenUI(selectedAns: (String) -> Unit) {
    val questionListData = listOf(
        ProfilerQuestionData(
            1,
            stringResource(R.string.txt_Select_statement_from_following),
            stringResource(R.string.txt_Does_not_participate),
            stringResource(R.string.txt_Below_average),
            stringResource(R.string.txt_Average),
            stringResource(R.string.txt_Above_Average)
        ),
        ProfilerQuestionData(
            2,
            stringResource(R.string.txt_Choose_multiple_from_following),
            stringResource(R.string.txt_Does_not_participate),
            stringResource(R.string.txt_Below_average),
            stringResource(R.string.txt_Average),
            stringResource(R.string.txt_Above_Average)
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 16.dp)
    ) {
        items(questionListData) { questionData ->
            ProfilerQuestionDataUI(questionData, selectedAns)
        }
    }
}

@Composable
fun TemperamentScreenUI(selectedAns: (String) -> Unit) {
    val questionListData = listOf(
        ProfilerQuestionData(
            1,
            stringResource(R.string.txt_Select_statement_from_following),
            stringResource(R.string.txt_Does_not_participate),
            stringResource(R.string.txt_Below_average),
            stringResource(R.string.txt_Average),
            stringResource(R.string.txt_Above_Average)
        ),
        ProfilerQuestionData(
            2,
            stringResource(R.string.txt_Choose_multiple_from_following),
            stringResource(R.string.txt_Does_not_participate),
            stringResource(R.string.txt_Below_average),
            stringResource(R.string.txt_Average),
            stringResource(R.string.txt_Above_Average)
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 16.dp)
    ) {
        items(questionListData) { questionData ->
            ProfilerQuestionDataUI(questionData, selectedAns)
        }
    }
}

@Composable
fun ProfilerQuestionDataUI(questionData: ProfilerQuestionData, selectedAns: (String) -> Unit) {
    val selectedBorder = BorderStroke(
        width = 0.5.dp, if (isSystemInDarkTheme()) {
            Dark_02
        } else {
            LightPurple05
        }
    )
    var trueFalseZero by remember { mutableStateOf(false) }
    var trueFalseOne by remember { mutableStateOf(false) }
    var trueFalseTwo by remember { mutableStateOf(false) }
    var trueFalseThree by remember { mutableStateOf(false) }

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
                if (trueFalseZero || trueFalseOne || trueFalseTwo || trueFalseThree) {
                    GrayLight06
                } else {
                    Black
                }
            } else {
                if (trueFalseZero || trueFalseOne || trueFalseTwo || trueFalseThree) {
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
                        if (trueFalseZero || trueFalseOne || trueFalseTwo || trueFalseThree) {
                            GrayLight06
                        } else {
                            Black
                        }
                    } else {
                        if (trueFalseZero || trueFalseOne || trueFalseTwo || trueFalseThree) {
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
                SelectOptBtnUi(
                    onClick = {
                        selectedAns(questionData.ansZero)
                        trueFalseZero = true
                    },
                    title = questionData.ansZero,
                    enabled = trueFalseZero
                )
            }

            Row(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp, start = 48.dp, end = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                SelectOptBtnUi(
                    onClick = {
                        selectedAns(questionData.ansOne)
                        trueFalseOne = true
                    },
                    title = questionData.ansOne,
                    enabled = trueFalseOne
                )

                Spacer(modifier = Modifier.width(16.dp))

                YesNoBtnUi(
                    onClick = {
                        selectedAns(questionData.ansTwo)
                        trueFalseTwo = true
                    }, title = questionData.ansTwo, enabled = trueFalseTwo
                )
            }

            Row(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp, start = 48.dp, end = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                SelectOptBtnUi(
                    onClick = {
                        selectedAns(questionData.ansThree)
                        trueFalseThree = true
                    },
                    title = questionData.ansThree,
                    enabled = trueFalseThree
                )
            }
        }
    }
}

@Composable
fun DashedLine() {
    Canvas(
        modifier = Modifier
            .width(16.dp)
            .height(1.dp)
    ) {
        val dashWidth = 4.dp.toPx()
        val gap = 2.dp.toPx()
        var x = 0f
        while (x < size.width) {
            drawLine(
                color = Color.Blue,
                start = Offset(x, 0f),
                end = Offset(x + dashWidth, 0f),
                strokeWidth = 2f
            )
            x += dashWidth + gap
        }
    }
}

@Composable
fun ProgressBarWithText(
    progress: Float,
    index: Int,
    title: String,
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(if (index == 0) LightYellow04 else White)
            .border(
                width = 1.dp,
                color = if (index == 0) LightPurple04 else White,
                shape = CircleShape
            )
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Text(
            text = title,
            color = Color.Black,
            fontFamily = fontMedium,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}

data class ProfilerQuestionData(
    var queNumber: Int,
    var question: String,
    var ansZero: String,
    var ansOne: String,
    var ansTwo: String,
    var ansThree: String,
)

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProfilerFormPagePreview() {
    val onNext: () -> Unit = {}
    val onBack: () -> Unit = {}
    ProfilerFormPageScreen(onNext, onBack)
}