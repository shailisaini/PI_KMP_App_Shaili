package com.pi.ProjectInclusion.android.screens.login

import android.annotation.SuppressLint
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.kmptemplate.logger.LoggerProvider
import com.pi.ProjectInclusion.Bg_Gray1
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.BorderBlue
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.Dark_02
import com.pi.ProjectInclusion.Dark_Selected_BG
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.GrayLight02
import com.pi.ProjectInclusion.PRIMARY_AURO_BLUE
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.PrimaryBlueLt1
import com.pi.ProjectInclusion.Transparent
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.CommonFunction.LoginScreenTitle
import com.pi.ProjectInclusion.constants.CommonFunction.NoDataFound
import com.pi.ProjectInclusion.constants.CommonFunction.ShowError
import com.pi.ProjectInclusion.constants.CommonFunction.isNetworkAvailable
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION
import com.pi.ProjectInclusion.constants.ConstantVariables.SELECTED_LANGUAGE_ID
import com.pi.ProjectInclusion.constants.CustomDialog
import com.pi.ProjectInclusion.data.model.authenticationModel.response.GetLanguageListResponse
import kotlinx.coroutines.launch

@Composable
fun LanguageScreen(viewModel: LoginViewModel, onNext: () -> Unit) {

    val context = LocalContext.current

    LoggerProvider.logger.d("Screen: " + "LanguageScreen()")

    Surface(
        modifier = Modifier.fillMaxWidth(), color = White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Bg_Gray1),
            verticalArrangement = Arrangement.Top
        ) {
            LanguageResponseUI(context, onNext, viewModel)
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun LanguageResponseUI(
    context: Context,
    onNext: () -> Unit,
    viewModel: LoginViewModel,
) {
    val errColor = PrimaryBlue
    val scrollState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    var noData = stringResource(R.string.txt_oops_no_data_found)
    var isInternetAvailable by remember { mutableStateOf(true) }
    val internetMessage = stringResource(R.string.txt_oops_no_internet)
    var noDataMessage by remember { mutableStateOf(noData) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    val selectedLanguage = remember { mutableStateOf<String?>(null) }
    val title = stringResource(R.string.select_language)
    val languageData = remember { mutableStateListOf<GetLanguageListResponse.LanguageResponse>() }
    var isDialogVisible by remember { mutableStateOf(true) }
    CustomDialog(
        isVisible = isDialogVisible,
        onDismiss = { isDialogVisible = false },
        message = stringResource(R.string.txt_loading)
    )

    LaunchedEffect(Unit) {
        isDialogVisible = true
        viewModel.getLanguages()
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(uiState) {
        when {
            uiState.isLoading -> {
                languageData.clear()
//                isDialogVisible = true
            }

            uiState.error.isNotEmpty() -> {
                languageData.clear()
                LoggerProvider.logger.d("Error: ${uiState.error}")
                context.toast(uiState.error)
                noDataMessage = uiState.error
            }

            uiState.success != null -> {
                val list = uiState.success?.response ?: emptyList()
                LoggerProvider.logger.d("Languages fetched: ${list.size}")

                if (list.isNotEmpty()) {
                    languageData.clear()
                    languageData.addAll(list)
                } else {
                    LoggerProvider.logger.d("Languages fetched: 0 (null or empty response)")
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .padding(top = 5.dp)
            .wrapContentSize(Alignment.Center)
            .background(
                color = if (isSystemInDarkTheme()) {
                    Dark_01
                } else {
                    Transparent
                }
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginScreenTitle(title, Black, Gray, Transparent, "")
            if (languageData.isNotEmpty()) {
                isDialogVisible = false
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        state = scrollState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .draggable(
                                orientation = Orientation.Vertical,
                                state = rememberDraggableState { delta ->
                                    coroutineScope.launch {
                                        scrollState.scrollBy(-delta)
                                    }
                                })
                    ) {
                        items(languageData.size) { index ->
                            ItemLanguageCard(
                                onNext,
                                context,
                                isSelected = selectedIndex == index,
                                index,
                                language = languageData,
                                onItemClicked = {
                                    selectedIndex =
                                        if (selectedIndex == index) null else index // Toggle selection
                                    selectedLanguage.value = languageData[index].id.toString()
                                    viewModel.savePrefData(SELECTED_LANGUAGE_ID, languageData[index].id.toString())
                                }
                            )
                        }
                    }
                }
            } else {
                isInternetAvailable = isNetworkAvailable(context)
                if (!isInternetAvailable) {
                    ShowError(internetMessage, errColor, painterResource(R.drawable.sad_emoji))
                    isDialogVisible = false
                } else {
                    NoDataFound(noDataMessage, painterResource(R.drawable.sad_emoji))

                }
            }
        }
    }
}

@Composable
fun ItemLanguageCard(
    onNext: () -> Unit,
    context: Context,
    isSelected: Boolean = true,
    index: Int,
    language: MutableList<GetLanguageListResponse.LanguageResponse>,
    onItemClicked: () -> Unit = {},
) {
    val languageIndex = language[index]
    val errorToast = stringResource(R.string.choose_hindi_english)

    val selectedBorder = if (isSelected) BorderStroke(
        width = 1.dp,
        if (isSystemInDarkTheme()) {
            PRIMARY_AURO_BLUE
        } else {
            BorderBlue
        }
    ) else BorderStroke(
        width = 0.5.dp, if (isSystemInDarkTheme()) {
            Dark_02
        } else {
            GrayLight02
        }
    )
    val backGroundColor = if (isSelected) {
        if (isSystemInDarkTheme()) {
            Dark_Selected_BG
        } else {
            PrimaryBlueLt1
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
                if (languageIndex.status == 1) {
                    onItemClicked.invoke()
                    onNext()
                } else {
                    LoggerProvider.logger.d("Languages fetched: ${languageIndex.status}")
                    context.toast(errorToast)
                }
            }
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(1.dp),
        colors = CardDefaults.cardColors(
            if (isSystemInDarkTheme()) {
                Dark_02
            } else {
                GrayLight02
            }
        ),
        border = selectedBorder,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = backGroundColor),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 15.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .background(Color.Unspecified)
                        .size(45.dp),
                    contentScale = ContentScale.Fit,
                    painter = if (!languageIndex.langIcon.isNullOrEmpty()) {
                        rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(languageIndex.langIcon)
                                .decoderFactory(SvgDecoder.Factory())
                                .size(Size.ORIGINAL)
                                .placeholder(R.drawable.ic_hindi)
                                .error(R.drawable.ic_hindi)
                                .build()
                        )
                    } else {
                        painterResource(id = R.drawable.ic_hindi)
                    },
                    contentDescription = IMG_DESCRIPTION
                )
                Text(
                    (languageIndex.translatedName?:languageIndex.name)!!,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = Black,
                    modifier = Modifier
                        .padding(top = 10.dp)
                )

                Text(
                    "${languageIndex.name} ",
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    fontSize = 14.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = Gray,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 5.dp)
//                            .heightIn(min = 40.dp)
                )
            }
        }
    }
}