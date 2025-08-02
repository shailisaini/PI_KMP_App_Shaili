package com.pi.ProjectInclusion.android.screens.dashboardNavActivity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.BannerColor03
import com.pi.ProjectInclusion.Bg_Gray2
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.DARK_TITLE_TEXT
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.Dark_02
import com.pi.ProjectInclusion.Dark_03
import com.pi.ProjectInclusion.FaqColor1
import com.pi.ProjectInclusion.FaqColor2
import com.pi.ProjectInclusion.FaqColor3
import com.pi.ProjectInclusion.FaqColor4
import com.pi.ProjectInclusion.FaqColor5
import com.pi.ProjectInclusion.FaqColor6
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.GrayLight02
import com.pi.ProjectInclusion.HeaderColor01
import com.pi.ProjectInclusion.LightPurple04
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.PrimaryBlue3
import com.pi.ProjectInclusion.Yellow
import com.pi.ProjectInclusion.android.MyApplicationTheme
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.DetailsNoImgBackgroundUi
import com.pi.ProjectInclusion.android.common_UI.DropdownMenuUi
import com.pi.ProjectInclusion.android.common_UI.TextViewField
import com.pi.ProjectInclusion.android.screens.StudentDashboardActivity
import com.pi.ProjectInclusion.android.screens.screeningScreen.ScreeningData
import com.pi.ProjectInclusion.android.screens.screeningScreen.ScreeningFirstDataUI
import com.pi.ProjectInclusion.android.utils.fontBold
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.fontRegular
import com.pi.ProjectInclusion.constants.BackHandler
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION
import kotlinx.coroutines.launch

class FaqActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val context = LocalContext.current

            BackHandler {
                startActivity(
                    context, Intent(context, StudentDashboardActivity::class.java), null
                ).apply { (context as? Activity)?.finish() }
            }

            MyApplicationTheme {
                logger.d("Screen: " + "FaqActivity()")
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = White),
                    verticalArrangement = Arrangement.Top
                ) {
                    ShowFAQData(navController, context)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowFAQData(
    controller: NavHostController,
    context: Context,
) {

    val colors = MaterialTheme.colorScheme
    val textEnterKeyEg = stringResource(R.string.txt_Enter_Keyword)
    var searchKeyName = rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    var isCategoryVisible by rememberSaveable { mutableStateOf(false) }
    var isSubCategoryVisible by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true, confirmValueChange = { it != SheetValue.Hidden })
    var selectedCategory = stringResource(R.string.txt_category)
    var selectedSubCategory = stringResource(R.string.txt_subcategory)

    val faqListData = listOf(
        FAQData(
            stringResource(R.string.txt_General),
            FaqColor1,
            stringResource(R.string.txt_Accessibility),
            LightPurple04,
            stringResource(R.string.txt_Is_Project_Inclusion_available),
            stringResource(R.string.txt_New_User_access_first_course)
        ), FAQData(
            stringResource(R.string.txt_LMS),
            HeaderColor01,
            stringResource(R.string.txt_Course),
            FaqColor2,
            stringResource(R.string.txt_Which_course_new_access),
            stringResource(R.string.txt_New_User_access_first_course)
        ), FAQData(
            stringResource(R.string.txt_Support),
            FaqColor3,
            stringResource(R.string.txt_Technical),
            FaqColor4,
            stringResource(R.string.txt_Why_sometimes_not_open_show),
            stringResource(R.string.txt_New_User_access_first_course)
        ), FAQData(
            stringResource(R.string.txt_LMS),
            FaqColor5,
            stringResource(R.string.txt_Certification),
            FaqColor6,
            stringResource(R.string.txt_Is_Project_Inclusion_available),
            stringResource(R.string.txt_New_User_access_first_course)
        )
    )

    BackHandler {
        startActivity(
            context, Intent(context, StudentDashboardActivity::class.java), null
        ).apply { (context as? Activity)?.finish() }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 5.dp)
            .background(color = White),
        verticalArrangement = Arrangement.Top
    ) {
        DetailsNoImgBackgroundUi(
            backgroundColor = White,
            textColor = Black,
            pageTitle = stringResource(R.string.nav_faq1),
            moreInfoIcon = painterResource(id = R.drawable.vertical_dot),
            isShowBackButton = true,
            isShowMoreInfo = false,
            onBackButtonClick = {
                startActivity(
                    context, Intent(context, StudentDashboardActivity::class.java), null
                ).apply { (context as? Activity)?.finish() }
            },
            onMoreInfoClick = {},
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    SectionDivider()

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, end = 12.dp, top = 16.dp)
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            DropdownMenuUi(
                                listOf(),
                                onItemSelected = {},
                                modifier = Modifier.clickable {
                                    logger.e("Category")
                                },
                                placeholder = selectedCategory,
                                onClick = {
                                    scope.launch {
                                        isCategoryVisible = true // true under development code
                                        sheetState.expand()
                                    }
                                })
                        }

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            DropdownMenuUi(
                                listOf(),
                                onItemSelected = {},
                                modifier = Modifier.clickable {
                                    logger.e("SubCategory")
                                },
                                placeholder = selectedSubCategory,
                                onClick = {
                                    scope.launch {
                                        isSubCategoryVisible = true // true under development code
                                        sheetState.expand()
                                    }
                                })
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, end = 12.dp, top = 12.dp)
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            TextViewField(
                                isIcon = false,
                                icon = ImageVector.vectorResource(id = R.drawable.call_on_otp),
                                colors = colors,
                                text = searchKeyName,
                                trueFalse = true,
                                hint = textEnterKeyEg.toString()
                            )
                        }

                        Card(
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .width(60.dp)
                                .height(55.dp)
                                .align(Alignment.CenterVertically)
                                .padding(start = 8.dp),
                            elevation = CardDefaults.cardElevation(1.dp),
                            colors = if (isSystemInDarkTheme()) {
                                CardDefaults.cardColors(PrimaryBlue)
                            } else {
                                CardDefaults.cardColors(
                                    containerColor = PrimaryBlue,
                                    contentColor = PrimaryBlue,
                                    disabledContentColor = PrimaryBlue,
                                    disabledContainerColor = PrimaryBlue
                                )
                            }
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_search),
                                tint = White,
                                contentDescription = IMG_DESCRIPTION,
                                modifier = Modifier
                                    .background(Color.Unspecified)
                                    .size(50.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .padding(8.dp)
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.txt_Frequently_Asked_Questions),
                            fontSize = 18.sp,
                            fontFamily = fontBold,
                            color = Black,
                            modifier = Modifier.padding(top = 10.dp)
                        )

                        LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
                            items(faqListData) { faqData ->
                                FaqDataUI(faqData)
                            }
                        }
                    }
                }
            })
    }
}

@Composable
fun FaqDataUI(faqData: FAQData) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp)
            .wrapContentHeight(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = if (isSystemInDarkTheme()) {
            CardDefaults.cardColors(Dark_03)
        } else {
            CardDefaults.cardColors(
                containerColor = White,
                contentColor = White,
                disabledContentColor = White,
                disabledContainerColor = White
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(0.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = .4.dp, top = 4.dp)
                    .wrapContentHeight(), verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 8.dp, bottom = 8.dp, top = 8.dp, end = 2.dp)
                        .wrapContentHeight(),
                    shape = RoundedCornerShape(6.dp),
                    colors = if (isSystemInDarkTheme()) {
                        CardDefaults.cardColors(
                            containerColor = faqData.categoryColor,
                            contentColor = faqData.categoryColor,
                            disabledContentColor = faqData.categoryColor,
                            disabledContainerColor = faqData.categoryColor
                        )
                    } else {
                        CardDefaults.cardColors(
                            containerColor = faqData.categoryColor,
                            contentColor = faqData.categoryColor,
                            disabledContentColor = faqData.categoryColor,
                            disabledContainerColor = faqData.categoryColor
                        )
                    }
                ) {
                    Text(
                        text = faqData.categoryName,
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
                        fontFamily = fontMedium,
                        fontSize = 14.sp,
                        color = if (isSystemInDarkTheme()) {
                            White
                        } else {
                            White
                        },
                        textAlign = TextAlign.Center
                    )
                }

                Card(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 2.dp, bottom = 8.dp, top = 8.dp, end = 8.dp)
                        .wrapContentHeight(),
                    shape = RoundedCornerShape(6.dp),
                    colors = if (isSystemInDarkTheme()) {
                        CardDefaults.cardColors(
                            containerColor = faqData.subCateColor,
                            contentColor = faqData.subCateColor,
                            disabledContentColor = faqData.subCateColor,
                            disabledContainerColor = faqData.subCateColor
                        )
                    } else {
                        CardDefaults.cardColors(
                            containerColor = faqData.subCateColor,
                            contentColor = faqData.subCateColor,
                            disabledContentColor = faqData.subCateColor,
                            disabledContainerColor = faqData.subCateColor
                        )
                    }
                ) {
                    Text(
                        text = faqData.subCateName,
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
                        fontFamily = fontMedium,
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
                    .fillMaxWidth()
                    .padding(start = 12.dp)
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = faqData.questionName,
                    fontFamily = fontMedium,
                    fontSize = 14.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_TITLE_TEXT
                    } else {
                        Black
                    },
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f)
                )

                IconButton(onClick = {
                    isExpanded = !isExpanded
                }) {
                    Icon(
                        imageVector = if (isExpanded) ImageVector.vectorResource(id = R.drawable.minus_icon)
                        else ImageVector.vectorResource(id = R.drawable.plus_icon),
                        tint = Color.Unspecified,
                        contentDescription = IMG_DESCRIPTION,
                        modifier = Modifier
                            .background(Color.Unspecified)
                            .size(25.dp)
                    )
                }
            }

            if (isExpanded) {
                Column(
                    modifier = Modifier.background(BannerColor03),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = faqData.answer,
                        fontFamily = fontRegular,
                        fontSize = 14.sp,
                        color = if (isSystemInDarkTheme()) {
                            DARK_TITLE_TEXT
                        } else {
                            Black
                        },
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(
                            start = 12.dp, top = 8.dp, end = 8.dp, bottom = 12.dp
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun SectionDivider() {
    Divider(
        thickness = 1.dp, color = Bg_Gray2
    )
}

data class FAQData(
    var categoryName: String,
    var categoryColor: Color,
    var subCateName: String,
    var subCateColor: Color,
    var questionName: String,
    var answer: String,
)

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ShowFAQDataUIPreview() {
    val navController = rememberNavController()
    val context = LocalContext.current
    ShowFAQData(navController, context)
}