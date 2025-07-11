package com.pi.ProjectInclusion.android.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kmptemplate.logger.LoggerProvider
import com.pi.ProjectInclusion.Bg_Gray
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.DARK_BODY_TEXT
import com.pi.ProjectInclusion.DARK_TITLE_TEXT
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.BtnUi
import com.pi.ProjectInclusion.android.common_UI.DefaultBackgroundUi
import com.pi.ProjectInclusion.android.common_UI.PasswordTextField
import com.pi.ProjectInclusion.android.navigation.AppRoute
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import okhttp3.Route

@Composable
fun SetNewPasswordScreen(navController: NavHostController, viewModel: LoginViewModel) {

    val context = LocalContext.current
    var enterPasswordStr = rememberSaveable { mutableStateOf("") }
    var enterConfirmPasswordStr = rememberSaveable { mutableStateOf("") }
    val enterPassword = stringResource(R.string.txt_Enter_your_password)
    val enterConfirmPassword = stringResource(R.string.txt_Confirm_your_password)
    val txtContinue = stringResource(R.string.txt_Update)
    var showError by remember { mutableStateOf(false) }
    var inValidPassword by remember { mutableStateOf(false) }
    var isDialogVisible by remember { mutableStateOf(false) }
    var buttonClicked by remember { mutableStateOf(false) }
    val showPassword = remember { mutableStateOf(false) }
    val showConfirmPassword = remember { mutableStateOf(false) }
    val txtCharacter = stringResource(R.string.txt_Passwords_must_be_8_16_characters)
    val txtUppercase = stringResource(R.string.txt_Including_one_uppercase_letter)
    val txtAtleastOne = stringResource(R.string.txt_Must_include_at_least_one_number)
    val txtSpecialCharacter = stringResource(R.string.txt_Must_include_one_special_characters)
    var isCheckedCharacter by remember { mutableStateOf(false) }
    var isCheckedUppercase by remember { mutableStateOf(false) }
    var isCheckedAtleastOne by remember { mutableStateOf(false) }
    var isCheckedSpecialCharacter by remember { mutableStateOf(false) }

    LoggerProvider.logger.d("Screen: " + "SetNewPasswordScreen()")

    DefaultBackgroundUi(isShowBackButton = true, onBackButtonClick = {
        navController.popBackStack()
        navController.navigate(AppRoute.ForgetPasswordUI.route)
    }, content = {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.txt_Set_New_Password),
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
                    text = stringResource(R.string.txt_Set_New_password_for_your_account),
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
                    text = buildAnnotatedString {
                        append(stringResource(R.string.txt_New_Password))
                        pushStyle(SpanStyle(color = Color.Red))
                        append("*")
                        pop()
                    },
                    modifier = Modifier.padding(
                        top = 24.dp,
                        bottom = 10.dp,
                        start = 8.dp, end = 8.dp
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

                PasswordTextField(
                    password = enterPasswordStr,
                    showPassword = showPassword,
                    hint = enterPassword
                )


                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.txt_Confirm_Password))
                        pushStyle(SpanStyle(color = Color.Red))
                        append("*")
                        pop()
                    },
                    modifier = Modifier.padding(
                        top = 24.dp,
                        bottom = 10.dp,
                        start = 8.dp, end = 8.dp
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

                PasswordTextField(
                    password = enterConfirmPasswordStr,
                    showPassword = showConfirmPassword,
                    hint = enterConfirmPassword
                )

                /*PasswordCheckField(isChecked, txtCharacter, Modifier.padding(
                    top = 16.dp,
                    start = 12.dp,
                    end = 12.dp,
                    bottom = 8.dp
                ))*/

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(
                        top = 16.dp,
                        start = 12.dp,
                        end = 12.dp,
                        bottom = 8.dp
                    )
                ) {
                    Checkbox(
                        checked = isCheckedCharacter,
                        onCheckedChange = { isCheckedCharacter = it }, // Disabled for display-only
                        colors = if (isSystemInDarkTheme()) {
                            CheckboxDefaults.colors(
                                checkedColor = Color.Transparent,     // Light purple-gray
                                uncheckedColor = Color.LightGray,   // Same for unchecked
                                checkmarkColor = PrimaryBlue
                            )
                        } else {
                            CheckboxDefaults.colors(
                                checkedColor = Color.Transparent,     // Light purple-gray
                                uncheckedColor = Color.LightGray,   // Same for unchecked
                                checkmarkColor = PrimaryBlue
                            )
                        },
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = txtCharacter,
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                        textAlign = TextAlign.Center,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = if (isSystemInDarkTheme()) {
                            DARK_BODY_TEXT
                        } else {
                            Gray
                        }
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 8.dp)
                ) {
                    Checkbox(
                        checked = isCheckedUppercase,
                        onCheckedChange = { isCheckedUppercase = it }, // Disabled for display-only
                        colors = if (isSystemInDarkTheme()) {
                            CheckboxDefaults.colors(
                                checkedColor = Color.Transparent,     // Light purple-gray
                                uncheckedColor = Color.LightGray,   // Same for unchecked
                                checkmarkColor = PrimaryBlue
                            )
                        } else {
                            CheckboxDefaults.colors(
                                checkedColor = Color.Transparent,     // Light purple-gray
                                uncheckedColor = Color.LightGray,   // Same for unchecked
                                checkmarkColor = PrimaryBlue
                            )
                        },
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = txtUppercase,
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                        textAlign = TextAlign.Center,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = if (isSystemInDarkTheme()) {
                            DARK_BODY_TEXT
                        } else {
                            Gray
                        }
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 8.dp)
                ) {
                    Checkbox(
                        checked = isCheckedAtleastOne,
                        onCheckedChange = { isCheckedAtleastOne = it }, // Disabled for display-only
                        colors = if (isSystemInDarkTheme()) {
                            CheckboxDefaults.colors(
                                checkedColor = Color.Transparent,     // Light purple-gray
                                uncheckedColor = Color.LightGray,   // Same for unchecked
                                checkmarkColor = PrimaryBlue
                            )
                        } else {
                            CheckboxDefaults.colors(
                                checkedColor = Color.Transparent,     // Light purple-gray
                                uncheckedColor = Color.LightGray,   // Same for unchecked
                                checkmarkColor = PrimaryBlue
                            )
                        },
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = txtAtleastOne,
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp),
                        textAlign = TextAlign.Center,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = if (isSystemInDarkTheme()) {
                            DARK_BODY_TEXT
                        } else {
                            Gray
                        }
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 8.dp)
                ) {
                    Checkbox(
                        checked = isCheckedSpecialCharacter,
                        onCheckedChange = {
                            isCheckedSpecialCharacter = it
                        }, // Disabled for display-only
                        colors = if (isSystemInDarkTheme()) {
                            CheckboxDefaults.colors(
                                checkedColor = Color.Transparent,     // Light purple-gray
                                uncheckedColor = Color.LightGray,   // Same for unchecked
                                checkmarkColor = PrimaryBlue
                            )
                        } else {
                            CheckboxDefaults.colors(
                                checkedColor = Color.Transparent,     // Light purple-gray
                                uncheckedColor = Color.LightGray,   // Same for unchecked
                                checkmarkColor = PrimaryBlue
                            )
                        },
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = txtSpecialCharacter,
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                        textAlign = TextAlign.Center,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = if (isSystemInDarkTheme()) {
                            DARK_BODY_TEXT
                        } else {
                            Gray
                        }
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
                    ),
                contentAlignment = Alignment.CenterEnd
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    BtnUi(
                        txtContinue, onClick = {
                            if (enterPasswordStr.value.isEmpty()) {
                                context.toast("Please enter password.")
                            } else if (enterConfirmPasswordStr.value.isEmpty()) {
                                context.toast("Please enter confirm password.")
                            } else if (enterConfirmPasswordStr.value.isEmpty() != enterPasswordStr.value.isEmpty()) {
                                context.toast("Password and confirm password should be same.")
                            } else {
                                showError = enterConfirmPasswordStr.value.isEmpty()
//                                val firstDigitChar = enterConfirmPasswordStr.value.toString().first()
//                                val firstDigit = firstDigitChar.digitToInt()
                                if (showError || enterConfirmPasswordStr.value.length < 10) {
                                    inValidPassword = true
                                } else { // if first digit of mobile is less than 6 then error will show
                                    if (enterConfirmPasswordStr.value.length < 6) {
                                        inValidPassword = true
                                    } else {
                                        isDialogVisible = true
//                                viewModel.saveUserPhoneNo(mobNo.value)
                                        buttonClicked = true
                                        navController.navigate(AppRoute.UserNameScreen.route)
                                    }
                                }
                            }
                        }, true
                    )
                }
            }
        }
    })
}