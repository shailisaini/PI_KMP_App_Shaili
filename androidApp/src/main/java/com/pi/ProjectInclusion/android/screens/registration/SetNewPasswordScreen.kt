package com.pi.ProjectInclusion.android.screens.registration

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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.kmptemplate.logger.LoggerProvider
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.Bg_Gray
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.DARK_BODY_TEXT
import com.pi.ProjectInclusion.DARK_TITLE_TEXT
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.AESEncryption.encryptAES
import com.pi.ProjectInclusion.android.common_UI.BtnUi
import com.pi.ProjectInclusion.android.common_UI.DefaultBackgroundUi
import com.pi.ProjectInclusion.android.common_UI.PasswordTextField
import com.pi.ProjectInclusion.android.navigation.AppRoute
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.ConstantVariables.ASTRICK
import com.pi.ProjectInclusion.constants.ConstantVariables.SUCCESS
import com.pi.ProjectInclusion.constants.ConstantVariables.TOKEN_PREF_KEY
import com.pi.ProjectInclusion.constants.ConstantVariables.USER_MOBILE_NO
import com.pi.ProjectInclusion.constants.ConstantVariables.USER_TYPE_ID
import com.pi.ProjectInclusion.data.model.authenticationModel.request.ForgetPasswordRequest
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import okhttp3.Route
import java.util.regex.Pattern

@Composable
fun SetNewPasswordScreen(
    onNext: () -> Unit,
    onBack: () -> Unit,
    viewModel: LoginViewModel,
) {

    logger.d("Screen: " + "SetNewPasswordScreen()")

    val context = LocalContext.current
    var enterPasswordStr = rememberSaveable { mutableStateOf("") }
    var enterConfirmPasswordStr = rememberSaveable { mutableStateOf("") }
    val enterPassword = stringResource(R.string.txt_Enter_your_password)
    val enterConfirmPassword = stringResource(R.string.txt_Confirm_your_password)
    val txtContinue = stringResource(R.string.txt_Update)
    var showError by remember { mutableStateOf(false) }
    var inValidPassword by remember { mutableStateOf(false) }
    var isDialogVisible by remember { mutableStateOf(false) }
    val showPassword = remember { mutableStateOf(false) }
    val showConfirmPassword = remember { mutableStateOf(false) }
    val txtCharacter = stringResource(R.string.txt_Passwords_must_be_8_16_characters)
    val txtUppercase = stringResource(R.string.txt_Including_one_uppercase_letter)
    val txtAtleastOne = stringResource(R.string.txt_Must_include_at_least_one_number)
    val txtSpecialCharacter = stringResource(R.string.txt_Must_include_one_special_characters)
    val enterPasswordMsgStr = stringResource(R.string.txt_Please_enter_Password)
    val enterConfirmPasswordMsgStr = stringResource(R.string.txt_Please_enter_confirm_Password)
    val enterConfirmPasswordSameMsgStr =
        stringResource(R.string.txt_Please_enter_confirm_Password_same)

    logger.d("Screen: " + "SetNewPasswordScreen()")

    val minLength = enterConfirmPasswordStr.value.length >= 8 || enterPasswordStr.value.length >= 8
    val hasLetter =
        enterConfirmPasswordStr.value.any { it.isUpperCase() } || enterPasswordStr.value.any { it.isUpperCase() }
    val hasDigit =
        enterConfirmPasswordStr.value.any { it.isDigit() } || enterPasswordStr.value.any { it.isDigit() }
    val hasSymbol = Pattern.compile("[^a-zA-Z0-9]").matcher(enterConfirmPasswordStr.value)
        .find() || Pattern.compile("[^a-zA-Z0-9]").matcher(enterPasswordStr.value).find()

    val forgetPasswordState by viewModel.forgetPasswordResponse.collectAsStateWithLifecycle()

    val encryptedPassword = enterConfirmPasswordStr.value.encryptAES().toString().trim()
    val encryptedUserName = viewModel.userNameValue!!.encryptAES().toString().trim()
    var userTypeId = viewModel.getPrefData(USER_TYPE_ID)
    var userName = viewModel.userNameValue

    var strToken = viewModel.getPrefData(TOKEN_PREF_KEY)

    LaunchedEffect(forgetPasswordState) {
        when {
            forgetPasswordState.isLoading -> {
                isDialogVisible = true
            }

            forgetPasswordState.error.isNotEmpty() -> {
                logger.d("Forget Password Error: ${forgetPasswordState.error}")
                isDialogVisible = false
            }

            forgetPasswordState.success != null -> {
                logger.d("Forget Password Response :- ${forgetPasswordState.success!!.response}")
                if (forgetPasswordState.success!!.status == true) {
                    context.toast(forgetPasswordState.success!!.response!!)
                    onNext()
                } else {
                    context.toast(forgetPasswordState.success!!.response!!)
                }
                isDialogVisible = false
            }
        }
    }

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
                        append(ASTRICK)
                        pop()
                    },
                    modifier = Modifier.padding(
                        top = 24.dp, bottom = 10.dp, start = 8.dp, end = 8.dp
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
                    password = enterPasswordStr, showPassword = showPassword, hint = enterPassword
                )


                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.txt_Confirm_Password))
                        pushStyle(SpanStyle(color = Color.Red))
                        append("*")
                        pop()
                    },
                    modifier = Modifier.padding(
                        top = 24.dp, bottom = 10.dp, start = 8.dp, end = 8.dp
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

                Row(
                    verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(
                        top = 16.dp, start = 12.dp, end = 12.dp, bottom = 8.dp
                    )
                ) {
                    Checkbox(
                        checked = minLength, onCheckedChange = null,
                        colors = if (isSystemInDarkTheme()) {
                            CheckboxDefaults.colors(
                                checkedColor = Color.Transparent,
                                uncheckedColor = Color.LightGray,
                                checkmarkColor = PrimaryBlue
                            )
                        } else {
                            CheckboxDefaults.colors(
                                checkedColor = Color.Transparent,
                                uncheckedColor = Color.LightGray,
                                checkmarkColor = PrimaryBlue
                            )
                        }, modifier = Modifier.size(20.dp)
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
                        checked = hasLetter, onCheckedChange = null,
                        colors = if (isSystemInDarkTheme()) {
                            CheckboxDefaults.colors(
                                checkedColor = Color.Transparent,
                                uncheckedColor = Color.LightGray,
                                checkmarkColor = PrimaryBlue
                            )
                        } else {
                            CheckboxDefaults.colors(
                                checkedColor = Color.Transparent,
                                uncheckedColor = Color.LightGray,
                                checkmarkColor = PrimaryBlue
                            )
                        }, modifier = Modifier.size(20.dp)
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
                        checked = hasDigit, onCheckedChange = null,
                        colors = if (isSystemInDarkTheme()) {
                            CheckboxDefaults.colors(
                                checkedColor = Color.Transparent,
                                uncheckedColor = Color.LightGray,
                                checkmarkColor = PrimaryBlue
                            )
                        } else {
                            CheckboxDefaults.colors(
                                checkedColor = Color.Transparent,
                                uncheckedColor = Color.LightGray,
                                checkmarkColor = PrimaryBlue
                            )
                        }, modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = txtAtleastOne,
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
                        checked = hasSymbol, onCheckedChange = null,
                        colors = if (isSystemInDarkTheme()) {
                            CheckboxDefaults.colors(
                                checkedColor = Color.Transparent,
                                uncheckedColor = Color.LightGray,
                                checkmarkColor = PrimaryBlue
                            )
                        } else {
                            CheckboxDefaults.colors(
                                checkedColor = Color.Transparent,
                                uncheckedColor = Color.LightGray,
                                checkmarkColor = PrimaryBlue
                            )
                        }, modifier = Modifier.size(20.dp)
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
                    ), contentAlignment = Alignment.CenterEnd
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
                                context.toast(enterPasswordMsgStr)
                            } else if (enterConfirmPasswordStr.value.isEmpty()) {
                                context.toast(enterConfirmPasswordMsgStr)
                            } else if (enterConfirmPasswordStr.value != enterPasswordStr.value) {
                                context.toast(enterConfirmPasswordSameMsgStr)
                            } else {
                                showError = enterConfirmPasswordStr.value.isEmpty()
                                if (showError || enterConfirmPasswordStr.value.length < 10) {
                                    inValidPassword = true
                                } else {
                                    isDialogVisible = true
                                    val passwordRequest =
                                        ForgetPasswordRequest(
                                            userName,
                                            userTypeId.toInt(),
                                            encryptedPassword
                                        )
                                    viewModel.forgetPassword(passwordRequest, strToken)
                                }
                            }
                        }, true
                    )
                }
            }
        }
    })
}