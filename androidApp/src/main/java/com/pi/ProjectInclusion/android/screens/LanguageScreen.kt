package com.pi.ProjectInclusion.android.screens

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kmptemplate.logger.AppLogger
import com.example.kmptemplate.logger.AppLoggerImpl
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.Dark_02
import com.pi.ProjectInclusion.Dark_Selected_BG
import com.pi.ProjectInclusion.GrayLight01
import com.pi.ProjectInclusion.GrayLight02
import com.pi.ProjectInclusion.PRIMARY_AURO_BLUE
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.PrimaryBlueLt
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.utils.toast
//import com.pi.ProjectInclusion.constants.CustomDialog
import com.pi.ProjectInclusion.data.model.GetLanguageListResponse
import kotlinx.coroutines.launch
import org.koin.core.logger.Logger

@Composable
fun LanguageScreen(navController: NavHostController,viewModel: LoginViewModel) {

    var isDialogVisible by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val query by rememberSaveable {
        mutableStateOf("")
    }
    val languageData = remember { mutableStateListOf<GetLanguageListResponse.Data.Result>() }

   /* CustomDialog(
        isVisible = isDialogVisible,
        onDismiss = { isDialogVisible = false },
        message = "Loading your data..."
    )*/

    Surface(
        modifier = Modifier.fillMaxWidth(), color = White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = PrimaryBlue),
            verticalArrangement = Arrangement.Top
        ) {
            if (uiState.isLoading){
                languageData.clear()
                isDialogVisible = true
            }
            if (uiState.error.isNotEmpty()){
                languageData.clear()
                isDialogVisible = false
            }
            uiState.success.let { response ->
                languageData.addAll(response?.data?.results?.reversed()!!)
                languageResponseUI(languageData)
                isDialogVisible = false
            }
        }
    }
}

@Composable
fun languageResponseUI(languageData: MutableList<GetLanguageListResponse.Data.Result>) {
    val scrollState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    var isInternetAvailable by remember { mutableStateOf(true) }
    var isApiResponded by remember { mutableStateOf(false) }
    var internetMessage by remember { mutableStateOf("") }
    val context = LocalContext.current
    var isDialogVisible by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    val selectedLanguage = remember { mutableStateOf<String?>(null) }

    if (languageData.size > 0) {
        Box(
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .background(
                    color = if (isSystemInDarkTheme()) {
                        Dark_01
                    } else {
                        White
                    }
                )
                .padding(4.dp), // Add horizontal padding,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(bottom = 10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .weight(1f)
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        state = scrollState,
                        contentPadding = PaddingValues(bottom = 15.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp, start = 8.dp, end = 8.dp)
                            .draggable(
                                orientation = Orientation.Vertical,
                                state = rememberDraggableState { delta ->
                                    coroutineScope.launch {
                                        scrollState.scrollBy(-delta)
                                    }
                                })
                    ) {
                        val logger = AppLoggerImpl()
                        logger.e(",")
                       /* items(languageData.size) { index ->
                            ItemLanguageCard(
                                context,
                                isSelected = selectedIndex == index,
                                index,
                                selectedLanguage,
                                viewModel,
                                language = languageData,
                                onItemClicked = {
                                    selectedIndex =
                                        if (selectedIndex == index) null else index // Toggle selection
                                    selectedLanguage.value =
                                        languageData[index].id.toString()
//                                    viewModel.saveLanguageId(languageData[index].id.toString())
//                                    viewModel.saveLanguageCode(languageData[index].name[0].toString())
                                    println("Selected Item :- $selectedIndex")
                                }, colors
                            )
                        }*/
                    }
                }
            }
        }
    } else {
       /* if (!isInternetAvailable) {
            ShowError(internetMessage, colors)
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.no_data_found),
                    contentDescription = "logo",
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .background(Color.Unspecified),
                )

                Text(
                    text = stringResource(R.string.txt_oops_no_data_found),
                    modifier = Modifier.wrapContentSize(),
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = colors.onSurface,
                    textAlign = TextAlign.Start
                )
            }
        }*/
    }
}

/*
@Composable
fun ItemLanguageCard(
    context: Context,
    isSelected: Boolean = true,
    index: Int,
    selectedLanguage: MutableState<String?>,
    viewModel: LoginViewModel,
    language: List<Result>,
    onItemClicked: () -> Unit = {},
    colors: ColorScheme,
) {
    val languageIndex = language[index]

    var selectedBorder = if (isSelected) BorderStroke(
        width = 1.dp,
        if (isSystemInDarkTheme()) {
            PRIMARY_AURO_BLUE
        } else {
            PrimaryBlue
        }
    ) else BorderStroke(
        width = 0.5.dp, if (isSystemInDarkTheme()) {
            Dark_02
        } else {
            GrayLight02
        }
    )
    var backGroundColor = if (isSelected) {
        if (isSystemInDarkTheme()) {
            Dark_Selected_BG
        } else {
            PrimaryBlueLt
        }
    } else {
        if (isSystemInDarkTheme()) {
            Dark_02
        } else {
            White
        }
    }

    Card(
        modifier = Modifier
            .clickable {
                if (languageIndex.status == "Active")
                    onItemClicked.invoke()
                else {
                    context.toast("Choose Language Hindi & English")
                }
            }
            .padding(5.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(1.dp),
        colors = CardDefaults.cardColors(
            if (isSystemInDarkTheme()) {
                Dark_02
            } else {
                colors.surfaceBright
            }
        ),
        border = selectedBorder
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = backGroundColor),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier
                        .padding(start = 8.dp, end = 5.dp)
                        .background(Color.Unspecified)
                        .size(25.dp),
//                        .wrapContentSize(),
                    contentScale = ContentScale.Fit,
                    painter = if (languageIndex.icon != null) {
                        rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(languageIndex.icon)
                                .decoderFactory(SvgDecoder.Factory()) // Adds SVG support
                                .size(Size.ORIGINAL) // Use original or specify size
                                .placeholder(R.drawable.icon_hindi)
                                .error(R.drawable.icon_hindi)
                                .build()
                        )
                    } else {
                        painterResource(R.drawable.icon_hindi)
                    },
                    contentDescription = "Logo"
                )

                Column(
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        languageIndex.nativeName,
                        textAlign = TextAlign.Start,
                        maxLines = 1,
                        fontSize = 16.sp,
                        overflow = TextOverflow.Ellipsis,
                        */
/*color = Black,*//*

                        color = colors.onSurface, fontFamily = FontFamily(
                            Font(resou, FontWeight.Medium)
                        ),
                        modifier = Modifier
                            .padding(top = 10.dp, start = 10.dp)
                    )

                    Text(
                        "${languageIndex.name} ",
                        textAlign = TextAlign.Start,
                        maxLines = 1,
                        fontSize = 10.sp,
                        overflow = TextOverflow.Ellipsis,
                        color = Gray,
                        fontFamily = FontFamily(
                            Font(R.font.inter_regular, FontWeight.Normal)
                        ),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(bottom = 10.dp, start = 10.dp)
//                            .heightIn(min = 40.dp)
                    )
                }
            }
        }
    }
}*/
