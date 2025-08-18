package com.pi.ProjectInclusion.android.screens.screeningScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pi.ProjectInclusion.Bg_Gray1
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.Black1
import com.pi.ProjectInclusion.BlueBackground4
import com.pi.ProjectInclusion.BorderBlue
import com.pi.ProjectInclusion.DARK_TITLE_TEXT
import com.pi.ProjectInclusion.Dark_02
import com.pi.ProjectInclusion.GrayLight02
import com.pi.ProjectInclusion.GrayLight07
import com.pi.ProjectInclusion.GrayLight08
import com.pi.ProjectInclusion.LightGreen01
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.PrimaryBlue4
import com.pi.ProjectInclusion.White
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.DefaultBackgroundUi
import com.pi.ProjectInclusion.android.common_UI.ProfileWithFullProgress
import com.pi.ProjectInclusion.android.common_UI.TextWithIconOnRight
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.fontRegular
import com.pi.ProjectInclusion.android.utils.fontSemiBold
import com.pi.ProjectInclusion.constants.BackHandler
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION

@Composable
fun ViewScreeningProfileDetailsScreen(
    onNext: () -> Unit,
    onBack: () -> Unit,
) {
    val scrollState = rememberScrollState()

    BackHandler {
        onBack()
    }

    DefaultBackgroundUi(isShowBackButton = true, onBackButtonClick = {
        onBack()
    }, content = {

        Divider(
            color = GrayLight02, thickness = 1.dp, modifier = Modifier.padding(horizontal = 0.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Bg_Gray1)
                .verticalScroll(scrollState)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileWithFullProgress(image = "", progress = 1f)

                Text(
                    text = stringResource(R.string.txt_Student_Name),
                    fontSize = 18.sp,
                    fontFamily = fontSemiBold,
                    color = PrimaryBlue,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 10.dp)
                )

                Text(
                    text = stringResource(R.string.key_Class_5),
                    fontSize = 15.sp,
                    fontFamily = fontRegular,
                    color = BorderBlue,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                ViewReportCardUI()

                Spacer(Modifier.height(16.dp))

                ViewPersonalDetailsCardUI()

                Spacer(Modifier.height(16.dp))

                ViewClassroomObservationCardUI()

                Spacer(Modifier.height(16.dp))

                ViewStrengthsAbilityCardUI()
            }
        }
    })
}

@Composable
fun ViewReportCardUI() {
    val selectedBorder = BorderStroke(
        width = 0.5.dp, if (isSystemInDarkTheme()) {
            Dark_02
        } else {
            GrayLight02
        }
    )

    var screeningStr = stringResource(R.string.nav_screening)
    var profilerStr = stringResource(R.string.txt_Profiler_Form)
    var advanceScreeningStr = stringResource(R.string.txt_Advance_Screening)
    var doneStr = stringResource(R.string.key_done)

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = if (isSystemInDarkTheme()) {
            CardDefaults.cardColors(Dark_02)
        } else {
            CardDefaults.cardColors(
                containerColor = White,
                contentColor = White,
                disabledContentColor = White,
                disabledContainerColor = White
            )
        },
        border = selectedBorder
    ) {
        Column(
            modifier = Modifier.padding(
                start = 5.dp, end = 5.dp, top = 5.dp, bottom = 5.dp
            ), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ViewReportDataUI(screeningStr, doneStr)

            ViewReportDataUI(profilerStr, doneStr)

            ViewReportDataUI(advanceScreeningStr, doneStr)

        }
    }
}

@Composable
fun ViewPersonalDetailsCardUI() {
    val selectedBorder = BorderStroke(
        width = 0.5.dp, if (isSystemInDarkTheme()) {
            Dark_02
        } else {
            GrayLight02
        }
    )

    var ageStr = stringResource(R.string.key_Age)
    var ageValueStr = stringResource(R.string.key_Age_Value)
    var genderStr = stringResource(R.string.student_gender)
    var genderValueStr = stringResource(R.string.key_Female)
    var fatherNameStr = stringResource(R.string.f_name)
    var fatherNameValueStr = stringResource(R.string.key_Rakesh_Sharma)
    var fatherProfessionStr = stringResource(R.string.f_occupation)
    var fatherProfessionValueStr = stringResource(R.string.key_business_man)
    var motherNameStr = stringResource(R.string.m_name)
    var motherNameValueStr = stringResource(R.string.key_suman_sharma)
    var motherProfessionStr = stringResource(R.string.m_occupation)
    var motherProfessionValueStr = stringResource(R.string.key_House_Wife)
    var residenceStr = stringResource(R.string.select_Residence)
    var residenceValueStr = stringResource(R.string.key_Urban)
    var parentPhoneNoStr = stringResource(R.string.key_phone_no)
    var parentPhoneNoValueStr = stringResource(R.string.key_fake_no)
    var schoolTypeStr = stringResource(R.string.type_school)
    var schoolTypeValueStr = stringResource(R.string.txt_Private)
    var schoolBoardStr = stringResource(R.string.key_school_board)
    var schoolBoardValueStr = stringResource(R.string.txt_CBSE)
    var educationDetailsStr = stringResource(R.string.key_educational_details)
    var educationDetailsValueStr = stringResource(R.string.key_continuous_education)

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = if (isSystemInDarkTheme()) {
            CardDefaults.cardColors(Dark_02)
        } else {
            CardDefaults.cardColors(
                containerColor = White,
                contentColor = White,
                disabledContentColor = White,
                disabledContainerColor = White
            )
        },
        border = selectedBorder
    ) {
        Column(
            modifier = Modifier.padding(
                start = 5.dp, end = 5.dp, top = 5.dp, bottom = 5.dp
            ), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.key_personal_details),
                fontSize = 15.sp,
                fontFamily = fontSemiBold,
                color = GrayLight07,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(top = 10.dp, start = 16.dp, bottom = 16.dp)
                    .fillMaxWidth()
            )

            ViewPersonalDataUI(ageStr, ageValueStr)

            ViewPersonalDataUI(genderStr, genderValueStr)

            ViewPersonalDataUI(fatherNameStr, fatherNameValueStr)

            ViewPersonalDataUI(fatherProfessionStr, fatherProfessionValueStr)

            ViewPersonalDataUI(motherNameStr, motherNameValueStr)

            ViewPersonalDataUI(motherProfessionStr, motherProfessionValueStr)

            ViewPersonalDataUI(residenceStr, residenceValueStr)

            ViewPersonalDataUI(parentPhoneNoStr, parentPhoneNoValueStr)

            ViewPersonalDataUI(schoolTypeStr, schoolTypeValueStr)

            ViewPersonalDataUI(schoolBoardStr, schoolBoardValueStr)

            ViewPersonalDataUI(educationDetailsStr, educationDetailsValueStr)

        }
    }
}

@Composable
fun ViewClassroomObservationCardUI() {
    val selectedBorder = BorderStroke(
        width = 0.5.dp, if (isSystemInDarkTheme()) {
            Dark_02
        } else {
            GrayLight02
        }
    )

    var showDataUI by remember { mutableStateOf(true) }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = if (isSystemInDarkTheme()) {
            CardDefaults.cardColors(Dark_02)
        } else {
            CardDefaults.cardColors(
                containerColor = White,
                contentColor = White,
                disabledContentColor = White,
                disabledContainerColor = White
            )
        },
        border = selectedBorder
    ) {
        Column(
            modifier = Modifier.padding(0.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.background(BlueBackground4),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.key_classroom_observation),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 12.dp, end = 8.dp),
                    fontFamily = fontSemiBold,
                    fontSize = 15.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_TITLE_TEXT
                    } else {
                        GrayLight07
                    },
                    textAlign = TextAlign.Start
                )

                Spacer(Modifier.weight(1f))

                IconButton(onClick = {
                    showDataUI = !showDataUI
                }) {
                    Icon(
                        imageVector = if (showDataUI) ImageVector.vectorResource(id = R.drawable.upper_arrow_img)
                        else ImageVector.vectorResource(id = R.drawable.down_arrow_img),
                        tint = Color.Unspecified,
                        contentDescription = IMG_DESCRIPTION,
                        modifier = Modifier
                            .background(Color.Unspecified)
                            .size(25.dp)
                    )
                }
            }

            if (showDataUI) {
                Column {
                    Column(
                        modifier = Modifier.padding(5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.txt_Screening),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, end = 8.dp, top = 8.dp),
                            fontFamily = fontMedium,
                            fontSize = 15.sp,
                            color = if (isSystemInDarkTheme()) {
                                DARK_TITLE_TEXT
                            } else {
                                Black
                            },
                            textAlign = TextAlign.Start
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, top = 8.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.key_dots),
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )

                            Text(
                                text = stringResource(R.string.key_seems_fearful_predominantly),
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, top = 8.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.key_dots),
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )

                            Text(
                                text = stringResource(R.string.key_showed_shortcomings_limitations),
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, top = 8.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.key_dots),
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )

                            Text(
                                text = stringResource(R.string.key_seems_fearful),
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    Divider(
                        color = GrayLight02,
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 0.dp)
                    )

                    Spacer(Modifier.height(16.dp))

                    Column(
                        modifier = Modifier.padding(5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.txt_Advance_Screening),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, end = 8.dp),
                            fontFamily = fontMedium,
                            fontSize = 15.sp,
                            color = if (isSystemInDarkTheme()) {
                                DARK_TITLE_TEXT
                            } else {
                                Black
                            },
                            textAlign = TextAlign.Start
                        )

                        Text(
                            text = stringResource(R.string.key_action_initiated_yet),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
                            fontFamily = fontRegular,
                            fontSize = 15.sp,
                            color = if (isSystemInDarkTheme()) {
                                DARK_TITLE_TEXT
                            } else {
                                Black1
                            },
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ViewStrengthsAbilityCardUI() {
    val selectedBorder = BorderStroke(
        width = 0.5.dp, if (isSystemInDarkTheme()) {
            Dark_02
        } else {
            GrayLight02
        }
    )

    var showDataUI by remember { mutableStateOf(true) }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = if (isSystemInDarkTheme()) {
            CardDefaults.cardColors(Dark_02)
        } else {
            CardDefaults.cardColors(
                containerColor = White,
                contentColor = White,
                disabledContentColor = White,
                disabledContainerColor = White
            )
        },
        border = selectedBorder
    ) {
        Column(
            modifier = Modifier.padding(0.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.background(BlueBackground4),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.key_strengths_and_abilities),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 12.dp, end = 8.dp),
                    fontFamily = fontSemiBold,
                    fontSize = 15.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_TITLE_TEXT
                    } else {
                        GrayLight07
                    },
                    textAlign = TextAlign.Start
                )

                Spacer(Modifier.weight(1f))

                IconButton(onClick = {
                    showDataUI = !showDataUI
                }) {
                    Icon(
                        imageVector = if (showDataUI) ImageVector.vectorResource(id = R.drawable.upper_arrow_img)
                        else ImageVector.vectorResource(id = R.drawable.down_arrow_img),
                        tint = Color.Unspecified,
                        contentDescription = IMG_DESCRIPTION,
                        modifier = Modifier
                            .background(Color.Unspecified)
                            .size(25.dp)
                    )
                }
            }

            if (showDataUI) {
                Column {
                    Column(
                        modifier = Modifier.padding(5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.key_academic_performance),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, end = 8.dp, top = 8.dp),
                            fontFamily = fontMedium,
                            fontSize = 15.sp,
                            color = if (isSystemInDarkTheme()) {
                                DARK_TITLE_TEXT
                            } else {
                                Black
                            },
                            textAlign = TextAlign.Start
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, top = 8.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.key_dots),
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )

                            Text(
                                text = stringResource(R.string.key_seems_fearful_predominantly),
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, top = 8.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.key_dots),
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )

                            Text(
                                text = stringResource(R.string.key_showed_shortcomings_limitations),
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, top = 8.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.key_dots),
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )

                            Text(
                                text = stringResource(R.string.key_seems_fearful),
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    Divider(
                        color = GrayLight02,
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 0.dp)
                    )

                    Spacer(Modifier.height(16.dp))

                    Column(
                        modifier = Modifier.padding(5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.key_interests),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, end = 8.dp),
                            fontFamily = fontMedium,
                            fontSize = 15.sp,
                            color = if (isSystemInDarkTheme()) {
                                DARK_TITLE_TEXT
                            } else {
                                Black
                            },
                            textAlign = TextAlign.Start
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, top = 8.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.key_dots),
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )

                            Text(
                                text = stringResource(R.string.key_seems_fearful_predominantly),
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, top = 8.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.key_dots),
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )

                            Text(
                                text = stringResource(R.string.key_showed_shortcomings_limitations),
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, top = 8.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.key_dots),
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )

                            Text(
                                text = stringResource(R.string.key_seems_fearful),
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    Divider(
                        color = GrayLight02,
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 0.dp)
                    )

                    Spacer(Modifier.height(16.dp))

                    Column(
                        modifier = Modifier.padding(5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.key_strength),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, end = 8.dp),
                            fontFamily = fontMedium,
                            fontSize = 15.sp,
                            color = if (isSystemInDarkTheme()) {
                                DARK_TITLE_TEXT
                            } else {
                                Black
                            },
                            textAlign = TextAlign.Start
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, top = 8.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.key_dots),
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )

                            Text(
                                text = stringResource(R.string.key_seems_fearful_predominantly),
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, top = 8.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.key_dots),
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )

                            Text(
                                text = stringResource(R.string.key_showed_shortcomings_limitations),
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, top = 8.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.key_dots),
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )

                            Text(
                                text = stringResource(R.string.key_seems_fearful),
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    Divider(
                        color = GrayLight02,
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 0.dp)
                    )

                    Spacer(Modifier.height(16.dp))

                    Column(
                        modifier = Modifier.padding(5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.key_academic_performance),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, end = 8.dp),
                            fontFamily = fontMedium,
                            fontSize = 15.sp,
                            color = if (isSystemInDarkTheme()) {
                                DARK_TITLE_TEXT
                            } else {
                                Black
                            },
                            textAlign = TextAlign.Start
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, top = 8.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.key_dots),
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )

                            Text(
                                text = stringResource(R.string.key_seems_fearful_predominantly),
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, top = 8.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.key_dots),
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )

                            Text(
                                text = stringResource(R.string.key_showed_shortcomings_limitations),
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, top = 8.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.key_dots),
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )

                            Text(
                                text = stringResource(R.string.key_seems_fearful),
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    Divider(
                        color = GrayLight02,
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 0.dp)
                    )

                    Spacer(Modifier.height(16.dp))

                    Column(
                        modifier = Modifier.padding(5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.key_temperament),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, end = 8.dp),
                            fontFamily = fontMedium,
                            fontSize = 15.sp,
                            color = if (isSystemInDarkTheme()) {
                                DARK_TITLE_TEXT
                            } else {
                                Black
                            },
                            textAlign = TextAlign.Start
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, top = 8.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.key_dots),
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )

                            Text(
                                text = stringResource(R.string.key_seems_fearful_predominantly),
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, top = 8.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.key_dots),
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )

                            Text(
                                text = stringResource(R.string.key_showed_shortcomings_limitations),
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, top = 8.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.key_dots),
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )

                            Text(
                                text = stringResource(R.string.key_seems_fearful),
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .padding(start = 8.dp, top = 4.dp),
                                fontFamily = fontRegular,
                                fontSize = 15.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black1
                                },
                                textAlign = TextAlign.Start
                            )
                        }

                        Spacer(Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ViewReportDataUI(screenStr: String, doneStr: String) {
    Row(
        modifier = Modifier.padding(horizontal = 6.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(30.dp)
                .height(30.dp)
                .background(Color.Unspecified)
        ) {
            Image(
                painter = painterResource(id = R.drawable.okay_check_img),
                contentDescription = IMG_DESCRIPTION,
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                    .background(Color.Unspecified),
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier
                .padding(
                    start = 6.dp, end = 6.dp, top = 6.dp, bottom = 6.dp
                )
                .weight(1f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = screenStr,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(start = 8.dp, end = 8.dp),
                fontFamily = fontRegular,
                fontSize = 16.sp,
                color = if (isSystemInDarkTheme()) {
                    DARK_TITLE_TEXT
                } else {
                    Black
                },
                textAlign = TextAlign.Start
            )

            Text(
                text = doneStr,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(start = 8.dp, end = 8.dp),
                fontFamily = fontRegular,
                fontSize = 13.sp,
                color = if (isSystemInDarkTheme()) {
                    DARK_TITLE_TEXT
                } else {
                    LightGreen01
                },
                textAlign = TextAlign.Start
            )
        }

        TextWithIconOnRight(
            text = stringResource(R.string.view_report),
            icon = ImageVector.vectorResource(id = R.drawable.right_arrow_img),
            textColor = if (isSystemInDarkTheme()) {
                PrimaryBlue4
            } else {
                PrimaryBlue4
            },
            iconColor = if (isSystemInDarkTheme()) {
                PrimaryBlue4
            } else {
                PrimaryBlue4
            },
            modifier = Modifier.padding(start = 8.dp, top = 8.dp),
            onClick = {})
    }
}

@Composable
fun ViewPersonalDataUI(headingStr: String, valueStr: String) {
    Row(
        modifier = Modifier.padding(horizontal = 6.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = headingStr,
            modifier = Modifier
                .wrapContentWidth()
                .padding(start = 10.dp, end = 8.dp),
            fontFamily = fontRegular,
            fontSize = 13.sp,
            color = if (isSystemInDarkTheme()) {
                DARK_TITLE_TEXT
            } else {
                GrayLight08
            },
            textAlign = TextAlign.Start
        )

        Spacer(Modifier.weight(1f))

        Text(
            text = valueStr,
            modifier = Modifier
                .wrapContentWidth()
                .padding(start = 10.dp, end = 8.dp),
            fontFamily = fontRegular,
            fontSize = 13.sp,
            color = if (isSystemInDarkTheme()) {
                DARK_TITLE_TEXT
            } else {
                Black
            },
            textAlign = TextAlign.Start
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ViewScreeningProfileDetailsScreenPreview() {
    val onNext: () -> Unit = {}
    val onBack: () -> Unit = {}
    ViewScreeningProfileDetailsScreen(onNext, onBack)
}