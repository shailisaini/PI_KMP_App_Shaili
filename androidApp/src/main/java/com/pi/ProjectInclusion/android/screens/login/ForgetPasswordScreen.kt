package com.pi.ProjectInclusion.android.screens.login

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.Bg_Gray
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.DARK_BODY_TEXT
import com.pi.ProjectInclusion.DARK_TITLE_TEXT
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.LightRed01
import com.pi.ProjectInclusion.android.common_UI.BtnUi
import com.pi.ProjectInclusion.android.common_UI.DefaultBackgroundUi
import com.pi.ProjectInclusion.android.common_UI.MobileTextField
import com.pi.ProjectInclusion.android.navigation.AppRoute
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.ChooseOneBottomSheet
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION
import com.pi.ProjectInclusion.contactUsTxt
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgetPasswordScreen(
    onNext: () -> Unit,  //OtpSendVerifyUI
    onBack: () -> Unit,
    viewModel: LoginViewModel,
) {

    logger.d("Screen: " + "ForgetPasswordScreen()")

    val colors = MaterialTheme.colorScheme
    val context = LocalContext.current
    var mobNo = rememberSaveable { mutableStateOf("") }
    val enterMobile = stringResource(R.string.txt_enter_mobile_no_)
    val txtContinue = stringResource(R.string.txt_Send_OTP)
    val invalidMobNo = stringResource(R.string.txt_Enter_valid_mobile_number)
    val enterMobileNoStr = stringResource(R.string.txt_Please_enter_mobile)
    var showError by remember { mutableStateOf(false) }
    var inValidMobNo by remember { mutableStateOf(false) }
    var isDialogVisible by remember { mutableStateOf(false) }
    var buttonClicked by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    var isBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true, confirmValueChange = { it != SheetValue.Hidden })

    var showBottomSheet by remember { mutableStateOf(false) }
    var sendOtpViaWhatsApp by remember { mutableStateOf(false) }
    var sendOtpViaCall by remember { mutableStateOf(false) }

    BottomSheetContactUsScreen(
        isBottomSheetVisible = isBottomSheetVisible,
        sheetState = sheetState,
        onDismiss = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                isBottomSheetVisible = false
            }
        })

    DefaultBackgroundUi(isShowBackButton = true, onBackButtonClick = {
        onBack()
    }, content = {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.txt_Forgot_Password),
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .fillMaxWidth(),
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_TITLE_TEXT
                    } else {
                        Black
                    }
                )

                Text(
                    text = stringResource(R.string.txt_enter_mobile_number_receive_OTP),
                    modifier = Modifier.padding(top = 8.dp, start = 10.dp, end = 10.dp),
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_BODY_TEXT
                    } else {
                        Gray
                    }
                )

                Text(
                    text = stringResource(R.string.txt_Mobile_Number),
                    modifier = Modifier.padding(
                        top = 32.dp, bottom = 10.dp
                    ),
                    textAlign = TextAlign.Start,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_BODY_TEXT
                    } else {
                        Bg_Gray
                    }
                )

                MobileTextField(
                    isIcon = false,
                    icon = null,
                    colors = colors, number = mobNo, trueFalse = true, hint = enterMobile.toString()
                )

                if (inValidMobNo) {
                    Text(
                        invalidMobNo.toString(),
                        color = LightRed01,
                        modifier = Modifier.padding(start = 5.dp),
                        fontSize = 10.sp
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(bottom = 16.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        color = if (isSystemInDarkTheme()) {
                            Dark_01
                        } else {
                            Color.White
                        }
                    ), contentAlignment = Alignment.CenterEnd
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    var countNumber by remember { mutableStateOf<Int?>(null) }
                    if (mobNo.value.length == 10) {
                        countNumber = 1
                    } else {
                        countNumber = null
                    }

                    BtnUi(
                        title = txtContinue, onClick = {
                            if (mobNo.value.isEmpty()) {
                                /* scope.launch {
                                     isBottomSheetVisible = true
                                     sheetState.expand()
                                 }*/
                                context.toast(enterMobileNoStr)
                            } else {
//                                showBottomSheet = true

                                showError = mobNo.value.isEmpty()
                                val firstDigitChar = mobNo.value.toString().first()
                                val firstDigit = firstDigitChar.digitToInt()
                                if (showError || mobNo.value.length < 10) {
                                    inValidMobNo = true
                                } else { // if first digit of mobile is less than 6 then error will show
                                    if (firstDigit < 6) {
                                        inValidMobNo = true
                                    } else {
                                        isDialogVisible = true
//                                viewModel.saveUserPhoneNo(mobNo.value)
                                        buttonClicked = true
                                        onNext()
                                    }
                                }
                            }
                        }, countNumber != null
                    )
                }
            }
        }
    })

    // dialog to show otp on whatsapp or call
    if (showBottomSheet) {
        ChooseOneBottomSheet(onCallClick = {
            sendOtpViaCall = true
        }, onWhatsappClick = {
            sendOtpViaWhatsApp = true
        }, onDismiss = {
            showBottomSheet = false
        })
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContactUsScreen(
    isBottomSheetVisible: Boolean,
    sheetState: SheetState,
    onDismiss: () -> Unit,
) {

    val context: Context = LocalContext.current

    if (isBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            containerColor = if (isSystemInDarkTheme()) {
                Dark_01
            } else {
                Color.White
            },
            contentColor = MaterialTheme.colorScheme.onSurface,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            dragHandle = null,
            scrimColor = if (isSystemInDarkTheme()) {
                DARK_TITLE_TEXT.copy(alpha = 0.5f)
            } else {
                Color.Black.copy(alpha = 0.5f)
            },
            windowInsets = WindowInsets.ime
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(vertical = 15.dp, horizontal = 15.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "", style = MaterialTheme.typography.headlineLarge.copy(
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black
                                }, fontSize = 18.sp, fontFamily = FontFamily(
                                    Font(R.font.roboto_bold, FontWeight.Bold)
                                ), textAlign = TextAlign.Start
                            )
                        )

                        Image(
                            painter = painterResource(R.drawable.line),
                            contentDescription = IMG_DESCRIPTION,
                            modifier = Modifier
                                .wrapContentWidth()
                                .align(Alignment.CenterVertically)
                                .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                                .clickable {
                                    onDismiss.invoke()
                                }
                                .clip(RoundedCornerShape(100.dp))
                                .border(
                                    width = 2.dp, color = if (isSystemInDarkTheme()) {
                                        DARK_TITLE_TEXT
                                    } else {
                                        Gray
                                    }, shape = CircleShape
                                ))

                        Text(
                            text = "", style = MaterialTheme.typography.headlineLarge.copy(
                                color = if (isSystemInDarkTheme()) {
                                    DARK_TITLE_TEXT
                                } else {
                                    Black
                                }, fontSize = 18.sp, fontFamily = FontFamily(
                                    Font(R.font.roboto_bold, FontWeight.Bold)
                                ), textAlign = TextAlign.Start
                            )
                        )
                    }

                    Text(
                        text = stringResource(id = R.string.txt_Contact_us),
                        color = if (isSystemInDarkTheme()) {
                            DARK_BODY_TEXT
                        } else {
                            Gray
                        },
                        fontSize = 12.sp,
                        fontFamily = FontFamily(
                            Font(R.font.roboto_regular, FontWeight.Normal)
                        ),
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 32.dp, start = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.CenterVertically)
                                .padding(start = 8.dp, end = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            IconButton(onClick = {
                                val strCode: String = ""
                                val appPackageName = context.packageName
                                val appLink =
                                    "https://play.google.com/store/apps/details?id=$appPackageName"

                                val intent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(
                                        Intent.EXTRA_TEXT,
                                        "Hey, check out this awesome app:- $appLink \n\n" + "Your referal code:- $strCode"
                                    )
                                    setPackage("com.whatsapp") // Ensures it opens in WhatsApp
                                }

                                try {
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    val fallbackIntent =
                                        Intent(Intent.ACTION_VIEW, Uri.parse(appLink))
                                    context.startActivity(fallbackIntent)
                                }
                            }) {
                                Image(
                                    painter = painterResource(id = R.drawable.whats_app_icon),
                                    contentDescription = "WhatsApp",
                                    modifier = Modifier
                                        .size(75.dp)
                                        .align(Alignment.CenterHorizontally)
                                )
                            }

                            Text(
                                text = "Whatsapp",
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    color = if (isSystemInDarkTheme()) {
                                        DARK_TITLE_TEXT
                                    } else {
                                        contactUsTxt
                                    }, fontSize = 14.sp, fontFamily = FontFamily(
                                        Font(R.font.roboto_medium, FontWeight.Medium)
                                    ), textAlign = TextAlign.Start
                                )
                            )
                        }

                        Column(
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.CenterVertically)
                                .padding(start = 8.dp, end = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            IconButton(onClick = {
                                val strCode: String = ""
                                val appPackageName = context.packageName
                                val appLink =
                                    "https://play.google.com/store/apps/details?id=$appPackageName"
                                val intent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(
                                        Intent.EXTRA_TEXT,
                                        "Hey, check out this awesome app:- $appLink \n\n" + "Your referal code:- $strCode"
                                    )
                                    `package` = "com.instagram.android"
                                }
                                val resolveInfo = context.packageManager.resolveActivity(intent, 0)
                                if (resolveInfo != null) {
                                    startActivity(context, intent, null)
                                } else {
                                    val browserIntent =
                                        Intent(Intent.ACTION_VIEW, Uri.parse(appLink))
                                    startActivity(context, browserIntent, null)
                                }
                            }) {
                                Image(
                                    painter = painterResource(id = R.drawable.gmail_icon),
                                    contentDescription = "Gmail",
                                    modifier = Modifier
                                        .size(75.dp)
                                        .align(Alignment.CenterHorizontally)
                                )
                            }

                            Text(
                                text = "Gmail", style = MaterialTheme.typography.headlineLarge.copy(
                                    color = if (isSystemInDarkTheme()) {
                                        DARK_TITLE_TEXT
                                    } else {
                                        contactUsTxt
                                    }, fontSize = 14.sp, fontFamily = FontFamily(
                                        Font(R.font.roboto_medium, FontWeight.Medium)
                                    ), textAlign = TextAlign.Start
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}