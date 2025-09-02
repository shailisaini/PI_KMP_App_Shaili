package com.pi.ProjectInclusion.android.screens.history

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.Black1
import com.pi.ProjectInclusion.BorderBlue
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.GrayLight04
import com.pi.ProjectInclusion.Transparent
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.DetailsNoImgBackgroundUi
import com.pi.ProjectInclusion.android.screens.lockScreen.LockScreenOrientationPortrait
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.fontRegular
import com.pi.ProjectInclusion.data.model.profileModel.dummyTrackList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryCard(
    teacherImage: Int,
    teacherName: String,
    schoolName: String,
    onClick: () -> Unit,
    onCardClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = stringResource(R.string.conducted_by),
                fontSize = 13.sp,
                color = GrayLight04,
                fontFamily = fontRegular
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = teacherImage),
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = teacherName,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Black1,
                            fontFamily = fontMedium
                        )
                        Text(
                            text = schoolName,
                            fontSize = 13.sp,
                            color = BorderBlue,
                            fontFamily = fontRegular
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .wrapContentSize(Alignment.Center)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.round_right_arrow_img),
                        contentDescription = null,
                        modifier = Modifier.size(28.dp),
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryListScreen(
    context: Context,
    onBack: () -> Unit,
    onNext: () -> Unit
) {

    DetailsNoImgBackgroundUi(
        backgroundColor = White,
        textColor = Black,
        pageTitle = stringResource(R.string.history_list),
        isShowBackButton = true,
        isShowMoreInfo = false,
        onBackButtonClick = {
            onBack()
        },
        onMoreInfoClick = {
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

            HistoryList()
        })
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun HistoryListScreen() {
    val context = LocalContext.current
    val onNext: () -> Unit = {}
    val onBack: () -> Unit = {}
    HistoryListScreen(context, onNext, onBack)
}
@Composable
fun HistoryList() {
    LockScreenOrientationPortrait()
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(dummyTrackList) { item ->

            HistoryCard(
                teacherImage =R.drawable.profile_user_icon,
                teacherName = stringResource(R.string.user_name),
                schoolName = stringResource(R.string.txt_dummy_school),
                onClick = {},
                onCardClick = {}
                )

        }
    }
}