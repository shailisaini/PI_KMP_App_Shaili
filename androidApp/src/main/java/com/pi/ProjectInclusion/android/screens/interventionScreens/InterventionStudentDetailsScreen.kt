package com.pi.ProjectInclusion.android.screens.interventionScreens

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
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
import com.pi.ProjectInclusion.GrayLight02
import com.pi.ProjectInclusion.GrayLight03
import com.pi.ProjectInclusion.LightOrange
import com.pi.ProjectInclusion.LightOrange1
import com.pi.ProjectInclusion.LightPink02
import com.pi.ProjectInclusion.LightPurple04
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.PrimaryBlue3
import com.pi.ProjectInclusion.White
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.BackButtonPress
import com.pi.ProjectInclusion.android.common_UI.CustomHorizontalProgressBar
import com.pi.ProjectInclusion.android.common_UI.DetailsBackgroundUi
import com.pi.ProjectInclusion.android.navigation.AppRoute
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION
import com.pi.ProjectInclusion.contactUsTxt
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterventionStudentDetailsScreen(navHostController: NavHostController) {

    val selectedBorder = BorderStroke(
        width = 1.dp, if (isSystemInDarkTheme()) {
            LightPurple04
        } else {
            LightPurple04
        }
    )

    val selectedBorder1 = BorderStroke(
        width = 1.dp, if (isSystemInDarkTheme()) {
            GrayLight02
        } else {
            GrayLight02
        }
    )

    var showSocialAndCommunicationUI by remember { mutableStateOf(false) }
    var showCognitiveUI by remember { mutableStateOf(false) }
    var showBehavioralUI by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    var isBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true, confirmValueChange = { it != SheetValue.Hidden })

    BottomSheetCamGalScreen(
        isBottomSheetVisible = isBottomSheetVisible,
        sheetState = sheetState,
        onDismiss = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                isBottomSheetVisible = false
            }
        })

    DetailsBackgroundUi(
        imgUrl = "",
        stringResource(R.string.txt_Student_Name),
        stringResource(R.string.txt_Grade_5),
        isShowBackButton = true,
        isShowProfile = true,
        isShowMoreInfo = true,
        onBackButtonClick = {
            BackButtonPress(navHostController, AppRoute.InterventionScreen.route)
        },
        onMoreInfoClick = {},
        content = {
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .background(
                        color = if (isSystemInDarkTheme()) {
                            Dark_01
                        } else {
                            White
                        }
                    )
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp, start = 12.dp, end = 12.dp)
                            .background(
                                color = if (isSystemInDarkTheme()) {
                                    Dark_01
                                } else {
                                    White
                                }
                            ), verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = stringResource(R.string.txt_following_domains_disc),
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Normal,
                            fontSize = 13.sp,
                            color = if (isSystemInDarkTheme()) {
                                DARK_TITLE_TEXT
                            } else {
                                Gray
                            },
                            textAlign = TextAlign.Start
                        )

//                      This is use for Social and communication content
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
                                modifier = Modifier.padding(
                                    start = 8.dp, end = 8.dp
                                ),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = stringResource(R.string.txt_Social_communication),
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
                                        .padding(start = 12.dp, end = 8.dp)
                                        .wrapContentHeight(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .width(225.dp)
                                            .wrapContentHeight(),
                                        horizontalAlignment = Alignment.Start,
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
                                                text = stringResource(R.string.txt_Progress_achieved),
                                                modifier = Modifier.wrapContentWidth(),
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
                                                modifier = Modifier.wrapContentWidth(),
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
                                            }) {
                                        Image(
                                            painter = if (showSocialAndCommunicationUI == false) {
                                                painterResource(R.drawable.down_arrow_img)
                                            } else {
                                                painterResource(R.drawable.upper_arrow_img)
                                            },
                                            contentDescription = IMG_DESCRIPTION,
                                            modifier = Modifier.background(Color.Unspecified),
                                            colorFilter = ColorFilter.tint(Color.Companion.Black)
                                        )
                                    }
                                }
                            }
                        }

                        if (showSocialAndCommunicationUI) {
                            SocialAndCommunicationUI(navHostController)
                        }

//                      This is use for Cognitive content
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
                                modifier = Modifier.padding(
                                    start = 8.dp, end = 8.dp
                                ),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = stringResource(R.string.txt_Cognitive),
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
                                        .padding(start = 12.dp, end = 8.dp)
                                        .wrapContentHeight(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .width(225.dp)
                                            .wrapContentHeight(),
                                        horizontalAlignment = Alignment.Start,
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
                                                text = stringResource(R.string.txt_Progress_achieved),
                                                modifier = Modifier.wrapContentWidth(),
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
                                                text = "20%",
                                                modifier = Modifier.wrapContentWidth(),
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
                                                .wrapContentHeight(),
                                            horizontalAlignment = Alignment.Start,
                                            verticalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            val num: Float = (20.toFloat() / 100)
                                            CustomHorizontalProgressBar(
                                                num, LightPurple04, GrayLight03
                                            )
                                        }
                                    }

                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.Top)
                                            .background(Color.Unspecified)
                                            .clickable {
                                                showCognitiveUI = if (showCognitiveUI == false) {
                                                    true
                                                } else {
                                                    false
                                                }
                                            }) {
                                        Image(
                                            painter = if (showCognitiveUI == false) {
                                                painterResource(R.drawable.down_arrow_img)
                                            } else {
                                                painterResource(R.drawable.upper_arrow_img)
                                            },
                                            contentDescription = IMG_DESCRIPTION,
                                            modifier = Modifier.background(Color.Unspecified),
                                            colorFilter = ColorFilter.tint(Color.Companion.Black)
                                        )
                                    }
                                }
                            }
                        }

                        if (showCognitiveUI) {
                            CognitiveUI()
                        }

//                      This is use for Behavioral content
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
                                modifier = Modifier.padding(
                                    start = 8.dp, end = 8.dp
                                ),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = stringResource(R.string.txt_Behavioral),
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
                                        .padding(start = 12.dp, end = 8.dp)
                                        .wrapContentHeight(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .width(225.dp)
                                            .wrapContentHeight(),
                                        horizontalAlignment = Alignment.Start,
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
                                                text = stringResource(R.string.txt_Progress_achieved),
                                                modifier = Modifier.wrapContentWidth(),
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
                                                text = "0%",
                                                modifier = Modifier.wrapContentWidth(),
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
                                                .wrapContentHeight(),
                                            horizontalAlignment = Alignment.Start,
                                            verticalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            val num: Float = (0.toFloat() / 100)
                                            CustomHorizontalProgressBar(
                                                num, LightPurple04, GrayLight03
                                            )
                                        }
                                    }

                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.Top)
                                            .background(Color.Unspecified)
                                            .clickable {
                                                showBehavioralUI = if (showBehavioralUI == false) {
                                                    true
                                                } else {
                                                    false
                                                }
                                            }) {
                                        Image(
                                            painter = if (showBehavioralUI == false) {
                                                painterResource(R.drawable.down_arrow_img)
                                            } else {
                                                painterResource(R.drawable.upper_arrow_img)
                                            },
                                            contentDescription = IMG_DESCRIPTION,
                                            modifier = Modifier.background(Color.Unspecified),
                                            colorFilter = ColorFilter.tint(Color.Companion.Black)
                                        )
                                    }
                                }
                            }
                        }

                        if (showBehavioralUI) {
                            BehavioralUI()
                        }

//                      This is use for Uploaded Documents content
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 16.dp)
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
                                modifier = Modifier.padding(
                                    start = 8.dp, end = 8.dp
                                ),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = stringResource(R.string.txt_We_Invite_disc),
                                    modifier = Modifier.padding(
                                        start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp
                                    ),
                                    fontStyle = FontStyle.Normal,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 13.sp,
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
                                    .width(140.dp)
                                    .padding(start = 16.dp, bottom = 8.dp, top = 8.dp)
                                    .wrapContentHeight(),
                                shape = RoundedCornerShape(8.dp),
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
                                        .height(36.dp)
                                        .align(Alignment.CenterHorizontally)
                                        .clickable {
                                            scope.launch {
                                                isBottomSheetVisible = true
                                                sheetState.expand()
                                            }
                                        },
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .padding(end = 8.dp)
                                            .background(Color.Unspecified)
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.upload),
                                            contentDescription = IMG_DESCRIPTION,
                                            modifier = Modifier.background(Color.Unspecified),
                                            colorFilter = ColorFilter.tint(Color.White)
                                        )
                                    }

                                    Text(
                                        text = stringResource(R.string.txt_Upload_Doc),
                                        modifier = Modifier.wrapContentWidth(),
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

                            Row(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 16.dp, bottom = 12.dp, top = 8.dp, end = 16.dp)
                                    .wrapContentHeight(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(R.string.txt_Uploaded_Documents),
                                    modifier = Modifier.wrapContentWidth(),
                                    fontStyle = FontStyle.Normal,
                                    fontWeight = FontWeight.SemiBold,
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
                                    text = stringResource(R.string.txt_View_All),
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .clickable {
                                            navHostController.navigate(AppRoute.UploadedDocuments.route)
                                        },
                                    fontStyle = FontStyle.Normal,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp,
                                    color = if (isSystemInDarkTheme()) {
                                        White
                                    } else {
                                        PrimaryBlue
                                    },
                                    textAlign = TextAlign.Center
                                )
                            }

                            Divider(
                                color = GrayLight02,
                                thickness = 1.dp,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )

                            Row(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(
                                        start = 16.dp,
                                        bottom = 16.dp,
                                        top = 16.dp,
                                        end = 16.dp
                                    )
                                    .wrapContentHeight(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(end = 8.dp)
                                        .background(Color.Unspecified)
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.docs_img),
                                        contentDescription = IMG_DESCRIPTION,
                                        modifier = Modifier.background(Color.Unspecified),
                                    )
                                }

                                Column(
                                    modifier = Modifier.padding(
                                        start = 8.dp, end = 8.dp
                                    ),
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = stringResource(R.string.txt_ScreeningReport_doc),
                                        modifier = Modifier.wrapContentWidth(),
                                        fontStyle = FontStyle.Normal,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 12.sp,
                                        color = if (isSystemInDarkTheme()) {
                                            DARK_TITLE_TEXT
                                        } else {
                                            Black
                                        },
                                        textAlign = TextAlign.Center
                                    )

                                    Text(
                                        text = stringResource(R.string.txt_date),
                                        modifier = Modifier.wrapContentWidth(),
                                        fontStyle = FontStyle.Normal,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 11.sp,
                                        color = if (isSystemInDarkTheme()) {
                                            DARK_BODY_TEXT
                                        } else {
                                            Gray
                                        },
                                        textAlign = TextAlign.Center
                                    )
                                }

                                Spacer(modifier = Modifier.weight(1f))

                                Box(
                                    modifier = Modifier.background(Color.Unspecified)
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.docs_preview_img),
                                        contentDescription = IMG_DESCRIPTION,
                                        modifier = Modifier.background(Color.Unspecified)
                                    )
                                }
                            }

                            Divider(
                                color = GrayLight02,
                                thickness = 1.dp,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )

                            Row(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(
                                        start = 16.dp,
                                        bottom = 16.dp,
                                        top = 16.dp,
                                        end = 16.dp
                                    )
                                    .wrapContentHeight(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(end = 8.dp)
                                        .background(Color.Unspecified)
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.pdf_img),
                                        contentDescription = IMG_DESCRIPTION,
                                        modifier = Modifier.background(Color.Unspecified),
                                    )
                                }

                                Column(
                                    modifier = Modifier.padding(
                                        start = 8.dp, end = 8.dp
                                    ),
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = stringResource(R.string.txt_ScreeningReport_pdf),
                                        modifier = Modifier.wrapContentWidth(),
                                        fontStyle = FontStyle.Normal,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 12.sp,
                                        color = if (isSystemInDarkTheme()) {
                                            DARK_TITLE_TEXT
                                        } else {
                                            Black
                                        },
                                        textAlign = TextAlign.Center
                                    )

                                    Text(
                                        text = stringResource(R.string.txt_date),
                                        modifier = Modifier.wrapContentWidth(),
                                        fontStyle = FontStyle.Normal,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 11.sp,
                                        color = if (isSystemInDarkTheme()) {
                                            DARK_BODY_TEXT
                                        } else {
                                            Gray
                                        },
                                        textAlign = TextAlign.Center
                                    )
                                }

                                Spacer(modifier = Modifier.weight(1f))

                                Box(
                                    modifier = Modifier.background(Color.Unspecified)
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.docs_preview_img),
                                        contentDescription = IMG_DESCRIPTION,
                                        modifier = Modifier.background(Color.Unspecified)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        })
}

@Composable
private fun SocialAndCommunicationUI(navHostController: NavHostController) {
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
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.txt_Socialization_Domain),
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
                        append(stringResource(R.string.txt_Progress_achieved))
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
                    .clickable {
                        navHostController.navigate(AppRoute.InterventionAcceptLevel.route)
                    }) {
                Image(
                    painter = painterResource(R.drawable.right_arrow_img),
                    contentDescription = IMG_DESCRIPTION,
                    modifier = Modifier.background(Color.Unspecified),
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
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.txt_Sensory_Domain),
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
                        append(stringResource(R.string.txt_Progress_achieved))
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
                    modifier = Modifier.background(Color.Unspecified),
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
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.txt_Socialization_Domain),
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
                        append(stringResource(R.string.txt_Progress_achieved))
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
                    modifier = Modifier.background(Color.Unspecified),
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
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.txt_Sensory_Domain),
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
                        append(stringResource(R.string.txt_Progress_achieved))
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
                    modifier = Modifier.background(Color.Unspecified),
                    colorFilter = ColorFilter.tint(Color.Gray)
                )
            }
        }
    }
}

@Composable
fun BehavioralUI() {
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
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.txt_Socialization_Domain),
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
                        append(stringResource(R.string.txt_Progress_achieved))
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
                    modifier = Modifier.background(Color.Unspecified),
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
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.txt_Sensory_Domain),
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
                        append(stringResource(R.string.txt_Progress_achieved))
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
                    modifier = Modifier.background(Color.Unspecified),
                    colorFilter = ColorFilter.tint(Color.Gray)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetCamGalScreen(
    isBottomSheetVisible: Boolean,
    sheetState: SheetState,
    onDismiss: () -> Unit,
) {

    val context: Context = LocalContext.current

    if (isBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            containerColor = if (isSystemInDarkTheme()) {
                Dark_01
            } else {
                Color.White
            },
            contentColor = MaterialTheme.colorScheme.onSurface,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            dragHandle = null,
            scrimColor = if (isSystemInDarkTheme()) {
                DARK_TITLE_TEXT.copy(alpha = 0.5f)
            } else {
                Color.Black.copy(alpha = 0.5f)
            },
            windowInsets = WindowInsets.ime
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(vertical = 15.dp, horizontal = 15.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "", style = MaterialTheme.typography.headlineLarge.copy(
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black
                                }, fontSize = 18.sp, fontFamily = FontFamily(
                                    Font(R.font.roboto_bold, FontWeight.Bold)
                                ), textAlign = TextAlign.Start
                            )
                        )

                        Image(
                            painter = painterResource(R.drawable.line),
                            contentDescription = IMG_DESCRIPTION,
                            modifier = Modifier
                                .wrapContentWidth()
                                .align(Alignment.CenterVertically)
                                .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                                .clickable {
                                    onDismiss.invoke()
                                }
                                .clip(RoundedCornerShape(100.dp))
                                .border(
                                    width = 2.dp, color = if (isSystemInDarkTheme()) {
                                        DARK_TITLE_TEXT
                                    } else {
                                        Gray
                                    }, shape = CircleShape
                                ))

                        Text(
                            text = "", style = MaterialTheme.typography.headlineLarge.copy(
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black
                                }, fontSize = 18.sp, fontFamily = FontFamily(
                                    Font(R.font.roboto_bold, FontWeight.Bold)
                                ), textAlign = TextAlign.Start
                            )
                        )
                    }

                    Text(
                        text = stringResource(id = R.string.txt_Choose_option),
                        color = if (isSystemInDarkTheme()) {
                            DARK_BODY_TEXT
                        } else {
                            Gray
                        },
                        fontSize = 16.sp,
                        fontFamily = FontFamily(
                            Font(R.font.roboto_regular, FontWeight.Medium)
                        ),
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(top = 16.dp, start = 16.dp)
                            .fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 32.dp, start = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.CenterVertically)
                                .padding(start = 16.dp, end = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(Color.Unspecified)
                                    .border(
                                        width = 1.dp,
                                        color = GrayLight02,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.camera_img),
                                    contentDescription = IMG_DESCRIPTION,
                                    modifier = Modifier
                                        .background(Color.Unspecified)
                                        .size(35.dp)
                                        .clickable {
                                            onDismiss.invoke()
                                            context.toast("Under processing for use camera...")
                                        },
                                )
                            }

                            Text(
                                text = stringResource(R.string.txt_Camera),
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    color = if (isSystemInDarkTheme()) {
                                        DARK_TITLE_TEXT
                                    } else {
                                        contactUsTxt
                                    }, fontSize = 14.sp, fontFamily = FontFamily(
                                        Font(R.font.roboto_medium, FontWeight.Medium)
                                    ), textAlign = TextAlign.Start
                                )
                            )
                        }

                        Column(
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.CenterVertically)
                                .padding(start = 16.dp, end = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(Color.Unspecified)
                                    .border(
                                        width = 1.dp,
                                        color = GrayLight02,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.gallery_img),
                                    contentDescription = IMG_DESCRIPTION,
                                    modifier = Modifier
                                        .background(Color.Unspecified)
                                        .size(35.dp)
                                        .clickable {
                                            onDismiss.invoke()
                                            context.toast("Under processing for use gallery...")
                                        },
                                )
                            }

                            Text(
                                text = stringResource(R.string.txt_Gallery),
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    color = if (isSystemInDarkTheme()) {
                                        DARK_TITLE_TEXT
                                    } else {
                                        contactUsTxt
                                    }, fontSize = 14.sp, fontFamily = FontFamily(
                                        Font(R.font.roboto_medium, FontWeight.Medium)
                                    ), textAlign = TextAlign.Start
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}