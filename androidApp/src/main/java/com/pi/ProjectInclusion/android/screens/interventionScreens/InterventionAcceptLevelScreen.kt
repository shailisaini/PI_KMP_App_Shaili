package com.pi.ProjectInclusion.android.screens.interventionScreens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.DARK_BODY_TEXT
import com.pi.ProjectInclusion.DARK_TITLE_TEXT
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.Dark_02
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.LightOrange2
import com.pi.ProjectInclusion.LightPurple04
import com.pi.ProjectInclusion.PRIMARY_AURO_BLUE
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.PrimaryBlue3
import com.pi.ProjectInclusion.PrimaryBlueLt1
import com.pi.ProjectInclusion.White
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.BackButtonPress
import com.pi.ProjectInclusion.android.common_UI.BtnUi
import com.pi.ProjectInclusion.android.common_UI.DetailsBackgroundUi
import com.pi.ProjectInclusion.android.common_UI.NoBtnUi
import com.pi.ProjectInclusion.android.common_UI.TextWithIconOnRight
import com.pi.ProjectInclusion.android.common_UI.YesBtnUi
import com.pi.ProjectInclusion.android.navigation.AppRoute
import com.pi.ProjectInclusion.android.utils.fontBold
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.fontRegular
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION

@Composable
fun InterventionAcceptLevelScreen(navHostController: NavHostController) {

    val selectedBorder = BorderStroke(
        width = 1.dp, if (isSystemInDarkTheme()) {
            LightPurple04
        } else {
            LightPurple04
        }
    )

    var isCheckedLevelOne by remember { mutableStateOf(false) }
    var isCheckedLevelTwo by remember { mutableStateOf(false) }
    var isCheckedLevelThree by remember { mutableStateOf(false) }
    var showLevelCheckDialog by remember { mutableStateOf(false) }
    var showHowMarkLevelDialog by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    if (showLevelCheckDialog) {
        LevelCheckDialog {
            showLevelCheckDialog = false
        }
    }

    if (showHowMarkLevelDialog) {
        HowMarkLevelDialog {
            showHowMarkLevelDialog = false
        }
    }

    if (showDialog) {
        InterventionIntroDialog {
            showDialog = false
        }
    }

    DetailsBackgroundUi(
        "",
        stringResource(R.string.txt_Intervention),
        stringResource(R.string.txt_Student_Name),
        isShowBackButton = true,
        isShowProfile = false,
        isShowMoreInfo = true,
        onBackButtonClick = {
            BackButtonPress(navHostController, AppRoute.InterventionStudentDetails.route)
        },
        onMoreInfoClick = {
            showDialog = true
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp, end = 8.dp)
                    .background(
                        color = if (isSystemInDarkTheme()) {
                            Dark_01
                        } else {
                            White
                        }
                    ), verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = stringResource(R.string.txt_Select_specific_concern),
                    modifier = Modifier.padding(
                        start = 8.dp, end = 8.dp, bottom = 8.dp, top = 8.dp
                    ),
                    fontFamily = fontRegular,
                    fontSize = 13.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_TITLE_TEXT
                    } else {
                        Gray
                    },
                    textAlign = TextAlign.Start
                )

                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 8.dp, bottom = 12.dp, top = 32.dp, end = 8.dp)
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.txt_Socialization_Domain),
                        modifier = Modifier.wrapContentWidth(),
                        fontFamily = fontMedium,
                        fontSize = 14.sp,
                        color = if (isSystemInDarkTheme()) {
                            White
                        } else {
                            Black
                        },
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = stringResource(R.string.txt_how_to_mark_levels),
                        modifier = Modifier
                            .wrapContentWidth()
                            .clickable {
                                showHowMarkLevelDialog = true
                            },
                        fontFamily = fontMedium,
                        fontSize = 12.sp,
                        color = if (isSystemInDarkTheme()) {
                            White
                        } else {
                            PrimaryBlue
                        },
                        textAlign = TextAlign.Center
                    )
                }

                Card(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
                        .wrapContentHeight(),
                    colors = if (isSystemInDarkTheme()) {
                        CardDefaults.cardColors(PrimaryBlue3)
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
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier.padding(
                                start = 8.dp, end = 8.dp
                            ),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(R.string.txt_difficulty_while_climbing),
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp, end = 8.dp, top = 8.dp),
                                fontFamily = fontRegular,
                                fontSize = 12.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black
                                },
                                textAlign = TextAlign.Start
                            )

                            TextWithIconOnRight(
                                text = stringResource(R.string.txt_Read_Plan),
                                icon = ImageVector.vectorResource(id = R.drawable.right_arrow_img),
                                textColor = if (isSystemInDarkTheme()) {
                                    PRIMARY_AURO_BLUE
                                } else {
                                    PrimaryBlue
                                },
                                iconColor = if (isSystemInDarkTheme()) {
                                    PRIMARY_AURO_BLUE
                                } else {
                                    PrimaryBlue
                                },
                                modifier = Modifier.padding(start = 8.dp, top = 8.dp),
                                onClick = {
                                    navHostController.navigate(AppRoute.TeachingPlan.route)
                                })
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                                .wrapContentHeight()
                                .background(
                                    color = if (isSystemInDarkTheme()) {
                                        PrimaryBlueLt1
                                    } else {
                                        PrimaryBlueLt1
                                    }
                                ),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(
                                        start = 16.dp, end = 8.dp, bottom = 16.dp, top = 16.dp
                                    )
                            ) {
                                Checkbox(
                                    checked = isCheckedLevelOne, onCheckedChange = {
                                        isCheckedLevelOne = it
                                        showLevelCheckDialog = true
                                    }, // Disabled for display-only
                                    colors = if (isSystemInDarkTheme()) {
                                        CheckboxDefaults.colors(
                                            checkedColor = PrimaryBlue,     // Light purple-gray
                                            uncheckedColor = White,   // Same for unchecked
                                            checkmarkColor = White
                                        )
                                    } else {
                                        CheckboxDefaults.colors(
                                            checkedColor = PrimaryBlue,     // Light purple-gray
                                            uncheckedColor = White,   // Same for unchecked
                                            checkmarkColor = White
                                        )
                                    }, modifier = Modifier
                                        .size(20.dp)
                                        /*.background(
                                            color = White,
                                            shape = RoundedCornerShape(8.dp)
                                        )*/
                                )

                                Spacer(modifier = Modifier.width(4.dp))

                                Text(
                                    text = stringResource(R.string.txt_Level_1),
                                    modifier = Modifier.padding(start = 4.dp, end = 8.dp),
                                    textAlign = TextAlign.Center,
                                    fontFamily = fontRegular,
                                    fontSize = 12.sp,
                                    color = if (isSystemInDarkTheme()) {
                                        DARK_TITLE_TEXT
                                    } else {
                                        Black
                                    }
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(
                                    start = 8.dp, end = 8.dp, bottom = 16.dp, top = 16.dp
                                )
                            ) {
                                Checkbox(
                                    checked = isCheckedLevelTwo, onCheckedChange = {
                                        isCheckedLevelTwo = it
                                    }, // Disabled for display-only
                                    colors = if (isSystemInDarkTheme()) {
                                        CheckboxDefaults.colors(
                                            checkedColor = PrimaryBlue,     // Light purple-gray
                                            uncheckedColor = White,   // Same for unchecked
                                            checkmarkColor = White
                                        )
                                    } else {
                                        CheckboxDefaults.colors(
                                            checkedColor = PrimaryBlue,     // Light purple-gray
                                            uncheckedColor = White,   // Same for unchecked
                                            checkmarkColor = White
                                        )
                                    }, modifier = Modifier.size(20.dp)
                                )

                                Spacer(modifier = Modifier.width(4.dp))

                                Text(
                                    text = stringResource(R.string.txt_Level_2),
                                    modifier = Modifier.padding(start = 4.dp, end = 8.dp),
                                    textAlign = TextAlign.Center,
                                    fontFamily = fontRegular,
                                    fontSize = 12.sp,
                                    color = if (isSystemInDarkTheme()) {
                                        DARK_TITLE_TEXT
                                    } else {
                                        Black
                                    }
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(
                                    start = 8.dp, end = 16.dp, bottom = 16.dp, top = 16.dp
                                )
                            ) {
                                Checkbox(
                                    checked = isCheckedLevelThree, onCheckedChange = {
                                        isCheckedLevelThree = it
                                    }, // Disabled for display-only
                                    colors = if (isSystemInDarkTheme()) {
                                        CheckboxDefaults.colors(
                                            checkedColor = PrimaryBlue,     // Light purple-gray
                                            uncheckedColor = White,   // Same for unchecked
                                            checkmarkColor = White
                                        )
                                    } else {
                                        CheckboxDefaults.colors(
                                            checkedColor = PrimaryBlue,     // Light purple-gray
                                            uncheckedColor = White,   // Same for unchecked
                                            checkmarkColor = White
                                        )
                                    }, modifier = Modifier.size(20.dp)
                                )

                                Spacer(modifier = Modifier.width(4.dp))

                                Text(
                                    text = stringResource(R.string.txt_Level_3),
                                    modifier = Modifier.padding(start = 4.dp, end = 8.dp),
                                    textAlign = TextAlign.Center,
                                    fontFamily = fontRegular,
                                    fontSize = 12.sp,
                                    color = if (isSystemInDarkTheme()) {
                                        DARK_TITLE_TEXT
                                    } else {
                                        Black
                                    }
                                )
                            }
                        }
                    }
                }
            }
        })
}

@Composable
fun LevelCheckDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    if (isSystemInDarkTheme()) {
                        Dark_02
                    } else {
                        Color.White
                    }
                )
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(bottom = 16.dp, start = 8.dp, end = 8.dp)
                    .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .background(Color.Unspecified)
                ) {
                    Image(
                        painter = painterResource(R.drawable.level_check_ok_img),
                        contentDescription = IMG_DESCRIPTION,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(top = 16.dp)
                            .background(Color.Unspecified)
                            .align(Alignment.Center),
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.txt_Are_you_sure_Level),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    fontFamily = fontBold,
                    fontSize = 18.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_TITLE_TEXT
                    } else {
                        Black
                    },
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.txt_Are_you_sure_achieved_Level),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    fontFamily = fontRegular,
                    fontSize = 14.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_TITLE_TEXT
                    } else {
                        Gray
                    },
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .padding(bottom = 8.dp, start = 12.dp, end = 12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    NoBtnUi(
                        onClick = {
                            onDismiss()
                        }, title = stringResource(R.string.txt_No), enabled = true
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    YesBtnUi(
                        onClick = {
                            onDismiss()
                        }, title = stringResource(R.string.txt_Yes), enabled = true
                    )
                }
            }
        }
    }
}

@Composable
fun HowMarkLevelDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    if (isSystemInDarkTheme()) {
                        Dark_02
                    } else {
                        Color.White
                    }
                )
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(bottom = 16.dp, start = 8.dp, end = 8.dp)
                    .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.txt_how_to_mark_levels),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    fontFamily = fontBold,
                    fontSize = 18.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_TITLE_TEXT
                    } else {
                        Black
                    },
                    textAlign = TextAlign.Center
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                        .padding(
                            start = 8.dp, end = 16.dp, bottom = 16.dp, top = 16.dp
                        )
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.txt_Level_1_D),
                        modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                        textAlign = TextAlign.Start,
                        fontFamily = fontMedium,
                        fontSize = 14.sp,
                        color = if (isSystemInDarkTheme()) {
                            DARK_TITLE_TEXT
                        } else {
                            Black
                        }
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "40%",
                        textAlign = TextAlign.Start,
                        fontFamily = fontMedium,
                        fontSize = 14.sp,
                        color = if (isSystemInDarkTheme()) {
                            LightOrange2
                        } else {
                            LightOrange2
                        }
                    )
                }

                Text(
                    text = stringResource(R.string.txt_When_Child_Able),
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    fontFamily = fontRegular,
                    fontSize = 14.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_BODY_TEXT
                    } else {
                        Gray
                    }
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                        .padding(
                            start = 8.dp, end = 16.dp, bottom = 16.dp, top = 16.dp
                        )
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.txt_Level_2_D),
                        modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                        textAlign = TextAlign.Start,
                        fontFamily = fontMedium,
                        fontSize = 14.sp,
                        color = if (isSystemInDarkTheme()) {
                            DARK_TITLE_TEXT
                        } else {
                            Black
                        }
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "60%",
                        textAlign = TextAlign.Start,
                        fontFamily = fontMedium,
                        fontSize = 14.sp,
                        color = if (isSystemInDarkTheme()) {
                            LightOrange2
                        } else {
                            LightOrange2
                        }
                    )
                }

                Text(
                    text = stringResource(R.string.txt_Able_climb_independently),
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    fontFamily = fontRegular,
                    fontSize = 14.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_BODY_TEXT
                    } else {
                        Gray
                    }
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                        .padding(
                            start = 8.dp, end = 16.dp, bottom = 16.dp, top = 16.dp
                        )
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.txt_Level_3_D),
                        modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                        textAlign = TextAlign.Start,
                        fontFamily = fontMedium,
                        fontSize = 14.sp,
                        color = if (isSystemInDarkTheme()) {
                            DARK_TITLE_TEXT
                        } else {
                            Black
                        }
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "80% and above",
                        textAlign = TextAlign.Start,
                        fontFamily = fontMedium,
                        fontSize = 14.sp,
                        color = if (isSystemInDarkTheme()) {
                            LightOrange2
                        } else {
                            LightOrange2
                        }
                    )
                }

                Text(
                    text = stringResource(R.string.txt_stairs_available_prompting),
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    fontFamily = fontRegular,
                    fontSize = 14.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_BODY_TEXT
                    } else {
                        Gray
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
                        .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BtnUi(
                        onClick = {
                            onDismiss()
                        }, title = stringResource(R.string.txt_Ok_Got_it), enabled = true
                    )
                }
            }
        }
    }
}