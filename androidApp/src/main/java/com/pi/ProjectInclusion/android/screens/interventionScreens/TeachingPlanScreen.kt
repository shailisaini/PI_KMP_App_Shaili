package com.pi.ProjectInclusion.android.screens.interventionScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.DARK_BODY_TEXT
import com.pi.ProjectInclusion.DARK_TITLE_TEXT
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.GrayLight02
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.White
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.BackButtonPress
import com.pi.ProjectInclusion.android.common_UI.DetailsNoImgBackgroundUi
import com.pi.ProjectInclusion.android.navigation.AppRoute
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.fontRegular
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeachingPlanScreen(navHostController: NavHostController) {

    DetailsNoImgBackgroundUi(
        backgroundColor = PrimaryBlue,
        pageTitle = stringResource(R.string.txt_Teaching_Plan),
        moreInfoIcon = painterResource(id = R.drawable.close_img),
        isShowBackButton = true,
        isShowMoreInfo = true,
        onBackButtonClick = {
            BackButtonPress(navHostController, AppRoute.InterventionAcceptLevel.route)
        },
        onMoreInfoClick = {
            BackButtonPress(navHostController, AppRoute.InterventionAcceptLevel.route)
        },
        content = {
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp)
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
                            .padding(bottom = 16.dp, start = 8.dp, end = 8.dp)
                            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.txt_Concern),
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Start,
                            fontFamily = fontMedium,
                            fontSize = 14.sp,
                            color = if (isSystemInDarkTheme()) {
                                DARK_TITLE_TEXT
                            } else {
                                Black
                            }
                        )

                        Text(
                            text = stringResource(R.string.txt_difficulty_while_climbing),
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
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

                        Text(
                            text = stringResource(R.string.txt_Goal),
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Start,
                            fontFamily = fontMedium,
                            fontSize = 14.sp,
                            color = if (isSystemInDarkTheme()) {
                                DARK_TITLE_TEXT
                            } else {
                                Black
                            }
                        )

                        Text(
                            text = stringResource(R.string.txt_Able_climb_independently),
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
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

                        Text(
                            text = stringResource(R.string.txt_Accommodation_Flexibility),
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Start,
                            fontFamily = fontMedium,
                            fontSize = 14.sp,
                            color = if (isSystemInDarkTheme()) {
                                DARK_TITLE_TEXT
                            } else {
                                Black
                            }
                        )

                        Text(
                            text = stringResource(R.string.txt_stairs_available_prompting),
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
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

                        Divider(
                            color = GrayLight02,
                            thickness = 1.dp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = stringResource(R.string.txt_Steps_To_Teach),
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Start,
                            fontFamily = fontMedium,
                            fontSize = 14.sp,
                            color = if (isSystemInDarkTheme()) {
                                DARK_TITLE_TEXT
                            } else {
                                Black
                            }
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                                .fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .wrapContentWidth()
                                    .background(Color.Unspecified)
                                    .align(Alignment.Top)
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.double_right_img),
                                    contentDescription = IMG_DESCRIPTION,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .background(Color.Unspecified),
                                )
                            }

                            Spacer(modifier = Modifier.width(4.dp))

                            Text(
                                text = stringResource(R.string.txt_Demonstrate_climb_slow),
                                modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                                textAlign = TextAlign.Start,
                                fontFamily = fontRegular,
                                fontSize = 14.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_BODY_TEXT
                                } else {
                                    Gray
                                }
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                                .fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .wrapContentWidth()
                                    .background(Color.Unspecified)
                                    .align(Alignment.Top)
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.double_right_img),
                                    contentDescription = IMG_DESCRIPTION,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .background(Color.Unspecified),
                                )
                            }

                            Spacer(modifier = Modifier.width(4.dp))

                            Text(
                                text = stringResource(R.string.txt_Initially_allow_climb_assistance),
                                modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                                textAlign = TextAlign.Start,
                                fontFamily = fontRegular,
                                fontSize = 14.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_BODY_TEXT
                                } else {
                                    Gray
                                }
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                                .fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .wrapContentWidth()
                                    .background(Color.Unspecified)
                                    .align(Alignment.Top)
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.double_right_img),
                                    contentDescription = IMG_DESCRIPTION,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .background(Color.Unspecified),
                                )
                            }

                            Spacer(modifier = Modifier.width(4.dp))

                            Text(
                                text = stringResource(R.string.txt_Provide_simple_clear_instructions),
                                modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                                textAlign = TextAlign.Start,
                                fontFamily = fontRegular,
                                fontSize = 14.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_BODY_TEXT
                                } else {
                                    Gray
                                }
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                                .fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .wrapContentWidth()
                                    .background(Color.Unspecified)
                                    .align(Alignment.Top)
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.double_right_img),
                                    contentDescription = IMG_DESCRIPTION,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .background(Color.Unspecified),
                                )
                            }

                            Spacer(modifier = Modifier.width(4.dp))

                            Text(
                                text = stringResource(R.string.txt_Initially_allow_climb_assistance),
                                modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                                textAlign = TextAlign.Start,
                                fontFamily = fontRegular,
                                fontSize = 14.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_BODY_TEXT
                                } else {
                                    Gray
                                }
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                                .fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .wrapContentWidth()
                                    .background(Color.Unspecified)
                                    .align(Alignment.Top)
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.double_right_img),
                                    contentDescription = IMG_DESCRIPTION,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .background(Color.Unspecified),
                                )
                            }

                            Spacer(modifier = Modifier.width(4.dp))

                            Text(
                                text = stringResource(R.string.txt_Start_training_fewer_gradually),
                                modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                                textAlign = TextAlign.Start,
                                fontFamily = fontRegular,
                                fontSize = 14.sp,
                                color = if (isSystemInDarkTheme()) {
                                    DARK_BODY_TEXT
                                } else {
                                    Gray
                                }
                            )
                        }
                    }
                }
            }
        })
}