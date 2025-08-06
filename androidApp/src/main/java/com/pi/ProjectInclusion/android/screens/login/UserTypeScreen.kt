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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.kmptemplate.logger.LoggerProvider
import com.pi.ProjectInclusion.Bg_Gray1
import com.pi.ProjectInclusion.Black
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
import com.pi.ProjectInclusion.android.common_UI.DefaultBackgroundUi
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.BackHandler
import com.pi.ProjectInclusion.constants.CommonFunction.LoginScreenTitle
import com.pi.ProjectInclusion.constants.CommonFunction.NoDataFound
import com.pi.ProjectInclusion.constants.CommonFunction.ShowError
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION
import com.pi.ProjectInclusion.constants.CustomDialog
import com.pi.ProjectInclusion.data.model.GetUserTypeResponse
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun UserTypeScreen(
    viewModel: LoginViewModel,
    onNext: () -> Unit,
    onBack: () -> Unit,
) {
    var isDialogVisible by remember { mutableStateOf(false) }
    val uiState by viewModel.uiStateType.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val userType = remember { mutableStateListOf<GetUserTypeResponse.UserTypeResponse>() }
    CustomDialog(
        isVisible = isDialogVisible,
        onDismiss = { isDialogVisible = false },
        message = stringResource(R.string.txt_loading)
    )

    LoggerProvider.logger.d("Screen: " + "UserTypeScreen()")
    LaunchedEffect(Unit) {
        viewModel.getUserType()
    }

    LaunchedEffect(uiState) {
        when {
            uiState.isLoading -> {
                userType.clear()
                isDialogVisible = true
            }

            uiState.error.isNotEmpty() -> {
                userType.clear()
                LoggerProvider.logger.d("Error: ${uiState.error}")
                context.toast(uiState.error)
                isDialogVisible = false
            }

            uiState.success != null -> {
                uiState.success!!.let {
                    userType.clear()
                    userType.addAll(it.response!!)
                }
                LoggerProvider.logger.d("Languages fetched: ${uiState.success!!}")
                isDialogVisible = false
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxWidth(), color = White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Bg_Gray1),
            verticalArrangement = Arrangement.Top
        ) {
            UserTypeResponseUI(context, userType, onNext, onBack)
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun UserTypeResponseUI(
    context: Context,
    userTypeData: MutableList<GetUserTypeResponse.UserTypeResponse>,
    onNext: () -> Unit,
    onBack: () -> Unit,
) {
    val errColor = PrimaryBlue
    val scrollState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    val isInternetAvailable by remember { mutableStateOf(true) }
    var isApiResponded by remember { mutableStateOf(false) }

    var isDialogVisible by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    val selectedLanguage = remember { mutableStateOf<String?>(null) }
    val title = stringResource(R.string.select_userType)
    val subTitle = stringResource(R.string.select_userType_subtitle)
    val internetMessage = stringResource(R.string.txt_oops_no_internet)
    val noDataMessage = stringResource(R.string.txt_oops_no_data_found)

    DefaultBackgroundUi(isShowBackButton = true, onBackButtonClick = {
        onBack()
    }, content = {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(15.dp)
                    .background(
                        color = if (isSystemInDarkTheme()) {
                            Dark_01
                        } else {
                            Transparent
                        }
                    )
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoginScreenTitle(title, Black, Gray, Transparent, subTitle)
                Box(
                    modifier = Modifier
                        .wrapContentSize(Alignment.Center)
                        .background(
                            color = if (isSystemInDarkTheme()) {
                                Dark_01
                            } else {
                                Transparent
                            }
                        )
                        .padding(4.dp), // Add horizontal padding,
                ) {
                    if (userTypeData.isNotEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()

                        ) {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                state = scrollState,
                                contentPadding = PaddingValues(vertical = 15.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp, horizontal = 8.dp)
                                    .draggable(
                                        orientation = Orientation.Vertical,
                                        state = rememberDraggableState { delta ->
                                            coroutineScope.launch {
                                                scrollState.scrollBy(-delta)
                                            }
                                        })
                            ) {
                                items(userTypeData.size) { index ->
                                    UserTypeCard(
                                        onNext,
                                        context,
                                        isSelected = selectedIndex == index,
                                        index,
                                        userType = userTypeData,
                                        onItemClicked = {
                                            selectedIndex =
                                                if (selectedIndex == index) null else index // Toggle selection
                                            selectedLanguage.value =
                                                userTypeData[index].id.toString()
                                        }
                                    )
                                }
                            }
                        }
                    } else {
                        if (!isInternetAvailable) {
                            ShowError(internetMessage, errColor, painterResource(R.drawable.sad_emoji))
                        } else {
                            NoDataFound(noDataMessage, painterResource(R.drawable.sad_emoji))
                        }
                    }
                }
            }
        }
    })

    BackHandler {
        onBack()
    }
}

@Composable
fun UserTypeCard(
    onNext: () -> Unit,
    context: Context,
    isSelected: Boolean = true,
    index: Int,
    userType: MutableList<GetUserTypeResponse.UserTypeResponse>,
    onItemClicked: () -> Unit = {},
) {
    val userTypeIndex = userType[index]
    val errorToast = stringResource(R.string.select_userType)

    val selectedBorder = if (isSelected) BorderStroke(
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
                onItemClicked.invoke()
                onNext()
            }
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(1.dp),
        colors = CardDefaults.cardColors(
            if (isSystemInDarkTheme()) {
                Dark_02
            } else {
                White
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
                    .padding(vertical = 25.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .background(Color.Unspecified)
                        .size(65.dp),
                    contentScale = ContentScale.Fit,
//                    painter = if (userTypeIndex.isNotEmpty()) {
                    painter =
                        rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(userTypeIndex.icon)
                                .decoderFactory(SvgDecoder.Factory())
                                .size(Size.ORIGINAL)
                                .placeholder(R.drawable.img_teacher)
                                .error(R.drawable.img_teacher)
                                .build()
                        )
                    /*} else {
                        painterResource(id = R.drawable.img_teacher)
                    }*/,
                    contentDescription = IMG_DESCRIPTION
                )

                Text(
                    "${userTypeIndex.name} ",
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    fontSize = 14.sp,
                    color = if (isSelected) Black else Gray,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 5.dp)
//                            .heightIn(min = 40.dp)
                )
            }
        }
    }
}