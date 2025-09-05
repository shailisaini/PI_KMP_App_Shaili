package com.pi.ProjectInclusion.android.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.DARK_TITLE_TEXT
import com.pi.ProjectInclusion.Dark_02
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.android.MyApplicationTheme
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.BtnUi
import com.pi.ProjectInclusion.android.navigation.AppRoute
import com.pi.ProjectInclusion.android.utils.fontBold
import com.pi.ProjectInclusion.android.utils.fontRegular
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION
import com.pi.ProjectInclusion.constants.ConstantVariables.MARKET_ID
import com.pi.ProjectInclusion.constants.ConstantVariables.PLAY_STORE_LINK
import com.pi.ProjectInclusion.constants.ConstantVariables.ROUTE
import com.pi.ProjectInclusion.constants.ConstantVariables.SPLASH_KEY
import com.pi.ProjectInclusion.constants.CustomDialog
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ForceUpdateResponse
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import com.pi.ProjectInclusion.constants.ConstantVariables.TOKEN_PREF_KEY

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel: LoginViewModel = koinViewModel()
            val context = LocalContext.current
            val (currentRoute, setRoute) = remember { mutableStateOf(SPLASH_KEY) }
            var isForward by remember { mutableStateOf(true) }  // is screen moving forward
            val forceUpdateState by viewModel.forceUpdateResponse.collectAsStateWithLifecycle()
            var isDialogVisible by remember { mutableStateOf(false) }
            var forceUpdateData by remember {
                mutableStateOf<ForceUpdateResponse.ForceUpdateData?>(
                    null
                )
            }
            var isForceUpdate by remember { mutableStateOf(false) }
            var serverDownDialog by remember { mutableStateOf(false) }

            fun navigateTo(route: String) {
                isForward = route != SPLASH_KEY // You can define logic to detect back
                setRoute(route)
            }

            CustomDialog(
                isVisible = isDialogVisible,
                onDismiss = { isDialogVisible = false },
                message = stringResource(R.string.txt_loading)
            )

            if (serverDownDialog) {
                ServerDownDialog {
                    serverDownDialog = false
                }
            }

            var appPackageName: String = ""
            var appVersion: String = ""
            var appMsg: String = stringResource(R.string.app_update)
            val deviceVersion = Build.VERSION.RELEASE
            val androidVersion = deviceVersion.substringBeforeLast(".")
            appPackageName = context.packageName

            try {
                val pInfo: PackageInfo =
                    context.packageManager.getPackageInfo(appPackageName, 0)
                appVersion = pInfo.versionName.toString()
                logger.d("Android version name & application version :- ${androidVersion.toDouble()}, ${appVersion.toDouble()}")
                viewModel.getForceUpdateApp(androidVersion.toDouble(), appVersion.toDouble())
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            LaunchedEffect(forceUpdateState) {
                when {
                    forceUpdateState.isLoading -> {
//                        isDialogVisible = true
                    }

                    forceUpdateState.error.isNotEmpty() -> {
                        logger.d("Force update error : ${forceUpdateState.success}")
                        isDialogVisible = false
                        serverDownDialog = true
                    }

                    forceUpdateState.success != null -> {
                        logger.d("Force update data:- ${forceUpdateState.success}")
                        if (forceUpdateState.success?.message == "Success") {
                            isDialogVisible = false
                            forceUpdateData = forceUpdateState.success!!.response
                            logger.d("Force update data details : $forceUpdateData")
                            if (forceUpdateState.success?.response?.isForceUpdate == 1) {
                                try {
                                    startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            "$MARKET_ID$appPackageName".toUri()
                                        )
                                    )
                                } catch (exception: ActivityNotFoundException) {
                                    logger.d("Play store redirect exception :- ${exception.message.toString()}")
                                    startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            "$PLAY_STORE_LINK$appPackageName".toUri()
                                        )
                                    )
                                }
                                context.toast(appMsg)
                            } else {
                                isForceUpdate = true
                            }
                        } else {
                            isDialogVisible = false
                            isForceUpdate = true
                            context.toast(forceUpdateState.success!!.message.toString())
                        }
                    }
                }
            }

            MyApplicationTheme {
                AnimatedContent(
                    targetState = currentRoute,
                    label = ROUTE,
                    transitionSpec = {
                        val duration = 400
                        if (isForward) {
                            (slideInHorizontally(
                                animationSpec = tween(duration),
                                initialOffsetX = { it }
                            ) + fadeIn(animationSpec = tween(duration))) with
                                    (slideOutHorizontally(
                                        animationSpec = tween(duration),
                                        targetOffsetX = { -it }
                                    ) + fadeOut(animationSpec = tween(duration)))
                        } else {
                            (slideInHorizontally(
                                animationSpec = tween(duration),
                                initialOffsetX = { -it }
                            ) + fadeIn(animationSpec = tween(duration))) with
                                    (slideOutHorizontally(
                                        animationSpec = tween(duration),
                                        targetOffsetX = { it }
                                    ) + fadeOut(animationSpec = tween(duration)))
                        }.using(SizeTransform(clip = false))
                    }
                ) { route ->
                    when (route) {
                        SPLASH_KEY -> SplashScreen {
                            if (isForceUpdate) {
                                // shaili
                                var userToken = viewModel.getPrefData(TOKEN_PREF_KEY)
                                if (userToken.isNotEmpty()) {
                                    navigateTo(AppRoute.TeacherDashboard.route)
                                }
                                else {
                                    navigateTo(AppRoute.LanguageSelect.route)
                                }
                            }
                        }

                        AppRoute.LanguageSelect.route -> {
                            if (isForceUpdate) {
                                LaunchedEffect(Unit) {
                                    delay(300) // optional smooth transition
                                    startActivity(
                                        Intent(
                                            this@SplashActivity,
                                            LoginNavigationScreen::class.java
                                        )
                                    ).also { finish() }
                                }
                            }
                        }

                        AppRoute.TeacherDashboard.route -> {
                            if (isForceUpdate) {
                                context.startActivity(
                                    Intent(context, StudentDashboardActivity::class.java)
                                )
                                (context as? Activity)?.finish()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ServerDownDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    if (isSystemInDarkTheme()) {
                        Dark_02
                    } else {
                        Color.White
                    }
                )
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(bottom = 16.dp, start = 8.dp, end = 8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.app_upgradation),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 16.dp, start = 8.dp, end = 8.dp),
                    fontFamily = fontBold,
                    fontSize = 18.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_TITLE_TEXT
                    } else {
                        Black
                    },
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Image(
                    painter = painterResource(id = R.drawable.sad_emoji),
                    contentDescription = IMG_DESCRIPTION,
                    modifier = Modifier
                        .width(90.dp)
                        .height(90.dp)
                        .background(Color.Unspecified),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.app_upgradation),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    fontFamily = fontRegular,
                    fontSize = 14.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_TITLE_TEXT
                    } else {
                        Gray
                    },
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BtnUi(
                        onClick = {
                            onDismiss()
                        },
                        title = stringResource(R.string.key_Retry),
                        enabled = true
                    )
                }
            }
        }
    }
}

@Composable
fun SplashScreen(onDone: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(3000) // simulate loading
        onDone()
    }
}