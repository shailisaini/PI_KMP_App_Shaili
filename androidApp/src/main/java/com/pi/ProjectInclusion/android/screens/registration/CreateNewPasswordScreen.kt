package com.pi.ProjectInclusion.android.screens.registration

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.kmptemplate.logger.LoggerProvider
import com.pi.ProjectInclusion.Bg_Gray
import com.pi.ProjectInclusion.Bg_Gray1
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.DARK_BODY_TEXT
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.Dark_02
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.PrimaryBlueLt1
import com.pi.ProjectInclusion.White
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.BtnUi
import com.pi.ProjectInclusion.android.common_UI.DefaultBackgroundUi
import com.pi.ProjectInclusion.android.common_UI.PasswordTextField
import com.pi.ProjectInclusion.android.common_UI.TextWithIconOnLeft
import com.pi.ProjectInclusion.android.navigation.AppRoute
import com.pi.ProjectInclusion.android.utils.fontRegular
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.CommonFunction.LoginScreenTitle
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel

@Composable
fun CreateNewPasswordScreen(navController: NavHostController, viewModel: LoginViewModel) {

    LoggerProvider.logger.d("Screen: " + "CreateNewPasswordScreen()")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Bg_Gray1),
        verticalArrangement = Arrangement.Top
    ) {
        DefaultBackgroundUi(isShowBackButton = true, onBackButtonClick = {
            navController.popBackStack()
            navController.navigate(AppRoute.ForgetPasswordUI.route)
        }, content = {
            CreateNewPasswordUI(navController)
        })
    }
}

@Composable
fun CreateNewPasswordUI(navController: NavHostController) {
    val context = LocalContext.current
    var showBottomSheet by remember { mutableStateOf(false) }
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
    val enterPasswordMsgStr = stringResource(R.string.txt_Please_enter_Password)
    val enterConfirmPasswordMsgStr = stringResource(R.string.txt_Please_enter_confirm_Password)
    val enterConfirmPasswordSameMsgStr =
        stringResource(R.string.txt_Please_enter_confirm_Password_same)
    var isCheckedCharacter by remember { mutableStateOf(false) }
    var isCheckedUppercase by remember { mutableStateOf(false) }
    var isCheckedAtleastOne by remember { mutableStateOf(false) }
    var isCheckedSpecialCharacter by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        SelectUserBottomSheet(onClick = {
            navController.popBackStack()
            navController.navigate(AppRoute.OtpSendVerifyUI.route)
        }, onDismiss = {
            showBottomSheet = false
        })
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth()
        ) {
            LoginScreenTitle(
                stringResource(R.string.txt_Set_your_Password), Black, Gray,
                stringResource(R.string.txt_set_password_desc)
            )

            Row(
                modifier = Modifier
                    .padding(start = 5.dp, end = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(color = PrimaryBlueLt1)
                        .wrapContentSize(),
                    elevation = CardDefaults.cardElevation(1.dp),
                    colors = CardDefaults.cardColors(
                        if (isSystemInDarkTheme()) {
                            Dark_02
                        } else {
                            PrimaryBlueLt1
                        }
                    ),
                    border = BorderStroke(width = 1.dp, PrimaryBlue),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(vertical = 5.dp, horizontal = 15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(R.drawable.img_teacher)
                                    .decoderFactory(SvgDecoder.Factory())
                                    .placeholder(R.drawable.img_teacher)
                                    .error(R.drawable.img_teacher)
                                    .build()
                            ),
                            contentDescription = IMG_DESCRIPTION,
                            modifier = Modifier
                                .size(50.dp)
                                .background(Color.Unspecified),
                            contentScale = ContentScale.Crop
                        )

                        Text(
                            text = stringResource(R.string.user_profile),
                            modifier = Modifier.padding(
                                top = 5.dp,
                                bottom = 5.dp
                            ),
                            textAlign = TextAlign.Start,
                            fontFamily = fontRegular,
                            fontSize = 10.sp,
                            color = Black
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.txt_change_user),
                        modifier = Modifier.padding(
                            top = 5.dp,
                            bottom = 5.dp
                        ),
                        textAlign = TextAlign.Start,
                        fontFamily = fontRegular,
                        fontSize = 12.sp,
                        color = Black
                    )
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .wrapContentHeight()
                            .clickable {
                                showBottomSheet = true
                            },
                        colors = if (isSystemInDarkTheme()) {
                            CardDefaults.cardColors(Dark_01)
                        } else {
                            CardDefaults.cardColors(
                                containerColor = White,
                                contentColor = White,
                                disabledContentColor = White,
                                disabledContainerColor = White
                            )
                        },
                        border = BorderStroke(0.8.dp, color = PrimaryBlue)
                    ) {
                        TextWithIconOnLeft(
                            text = stringResource(R.string.txt_change),
                            icon = ImageVector.vectorResource(id = R.drawable.ic_refresh),
                            textColor = PrimaryBlue,
                            iconColor = Color.Unspecified,
                            modifier = Modifier.padding(10.dp),
                            onClick = { showBottomSheet = true })
                    }
                }
            }

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
                    txtContinue,
                    onClick = {
                        if (enterPasswordStr.value.isEmpty()) {
                            context.toast(enterPasswordMsgStr)
                        } else if (enterConfirmPasswordStr.value.isEmpty()) {
                            context.toast(enterConfirmPasswordMsgStr)
                        } else if (enterConfirmPasswordStr.value.isEmpty() != enterPasswordStr.value.isEmpty()) {
                            context.toast(enterConfirmPasswordSameMsgStr)
                        } else {
                            showError = enterConfirmPasswordStr.value.isEmpty()
//                                val firstDigitChar = enterConfirmPasswordStr.value.toString().first()
//                                val firstDigit = firstDigitChar.digitToInt()
                            if (showError || enterConfirmPasswordStr.value.length < 8) {
                                inValidPassword = true
                            } else { // if first digit of mobile is less than 6 then error will show
                                isDialogVisible = true
                                buttonClicked = true
                                navController.navigate(AppRoute.EnterUserProfileScreen.route)
                            }
                        }
                    }, true
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectUserBottomSheet(
    onClick: () -> Unit = {},
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },
        sheetState = sheetState
    ) {
        // Sheet content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, bottom = 20.dp)
        ) {

            Text(
                stringResource(R.string.choose_an_option),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp, start = 10.dp),
                fontSize = 14.sp,
                fontFamily = fontRegular,
                color = Gray,
                textAlign = TextAlign.Start
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, bottom = 20.dp)
            ) {
                Card(
                    modifier = Modifier
                        .clickable {
                            onClick()

                        }
                        .padding(8.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(1.dp),
                    colors = CardDefaults.cardColors(
                        if (isSystemInDarkTheme()) {
                            Dark_02
                        } else {
                            Color.Companion.White
                        }
                    ),
                    border = BorderStroke(
                        width = 1.dp, PrimaryBlue
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = PrimaryBlueLt1),
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
                                            .data(R.drawable.img_teacher)
                                            .decoderFactory(SvgDecoder.Factory())
                                            .size(Size.ORIGINAL)
                                            .placeholder(R.drawable.img_teacher)
                                            .error(R.drawable.img_teacher)
                                            .build()
                                    ),
                                contentDescription = IMG_DESCRIPTION
                            )

                            Text(
                                "Teacher",
                                textAlign = TextAlign.Start,
                                maxLines = 1,
                                fontSize = 14.sp,
                                color = Gray,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(top = 5.dp)
//                            .heightIn(min = 40.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun NewPasswordScreen() {
    val navController = rememberNavController()
    CreateNewPasswordUI(navController)
}
