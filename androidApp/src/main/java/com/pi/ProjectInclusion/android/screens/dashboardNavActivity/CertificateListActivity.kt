package com.pi.ProjectInclusion.android.screens.dashboardNavActivity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.Bg_Gray2
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.BorderBlue
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.LightOrange2
import com.pi.ProjectInclusion.TextPurple
import com.pi.ProjectInclusion.Transparent
import com.pi.ProjectInclusion.android.MyApplicationTheme
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.DetailsNoImgBackgroundUi
import com.pi.ProjectInclusion.android.common_UI.SectionDivider
import com.pi.ProjectInclusion.android.common_UI.TextWithIconOnLeft
import com.pi.ProjectInclusion.android.screens.StudentDashboardActivity
import com.pi.ProjectInclusion.android.screens.dashboardScreen.WebViewState
import com.pi.ProjectInclusion.android.utils.fontBold
import com.pi.ProjectInclusion.android.utils.fontRegular
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.BackHandler
import com.pi.ProjectInclusion.constants.ConstantVariables.ALL
import com.pi.ProjectInclusion.constants.ConstantVariables.COURSE
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION
import com.pi.ProjectInclusion.constants.ConstantVariables.MODULE
import com.pi.ProjectInclusion.constants.CustomDialog
import com.pi.ProjectInclusion.data.model.authenticationModel.Response.CertificateListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.request.CertificateRequest
import com.pi.ProjectInclusion.ui.viewModel.DashboardViewModel
import org.koin.androidx.compose.koinViewModel

class CertificateListActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val context = LocalContext.current
            val viewModel: DashboardViewModel = koinViewModel()

            BackHandler {
                startActivity(
                    context, Intent(context, StudentDashboardActivity::class.java), null
                ).apply { (context as? Activity)?.finish() }
            }

            MyApplicationTheme {
                logger.d("Screen: CertificateListActivity()")
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = White),
                    verticalArrangement = Arrangement.Top
                ) {
                    ShowCertificateData(navController, context, viewModel)
                }
            }
        }
    }
}

@Composable
fun ShowCertificateData(
    navController: NavHostController,
    context: Context,
    viewModel: DashboardViewModel,
) {

    BackHandler {
        startActivity(
            context, Intent(context, StudentDashboardActivity::class.java), null
        ).apply { (context as? Activity)?.finish() }
    }

    val certificateState by viewModel.getCertificateResponse.collectAsStateWithLifecycle()
    var isDialogVisible by remember { mutableStateOf(false) }
    var certificateData by remember { mutableStateOf(mutableListOf<CertificateListResponse.CertificateResponse>()) }

    CustomDialog(
        isVisible = isDialogVisible,
        onDismiss = { isDialogVisible = false },
        message = stringResource(R.string.txt_loading)
    )

    LaunchedEffect(Unit) {
        isDialogVisible = true
        val certificateRequest = CertificateRequest(2, 270)
        viewModel.getLMSUserCertificate(certificateRequest, "")
    }

    LaunchedEffect(certificateState) {
        when {
            certificateState.isLoading -> {
                isDialogVisible = true
            }

            certificateState.error.isNotEmpty() -> {
                logger.d("Certificate Error: ${certificateState.error}")
                isDialogVisible = false
            }

            certificateState.success != null -> {
                logger.d("Certificate Response :- ${certificateState.success!!.response}")
                if (certificateState.success!!.response != null) {
                    if (certificateState.success!!.response?.size != 0) {
                        certificateData = certificateState.success!!.response!!
                        println("Certificate Data :- $certificateData")
                    } else {
                        context.toast(certificateState.success!!.message!!)
                    }
                } else {
                    context.toast(certificateState.success!!.message!!)
                }
                isDialogVisible = false
            }
        }
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
            pageTitle = stringResource(R.string.certificate_txt),
            moreInfoIcon = painterResource(id = R.drawable.vertical_dot),
            isShowBackButton = true,
            isShowMoreInfo = false,
            onBackButtonClick = {
                startActivity(
                    context, Intent(context, StudentDashboardActivity::class.java), null
                ).apply { (context as? Activity)?.finish() }
            },
            onMoreInfoClick = {

            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    SectionDivider()

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(R.string.txt_congratulations),
                                    fontSize = 19.sp,
                                    fontFamily = fontBold,
                                    color = Gray,
                                    modifier = Modifier.padding(top = 10.dp)
                                )

                                Text(
                                    text = stringResource(R.string.txt_you_have_earned_it),
                                    fontSize = 19.sp,
                                    fontFamily = fontBold,
                                    color = BorderBlue,
                                    modifier = Modifier.padding(top = 10.dp, start = 5.dp)
                                )
                            }

                            Text(
                                text = stringResource(R.string.txt_cert_desc),
                                fontSize = 13.sp,
                                fontFamily = fontRegular,
                                color = Gray,
                                modifier = Modifier.padding(vertical = 5.dp)
                            )
                        }
                        // Module/Courses Tab
                        ThreeTabButtons(certificateData)
                    }
                }
            })
    }
}

@Composable
fun ThreeTabButtons(certificateData: MutableList<CertificateListResponse.CertificateResponse>) {
    val tabTitles = listOf(ALL, MODULE, COURSE)
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .padding(vertical = 15.dp)
            .fillMaxSize()
    ) {
        // Tab buttons
        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 15.dp)
        ) {
            tabTitles.forEachIndexed { index, title ->
                Button(
                    onClick = { selectedTabIndex = index },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedTabIndex == index) BorderBlue else Bg_Gray2,
                        contentColor = if (selectedTabIndex == index) White else Dark_01
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.padding(horizontal = 5.dp)
                ) {
                    Text(text = title, fontFamily = fontRegular, fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Tab content
        when (selectedTabIndex) {
            0 -> TabContent(selectedTabIndex, ALL, certificateData)

            1 -> TabContent(selectedTabIndex, MODULE, certificateData)

            2 -> TabContent(selectedTabIndex, COURSE, certificateData)

        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun TabContent(
    selectedTabIndex: Int,
    tabName: String,
    certificateData: MutableList<CertificateListResponse.CertificateResponse>,
) {
    var showFullScreen by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var textNotDownloadable = stringResource(R.string.txt_not_downloadable)
    var textNotShare = stringResource(R.string.txt_not_share)

    val filteredList = certificateData.filter {
        it.category!!.contains(tabName, ignoreCase = true)
    }

    if (filteredList.size != 0) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items = filteredList) { item ->
                Column(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = item.category.toString(),
                        modifier = Modifier.padding(5.dp),
                        color = if (selectedTabIndex == 2 || tabName == COURSE) {
                            LightOrange2
                        } else {
                            TextPurple
                        },
                        fontSize = 15.sp,
                        fontFamily = fontRegular
                    )

                    Text(
                        text = if (item.moduleID == 0) {
                            item.courseTitle.toString()
                                ?: stringResource(R.string.txt_learning_difficulties)
                        } else {
                            item.moduleTitle.toString()
                                ?: stringResource(R.string.txt_learning_difficulties)
                        },
                        modifier = Modifier.padding(5.dp),
                        color = Black,
                        fontFamily = fontBold,
                        fontSize = 14.sp
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(8.dp))
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxSize()
                                .height(220.dp),
                            contentScale = ContentScale.FillWidth,
                            painter = if (item.certificatePath != null) {
                                rememberAsyncImagePainter(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(item.certificatePath.toString())
                                        .decoderFactory(SvgDecoder.Factory()) // Adds SVG support
                                        .size(Size.ORIGINAL) // Use original or specify size
                                        .placeholder(R.drawable.certificate)
                                        .error(R.drawable.certificate).build()
                                )
                            } else {
                                painterResource(R.drawable.certificate)
                            },
                            contentDescription = IMG_DESCRIPTION
                        )

                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .background(Transparent)
                                .fillMaxWidth()
                                .padding(4.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Button(
                                onClick = {
                                    showFullScreen = true
                                },
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(end = 2.dp)
                                    .clip(RoundedCornerShape(8.dp)),

                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = White, contentColor = BorderBlue
                                )
                            ) {
                                TextWithIconOnLeft(
                                    text = stringResource(R.string.txt_view),
                                    icon = ImageVector.vectorResource(id = R.drawable.ic_expand),
                                    textColor = Black,
                                    textSize = 14.sp,
                                    iconColor = Color.Unspecified,
                                    onClick = {
                                        showFullScreen = true
                                    })
                            }
                            Button(
                                onClick = {
                                    if (item.isDownloadAvailable.toString() == "NO") {
                                        context.toast(textNotDownloadable)
                                    } else {
                                        val filename =
                                            "Course_certificate" + ((0..400).random()).toString()
                                        downloadFileTest(
                                            filename,
                                            "Downloading",
                                            if (item.appCertificatePath != null) {
                                                item.appCertificatePath.toString()
                                            } else {
                                                ""
                                            },
                                            context
                                        )
                                    }
                                },
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(end = 2.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = White, contentColor = BorderBlue
                                )
                            ) {
                                TextWithIconOnLeft(
                                    text = stringResource(R.string.txt_download),
                                    icon = ImageVector.vectorResource(id = R.drawable.ic_download),
                                    textColor = Black,
                                    textSize = 14.sp,
                                    iconColor = Color.Unspecified,
                                    onClick = {
                                        if (item.isDownloadAvailable.toString() == "NO") {
                                            context.toast(textNotDownloadable)
                                        } else {
                                            val filename =
                                                "Course_certificate" + ((0..400).random()).toString()
                                            downloadFileTest(
                                                filename,
                                                "Downloading",
                                                if (item.appCertificatePath != null) {
                                                    item.appCertificatePath.toString()
                                                } else {
                                                    ""
                                                },
                                                context
                                            )
                                        }
                                    })
                            }

                            Button(
                                onClick = {
                                    if (item.isDownloadAvailable.toString() == "NO") {
                                        context.toast(textNotShare)
                                    } else {
                                        val sendIntent = Intent().apply {
                                            action = Intent.ACTION_SEND
                                            putExtra(
                                                Intent.EXTRA_TEXT,
                                                item.appCertificatePath.toString()
                                            )
                                            type = "text/plain"
                                        }
                                        val shareIntent =
                                            Intent.createChooser(sendIntent, "Share via")
                                        context.startActivity(shareIntent)
                                    }
                                },
                                modifier = Modifier
                                    .wrapContentSize()
                                    .clip(RoundedCornerShape(8.dp)),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = White, contentColor = Black
                                )
                            ) {
                                TextWithIconOnLeft(
                                    text = "",
                                    icon = ImageVector.vectorResource(id = R.drawable.ic_share),
                                    textColor = BorderBlue,
                                    iconColor = Black,
                                    onClick = {
                                        if (item.isDownloadAvailable.toString() == "NO") {
                                            context.toast(textNotShare)
                                        } else {
                                            val sendIntent = Intent().apply {
                                                action = Intent.ACTION_SEND
                                                putExtra(
                                                    Intent.EXTRA_TEXT,
                                                    item.appCertificatePath.toString()
                                                )
                                                type = "text/plain"
                                            }
                                            val shareIntent =
                                                Intent.createChooser(sendIntent, "Share via")
                                            context.startActivity(shareIntent)
                                        }
                                    })
                            }
                        }
                    }
                }

                // Full Screen Image Dialog
                if (showFullScreen) {
                    ViewCertificate(
                        item.certificatePath,
                        item.isDownloadAvailable,
                        item.appCertificatePath
                    ) {
                        showFullScreen = false
                    }
                }
            }
        }
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items = certificateData) { item ->
                Column(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = item.category.toString(),
                        modifier = Modifier.padding(5.dp),
                        color = if (selectedTabIndex == 2 || tabName == COURSE) {
                            LightOrange2
                        } else {
                            TextPurple
                        },
                        fontSize = 15.sp,
                        fontFamily = fontRegular
                    )

                    Text(
                        text = if (item.moduleID == 0) {
                            item.courseTitle.toString()
                                ?: stringResource(R.string.txt_learning_difficulties)
                        } else {
                            item.moduleTitle.toString()
                                ?: stringResource(R.string.txt_learning_difficulties)
                        },
                        modifier = Modifier.padding(5.dp),
                        color = Black,
                        fontFamily = fontBold,
                        fontSize = 14.sp
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(8.dp))
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxSize()
                                .height(220.dp),
                            contentScale = ContentScale.FillWidth,
                            painter = if (item.certificatePath != null) {
                                rememberAsyncImagePainter(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(item.certificatePath.toString())
                                        .decoderFactory(SvgDecoder.Factory()) // Adds SVG support
                                        .size(Size.ORIGINAL) // Use original or specify size
                                        .placeholder(R.drawable.certificate)
                                        .error(R.drawable.certificate).build()
                                )
                            } else {
                                painterResource(R.drawable.certificate)
                            },
                            contentDescription = IMG_DESCRIPTION
                        )

                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .background(Transparent)
                                .fillMaxWidth()
                                .padding(4.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Button(
                                onClick = {
                                    showFullScreen = true
                                },
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(end = 2.dp)
                                    .clip(RoundedCornerShape(8.dp)),

                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = White, contentColor = BorderBlue
                                )
                            ) {
                                TextWithIconOnLeft(
                                    text = stringResource(R.string.txt_view),
                                    icon = ImageVector.vectorResource(id = R.drawable.ic_expand),
                                    textColor = Black,
                                    textSize = 14.sp,
                                    iconColor = Color.Unspecified,
                                    onClick = {
                                        showFullScreen = true
                                    })
                            }
                            Button(
                                onClick = {
                                    if (item.isDownloadAvailable.toString() == "NO") {
                                        context.toast(textNotDownloadable)
                                    } else {
                                        val filename =
                                            "Course_certificate" + ((0..400).random()).toString()
                                        downloadFileTest(
                                            filename,
                                            "Downloading",
                                            if (item.appCertificatePath != null) {
                                                item.appCertificatePath.toString()
                                            } else {
                                                ""
                                            },
                                            context
                                        )
                                    }
                                },
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(end = 2.dp)
                                    .clip(RoundedCornerShape(4.dp)),

                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = White, contentColor = BorderBlue
                                )
                            ) {
                                TextWithIconOnLeft(
                                    text = stringResource(R.string.txt_download),
                                    icon = ImageVector.vectorResource(id = R.drawable.ic_download),
                                    textColor = Black,
                                    textSize = 14.sp,
                                    iconColor = Color.Unspecified,
                                    onClick = {
                                        if (item.isDownloadAvailable.toString() == "NO") {
                                            context.toast(textNotDownloadable)
                                        } else {
                                            val filename =
                                                "Course_certificate" + ((0..400).random()).toString()
                                            downloadFileTest(
                                                filename,
                                                "Downloading",
                                                if (item.appCertificatePath != null) {
                                                    item.appCertificatePath.toString()
                                                } else {
                                                    ""
                                                },
                                                context
                                            )
                                        }
                                    })
                            }

                            Button(
                                onClick = {
                                    if (item.isDownloadAvailable.toString() == "NO") {
                                        context.toast(textNotShare)
                                    } else {
                                        val sendIntent = Intent().apply {
                                            action = Intent.ACTION_SEND
                                            putExtra(
                                                Intent.EXTRA_TEXT,
                                                item.appCertificatePath.toString()
                                            )
                                            type = "text/plain"
                                        }
                                        val shareIntent =
                                            Intent.createChooser(sendIntent, "Share via")
                                        context.startActivity(shareIntent)
                                    }
                                },
                                modifier = Modifier
                                    .wrapContentSize()
                                    .clip(RoundedCornerShape(8.dp)),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = White, contentColor = Black
                                )
                            ) {
                                TextWithIconOnLeft(
                                    text = "",
                                    icon = ImageVector.vectorResource(id = R.drawable.ic_share),
                                    textColor = BorderBlue,
                                    iconColor = Black,
                                    onClick = {
                                        if (item.isDownloadAvailable.toString() == "NO") {
                                            context.toast(textNotShare)
                                        } else {
                                            val sendIntent = Intent().apply {
                                                action = Intent.ACTION_SEND
                                                putExtra(
                                                    Intent.EXTRA_TEXT,
                                                    item.appCertificatePath.toString()
                                                )
                                                type = "text/plain"
                                            }
                                            val shareIntent =
                                                Intent.createChooser(sendIntent, "Share via")
                                            context.startActivity(shareIntent)
                                        }
                                    })
                            }
                        }
                    }
                }

                // Full Screen Image Dialog
                if (showFullScreen) {
                    ViewCertificate(
                        item.certificatePath,
                        item.isDownloadAvailable,
                        item.appCertificatePath
                    ) {
                        showFullScreen = false
                    }
                }
            }
        }
    }
}

fun downloadFileTest(fileName: String, desc: String, url: String, context: Context) {
    val request = DownloadManager.Request(Uri.parse(url))
        .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        .setTitle(fileName).setDescription(desc)
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setAllowedOverMetered(true).setAllowedOverRoaming(false).setDestinationInExternalFilesDir(
            context, Environment.DIRECTORY_DOWNLOADS, ".pdf"
        )
    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val downloadIds = downloadManager.enqueue(request)
}

@Composable
fun ViewCertificate(
    certificate: String?,
    isDownload: String?,
    certificateDownloadPath: String?,
    showFullScreen: () -> Unit,
) {
    val context = LocalContext.current
    var textNotDownloadable = stringResource(R.string.txt_not_downloadable)

    Dialog(onDismissRequest = { showFullScreen() }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(20.dp)
                    .clickable {
                        showFullScreen()
                    }
                    .align(Alignment.TopEnd),
                painter = painterResource(id = R.drawable.close_img),
                contentDescription = IMG_DESCRIPTION)

            Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillWidth,
                painter = if (certificate != null) {
                    rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(certificate.toString())
                            .decoderFactory(SvgDecoder.Factory()) // Adds SVG support
                            .size(Size.ORIGINAL) // Use original or specify size
                            .placeholder(R.drawable.certificate).error(R.drawable.certificate)
                            .build()
                    )
                } else {
                    painterResource(R.drawable.certificate)
                },
                contentDescription = IMG_DESCRIPTION
            )

            Button(
                onClick = {
                    if (isDownload == "NO") {
                        context.toast(textNotDownloadable)
                    } else {
                        val filename =
                            "Course_certificate" + ((0..400).random()).toString()
                        downloadFileTest(
                            filename,
                            "Downloading",
                            certificateDownloadPath?.toString() ?: "",
                            context
                        )
                    }
                },
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(20.dp)
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(4.dp)),

                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = White, contentColor = BorderBlue
                )
            ) {
                TextWithIconOnLeft(
                    text = stringResource(R.string.txt_download),
                    icon = ImageVector.vectorResource(id = R.drawable.ic_download),
                    textColor = Black,
                    textSize = 14.sp,
                    iconColor = Color.Unspecified,
                    onClick = {
                        if (isDownload == "NO") {
                            context.toast(textNotDownloadable)
                        } else {
                            val filename =
                                "Course_certificate" + ((0..400).random()).toString()
                            downloadFileTest(
                                filename,
                                "Downloading",
                                certificateDownloadPath?.toString() ?: "",
                                context
                            )
                        }
                    })
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun UserCertificatesUI() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val viewModel: DashboardViewModel = koinViewModel()
    ShowCertificateData(navController, context, viewModel)
}