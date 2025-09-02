package com.pi.ProjectInclusion.android.screens.dashboardNavActivity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.BannerColor03
import com.pi.ProjectInclusion.Bg_Gray2
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.DARK_TITLE_TEXT
import com.pi.ProjectInclusion.Dark_03
import com.pi.ProjectInclusion.FaqColor1
import com.pi.ProjectInclusion.FaqColor2
import com.pi.ProjectInclusion.FaqColor3
import com.pi.ProjectInclusion.FaqColor4
import com.pi.ProjectInclusion.FaqColor5
import com.pi.ProjectInclusion.FaqColor6
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.HeaderColor01
import com.pi.ProjectInclusion.LightPurple04
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.android.MyApplicationTheme
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.DetailsNoImgBackgroundUi
import com.pi.ProjectInclusion.android.common_UI.DropdownMenuUi
import com.pi.ProjectInclusion.android.common_UI.TextViewField
import com.pi.ProjectInclusion.android.screens.StudentDashboardActivity
import com.pi.ProjectInclusion.android.utils.fontBold
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.fontRegular
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.BackHandler
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION
import com.pi.ProjectInclusion.constants.ConstantVariables.SELECTED_LANGUAGE_ID
import com.pi.ProjectInclusion.constants.ConstantVariables.USER_TYPE_ID
import com.pi.ProjectInclusion.constants.CustomDialog
import com.pi.ProjectInclusion.data.model.authenticationModel.response.CategoryListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.FAQsListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SubCategoryByCategoryIdResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SubCategoryListResponse
import com.pi.ProjectInclusion.ui.viewModel.DashboardViewModel
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

class FaqActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val viewModel: DashboardViewModel = koinViewModel()

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
                    ShowFAQData(context, viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowFAQData(
    context: Context,
    viewModel: DashboardViewModel,
) {

    val loginViewModel: LoginViewModel = koinViewModel()
    val colors = MaterialTheme.colorScheme
    val textEnterKeyEg = stringResource(R.string.txt_Enter_Keyword)
    val textSearchKeyEg = stringResource(R.string.key_search)
    var searchKeyName = rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    var isCategoryVisible by rememberSaveable { mutableStateOf(false) }
    var isSubCategoryVisible by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val categoryListState by viewModel.getCategoryListResponse.collectAsStateWithLifecycle()
    val subCategoryListState by viewModel.getSubCategoryListResponse.collectAsStateWithLifecycle()
    val subCategoryCategoryIdListState by viewModel.getSubCategoryByCategoryIdListResponse.collectAsStateWithLifecycle()
    val faqsListState by viewModel.getFAQsListResponse.collectAsStateWithLifecycle()
    var isDialogVisible by remember { mutableStateOf(false) }
    var categoryListData = remember { mutableStateListOf<CategoryListResponse>() }
    var subCategoryListData = remember { mutableStateListOf<SubCategoryListResponse>() }
    var subCategoryCategoryIdListData =
        remember { mutableStateListOf<SubCategoryByCategoryIdResponse.SubCategoryResponse>() }
    var faqsListData = remember { mutableStateListOf<FAQsListResponse.FAQsResponse>() }

    var selectedCategory = remember { mutableStateOf("") }
    var selectedSubCategory by remember { mutableStateOf("") }
    var selectedSubCategoryById = remember { mutableStateOf("") }
    var categorySelectedId = remember { mutableIntStateOf(0) }
    var subCategorySelectedId = remember { mutableIntStateOf(0) }
    var subCategoryByIdSelectedId = remember { mutableIntStateOf(0) }

    var languageId = loginViewModel.getPrefData(SELECTED_LANGUAGE_ID)
    var userTypeId = loginViewModel.getPrefData(USER_TYPE_ID)

    CustomDialog(
        isVisible = isDialogVisible,
        onDismiss = { isDialogVisible = false },
        message = stringResource(R.string.txt_loading)
    )

    LaunchedEffect(Unit) {
        isDialogVisible = true
        viewModel.getAllCategory()

        if (categorySelectedId.intValue != 0 && subCategoryByIdSelectedId.intValue != 0) {
            viewModel.getAllFAQs(
                searchKeyName.value.toString(),
                userTypeId.toString(),
                categorySelectedId.intValue.toString(),
                subCategoryByIdSelectedId.intValue.toString(),
                "",
                languageId.toString()
            )
        } else {
            viewModel.getAllFAQs(
                "",
                "1",
                "1",
                "1",
                "",
                languageId.toString()
            )
        }
    }

    LaunchedEffect(categoryListState) {
        when {
            categoryListState.isLoading -> {
                isDialogVisible = true
            }

            categoryListState.error.isNotEmpty() -> {
                logger.d("Category List Error: ${categoryListState.error}")
                isDialogVisible = false
            }

            categoryListState.success != null -> {
                logger.d("Category List Response :- ${categoryListState.success}")
                if (categoryListState.success?.size != 0) {
                    categoryListState.success.let { it.let { it1 -> categoryListData.addAll(it1!!.toList()) } }
                    println("Category List Data :- $categoryListData")
                } else {
                    context.toast(categoryListState.success!!.toString())
                }
                isDialogVisible = false
            }
        }
    }

    LaunchedEffect(subCategoryListState) {
        when {
            subCategoryListState.isLoading -> {
                isDialogVisible = true
            }

            subCategoryListState.error.isNotEmpty() -> {
                logger.d("SubCategory List Error: ${subCategoryListState.error}")
                isDialogVisible = false
            }

            subCategoryListState.success != null -> {
                logger.d("SubCategory List Response :- ${subCategoryListState.success}")
                if (subCategoryListState.success?.size != 0) {
                    subCategoryListState.success.let {
                        it.let { it2 ->
                            subCategoryListData.addAll(
                                it2!!.toList()
                            )
                        }
                    }
                    println("SubCategory List Data :- $subCategoryListData")
                } else {
                    context.toast(subCategoryListState.success!!.toString())
                }
                isDialogVisible = false
            }
        }
    }

    LaunchedEffect(subCategoryCategoryIdListState) {
        when {
            subCategoryCategoryIdListState.isLoading -> {
                isDialogVisible = true
            }

            subCategoryCategoryIdListState.error.isNotEmpty() -> {
                logger.d("SubCategory By Id List Error: ${subCategoryCategoryIdListState.error}")
                isDialogVisible = false
            }

            subCategoryCategoryIdListState.success != null -> {
                logger.d("SubCategory By Id List Response :- ${subCategoryCategoryIdListState.success}")
                if (subCategoryCategoryIdListState.success?.response?.size != 0) {
                    subCategoryCategoryIdListState.success?.response.let {
                        it.let { it3 ->
                            subCategoryCategoryIdListData.addAll(
                                it3!!.toList()
                            )
                        }
                    }
                    println("SubCategory By Id List Data :- $subCategoryCategoryIdListData")
                } else {
                    context.toast(subCategoryCategoryIdListState.success!!.message.toString())
                }
                isDialogVisible = false
            }
        }
    }

    LaunchedEffect(faqsListState) {
        when {
            faqsListState.isLoading -> {
                isDialogVisible = true
            }

            faqsListState.error.isNotEmpty() -> {
                logger.d("Faqs List Error: ${faqsListState.error}")
                isDialogVisible = false
            }

            faqsListState.success != null -> {
                logger.d("Faqs List Response :- ${faqsListState.success}")
                if (faqsListState.success?.response?.size != 0) {
                    faqsListState.success?.response.let { it.let { it3 -> faqsListData.addAll(it3!!.toList()) } }
                    println("Faqs List Data :- $faqsListData")
                } else {
                    context.toast(faqsListState.success!!.message.toString())
                }
                isDialogVisible = false
            }
        }
    }

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
                                options = listOf(),
                                onItemSelected = {},
                                modifier = Modifier.clickable {
                                    logger.e("Category list :- ${categoryListData.size}")
                                },
                                placeholder = if (selectedCategory.value.isNotEmpty()) {
                                    selectedCategory.value.toString()
                                } else {
                                    stringResource(R.string.txt_category)
                                },
                                onClick = {
                                    scope.launch {
                                        isCategoryVisible = true
                                        sheetState.expand()
                                    }
                                })

                            if (isCategoryVisible) {
                                CategoryBottomSheet(
                                    viewModel = viewModel,
                                    categories = categoryListData,
                                    selectedCategory = selectedCategory.value,
                                    selectedCategoryId = categorySelectedId,
                                    onCategorySelected = { name, id ->
                                        selectedCategory.value = name.toString()
                                        categorySelectedId.intValue = id.hashCode()
                                        isCategoryVisible = false
                                    },
                                    onDismiss = { isCategoryVisible = false })
                            }
                        }

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            DropdownMenuUi(
                                options = listOf(),
                                onItemSelected = {},
                                modifier = Modifier.clickable {
//                                    logger.e("SubCategory list :- ${subCategoryListData.size}")
                                    logger.e("SubCategory list :- ${subCategoryCategoryIdListData.size}")
                                },
                                placeholder = if (selectedSubCategoryById.value.isNotEmpty()) {
                                    selectedSubCategoryById.value.toString()
                                } else {
                                    stringResource(R.string.txt_subcategory)
                                },
                                onClick = {
                                    scope.launch {
                                        isSubCategoryVisible = true // true under development code
                                        sheetState.expand()
                                    }
                                })

                            if (isSubCategoryVisible) {
                                SubCategoryBottomSheet(
                                    subCategories = subCategoryCategoryIdListData,   // your API response list
                                    selectedCategory = selectedSubCategoryById.value,
                                    selectedSubCategoryId = subCategoryByIdSelectedId,
                                    onCategorySelected = { name, id ->
                                        selectedSubCategoryById.value = name.toString()
                                        subCategoryByIdSelectedId.intValue = id
                                        isSubCategoryVisible = false
                                    },
                                    onDismiss = { isSubCategoryVisible = false })
                            }
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
                                    .clickable {
                                        if (searchKeyName.value.isEmpty()) {
                                            context.toast(textSearchKeyEg)
                                        } else {
                                            viewModel.getAllFAQs(
                                                searchKeyName.value.toString(),
                                                userTypeId.toString(),
                                                categorySelectedId.intValue.toString(),
                                                subCategoryByIdSelectedId.intValue.toString(),
                                                "",
                                                languageId.toString()
                                            )
                                        }
                                    })
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
                            itemsIndexed(faqsListData) { index, faqData ->
                                FaqDataUI(index, faqData)
                            }
                        }
                    }
                }
            })
    }
}

@Composable
fun FaqDataUI(index: Int, faqData: FAQsListResponse.FAQsResponse) {

    var isExpanded by remember { mutableStateOf(false) }
    val colors1 = listOf(FaqColor1, HeaderColor01, FaqColor3, FaqColor5)
    val colors2 = listOf(LightPurple04, FaqColor2, FaqColor4, FaqColor6)

    val bgColor = colors1[index % colors1.size]
    val randomColor = colors2.random()

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
                            containerColor = bgColor,
                            contentColor = bgColor,
                            disabledContentColor = bgColor,
                            disabledContainerColor = bgColor
                        )
                    } else {
                        CardDefaults.cardColors(
                            containerColor = bgColor,
                            contentColor = bgColor,
                            disabledContentColor = bgColor,
                            disabledContainerColor = bgColor
                        )
                    }
                ) {
                    Text(
                        text = faqData.categoryName.toString(),
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
                            containerColor = randomColor,
                            contentColor = randomColor,
                            disabledContentColor = randomColor,
                            disabledContainerColor = randomColor
                        )
                    } else {
                        CardDefaults.cardColors(
                            containerColor = randomColor,
                            contentColor = randomColor,
                            disabledContentColor = randomColor,
                            disabledContainerColor = randomColor
                        )
                    }
                ) {
                    Text(
                        text = faqData.subCategoryName.toString(),
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
                    text = faqData.name.toString(),
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
                        text = faqData.description.toString(),
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
    var categoryColor: Color,
    var subCateColor: Color,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryBottomSheet(
    viewModel: DashboardViewModel,
    categories: SnapshotStateList<CategoryListResponse>,
    selectedCategory: String?,
    selectedCategoryId: MutableIntState,
    onCategorySelected: (String, Int) -> Unit,
    onDismiss: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 50.dp)
        ) {
            Text(
                text = "Select Categories",
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
                fontFamily = fontMedium,
                fontSize = 14.sp,
                color = if (isSystemInDarkTheme()) {
                    White
                } else {
                    Gray
                },
                textAlign = TextAlign.Start
            )

            categories.forEach { category ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val id = category.id ?: return@clickable
                            selectedCategoryId.intValue = id
                            onCategorySelected(category.name.toString(), id)
                            viewModel.getAllSubCategoryByCategoryId(selectedCategoryId.intValue)
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = category.name.toString(),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(start = 8.dp, end = 8.dp, top = 8.dp),
                        fontFamily = fontMedium,
                        fontSize = 14.sp,
                        color = if (isSystemInDarkTheme()) {
                            White
                        } else {
                            Black
                        },
                        textAlign = TextAlign.Start
                    )

                    RadioButton(
                        selected = selectedCategory == category.name?.toString(), onClick = {
                            val id = category.id ?: return@RadioButton
                            selectedCategoryId.intValue = id
                            onCategorySelected(category.name.toString(), id)
                        })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubCategoryBottomSheet(
    subCategories: SnapshotStateList<SubCategoryByCategoryIdResponse.SubCategoryResponse>,
    selectedCategory: String?,
    selectedSubCategoryId: MutableIntState,
    onCategorySelected: (String, Int) -> Unit,
    onDismiss: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 50.dp)
        ) {
            Text(
                text = "Select SubCategories",
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
                fontFamily = fontMedium,
                fontSize = 14.sp,
                color = if (isSystemInDarkTheme()) {
                    White
                } else {
                    Gray
                },
                textAlign = TextAlign.Start
            )

            subCategories.forEach { category ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val id = category.id ?: return@clickable
                            selectedSubCategoryId.intValue = id
                            onCategorySelected(category.name.toString(), id)
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = category.name.toString(),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
                        fontFamily = fontMedium,
                        fontSize = 14.sp,
                        color = if (isSystemInDarkTheme()) {
                            White
                        } else {
                            Black
                        },
                        textAlign = TextAlign.Start
                    )

                    RadioButton(
                        selected = selectedCategory == category.name.toString(), onClick = {
                            val id = category.id ?: return@RadioButton
                            selectedSubCategoryId.intValue = id
                            onCategorySelected(category.name.toString(), id)
                        })
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ShowFAQDataUIPreview() {
    val context = LocalContext.current
    val viewModel: DashboardViewModel = koinViewModel()
    ShowFAQData(context, viewModel)
}