package com.pi.ProjectInclusion.android.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
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

@Composable
fun ForgetPasswordScreen(navController: NavHostController, viewModel: LoginViewModel) {

    val colors = MaterialTheme.colorScheme
    val context = LocalContext.current
    var mobNo = rememberSaveable { mutableStateOf("") }
    val enterMobile by remember { mutableStateOf("Enter Mobile No.") }
    val txtContinue by remember { mutableStateOf("Send OTP") }
    val invalidMobNo by remember { mutableStateOf("Enter valid mobile number") }
    var showError by remember { mutableStateOf(false) }
    var inValidMobNo by remember { mutableStateOf(false) }
    var isDialogVisible by remember { mutableStateOf(false) }
    var buttonClicked by remember { mutableStateOf(false) }

    DefaultBackgroundUi(isShowBackButton = true, onBackButtonClick = {
        navController.popBackStack()
        navController.navigate(AppRoute.LanguageSelect.route)
    }, content = {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Forgot Password",
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
                    text = "Please enter your mobile number to receive OTP for reset password. ",
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
                    text = "Mobile Number",
                    modifier = Modifier.padding(
                        top = 32.dp,
                        bottom = 10.dp
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
                    colors = colors,
                    number = mobNo,
                    trueFalse = true,
                    hint = enterMobile
                )

                if (inValidMobNo) {
                    Text(
                        invalidMobNo,
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
                    .padding(bottom = 32.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        color = if (isSystemInDarkTheme()) {
                            Dark_01
                        } else {
                            Color.White
                        }
                    ),
                contentAlignment = Alignment.CenterEnd
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 24.dp)
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
                        txtContinue, onClick = {
                            if (mobNo.value.isEmpty()) {
                                Toast.makeText(
                                    context, "Please enter mobile number.", Toast.LENGTH_SHORT
                                ).show()
                                navController.navigate(AppRoute.OtpSendVerifyUI.route)
                            } else {
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
                                    }
                                }
                            }
                        }, countNumber != null
                    )
                }
            }
        }
    })
}
