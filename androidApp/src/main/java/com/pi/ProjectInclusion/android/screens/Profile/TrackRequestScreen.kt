package com.pi.ProjectInclusion.android.screens.Profile

import android.content.Context
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kmptemplate.logger.LoggerProvider
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.LightGreen06
import com.pi.ProjectInclusion.LightYellow02
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.RedText
import com.pi.ProjectInclusion.Transparent
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.DetailsNoImgBackgroundUi
import com.pi.ProjectInclusion.color_done
import com.pi.ProjectInclusion.constants.BackHandler
import com.pi.ProjectInclusion.constants.CustomDialog
import com.pi.ProjectInclusion.data.model.AuthenticationModel.GetUserTypeResponse
import com.pi.ProjectInclusion.data.model.profileModel.TrackListModel
import com.pi.ProjectInclusion.data.model.profileModel.dummyTrackList
import androidx.compose.foundation.lazy.items


@Composable
fun TrackRequestScreen(onNext: () -> Unit,onBack: () -> Unit) {
    var isDialogVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val userType = remember { mutableStateListOf<GetUserTypeResponse.UserTypeResponse>() }
    CustomDialog(
        isVisible = isDialogVisible,
        onDismiss = { isDialogVisible = false },
        message = stringResource(R.string.txt_loading)
    )
    BackHandler {
        onBack()
    }
    LoggerProvider.logger.d("Screen: " + "EnterUserNameScreen()")

    Surface(
        modifier = Modifier.fillMaxWidth(), color = White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = White),
            verticalArrangement = Arrangement.Top
        ) {
            TrackRequest(context, onNext = onNext, onBack = onBack)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackRequest(
    context: Context,
    onBack: () -> Unit,
    onNext: () -> Unit
) {

    DetailsNoImgBackgroundUi(
        backgroundColor = White,
        textColor = Black,
        pageTitle = stringResource(R.string.track_request),
        isShowBackButton = true,
        isShowMoreInfo = false,
        onBackButtonClick = {
            onBack()
        },
        onMoreInfoClick = {
//            showSheetMenu = true
        },
        content = {
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
            )
            TrackListScreen()
        })
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun TrackRequest() {
    val context = LocalContext.current
    val onNext: () -> Unit = {}
    val onBack: () -> Unit = {}
    TrackRequest(context, onNext, onBack)
}
/* this code will be required during API */
@Composable
private fun TrackListCard(item: TrackListModel){
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.name.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.date,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }

            Box(
                modifier = Modifier
                    .background(
//                        color =item.statusColor.toComposeColor() ,
                        color =LightGreen06,
                        shape = RoundedCornerShape(50)
                    )
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.reviewStatus,
                    color = White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        if (!item.reason.isNullOrBlank()){
            Card (
                shape = RoundedCornerShape(4.dp),
                colors = CardDefaults.cardColors(containerColor = LightYellow02),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    modifier = Modifier
                        .padding( start = 12 .dp, top = 4.dp, bottom = 6.dp),
                    text = stringResource(R.string.txt_reason)+" "+item.reason,
                    color = Black,
                    fontSize = 13.sp
                )
            }
        }
    }
}


@Composable
fun TrackListScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(dummyTrackList) { item ->
            TrackListCard(item = item)
        }
    }
}

//fun String.toComposeColor(): Color {
//    val clean = replace("#", "")
//    val argb = when (clean.length) {
//        6 -> "FF$clean"
//        8 -> clean
//        else -> "0xFF56A42F"
//    }
//    return Color(argb.toLong(16))
//}






