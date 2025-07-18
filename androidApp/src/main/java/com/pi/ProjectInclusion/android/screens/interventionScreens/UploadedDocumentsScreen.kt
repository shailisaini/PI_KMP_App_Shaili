package com.pi.ProjectInclusion.android.screens.interventionScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.pi.ProjectInclusion.White
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.BackButtonPress
import com.pi.ProjectInclusion.android.common_UI.DetailsNoImgBackgroundUi
import com.pi.ProjectInclusion.android.navigation.AppRoute
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.fontRegular
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION

@Composable
fun UploadedDocumentsScreen(navHostController: NavHostController) {

    val documentsListData = listOf(
        DocumentsData(
            "ScreeningReport.doc",
            "28 Jun 2024",
            painterResource(id = R.drawable.docs_docs_img),
            painterResource(id = R.drawable.docs_preview_img)
        ),
        DocumentsData(
            "ScreeningReport.pdf",
            "28 Jun 2024",
            painterResource(id = R.drawable.docs_pdf_img),
            painterResource(id = R.drawable.docs_preview_img)
        ),
        DocumentsData(
            "ScreeningReport.jpg",
            "28 Jun 2024",
            painterResource(id = R.drawable.doc_jpg_img),
            painterResource(id = R.drawable.docs_preview_img)
        ),
        DocumentsData(
            "ScreeningReport.mp4",
            "28 Jun 2024",
            painterResource(id = R.drawable.doc_video_img),
            painterResource(id = R.drawable.docs_preview_img)
        )
    )

    DetailsNoImgBackgroundUi(
        backgroundColor = DarkBlue,
        pageTitle = stringResource(R.string.txt_Documents),
        moreInfoIcon = painterResource(id = R.drawable.more_info_img),
        isShowBackButton = true,
        isShowMoreInfo = false,
        onBackButtonClick = {
            BackButtonPress(navHostController, AppRoute.InterventionStudentDetails.route)
        },
        onMoreInfoClick = {},
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 8.dp)
                    .background(
                        if (isSystemInDarkTheme()) {
                            Dark_01
                        } else {
                            White
                        }
                    )
            ) {
                LazyColumn {
                    items(documentsListData) { interventionData ->
                        UploadedDocumentsDataUI(interventionData, navHostController)
                    }
                }
            }
        }
    )
}

@Composable
fun UploadedDocumentsDataUI(data: DocumentsData, controller: NavHostController) {

    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, bottom = 8.dp, top = 8.dp, end = 16.dp)
            .wrapContentHeight(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(1.dp),
        colors = if (isSystemInDarkTheme()) {
            CardDefaults.cardColors(DarkBlue)
        } else {
            CardDefaults.cardColors(
                containerColor = White,
                contentColor = White,
                disabledContentColor = White,
                disabledContainerColor = White
            )
        }
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .padding(start = 16.dp, bottom = 16.dp, top = 16.dp, end = 16.dp)
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
                    painter = data.docImage,
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
                    text = data.documentName,
                    modifier = Modifier.wrapContentWidth(),
                    fontFamily = fontMedium,
                    fontSize = 14.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_TITLE_TEXT
                    } else {
                        Black
                    },
                    textAlign = TextAlign.Center
                )

                Text(
                    text = data.date,
                    modifier = Modifier.wrapContentWidth(),
                    fontFamily = fontRegular,
                    fontSize = 12.sp,
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
                modifier = Modifier
                    .background(Color.Unspecified)
                    .clickable {
                        context.toast("Under processing for preview documents...")
                    }
            ) {
                Image(
                    painter = data.openImage,
                    contentDescription = IMG_DESCRIPTION,
                    modifier = Modifier.background(Color.Unspecified)
                )
            }
        }
    }
}

data class DocumentsData(
    var documentName: String,
    var date: String,
    var docImage: Painter,
    var openImage: Painter,
)