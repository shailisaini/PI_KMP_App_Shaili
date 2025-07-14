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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.DARK_BODY_TEXT
import com.pi.ProjectInclusion.DARK_TITLE_TEXT
import com.pi.ProjectInclusion.DarkBlue
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.LightBlue
import com.pi.ProjectInclusion.LightOrange
import com.pi.ProjectInclusion.LightOrange1
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.PrimaryBlue3
import com.pi.ProjectInclusion.White
import com.pi.ProjectInclusion.WhiteA
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.BackButtonPress
import com.pi.ProjectInclusion.android.common_UI.CustomHorizontalProgressBar
import com.pi.ProjectInclusion.android.common_UI.DetailsBackgroundUi
import com.pi.ProjectInclusion.android.navigation.AppRoute
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION

@Composable
fun InterventionStudentDetailsScreen(navHostController: NavHostController) {

    val selectedBorder = BorderStroke(
        width = 0.5.dp, if (isSystemInDarkTheme()) {
            LightBlue
        } else {
            LightBlue
        }
    )

    val selectedBorder1 = BorderStroke(
        width = 0.5.dp, if (isSystemInDarkTheme()) {
            LightBlue
        } else {
            WhiteA
        }
    )
    var showSocialAndCommunicationUI by remember { mutableStateOf(false) }
    var showCognitiveUI by remember { mutableStateOf(false) }
    var showBehavioralUI by remember { mutableStateOf(false) }

    DetailsBackgroundUi(
        "", "Student name", "Grade 5",
        isShowBackButton = true, isShowProfile = true, isShowMoreInfo = true, onBackButtonClick = {
            BackButtonPress(navHostController, AppRoute.InterventionScreen.route)
        },
        onMoreInfoClick = {},
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 12.dp, end = 12.dp)
                    .background(
                        color = if (isSystemInDarkTheme()) {
                            Dark_01
                        } else {
                            White
                        }
                    ),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = stringResource(R.string.txt_following_domains_disc),
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp),
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_TITLE_TEXT
                    } else {
                        Gray
                    },
                    textAlign = TextAlign.Start
                )

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
                            containerColor = PrimaryBlue3,
                            contentColor = PrimaryBlue3,
                            disabledContentColor = PrimaryBlue3,
                            disabledContainerColor = PrimaryBlue3
                        )
                    },
                    border = selectedBorder
                ) {
                    Column(
                        modifier = Modifier
                            .padding(
                                start = 8.dp, end = 8.dp
                            ), horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Social and communication",
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(start = 12.dp, end = 8.dp, top = 8.dp),
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
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
                                .padding(start = 12.dp, end = 8.dp, top = 8.dp)
                                .wrapContentHeight(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier
                                    .width(225.dp)
                                    .wrapContentHeight(), horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp, bottom = 2.dp)
                                        .wrapContentHeight(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Progress achieved",
                                        modifier = Modifier
                                            .wrapContentWidth(),
                                        fontStyle = FontStyle.Normal,
                                        fontWeight = FontWeight.Normal,
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
                                        modifier = Modifier
                                            .wrapContentWidth(),
                                        fontStyle = FontStyle.Normal,
                                        fontWeight = FontWeight.Medium,
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
                                        .wrapContentHeight(), horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    val num: Float = (40.toFloat() / 100)
                                    CustomHorizontalProgressBar(num)
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .align(Alignment.Top)
                                    .background(Color.Unspecified)
                                    .clickable {
                                        showSocialAndCommunicationUI =
                                            if (showSocialAndCommunicationUI == false) {
                                                true
                                            } else {
                                                false
                                            }
                                    }
                            ) {
                                Image(
                                    painter = if (showSocialAndCommunicationUI == false) {
                                        painterResource(R.drawable.down_arrow_img)
                                    } else {
                                        painterResource(R.drawable.upper_arrow_img)
                                    },
                                    contentDescription = IMG_DESCRIPTION,
                                    modifier = Modifier
                                        .background(Color.Unspecified),
                                    colorFilter = ColorFilter.tint(Color.Companion.Black)
                                )
                            }
                        }
                    }
                }

                if (showSocialAndCommunicationUI) {
                    SocialAndCommunicationUI()
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
                            containerColor = PrimaryBlue3,
                            contentColor = PrimaryBlue3,
                            disabledContentColor = PrimaryBlue3,
                            disabledContainerColor = PrimaryBlue3
                        )
                    },
                    border = selectedBorder
                ) {
                    Column(
                        modifier = Modifier
                            .padding(
                                start = 8.dp, end = 8.dp
                            ), horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Cognitive",
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(start = 12.dp, end = 8.dp, top = 8.dp),
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
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
                                .padding(start = 12.dp, end = 8.dp, top = 8.dp)
                                .wrapContentHeight(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier
                                    .width(225.dp)
                                    .wrapContentHeight(), horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp, bottom = 2.dp)
                                        .wrapContentHeight(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Progress achieved",
                                        modifier = Modifier
                                            .wrapContentWidth(),
                                        fontStyle = FontStyle.Normal,
                                        fontWeight = FontWeight.Normal,
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
                                        modifier = Modifier
                                            .wrapContentWidth(),
                                        fontStyle = FontStyle.Normal,
                                        fontWeight = FontWeight.Medium,
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
                                        .wrapContentHeight(), horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    val num: Float = (20.toFloat() / 100)
                                    CustomHorizontalProgressBar(num)
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .align(Alignment.Top)
                                    .background(Color.Unspecified)
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.down_arrow_img),
                                    contentDescription = IMG_DESCRIPTION,
                                    modifier = Modifier
                                        .background(Color.Unspecified),
                                    colorFilter = ColorFilter.tint(Color.Companion.Black)
                                )
                            }
                        }
                    }
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
                            containerColor = PrimaryBlue3,
                            contentColor = PrimaryBlue3,
                            disabledContentColor = PrimaryBlue3,
                            disabledContainerColor = PrimaryBlue3
                        )
                    },
                    border = selectedBorder
                ) {
                    Column(
                        modifier = Modifier
                            .padding(
                                start = 8.dp, end = 8.dp
                            ), horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Behavioral",
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(start = 12.dp, end = 8.dp, top = 8.dp),
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
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
                                .padding(start = 12.dp, end = 8.dp, top = 8.dp)
                                .wrapContentHeight(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier
                                    .width(225.dp)
                                    .wrapContentHeight(), horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp, bottom = 2.dp)
                                        .wrapContentHeight(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Progress achieved",
                                        modifier = Modifier
                                            .wrapContentWidth(),
                                        fontStyle = FontStyle.Normal,
                                        fontWeight = FontWeight.Normal,
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
                                        modifier = Modifier
                                            .wrapContentWidth(),
                                        fontStyle = FontStyle.Normal,
                                        fontWeight = FontWeight.Medium,
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
                                        .wrapContentHeight(), horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    val num: Float = (0.toFloat() / 100)
                                    CustomHorizontalProgressBar(num)
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .align(Alignment.Top)
                                    .background(Color.Unspecified)
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.down_arrow_img),
                                    contentDescription = IMG_DESCRIPTION,
                                    modifier = Modifier
                                        .background(Color.Unspecified),
                                    colorFilter = ColorFilter.tint(Color.Companion.Black)
                                )
                            }
                        }
                    }
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
                    border = selectedBorder1
                ) {
                    Column(
                        modifier = Modifier
                            .padding(
                                start = 8.dp, end = 8.dp
                            ), horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.txt_We_Invite_disc),
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            color = if (isSystemInDarkTheme()) {
                                DARK_TITLE_TEXT
                            } else {
                                Gray
                            },
                            textAlign = TextAlign.Start
                        )
                    }

                    Card(
                        modifier = Modifier
                            .width(150.dp)
                            .padding(start = 8.dp, bottom = 8.dp, top = 8.dp)
                            .wrapContentHeight(),
                        shape = RoundedCornerShape(4.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = if (isSystemInDarkTheme()) {
                            CardDefaults.cardColors(DarkBlue)
                        } else {
                            CardDefaults.cardColors(
                                containerColor = DarkBlue,
                                contentColor = DarkBlue,
                                disabledContentColor = DarkBlue,
                                disabledContainerColor = DarkBlue
                            )
                        }
                    ) {
                        Row(
                            modifier = Modifier
                                .wrapContentWidth()
                                .wrapContentHeight(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(Color.Unspecified)
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.down_arrow_img),
                                    contentDescription = IMG_DESCRIPTION,
                                    modifier = Modifier
                                        .background(Color.Unspecified),
                                    colorFilter = ColorFilter.tint(Color.White)
                                )
                            }

                            Text(
                                text = "Upload Doc",
                                modifier = Modifier
                                    .wrapContentWidth(),
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                color = if (isSystemInDarkTheme()) {
                                    White
                                } else {
                                    White
                                },
                                textAlign = TextAlign.Center
                            )
                        }
                    }


                }

//                CognitiveUI()

//                BehavioralUI()
            }
        })
}

@Composable
private fun SocialAndCommunicationUI() {
    val selectedBorder = BorderStroke(
        width = 0.5.dp, if (isSystemInDarkTheme()) {
            LightOrange1
        } else {
            LightOrange1
        }
    )

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
            .wrapContentHeight(),
        colors = if (isSystemInDarkTheme()) {
            CardDefaults.cardColors(LightOrange)
        } else {
            CardDefaults.cardColors(
                containerColor = LightOrange,
                contentColor = LightOrange,
                disabledContentColor = LightOrange,
                disabledContainerColor = LightOrange
            )
        },
        border = selectedBorder
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(), horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Socialization Domain",
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                        .wrapContentWidth(),
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_TITLE_TEXT
                    } else {
                        Black
                    },
                    textAlign = TextAlign.Start
                )

                Text(
                    text = buildAnnotatedString {
                        append("Progress achieved ")
                        pushStyle(SpanStyle(color = Color.Black))
                        append("20%")
                        pop()
                    },
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                        .wrapContentWidth(),
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_BODY_TEXT
                    } else {
                        Gray
                    },
                    textAlign = TextAlign.Start
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .background(Color.Unspecified)
            ) {
                Image(
                    painter = painterResource(R.drawable.right_arrow_img),
                    contentDescription = IMG_DESCRIPTION,
                    modifier = Modifier
                        .background(Color.Unspecified),
                    colorFilter = ColorFilter.tint(Color.Gray)
                )
            }
        }
    }

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
            .wrapContentHeight(),
        colors = if (isSystemInDarkTheme()) {
            CardDefaults.cardColors(LightOrange)
        } else {
            CardDefaults.cardColors(
                containerColor = LightOrange,
                contentColor = LightOrange,
                disabledContentColor = LightOrange,
                disabledContainerColor = LightOrange
            )
        },
        border = selectedBorder
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(), horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Sensory Domain",
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                        .wrapContentWidth(),
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_TITLE_TEXT
                    } else {
                        Black
                    },
                    textAlign = TextAlign.Start
                )

                Text(
                    text = buildAnnotatedString {
                        append("Progress achieved ")
                        pushStyle(SpanStyle(color = Color.Black))
                        append("20%")
                        pop()
                    },
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                        .wrapContentWidth(),
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_BODY_TEXT
                    } else {
                        Gray
                    },
                    textAlign = TextAlign.Start
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .background(Color.Unspecified)
            ) {
                Image(
                    painter = painterResource(R.drawable.right_arrow_img),
                    contentDescription = IMG_DESCRIPTION,
                    modifier = Modifier
                        .background(Color.Unspecified),
                    colorFilter = ColorFilter.tint(Color.Gray)
                )
            }
        }
    }
}

@Composable
fun CognitiveUI() {
    val selectedBorder = BorderStroke(
        width = 0.5.dp, if (isSystemInDarkTheme()) {
            LightBlue
        } else {
            LightBlue
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp, bottom = 8.dp)
            .background(
                if (isSystemInDarkTheme()) {
                    Dark_01
                } else {
                    White
                }
            )
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp)
                .wrapContentHeight(),
            colors = if (isSystemInDarkTheme()) {
                CardDefaults.cardColors(PrimaryBlue3)
            } else {
                CardDefaults.cardColors(
                    containerColor = PrimaryBlue3,
                    contentColor = PrimaryBlue3,
                    disabledContentColor = PrimaryBlue3,
                    disabledContainerColor = PrimaryBlue3
                )
            },
            border = selectedBorder
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp
                    ), horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Cognitive",
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 12.dp, end = 8.dp, top = 8.dp),
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
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
                        .padding(start = 12.dp, end = 8.dp, top = 8.dp)
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .width(260.dp)
                            .wrapContentHeight(), horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp, bottom = 2.dp)
                                .wrapContentHeight(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Progress achieved",
                                modifier = Modifier
                                    .wrapContentWidth(),
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.Normal,
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
                                modifier = Modifier
                                    .wrapContentWidth(),
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.Medium,
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
                                .wrapContentHeight(), horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            val num: Float = (20.toFloat() / 100)
                            CustomHorizontalProgressBar(num)
                        }
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.Top)
                            .background(Color.Unspecified)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.down_arrow_img),
                            contentDescription = IMG_DESCRIPTION,
                            modifier = Modifier
                                .background(Color.Unspecified),
                            colorFilter = ColorFilter.tint(Color.Companion.Black)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BehavioralUI() {
    val selectedBorder = BorderStroke(
        width = 0.5.dp, if (isSystemInDarkTheme()) {
            LightBlue
        } else {
            LightBlue
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp, bottom = 8.dp)
            .background(
                if (isSystemInDarkTheme()) {
                    Dark_01
                } else {
                    White
                }
            )
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp)
                .wrapContentHeight(),
            colors = if (isSystemInDarkTheme()) {
                CardDefaults.cardColors(PrimaryBlue3)
            } else {
                CardDefaults.cardColors(
                    containerColor = PrimaryBlue3,
                    contentColor = PrimaryBlue3,
                    disabledContentColor = PrimaryBlue3,
                    disabledContainerColor = PrimaryBlue3
                )
            },
            border = selectedBorder
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp
                    ), horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Behavioral",
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 12.dp, end = 8.dp, top = 8.dp),
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
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
                        .padding(start = 12.dp, end = 8.dp, top = 8.dp)
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .width(260.dp)
                            .wrapContentHeight(), horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp, bottom = 2.dp)
                                .wrapContentHeight(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Progress achieved",
                                modifier = Modifier
                                    .wrapContentWidth(),
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.Normal,
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
                                modifier = Modifier
                                    .wrapContentWidth(),
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.Medium,
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
                                .wrapContentHeight(), horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            val num: Float = (0.toFloat() / 100)
                            CustomHorizontalProgressBar(num)
                        }
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.Top)
                            .background(Color.Unspecified)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.down_arrow_img),
                            contentDescription = IMG_DESCRIPTION,
                            modifier = Modifier
                                .background(Color.Unspecified),
                            colorFilter = ColorFilter.tint(Color.Companion.Black)
                        )
                    }
                }
            }
        }
    }
}