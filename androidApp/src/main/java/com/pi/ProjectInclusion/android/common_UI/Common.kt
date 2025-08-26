package com.pi.ProjectInclusion.android.common_UI

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.BannerColor03
import com.pi.ProjectInclusion.Bg_Gray2
import com.pi.ProjectInclusion.Bg_Gray3
import com.pi.ProjectInclusion.Bg_Gray4
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.BodyTextLight
import com.pi.ProjectInclusion.BorderBlue
import com.pi.ProjectInclusion.CardColor01
import com.pi.ProjectInclusion.DARK_BODY_TEXT
import com.pi.ProjectInclusion.DARK_DEFAULT_BUTTON_TEXT
import com.pi.ProjectInclusion.DARK_TITLE_TEXT
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.Dark_02
import com.pi.ProjectInclusion.Dark_03
import com.pi.ProjectInclusion.Dark_Selected_BG
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.GrayLight01
import com.pi.ProjectInclusion.GrayLight02
import com.pi.ProjectInclusion.GrayLight03
import com.pi.ProjectInclusion.GrayLight04
import com.pi.ProjectInclusion.GrayLight05
import com.pi.ProjectInclusion.LightBlue
import com.pi.ProjectInclusion.LightPurple04
import com.pi.ProjectInclusion.LightRed01
import com.pi.ProjectInclusion.OrangeSubTitle
import com.pi.ProjectInclusion.PRIMARY_AURO_BLUE
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.PrimaryBlueLt
import com.pi.ProjectInclusion.PrimaryBlueLt1
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.common_UI.EncryptedCommonFunction.decryptedEmail
import com.pi.ProjectInclusion.android.common_UI.EncryptedCommonFunction.decryptedPhoneNo
import com.pi.ProjectInclusion.android.common_UI.EncryptedCommonFunction.encryptedPhoneNo
import com.pi.ProjectInclusion.android.utils.fontBold
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.fontRegular
import com.pi.ProjectInclusion.android.utils.fontSemiBold
import com.pi.ProjectInclusion.constants.ConstantVariables.IMAGE_ALL_TYPE
import com.pi.ProjectInclusion.constants.ConstantVariables.IMAGE_MIME
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION
import com.pi.ProjectInclusion.constants.ConstantVariables.JPG
import kotlinx.coroutines.delay
import com.pi.ProjectInclusion.constants.ConstantVariables.KEY_FEMALE
import com.pi.ProjectInclusion.constants.ConstantVariables.KEY_MALE
import com.pi.ProjectInclusion.constants.ConstantVariables.PI_DOCUMENT
import com.pi.ProjectInclusion.contactUsTxt
import java.util.Calendar

fun BackButtonPress(navController: NavHostController, route: String) {
    navController.popBackStack()
    navController.navigate(route)
}

@Composable
fun SurfaceLine() {
    Surface(
        modifier = Modifier
            .height(1.dp)
            .width(50.dp),
        shape = RoundedCornerShape(corner = CornerSize(4.dp)),
        color = if (isSystemInDarkTheme()) {
            DARK_BODY_TEXT
        } else {
            GrayLight02
        }
    ) {
        // Empty surface to create a line
    }
}

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseOneBottomSheet(
    onCallClick: () -> Unit = {},
    onWhatsappClick: () -> Unit = {}, onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        }, sheetState = sheetState
    ) {
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, bottom = 20.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(horizontal = 10.dp)
                        .weight(1f)
                        .clickable {
                            onCallClick()
                            onDismiss()
                        },
                    colors = if (isSystemInDarkTheme()) {
                        CardDefaults.cardColors(Dark_01)
                    } else {
                        CardDefaults.cardColors(
                            containerColor = com.pi.ProjectInclusion.White,
                            contentColor = com.pi.ProjectInclusion.White,
                            disabledContentColor = com.pi.ProjectInclusion.White,
                            disabledContainerColor = com.pi.ProjectInclusion.White
                        )
                    },
                    border = BorderStroke(1.dp, color = GrayLight01)
                ) {
                    TextWithIconOnLeft(
                        text = stringResource(R.string.txt_OTP_call),
                        icon = ImageVector.vectorResource(id = R.drawable.ic_otp_on_call),
                        textColor = Black,
                        iconColor = Color.Unspecified,
                        modifier = Modifier.padding(10.dp),
                        onClick = {
                            onCallClick()
                            onDismiss()
                        })
                }
                Card(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .wrapContentHeight()
                        .clickable { onWhatsappClick() }
                        .padding(end = 10.dp)
                        .weight(1f),
                    colors = if (isSystemInDarkTheme()) {
                        CardDefaults.cardColors(Dark_01)
                    } else {
                        CardDefaults.cardColors(
                            containerColor = com.pi.ProjectInclusion.White,
                            contentColor = com.pi.ProjectInclusion.White,
                            disabledContentColor = com.pi.ProjectInclusion.White,
                            disabledContainerColor = com.pi.ProjectInclusion.White
                        )
                    },
                    border = BorderStroke(1.dp, color = GrayLight01)) {
                    TextWithIconOnLeft(
                        text = stringResource(R.string.txt_otp_whatsapp),
                        icon = ImageVector.vectorResource(id = R.drawable.ic_whatsapp_otp),
                        textColor = Black,
                        iconColor = Color.Unspecified,
                        modifier = Modifier.padding(10.dp),
                        onClick = {
                            onWhatsappClick()
                            onDismiss()
                        })
                }
            }
        }
    }
}


@Composable
fun DefaultBackgroundUi(
    modifier: Modifier = Modifier,
    isShowBackButton: Boolean = true,
    onBackButtonClick: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxWidth(), color = White
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(
                    color = if (isSystemInDarkTheme()) {
                        Dark_01
                    } else {
                        White
                    }
                ), verticalArrangement = Arrangement.Top
        ) {

            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                if (isShowBackButton && !LocalInspectionMode.current) {
                    Image(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(top = 16.dp, start = 8.dp)
                            .offset(y = 8.dp, x = 8.dp)
                            .size(50.dp) // adjust the size as needed
                            .clickable(onClick = onBackButtonClick),
                        painter = painterResource(id = R.drawable.round_back_key),
                        contentDescription = IMG_DESCRIPTION
                    )
                }
            }

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(
                        if (isSystemInDarkTheme()) {
                            Dark_01
                        } else {
                            White
                        }
                    )
            ) {
                Column(
                    modifier = modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    content()
                }
            }
        }
    }
}

@Composable
fun TextViewField(
    isIcon: Boolean = false,
    icon: ImageVector?,
    colors: ColorScheme,
    trueFalse: Boolean,
    modifier: Modifier = Modifier,
    text: MutableState<String> = remember { mutableStateOf("") },
    hint: String = remember { mutableStateOf("") }.toString(),
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = text.value,
        onValueChange = {
            text.value = it

            //  Hide keyboard on 20 digits
            if (it.length == 20) {
                keyboardController?.hide()
            }
        },
        enabled = trueFalse,
        placeholder = { Text(hint, color = GrayLight01, fontSize = 14.sp) },
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
        visualTransformation = VisualTransformation.None,

        leadingIcon = if (isIcon) {
            {
                Icon(
                    imageVector = icon!!,
                    contentDescription = null,
                    tint = if (isSystemInDarkTheme()) White else Black
                )
            }
        } else null,

        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .drawBehind {
                if (isFocused) {
                    drawRoundRect(
                        color = LightBlue.copy(alpha = 0.4f),
                        cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx()),
                        style = Stroke(width = 4.dp.toPx())
                    )
                }
            }
            .border(
                width = 1.dp, color = when {
                    isFocused -> LightBlue
                    isSystemInDarkTheme() -> Dark_02
                    else -> GrayLight02
                }, shape = RoundedCornerShape(8.dp)
            ),
        interactionSource = interactionSource,
        textStyle = TextStyle(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = if (isSystemInDarkTheme()) {
                DARK_DEFAULT_BUTTON_TEXT
            } else {
                Black
            }
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = if (isSystemInDarkTheme()) {
                Dark_02
            } else {
                White
            },
            focusedTextColor = Gray,
            unfocusedTextColor = colors.onSurface,
            focusedIndicatorColor = Transparent,
            unfocusedContainerColor = if (isSystemInDarkTheme()) {
                Dark_02
            } else {
                White
            },
            unfocusedIndicatorColor = Transparent,
            disabledContainerColor = BannerColor03
        ))
}

@Composable
fun TextViewFieldEmail(
    isIcon: Boolean = false,
    icon: ImageVector?,
    colors: ColorScheme,
    trueFalse: Boolean,
    modifier: Modifier = Modifier,
    text: MutableState<String> = remember { mutableStateOf("") },
    hint: String = remember { mutableStateOf("") }.toString(),
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val shownEmail = decryptedEmail(text.value.toString().trim()) // for email
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = shownEmail,
        onValueChange = {
            text.value = it
        },
        enabled = trueFalse,
        placeholder = { Text(hint, color = GrayLight01, fontSize = 14.sp) },
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
        visualTransformation = VisualTransformation.None,

        leadingIcon = if (isIcon) {
            {
                Icon(
                    imageVector = icon!!,
                    contentDescription = null,
                    tint = if (isSystemInDarkTheme()) White else Black
                )
            }
        } else null,

        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .drawBehind {
                if (isFocused) {
                    drawRoundRect(
                        color = LightBlue.copy(alpha = 0.4f),
                        cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx()),
                        style = Stroke(width = 4.dp.toPx())
                    )
                }
            }
            .border(
                width = 1.dp, color = when {
                    isFocused -> LightBlue
                    isSystemInDarkTheme() -> Dark_02
                    else -> GrayLight02
                }, shape = RoundedCornerShape(8.dp)
            ),
        interactionSource = interactionSource,
        textStyle = TextStyle(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = if (isSystemInDarkTheme()) {
                DARK_DEFAULT_BUTTON_TEXT
            } else {
                Black
            }
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = if (isSystemInDarkTheme()) {
                Dark_02
            } else {
                White
            },
            focusedTextColor = Gray,
            unfocusedTextColor = colors.onSurface,
            focusedIndicatorColor = Transparent,
            unfocusedContainerColor = if (isSystemInDarkTheme()) {
                Dark_02
            } else {
                White
            },
            unfocusedIndicatorColor = Transparent,
            disabledContainerColor = BannerColor03
        ))
}

@Composable
fun MobileTextField(
    isIcon: Boolean = false,
    icon: ImageVector?,
    colors: ColorScheme,
    enable: Boolean,
    modifier: Modifier = Modifier,
    number: MutableState<String>,
    hint: String = "",
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val keyboardController = LocalSoftwareKeyboardController.current
    val shownMobile = decryptedPhoneNo(number.value.trim())
    TextField(
        value = shownMobile,
        onValueChange = {
            if (it.length <= 10 && it.all { char -> char.isDigit() }) {
                number.value = it

                //  Hide keyboard on 10 digits
                if (it.length == 10) {
                    keyboardController?.hide()
                }
            }
        },
        enabled = enable,
        placeholder = {
            Text(
                hint,
                color = GrayLight01,
                fontFamily = fontRegular,
                fontSize = 14.sp) },
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        visualTransformation = VisualTransformation.None,

        leadingIcon = if (isIcon) {
            {
                Icon(
                    imageVector = icon!!,
                    contentDescription = null,
                    tint = if (isSystemInDarkTheme()) White else Gray
                )
            }
        } else null,

        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .drawBehind {
                if (isFocused) {
                    drawRoundRect(
                        color = LightBlue.copy(alpha = 0.4f),
                        cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx()),
                        style = Stroke(width = 4.dp.toPx())
                    )
                }
            }
            .border(
                width = 1.dp, color = when {
                    isFocused -> LightBlue
                    isSystemInDarkTheme() -> Dark_02
                    else -> GrayLight02
                }, shape = RoundedCornerShape(8.dp)
            ),
        interactionSource = interactionSource,
        textStyle = TextStyle(
            fontFamily = fontSemiBold, fontSize = 14.sp, color = if (isSystemInDarkTheme()) {
                DARK_DEFAULT_BUTTON_TEXT
            } else {
                Black
            }
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = if (isSystemInDarkTheme()) {
                Dark_02
            } else {
                White
            },
            focusedTextColor = Gray,
            unfocusedTextColor = colors.onSurface,
            focusedIndicatorColor = Transparent,
            unfocusedContainerColor = if (isSystemInDarkTheme()) {
                Dark_02
            } else {
                White
            },
            unfocusedIndicatorColor = Transparent,
            disabledContainerColor = BannerColor03
        ))
}

@Composable
fun UserNameTextField(
    isIcon: Boolean = false,
    icon: ImageVector?,
    colors: ColorScheme,
    trueFalse: Boolean,
    modifier: Modifier = Modifier,
    number: MutableState<String> = remember { mutableStateOf("") },
    hint: String = remember { mutableStateOf("") }.toString(),
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = number.value,
        onValueChange = {

            if (it.length <= 10) {
                number.value = it
            }
        },
        enabled = trueFalse,
        placeholder = { Text(hint, color = GrayLight01, fontSize = 14.sp) },
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
        visualTransformation = VisualTransformation.None,

        leadingIcon = if (isIcon) {
            {
                Icon(
                    imageVector = icon!!,
                    contentDescription = null,
                    tint = if (isSystemInDarkTheme()) White else Gray
                )
            }
        } else null,

        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .drawBehind {
                if (isFocused) {
                    drawRoundRect(
                        color = LightBlue.copy(alpha = 0.4f),
                        cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx()),
                        style = Stroke(width = 4.dp.toPx())
                    )
                }
            }
            .border(
                width = 1.dp, color = when {
                    isFocused -> LightBlue
                    isSystemInDarkTheme() -> Dark_02
                    else -> GrayLight02
                }, shape = RoundedCornerShape(8.dp)
            ),
        interactionSource = interactionSource,
        textStyle = TextStyle(
            fontFamily = fontSemiBold, fontSize = 14.sp, color = if (isSystemInDarkTheme()) {
                DARK_DEFAULT_BUTTON_TEXT
            } else {
                Black
            }
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = if (isSystemInDarkTheme()) {
                Dark_02
            } else {
                White
            },
            focusedTextColor = Gray,
            unfocusedTextColor = colors.onSurface,
            focusedIndicatorColor = Transparent,
            unfocusedContainerColor = if (isSystemInDarkTheme()) {
                Dark_02
            } else {
                White
            },
            unfocusedIndicatorColor = Transparent,
            disabledContainerColor = GrayLight03
        ))
}

@Preview
@Composable
fun OTPBtnUi(
    title: String = "Continue", onClick: () -> Unit = {},
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = White, contentColor = PrimaryBlue
        ),
        border = BorderStroke(1.dp, color = PrimaryBlue)
    ) {
        Text(
            title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp, top = 8.dp),
            fontSize = 16.sp,
            color = PrimaryBlue,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationHeader(
    title: String = "",
    colors: Color = Black,
    subtitle: String = "",
    subtitleColor: Color = OrangeSubTitle,
    onBackButtonClick: () -> Unit = {},
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = White)
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                val y = size.height - strokeWidth / 2
                drawLine(
                    color = GrayLight02,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = strokeWidth
                )
            }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 15.dp)
        ) {
            Image(
                modifier = Modifier
                    .offset(y = 8.dp, x = 8.dp)
                    .size(50.dp) // adjust the size as needed
                    .clickable(onClick = onBackButtonClick),
                painter = painterResource(id = R.drawable.round_back_key),
                contentDescription = IMG_DESCRIPTION
            )
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .wrapContentHeight()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    title,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontFamily = fontBold,
                    color = colors,
                    modifier = Modifier.padding(start = 10.dp)
                )
                Text(
                    subtitle,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    fontFamily = fontMedium,
                    color = subtitleColor,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun SmallBtnUi(
    title: String = stringResource(R.string.text_continue),
    onClick: () -> Unit = {},
    enabled: Boolean = false,
) {
    Button(
        onClick = onClick, modifier = Modifier
            .wrapContentSize()
            .clip(
                RoundedCornerShape(
                    5.dp
                )
            ), shape = RoundedCornerShape(12.dp), colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) {
                if (isSystemInDarkTheme()) {
                    Dark_Selected_BG
                } else {
                    PrimaryBlue
                }
            } else {
                if (isSystemInDarkTheme()) {
                    GrayLight03
                } else {
                    GrayLight03
                }
            }, contentColor = if (enabled) {
                White
            } else {
                White
            }
        )
    ) {
        Text(
            title,
            modifier = Modifier
                .wrapContentSize()
                .padding(bottom = 8.dp, top = 8.dp),
            fontSize = 16.sp,
            fontFamily = fontMedium,
            color = if (enabled) {
                White
            } else {
                White
            },
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun BtnUi(
    title: String = "Continue", onClick: () -> Unit = {}, enabled: Boolean = false,
) {
    Button(
        onClick = onClick, modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    5.dp
                )
            ), shape = RoundedCornerShape(12.dp), colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) {
                if (isSystemInDarkTheme()) {
                    Dark_Selected_BG
                } else {
                    PrimaryBlue
                }
            } else {
                if (isSystemInDarkTheme()) {
                    GrayLight03
                } else {
                    GrayLight03
                }
            }, contentColor = if (enabled) {
                White
            } else {
                White
            }
        )
    ) {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp, top = 8.dp),
            fontSize = 16.sp,
            fontFamily = fontMedium,
            color = if (enabled) {
                White
            } else {
                White
            },
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun BtnWithRightIconUi(
    title: String = "Continue", onClick: () -> Unit = {}, enabled: Boolean = false,
) {
    Button(
        onClick = onClick, modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    5.dp
                )
            ), shape = RoundedCornerShape(12.dp), colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) {
                if (isSystemInDarkTheme()) {
                    Dark_Selected_BG
                } else {
                    PrimaryBlue
                }
            } else {
                if (isSystemInDarkTheme()) {
                    GrayLight03
                } else {
                    GrayLight03
                }
            }, contentColor = if (enabled) {
                White
            } else {
                White
            }
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                modifier = Modifier.padding(bottom = 8.dp, top = 8.dp),
                fontSize = 16.sp,
                fontFamily = fontMedium,
                color = if (enabled) {
                    White
                } else {
                    White
                },
                textAlign = TextAlign.Center
            )

            Icon(
                painter = painterResource(R.drawable.double_right_arrow_img),
                contentDescription = IMG_DESCRIPTION,
                modifier = Modifier.size(20.dp),
                tint = White,
            )
        }
    }
}

@Composable
fun YesBtnUi(
    title: String = "Continue",
    modifier: Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = false,
) {
    Button(
        onClick = onClick, modifier = modifier.clip(
            RoundedCornerShape(
                5.dp
            )
        ), shape = RoundedCornerShape(12.dp), colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) {
                if (isSystemInDarkTheme()) {
                    Dark_Selected_BG
                } else {
                    PrimaryBlue
                }
            } else {
                if (isSystemInDarkTheme()) {
                    GrayLight03
                } else {
                    GrayLight03
                }
            }, contentColor = if (enabled) {
                White
            } else {
                White
            }
        )
    ) {
        Text(
            text = title,
            modifier = Modifier
                .wrapContentWidth()
                .padding(bottom = 8.dp, top = 8.dp),
            fontSize = 16.sp,
            color = if (enabled) {
                White
            } else {
                White
            },
            textAlign = TextAlign.Center,
            fontFamily = fontMedium,
        )
    }
}

@Preview
@Composable
fun NoBtnUi(
    title: String = "No", onClick: () -> Unit = {}, enabled: Boolean = false,
) {
    Button(
        onClick = onClick, modifier = Modifier
            .width(135.dp)
            .border(
                width = 1.dp, color = PrimaryBlue, RoundedCornerShape(
                    8.dp
                )
            ), shape = RoundedCornerShape(12.dp), colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) {
                if (isSystemInDarkTheme()) {
                    White
                } else {
                    White
                }
            } else {
                if (isSystemInDarkTheme()) {
                    White
                } else {
                    White
                }
            }, contentColor = if (enabled) {
                White
            } else {
                White
            }
        )
    ) {
        Text(
            text = title,
            modifier = Modifier
                .wrapContentWidth()
                .padding(bottom = 8.dp, top = 8.dp),
            fontSize = 16.sp,
            color = if (enabled) {
                PrimaryBlue
            } else {
                PrimaryBlue
            },
            textAlign = TextAlign.Center,
            fontFamily = fontMedium,
        )
    }
}

@Composable
fun InputTextField(
    isIcon: Boolean = false,
    icon: ImageVector?,
    modifier: Modifier = Modifier,
    value: MutableState<String> = remember { mutableStateOf("") },
    placeholder: String = "",
    editable: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text, // Add a parameter for the keyboard type,
    maxLength: Int = Int.MAX_VALUE, // Add a parameter for the maximum length,
) {
    val colors = MaterialTheme.colorScheme
    TextField(
        value = value.value,
        onValueChange = when (placeholder) {
            "Enter Beneficiary Name" -> {
                { newValue ->
                    if (newValue.length <= 35 && newValue.all { char -> !char.isDigit() }) {
                        value.value = newValue
                    }
                }
            }

            "Enter Account Number" -> {
                { newValue ->
                    if (newValue.length <= 18 && newValue.all { char -> char.isDigit() }) {
                        value.value = newValue
                    }
                }
            }

            "Enter Confirm Account Number" -> {
                { newValue ->
                    if (newValue.length <= 18 && newValue.all { char -> char.isDigit() }) {
                        value.value = newValue
                    }
                }
            }

            "Enter IFSC Code" -> {
                { newValue ->
                    if (newValue.length <= 11 && newValue.all { char -> char.isLetterOrDigit() }) {
                        value.value = newValue
                    }
                }
            }

            else -> {
                { newValue ->
                    if (newValue.length <= maxLength) { // Check if the new value is within the limit
                        value.value = newValue
                    }
                }
            }
        }, // Update the value state here
        shape = RoundedCornerShape(8.dp),
        enabled = editable,
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType), // Use the keyboardType parameter
        visualTransformation = VisualTransformation.None,
        leadingIcon = if (isIcon) {
            {
                Icon(
                    imageVector = icon!!,
                    contentDescription = null,
                    tint = if (isSystemInDarkTheme()) White else Gray
                )
            }
        } else null,
        placeholder = {
            Text(
                text = placeholder,
                color = if (isSystemInDarkTheme()) {
                    colors.onSurface // Light background
                } else {
                    Color.Gray
                },
                fontSize = 14.sp,
                fontFamily = fontRegular
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .border(
                width = 1.dp, color = if (isSystemInDarkTheme()) {
                    Dark_03
                } else {
                    GrayLight02
                }, shape = RoundedCornerShape(8.dp)
            ),
        textStyle = TextStyle(
//            color = Black,
            color = colors.onSurface,
            fontSize = 14.sp,
            fontStyle = FontStyle.Normal,
            fontFamily = fontSemiBold
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = if (isSystemInDarkTheme()) Dark_03 else White,
    focusedTextColor = if (isSystemInDarkTheme()) Dark_03 else Gray,
    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
    focusedIndicatorColor = Transparent,
    unfocusedContainerColor = if (isSystemInDarkTheme()) Dark_03 else White,
    unfocusedIndicatorColor = Transparent,
    disabledContainerColor = GrayLight03
    )
    )
}

@Composable
fun OtpInputField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpLength: Int = 6,
    shouldShowCursor: Boolean = false,
    shouldCursorBlink: Boolean = false,
    onOtpModified: (String, Boolean) -> Unit,
) {
    val colors = MaterialTheme.colorScheme
    val keyboardController = LocalSoftwareKeyboardController.current
    val textState = remember { mutableStateOf(TextFieldValue(otpText)) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    LaunchedEffect(textState) {
        if (otpText.length <= otpLength) {
            textState.value = TextFieldValue(otpText, selection = TextRange(otpText.length))
        }
    }

    BasicTextField(
        value = textState.value,
        onValueChange = {
            if (it.text.length <= otpLength) {
                textState.value = it
                onOtpModified(it.text, it.text.length == otpLength)
                if (it.text.length == otpLength) {
                    keyboardController?.hide()
                }
            }
        },
        visualTransformation = if (otpText.isNotEmpty()) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation(
                '*'
            )
        },

        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .focusRequester(focusRequester),
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()
            ) {
                repeat(otpLength) { index ->    // repeating box 6 times for otp & 4 times for PIN
                    CharacterContainer(
                        index = index,
                        text = textState.value.text,
                        shouldShowCursor = shouldShowCursor,
                        shouldCursorBlink = shouldCursorBlink,
                        colors = colors
                    )
                }
            }
        })
}


@Composable
fun TermsAndPrivacyText(
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        val annotatedText = buildAnnotatedString {
            append("By continue, you agree to our ")

            // Terms of Service
            pushStringAnnotation(tag = "TERMS", annotation = "terms")
            withStyle(
                style = SpanStyle(
                    color = PrimaryBlue, fontSize = 12.sp, fontFamily = fontRegular
                )
            ) {
                append("Terms of Service")
            }
            pop()

            append(" and ")

            // Privacy Policy
            pushStringAnnotation(tag = "PRIVACY", annotation = "privacy")
            withStyle(
                style = SpanStyle(
                    color = PrimaryBlue, fontFamily = fontRegular
                )
            ) {
                append("Privacy Policy")
            }
            pop()

            append(".")
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            ClickableText(
                text = annotatedText, style = TextStyle(
                    fontSize = 14.sp,
                    color = Gray,
                    textAlign = TextAlign.Center,
                    fontFamily = fontRegular
                ), onClick = { offset ->
                    annotatedText.getStringAnnotations("TERMS", offset, offset).firstOrNull()
                        ?.let { onTermsClick() }

                    annotatedText.getStringAnnotations("PRIVACY", offset, offset).firstOrNull()
                        ?.let { onPrivacyClick() }
                })
        }
    }
}

@Composable
internal fun CharacterContainer(
    index: Int,
    text: String,
    shouldShowCursor: Boolean,
    shouldCursorBlink: Boolean,
    colors: ColorScheme,
) {
    val isFocused = text.length == index
    val character = when {
        index < text.length -> text[index].toString()
        else -> ""
    }

    // Cursor visibility state
    val cursorVisible = remember { mutableStateOf(false) }

    // Blinking effect for the cursor
    LaunchedEffect(key1 = isFocused) {
        if (isFocused && shouldShowCursor && shouldCursorBlink) {
            cursorVisible.value = true
            while (true) {
                delay(800) // Adjust the blinking speed here
                cursorVisible.value = !cursorVisible.value
            }
        } else {
            cursorVisible.value = false
        }
    }

    Box(contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .size(45.dp) // Ensure this is wide enough
                .border(
                    width = if (isFocused) 2.dp else 1.dp,
                    color = if (isFocused) {
                        if (isSystemInDarkTheme()) {
                            Dark_03
                        } else {
                            LightBlue
                        }
                    } else {
                        if (isSystemInDarkTheme()) {
                            LightBlue
                        } else {
                            CardColor01
                        }
                    }, shape = RoundedCornerShape(6.dp)
                )
                .padding(2.dp)
        ) {
            Text(
                text = character,
                color = if (isFocused) {
                    if (isSystemInDarkTheme()) {
                        PRIMARY_AURO_BLUE
                    } else {
                        PrimaryBlue
                    }
                } else {
                    if (isSystemInDarkTheme()) {
                        colors.onSurface
                    } else {
                        Black
                    }
                },
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center), // Center the text within the Box
                fontSize = 18.sp
            )
        }


        // Display cursor when focused
        AnimatedVisibility(visible = isFocused && cursorVisible.value) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(2.dp)
                    .height(24.dp) // Adjust height according to your design
                    .background(
                        if (isSystemInDarkTheme()) {
                            Dark_03
                        } else {
                            GrayLight02
                        }
                    )
            )
        }
    }
}

@Composable
fun TextWithIconOnLeft(
    moreSpace: Boolean = false,
    text: String,
    textSize: TextUnit = 15.sp,
    icon: ImageVector,
    textColor: Color,
    iconColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .clickable { onClick() }
            .wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon, contentDescription = null, // Decorative element
            tint = iconColor
        )

        if (moreSpace) {
            Spacer(modifier = Modifier.width(10.dp))
        }

        if (text != "") {
            Spacer(modifier = Modifier.width(4.dp)) // Add space between text and icon
            Text(
                text = text,
                modifier = Modifier.padding(horizontal = 5.dp),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = textColor,
                    fontSize = textSize,
                    fontFamily = fontMedium,
                    textAlign = TextAlign.Start
                )
            )
        }
    }
}

@Composable
fun TextWithIconOnRight(
    text: String,
    icon: ImageVector,
    textColor: Color,
    iconColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier.clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = text, style = MaterialTheme.typography.bodyMedium.copy(
                color = textColor,
                fontSize = 14.sp,
                textAlign = TextAlign.Start,
                fontFamily = fontMedium,
            )
        )

//        Spacer(modifier = Modifier.width(4.dp)) // Add space between text and icon

        Icon(
            imageVector = icon, contentDescription = IMG_DESCRIPTION, // Decorative element
            tint = iconColor, modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    password: MutableState<String> = remember { mutableStateOf("") },
    showPassword: MutableState<Boolean> = remember { mutableStateOf(false) },
    hint: String = remember { mutableStateOf("") }.toString(),
) {
    TextField(
        value = password.value,
        onValueChange = {
            if (it.length <= 15) {
                password.value = it
            }
        },
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .wrapContentHeight()
            .border(
                width = 1.dp, color = if (isSystemInDarkTheme()) {
                    Dark_03
                } else {
                    GrayLight02
                }, shape = RoundedCornerShape(8.dp)
            ),
        textStyle = TextStyle(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = if (isSystemInDarkTheme()) {
                White
            } else {
                Black
            }
        ),

        placeholder = { Text(hint, color = GrayLight01, fontSize = 13.sp) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = if (isSystemInDarkTheme()) {
                Dark_03
            } else {
                White
            }, focusedTextColor = if (isSystemInDarkTheme()) {
                White
            } else {
                Black
            }, unfocusedTextColor = if (isSystemInDarkTheme()) {
                Dark_03
            } else {
                White
            }, focusedIndicatorColor = if (isSystemInDarkTheme()) {
                Dark_03
            } else {
                White
            }, unfocusedContainerColor = if (isSystemInDarkTheme()) {
                Dark_03
            } else {
                White
            }, unfocusedIndicatorColor = if (isSystemInDarkTheme()) {
                Dark_03
            } else {
                White
            }
        ),
        trailingIcon = {
            IconButton(onClick = { showPassword.value = !showPassword.value }) {
                Icon(
                    imageVector = if (showPassword.value) ImageVector.vectorResource(id = R.drawable.eye_open) else ImageVector.vectorResource(
                        id = R.drawable.eye_close
                    ), contentDescription = "Show/Hide Password"
                )
            }
        },
        visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(
            '*'
        )
    )
}


@Composable
fun PasswordCheckField(
    isChecked: Boolean = false,
    hint: String = remember { mutableStateOf("") }.toString(),
    padding: Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = padding
    ) {
        Checkbox(
            checked = isChecked, onCheckedChange = { it }, // Disabled for display-only
            colors = if (isSystemInDarkTheme()) {
                CheckboxDefaults.colors(
                    checkedColor = Transparent,     // Light purple-gray
                    uncheckedColor = Color.LightGray,   // Same for unchecked
                    checkmarkColor = PrimaryBlue
                )
            } else {
                CheckboxDefaults.colors(
                    checkedColor = Transparent,     // Light purple-gray
                    uncheckedColor = Color.LightGray,   // Same for unchecked
                    checkmarkColor = PrimaryBlue
                )
            }, modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = hint,
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

@Preview
@Composable
fun CustomProgressBar(
    percentage: Float = 0f,
    modifier: Modifier = Modifier,
    color: Color = PrimaryBlue,
    secondaryColor: Color = PrimaryBlueLt,
    strokeWidth: Float = 12f,
) {
    val clampedPercentage = percentage.coerceIn(1f, 100f)

    val animatedPercentage by animateFloatAsState(
        targetValue = clampedPercentage, animationSpec = tween(durationMillis = 100)
    )

    Box(
        contentAlignment = Alignment.Center, modifier = modifier
    ) {
        Canvas(modifier = modifier) {
            val diameter = size.minDimension
            val radius = diameter / 2
            val center = size.center

            drawArc(
                color = secondaryColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(strokeWidth, cap = StrokeCap.Round)
            )

            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360 * (animatedPercentage / 30),
                useCenter = false,
                topLeft = center - Offset(radius, radius),
                size = Size(diameter, diameter),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        Text(
            text = "${animatedPercentage.toInt()}",
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = color,
        )
    }
}

@Composable
fun CustomHorizontalProgressBar(
    progressBar: Float,
    progressColor: Color,
    backgroundColor: Color,
) {
    val progress by remember { mutableStateOf(progressBar) } // Example progress value
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(4.dp)
                .fillMaxWidth(fraction = 1f)
                .clip(RoundedCornerShape(50))
                .background(
                    if (isSystemInDarkTheme()) {
                        Dark_03
                    } else {
                        backgroundColor
                    }
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fraction = progress)
                    .background(progressColor)
            )
        }
    }
}

@Composable
fun DetailsBackgroundUi(
    imgUrl: String,
    studentName: String,
    grade: String,
    modifier: Modifier = Modifier,
    isShowBackButton: Boolean = true,
    isShowProfile: Boolean = true,
    isShowMoreInfo: Boolean = true,
    onBackButtonClick: () -> Unit = {},
    onMoreInfoClick: () -> Unit = {},
    content: @Composable (() -> Unit),
) {
    Surface(
        modifier = modifier.fillMaxWidth(), color = White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = if (isSystemInDarkTheme()) {
                        PrimaryBlue
                    } else {
                        PrimaryBlue
                    }
                ), verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .background(
                        color = if (isSystemInDarkTheme()) {
                            PrimaryBlue
                        } else {
                            PrimaryBlue
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .background(Color.Unspecified)
                ) {
                    if (isShowBackButton && !LocalInspectionMode.current) {
                        Image(
                            painter = painterResource(R.drawable.left_back_arrow),
                            contentDescription = IMG_DESCRIPTION,
                            modifier = Modifier
                                .background(Color.Unspecified)
                                .clickable(onClick = onBackButtonClick),
                            colorFilter = ColorFilter.tint(White)
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier.background(Color.Unspecified)
                    ) {
                        if (isShowProfile && !LocalInspectionMode.current) {
                            CustomCircularImageViewWithBorder(
                                gender = "Female",
                                imageRes = painterResource(R.drawable.dummy_image),
                                borderColor = GrayLight02,
                                borderWidth = 1f
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.background(
                            color = if (isSystemInDarkTheme()) {
                                PrimaryBlue
                            } else {
                                PrimaryBlue
                            }
                        )
                    ) {
                        Text(
                            text = studentName.toString(),
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                            fontFamily = fontBold,
                            fontSize = 14.sp,
                            color = if (isSystemInDarkTheme()) {
                                DARK_TITLE_TEXT
                            } else {
                                White
                            },
                            textAlign = TextAlign.Start
                        )

                        Text(
                            text = grade.toString(),
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                            fontFamily = fontMedium,
                            fontSize = 12.sp,
                            color = if (isSystemInDarkTheme()) {
                                LightPurple04
                            } else {
                                LightPurple04
                            },
                            textAlign = TextAlign.Start
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Spacer(modifier = Modifier.width(16.dp))

                Spacer(modifier = Modifier.width(16.dp))

                Spacer(modifier = Modifier.width(16.dp))

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .background(Color.Unspecified)
                ) {
                    if (isShowMoreInfo && !LocalInspectionMode.current) {
                        Image(
                            painter = painterResource(R.drawable.more_info_img),
                            contentDescription = IMG_DESCRIPTION,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(start = 10.dp)
                                .background(Color.Unspecified)
                                .clickable(onClick = onMoreInfoClick),
                            colorFilter = ColorFilter.tint(White)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(
                        if (isSystemInDarkTheme()) {
                            Dark_01
                        } else {
                            White
                        }
                    )
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    content()
                }
            }
        }
    }
}

@Preview
@Composable
fun DetailsNoImgBackgroundUi(
    backgroundColor: Color = PrimaryBlue,
    textColor: Color = White,
    pageTitle: String = "",
    moreInfoIcon: Painter = painterResource(R.drawable.close_img),
    modifier: Modifier = Modifier,
    isShowBackButton: Boolean = true,
    isShowMoreInfo: Boolean = true,
    onBackButtonClick: () -> Unit = {},
    onMoreInfoClick: () -> Unit = {},
    content: @Composable (() -> Unit) = {},
) {
    Surface(
        modifier = modifier.fillMaxWidth(), color = White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = if (isSystemInDarkTheme()) {
                        PrimaryBlue
                    } else {
                        backgroundColor
                    }
                ), verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .background(
                        color = if (isSystemInDarkTheme()) {
                            PrimaryBlue
                        } else {
                            backgroundColor
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .background(Color.Unspecified)
                ) {
                    if (isShowBackButton && !LocalInspectionMode.current) {
                        Image(
                            painter = painterResource(R.drawable.left_back_arrow),
                            contentDescription = IMG_DESCRIPTION,
                            modifier = Modifier
                                .background(Color.Unspecified)
                                .clickable(onClick = onBackButtonClick),
                            colorFilter = ColorFilter.tint(textColor)
                        )
                    }
                }

                Text(
                    text = pageTitle.toString(),
                    modifier = Modifier.padding(end = 16.dp, top = 8.dp, bottom = 8.dp),
                    fontFamily = fontBold,
                    fontSize = 18.sp,
                    color = if (isSystemInDarkTheme()) {
                        DARK_TITLE_TEXT
                    } else {
                        textColor
                    },
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.width(16.dp))

                Spacer(modifier = Modifier.width(16.dp))

                Spacer(modifier = Modifier.width(16.dp))

                Spacer(modifier = Modifier.width(16.dp))

                Spacer(modifier = Modifier.width(16.dp))

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .background(Color.Unspecified)
                ) {
                    if (isShowMoreInfo && !LocalInspectionMode.current) {
                        Image(
                            painter = moreInfoIcon,
                            contentDescription = IMG_DESCRIPTION,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(start = 8.dp, end = 6.dp)
                                .size(30.dp)
                                .background(Color.Unspecified)
                                .clickable(onClick = onMoreInfoClick),
                            colorFilter = ColorFilter.tint(textColor)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(
                        if (isSystemInDarkTheme()) {
                            Dark_01
                        } else {
                            White
                        }
                    )
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    content()
                }
            }
        }
    }
}

@Composable
fun CustomCircularImageViewWithBorder(
    gender: String,
    imageRes: Any, // Can be either a URL or a drawable resource ID
    borderColor: Color,
    borderWidth: Float,
    modifier: Modifier = Modifier, // Allow passing additional modifiers
) {
    Box(
        modifier = modifier
            .size(50.dp)
            .border(
                width = borderWidth.dp, color = borderColor, shape = CircleShape
            )
            .clip(CircleShape) // Clip to a circle
    ) {
        Image(
            painter = painterResource(
                if (gender != null) {
                    getGenderIconState(gender)
                } else {
                    R.drawable.dummy_image
                }
            ),
            contentDescription = null, // Provide a meaningful description if needed
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Unspecified),
            contentScale = ContentScale.Crop // Crop to fill the circle
        )
    }
}

@Composable
fun TextFieldWithLeftIcon(
    text : String = "",
    modifier: Modifier = Modifier,
    value: MutableState<String> = remember { mutableStateOf("") },
    placeholder: String = stringResource(R.string.enter_here),
) {
    val colors = MaterialTheme.colorScheme
    val selectedBorder = BorderStroke(
        width = 0.5.dp, if (isSystemInDarkTheme()) {
            Dark_03
        } else {
            GrayLight02
        }
    )

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.wrapContentHeight(),
        colors = if (isSystemInDarkTheme()) {
            CardDefaults.cardColors(Dark_03)
        } else {
            CardDefaults.cardColors(White)
        },
        border = selectedBorder,
    ) {
        Row(
            modifier = modifier
                .background(
                    if (isSystemInDarkTheme()) {
                        Dark_03
                    } else {
                        White
                    }
                )
                .height(50.dp)
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.calendar),
                contentDescription = "data is here",
                modifier = Modifier
                    .size(24.dp)
                    .padding(start = 7.dp)
            )
            Text(
                text =  if (text.isEmpty()){
                    placeholder
                }
                else{
                    text
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(10.dp),
                color = if (isSystemInDarkTheme()) {
                    DARK_DEFAULT_BUTTON_TEXT
                } else {
                    if (text.isEmpty()) {
                        BodyTextLight
                    }
                    else{
                        Black
                    }
                },
                fontSize = 14.sp,
                fontFamily = if (text.isEmpty()) {
                    fontRegular
                }
                else {
                    fontSemiBold
                }
            )
        }
    }
}

@Composable
fun TextFieldWithRightIcon(
    modifier: Modifier = Modifier,
    value: MutableState<String> = remember { mutableStateOf("") },
    placeholder: String = stringResource(R.string.enter_here),
) {
    val colors = MaterialTheme.colorScheme
    val selectedBorder = BorderStroke(
        width = 0.5.dp, if (isSystemInDarkTheme()) {
            Dark_03
        } else {
            GrayLight02
        }
    )

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.wrapContentHeight(),
        colors = if (isSystemInDarkTheme()) {
            CardDefaults.cardColors(Dark_03)
        } else {
            CardDefaults.cardColors(White)
        },
        border = selectedBorder,
    ) {
        Row(
            modifier = modifier
                .background(
                    if (isSystemInDarkTheme()) {
                        Dark_03
                    } else {
                        White
                    }
                )
                .height(50.dp)
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = placeholder,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(10.dp),
                color = if (isSystemInDarkTheme()) {
                    DARK_DEFAULT_BUTTON_TEXT
                } else {
                    Black
                },
                fontSize = 14.sp,
                fontFamily = fontSemiBold
            )

            Image(
                painter = painterResource(R.drawable.calendar),
                contentDescription = IMG_DESCRIPTION,
                modifier = Modifier
                    .size(30.dp)
                    .padding(end = 12.dp)
            )
        }
    }
}

fun getGenderIconState(state: String?): Int {
    return when (state) {
        KEY_FEMALE -> R.drawable.dummy_image
        "F" -> R.drawable.dummy_image
        KEY_MALE -> R.drawable.dummy_image
        "M" -> R.drawable.dummy_image
        else -> R.drawable.dummy_image
    }
}

@Preview
@Composable
fun GenderOption(
    gender: String = "",
    isSelected: Boolean = false,
    onSelected: () -> Unit = {},
) {
    val icon = when (gender) {
        KEY_MALE -> R.drawable.ic_male
        KEY_FEMALE -> R.drawable.ic_female
        else -> R.drawable.ic_other
    }

    val selectedIcon = when (gender) {
        KEY_MALE -> R.drawable.ic_male_selected
        KEY_FEMALE -> R.drawable.ic_female_selected
        else -> R.drawable.ic_other_selected
    }

    Column(
        modifier = Modifier
            .clickable { onSelected() }
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = if (isSelected) painterResource(id = selectedIcon) else painterResource(id = icon),
            contentDescription = gender,
            tint = Color.Unspecified,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = gender,
            color = Black,
            fontSize = 14.sp,
            fontFamily = if (isSelected) fontSemiBold else fontRegular
        )
    }
}

@Preview
@Composable
fun ResidenceOption(
    residence: String = "",
    isSelected: Boolean = false,
    onSelected: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .padding(end = 16.dp)
            .wrapContentWidth()
            .clickable { onSelected() },
        verticalAlignment = Alignment.CenterVertically
    ) {

        RadioButton(
            selected = isSelected,
            onClick = {
                onSelected()
            },
        )

        Text(
            text = residence,
            color = Black,
            fontSize = 14.sp,
            fontFamily = if (isSelected) fontSemiBold else fontRegular
        )
    }
}

@SuppressLint("NewApi")
@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
fun showDatePickerDialog(
    context: Context, onDateSelected: (year: Int, month: Int, dayOfMonth: Int) -> Unit,
) {
    val calendar = Calendar.getInstance()
    val fiveYearsAgoCalendar = Calendar.getInstance().apply {
        add(Calendar.YEAR, -4)
    }
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) // This is 0-based
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

    // Set the maximum date to today
//    val maxDate = calendar.timeInMillis
    val maxDate = fiveYearsAgoCalendar.timeInMillis

    val datePickerDialog = DatePickerDialog(
        context, { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            // Adjust the month by adding 1 to convert from 0-based to 1-based
            val mon = selectedMonth
            onDateSelected(selectedYear, mon + 1, selectedDayOfMonth)
        }, year, month, dayOfMonth
        // Set the background color of the date picker dialog
    ).apply {
        datePicker.maxDate = maxDate
//        datePicker.minDate = calendar.timeInMillis
    }
    // Set the maximum date to prevent future date selection
//    datePickerDialog.datePicker.maxDate = maxDate
    datePickerDialog.show()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchoolListBottomSheet(
    isBottomSheetVisible: Boolean,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onDecline: () -> Unit,
    onTextSelected: (String) -> Unit = { "" },
    myList: List<String>,
) {
//    var mySchoolList: List<String> = myList
    val colors = MaterialTheme.colorScheme
//    val viewModel: LoginViewModel = hiltViewModel()
//    val languageListName = stringResource(id = R.string.key_lang_list)
    val chooseOption = stringResource(id = R.string.choose_option)
    val searchHere = stringResource(id = R.string.txt_search_here)
    val otherOption = stringResource(id = R.string.txt_other)
    var languageData = HashMap<String, String>()
//    languageData = viewModel.getLanguageTranslationData(languageListName)

    if (isBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            containerColor = if (isSystemInDarkTheme()) {
                Black
            } else {
                White
            },
            contentColor = MaterialTheme.colorScheme.onSurface,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            dragHandle = null,
            scrimColor = Color.Black.copy(alpha = 0.5f),
            windowInsets = WindowInsets(0, 0, 0, 0).add(WindowInsets.ime)
        ) {
            var text by remember { mutableStateOf("") }
            val filteredList = remember(myList, text) {
                myList.filter { it.toString().contains(text, ignoreCase = true) }
            }

            Column(
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .background(
                        color = if (isSystemInDarkTheme()) {
                            Dark_03
                        } else {
                            Color.White
                        }
                    )
                    .windowInsetsPadding(WindowInsets.ime)
            ) {
                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                        .background(
                            color = if (isSystemInDarkTheme()) {
                                Dark_03
                            } else {
                                Color.White
                            }
                        )
                        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = IMG_DESCRIPTION,
                        modifier = Modifier
                            .background(
                                color = if (isSystemInDarkTheme()) {
                                    Dark_03
                                } else {
                                    White
                                }
                            )
                            .padding()
                            .padding(10.dp)
                            .background(Color.Unspecified)
                    )
                    TextField(
                        value = text, onValueChange = { newText ->
                            text = newText
                        }, modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = if (isSystemInDarkTheme()) {
                                    Dark_03
                                } else {
                                    Color.White
                                }
                            ), // Set background color to white
                        placeholder = {
                            Text(
                                text = searchHere, color = Color.Gray, fontFamily = fontRegular
                            )
                        }, // Placeholder text color
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = if (isSystemInDarkTheme()) {
                                Dark_03
                            } else {
                                Color.White
                            },
                            cursorColor = Black,
                            focusedIndicatorColor = Transparent,
                            unfocusedIndicatorColor = Transparent
                        ), singleLine = true
                    )
                }
                val selectedItem = remember { mutableStateOf<String?>(null) }
                val filteredListWithOther = filteredList + listOf(otherOption)

                LazyColumn() {
                    items(filteredListWithOther) { item ->
                        val isSelected = selectedItem.value == item.toString()
                        Text(
                            text = item, modifier = Modifier
                                .background(
                                    color = if (isSelected) {
                                        if (isSystemInDarkTheme()) {
                                            Dark_03
                                        } else {
                                            PrimaryBlueLt
                                        }
                                    } else {
                                        if (isSystemInDarkTheme()) {
                                            Dark_03
                                        } else {
                                            White
                                        }
                                    }
                                )
                                .fillMaxWidth()
                                .padding(16.dp)
                                .clickable {
                                    selectedItem.value = item
                                    onTextSelected.invoke(item)
                                    onDismiss.invoke()
                                }, style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        if (myList.isEmpty()) {
            onTextSelected.invoke(chooseOption)
        }
    }
}

@Preview
@Composable
fun ProfileWithProgress(
    image: String = "",
    painter: Painter = painterResource(id = R.drawable.round_back_key),
    progress: Float = 0.0f, // 0.0f to 1.0f
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.size(90.dp), contentAlignment = Alignment.Center) {
        // Canvas for circular progress
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 6.dp.toPx()
            val radius = 90.dp.toPx() / 2 - strokeWidth / 2

            drawArc(
                color = Color(0xFF4169E1), // royal blue
                startAngle = -90f,
                sweepAngle = 360 * progress,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        // Profile image

        Image(
            painter = if (image.isNotEmpty()) {
                rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(image)
                        .placeholder(R.drawable.profile_user_icon)
                        .error(R.drawable.profile_user_icon).crossfade(true).build()
                )
            } else {
                painterResource(R.drawable.profile_user_icon)
            },
            contentDescription = IMG_DESCRIPTION,
            modifier = Modifier
                .size(100.dp) // Add size modifier to make the image visible
                .clip(CircleShape) // Add clip modifier to make the image circular
                .background(shape = CircleShape, color = White)
                .border( // Add border modifier to make image stand out
                    width = 1.dp, color = GrayLight02, shape = CircleShape
                ),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
fun ProfileWithFullProgress(
    image: String = "",
    painter: Painter = painterResource(id = R.drawable.round_back_key),
    progress: Float = 0.0f, // 0.0f to 1.0f
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.size(90.dp), contentAlignment = Alignment.Center) {
        // Canvas for circular progress
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 6.dp.toPx()
            val radius = 90.dp.toPx() / 2 - strokeWidth / 2

            drawArc(
                color = LightRed01, // royal blue
                startAngle = -90f,
                sweepAngle = 360 * progress,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        // Profile image
        Image(
            painter = if (image.isNotEmpty()) {
                rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(image)
                        .placeholder(R.drawable.dummy_image)
                        .error(R.drawable.dummy_image).crossfade(true).build()
                )
            } else {
                painterResource(R.drawable.dummy_image)
            },
            contentDescription = IMG_DESCRIPTION,
            modifier = Modifier
                .size(100.dp) // Add size modifier to make the image visible
                .clip(CircleShape) // Add clip modifier to make the image circular
                .background(shape = CircleShape, color = White)
                .border( // Add border modifier to make image stand out
                    width = 1.dp, color = White, shape = CircleShape
                ),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun DropdownMenuUi(
    options: List<String>,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Select",
    icon: Painter = painterResource(R.drawable.ic_dropdown),
    onClick: () -> Unit,
) {

    var menuExpanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf<String?>(null) }
    val colors = MaterialTheme.colorScheme
    val selectedBorder = BorderStroke(
        width = 0.5.dp, if (isSystemInDarkTheme()) {
            Dark_03
        } else {
            GrayLight02
        }
    )

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.wrapContentHeight(),
        colors = if (isSystemInDarkTheme()) {
            CardDefaults.cardColors(Dark_03)
        } else {
            CardDefaults.cardColors(White)
        },
        border = selectedBorder,
    ) {
        Box(modifier = modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (options.isNotEmpty()) menuExpanded = true
                        onClick()
                    }
                    .padding(vertical = 15.dp, horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically) {

                Text(
                    text = selectedOption ?: placeholder,
                    modifier = Modifier.weight(1f),
                    color = if (selectedOption == null) {
                        if (isSystemInDarkTheme()) {
                            colors.onSurface
                        } else {
                            Color.Gray
                        }
                    } else {
                        if (isSystemInDarkTheme()) {
                            colors.onSurface
                        } else {
                            Black
                        }
                    },
                    fontSize = 15.sp,
                    fontFamily = if (selectedOption == null) {
                        fontRegular
                    } else {
                        fontSemiBold
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 7.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    colorFilter = ColorFilter.tint(
                        if (isSystemInDarkTheme()) {
                            DARK_DEFAULT_BUTTON_TEXT
                        } else {
                            Black
                        }
                    )
                )
            }

            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = GrayLight01)
                    .background(
                        if (isSystemInDarkTheme()) {
                            Dark_03
                        } else {
                            White
                        }
                    )
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                option,
                                fontSize = 15.sp,
                                fontFamily = fontSemiBold,
                                color = Black
                            )
                        },
                        onClick = {
                            onItemSelected(option)
                            selectedOption = option
                            menuExpanded = false
                        })
                }
            }
        }
    }

    // Update the selectedOption when options list is cleared
    LaunchedEffect(options) {
        if (options.isEmpty()) {
            selectedOption = null
        }
    }
}

fun Modifier.drawDashedBorder(
    color: Color,
    strokeWidth: Dp,
    dashLength: Dp,
    gapLength: Dp,
    cornerRadius: Dp = 0.dp,
) = this.then(
    Modifier.drawBehind {
        val stroke = Stroke(
            width = strokeWidth.toPx(),
            pathEffect = PathEffect.dashPathEffect(
                floatArrayOf(dashLength.toPx(), gapLength.toPx()), 0f
            )
        )

        val corner = cornerRadius.toPx()
        val rect = Rect(0f, 0f, size.width, size.height)

        drawRoundRect(
            color = color,
            topLeft = Offset.Zero,
            size = size,
            cornerRadius = CornerRadius(corner, corner),
            style = stroke
        )
    }
)

@Composable
fun SectionDivider() {
    Divider(
        thickness = 1.dp,
        color = Bg_Gray2
    )
}

// Create a file to store image
fun createImageUri(context: Context): Uri? {
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "camera_image_${System.currentTimeMillis()}.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    }
    return context.contentResolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        contentValues
    )
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Preview
@Composable
fun LeavingDialog(onDismiss: () -> Unit = {}, onClick: () -> Unit = {}) {
    val context = LocalContext.current

    Dialog(onDismissRequest = { onDismiss() }) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isSystemInDarkTheme()) Dark_02 else White)
                    .fillMaxWidth()

            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 15.dp, horizontal = 8.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.sad_emoji),
                        contentDescription = IMG_DESCRIPTION,
                        modifier = Modifier.size(60.dp),
                        tint = Color.Unspecified
                    )

                    Text(
                        text = stringResource(R.string.head_leave),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 16.dp, start = 8.dp, end = 8.dp),
                        fontFamily = fontBold,
                        fontSize = 19.sp,
                        color = Black,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = stringResource(R.string.body_leave),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 3.dp, start = 8.dp, end = 8.dp),
                        fontFamily = fontRegular,
                        fontSize = 15.sp,
                        color = GrayLight04,
                        textAlign = TextAlign.Center
                    )
                    Row(
                        modifier = Modifier
                            .padding(vertical = 15.dp, horizontal = 5.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,

                        ) {
                        Button(
                            onClick = {
                                onDismiss()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(end = 8.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = White,
                                contentColor = BorderBlue
                            ),
                            border = BorderStroke(1.dp, BorderBlue)
                        ) {
                            Text(
                                text = stringResource(R.string.txt_cancel),
                                fontSize = 16.sp,
                                fontFamily = fontMedium,
                                color = BorderBlue
                            )
                        }
                        // check Status
                        Button(
                            onClick = {
                                onClick()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(end = 8.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PrimaryBlue,
                                contentColor = White
                            ),
                            border = BorderStroke(1.dp, BorderBlue)
                        ) {
                            Text(
                                text = stringResource(R.string.txt_leave),
                                fontSize = 17.sp,
                                fontFamily = fontMedium,
                                color = White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomToastMessage(
    titleStr: String,
    messageStr: String,
    visible: Boolean,
    duration: Long = 3000L,
    onClose: () -> Unit,
) {
    var progress by remember { mutableFloatStateOf(1f) }

    if (visible) {
        LaunchedEffect(Unit) {
            val steps = 100
            val interval = duration / steps
            for (i in 0..steps) {
                progress = 1f - i / steps.toFloat()
                delay(interval)
            }
            onClose()
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 32.dp),
            contentAlignment = Alignment.BottomCenter,
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = Bg_Gray4)
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = titleStr,
                                fontFamily = fontRegular,
                                fontSize = 16.sp,
                                color = White
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = messageStr,
                                fontSize = 12.sp,
                                fontFamily = fontRegular,
                                color = LightGray
                            )
                        }

                        IconButton(onClick = onClose) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = White
                            )
                        }
                    }

                    LinearProgressIndicator(
                        progress = progress,
                        color = Bg_Gray4,
                        trackColor = Bg_Gray3,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .animateContentSize()
                    )
                }
            }
        }
    }
}

@Composable
fun ScreeningDetailsBackgroundUi(
    studentName: String,
    grade: String,
    modifier: Modifier = Modifier,
    isShowBackButton: Boolean = true,
    isShowMoreInfo: Boolean = true,
    onBackButtonClick: () -> Unit = {},
    onMoreInfoClick: () -> Unit = {},
    content: @Composable (() -> Unit),
) {
    Surface(
        modifier = modifier.fillMaxWidth(), color = White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = if (isSystemInDarkTheme()) {
                        PrimaryBlue
                    } else {
                        PrimaryBlue
                    }
                ), verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .background(
                        color = if (isSystemInDarkTheme()) {
                            PrimaryBlue
                        } else {
                            PrimaryBlue
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        modifier = Modifier.background(
                            color = if (isSystemInDarkTheme()) {
                                PrimaryBlue
                            } else {
                                PrimaryBlue
                            }
                        )
                    ) {
                        Text(
                            text = studentName.toString(),
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                            fontFamily = fontBold,
                            fontSize = 16.sp,
                            color = if (isSystemInDarkTheme()) {
                                DARK_TITLE_TEXT
                            } else {
                                White
                            },
                            textAlign = TextAlign.Start
                        )

                        Text(
                            text = grade.toString(),
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                            fontFamily = fontRegular,
                            fontSize = 14.sp,
                            color = if (isSystemInDarkTheme()) {
                                LightPurple04
                            } else {
                                LightPurple04
                            },
                            textAlign = TextAlign.Start
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Spacer(modifier = Modifier.width(16.dp))

                Spacer(modifier = Modifier.width(16.dp))

                Spacer(modifier = Modifier.width(16.dp))

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .background(Color.Unspecified)
                ) {
                    if (isShowMoreInfo && !LocalInspectionMode.current) {
                        Image(
                            painter = painterResource(R.drawable.more_info_img),
                            contentDescription = IMG_DESCRIPTION,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(start = 10.dp)
                                .background(Color.Unspecified)
                                .clickable(onClick = onMoreInfoClick),
                            colorFilter = ColorFilter.tint(White)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .background(Color.Unspecified)
                ) {
                    if (isShowBackButton && !LocalInspectionMode.current) {
                        Image(
                            painter = painterResource(R.drawable.close_img),
                            contentDescription = IMG_DESCRIPTION,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 10.dp)
                                .background(Color.Unspecified)
                                .clickable(onClick = onBackButtonClick),
                            colorFilter = ColorFilter.tint(White)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(
                        if (isSystemInDarkTheme()) {
                            Dark_01
                        } else {
                            White
                        }
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    content()
                }
            }
        }
    }
}

@Preview
@Composable
fun YesNoBtnUi(
    title: String = "No", onClick: () -> Unit = {}, enabled: Boolean = false,
) {
    val selectedBorder = BorderStroke(
        width = 1.dp, if (isSystemInDarkTheme()) {
            if (enabled) {
                PrimaryBlue
            } else {
                GrayLight05
            }
        } else {
            if (enabled) {
                PrimaryBlue
            } else {
                GrayLight05
            }
        }
    )

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .width(100.dp)
            .wrapContentHeight()
            .clickable {
                onClick()
            },
        colors = if (isSystemInDarkTheme()) {
            if (enabled) {
                CardDefaults.cardColors(PrimaryBlueLt1)
            } else {
                CardDefaults.cardColors(White)
            }
        } else {
            if (enabled) {
                CardDefaults.cardColors(
                    containerColor = PrimaryBlueLt1,
                    contentColor = PrimaryBlueLt1,
                    disabledContentColor = PrimaryBlueLt1,
                    disabledContainerColor = PrimaryBlueLt1
                )
            } else {
                CardDefaults.cardColors(
                    containerColor = White,
                    contentColor = White,
                    disabledContentColor = White,
                    disabledContainerColor = White
                )
            }
        },
        border = selectedBorder
    ) {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp, top = 8.dp, start = 8.dp, end = 8.dp),
            fontSize = 14.sp,
            color = if (enabled) {
                PrimaryBlue
            } else {
                BodyTextLight
            },
            textAlign = TextAlign.Center,
            fontFamily = fontMedium,
        )
    }
}

@Preview
@Composable
fun SelectOptBtnUi(
    title: String = "No", onClick: () -> Unit = {}, enabled: Boolean = false,
) {
    val selectedBorder = BorderStroke(
        width = 1.dp, if (isSystemInDarkTheme()) {
            if (enabled) {
                PrimaryBlue
            } else {
                GrayLight05
            }
        } else {
            if (enabled) {
                PrimaryBlue
            } else {
                GrayLight05
            }
        }
    )

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .wrapContentHeight()
            .clickable {
                onClick()
            },
        colors = if (isSystemInDarkTheme()) {
            if (enabled) {
                CardDefaults.cardColors(PrimaryBlueLt1)
            } else {
                CardDefaults.cardColors(White)
            }
        } else {
            if (enabled) {
                CardDefaults.cardColors(
                    containerColor = PrimaryBlueLt1,
                    contentColor = PrimaryBlueLt1,
                    disabledContentColor = PrimaryBlueLt1,
                    disabledContainerColor = PrimaryBlueLt1
                )
            } else {
                CardDefaults.cardColors(
                    containerColor = White,
                    contentColor = White,
                    disabledContentColor = White,
                    disabledContainerColor = White
                )
            }
        },
        border = selectedBorder
    ) {
        Text(
            text = title,
            modifier = Modifier
                .wrapContentWidth()
                .padding(bottom = 8.dp, top = 8.dp, start = 8.dp, end = 8.dp),
            fontSize = 14.sp,
            color = if (enabled) {
                PrimaryBlue
            } else {
                BodyTextLight
            },
            textAlign = TextAlign.Center,
            fontFamily = fontMedium,
        )
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Preview
@Composable
fun SubmissionDialog(onDismiss: () -> Unit = {}, onClick: () -> Unit = {}) {
    val context = LocalContext.current

    Dialog(onDismissRequest = { onDismiss() }) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(21.dp))
                    .background(if (isSystemInDarkTheme()) Dark_02 else White)
                    .fillMaxWidth()

            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 15.dp, horizontal = 9.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.screening_img),
                        contentDescription = IMG_DESCRIPTION,
                        modifier = Modifier.size(61.dp),
                        tint = Color.Unspecified
                    )

                    Text(
                        text = stringResource(R.string.head_screening),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 17.dp, start = 9.dp, end = 9.dp),
                        fontFamily = fontBold,
                        fontSize = 19.sp,
                        color = Black,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = stringResource(R.string.body_screening),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 3.dp, start = 9.dp, end = 9.dp),
                        fontFamily = fontRegular,
                        fontSize = 15.sp,
                        color = GrayLight04,
                        textAlign = TextAlign.Center
                    )
                    Row(
                        modifier = Modifier
                            .padding(vertical = 15.dp, horizontal = 5.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,

                        ) {
                        Button(
                            onClick = {
                                onDismiss()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(end = 9.dp)
                                .clip(RoundedCornerShape(9.dp)),
                            shape = RoundedCornerShape(9.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = White,
                                contentColor = BorderBlue
                            ),
                            border = BorderStroke(1.dp, BorderBlue)
                        ) {
                            Text(
                                text = stringResource(R.string.txt_check_again),
                                fontSize = 15.sp,
                                fontFamily = fontMedium,
                                color = BorderBlue
                            )
                        }
                        // check Status
                        Button(
                            onClick = {
                                onClick()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(end = 9.dp)
                                .clip(RoundedCornerShape(9.dp)),
                            shape = RoundedCornerShape(9.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PrimaryBlue,
                                contentColor = White
                            ),
                            border = BorderStroke(1.dp, BorderBlue)
                        ) {
                            Text(
                                text = stringResource(R.string.txt_submit),
                                fontSize = 15.sp,
                                fontFamily = fontMedium,
                                color = White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ThemeSwitch(
    isLightSelected: Boolean = false,
    onToggle: (Boolean) -> Unit = {},
) {
    val backgroundColor = Bg_Gray2
    val selectedColor = White
    val unselectedColor = Transparent

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(backgroundColor)
            .border(1.dp, Bg_Gray2, RoundedCornerShape(50))
            .padding(4.dp)
    ) {
        // Light Button
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(if (isLightSelected) selectedColor else unselectedColor)
                .clickable { onToggle(true) }
                .padding(horizontal = 15.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_sun),
                contentDescription = "",
                tint = if (isLightSelected) Color.Black else GrayLight04,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = stringResource(R.string.txt_light),
                color = if (isLightSelected) Color.Black else GrayLight04,
                fontFamily = fontMedium,
                fontSize = 14.sp
            )
        }

        // Dark Button
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(if (!isLightSelected) selectedColor else unselectedColor)
                .clickable { onToggle(false) }
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_moon),
                contentDescription = "",
                tint = if (!isLightSelected) Color.Black else GrayLight04,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = stringResource(R.string.txt_dark),
                color = if (!isLightSelected) Color.Black else GrayLight04,
                fontFamily = fontMedium,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun CameraGalleryButtons(
    onImageUri: (Uri?) -> Unit,
) {
    val context = LocalContext.current

    // For Camera
    val cameraImageUri = remember { mutableStateOf<Uri?>(null) }
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        onImageUri(cameraImageUri.value)
    }

    // For Gallery
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            onImageUri(uri)
        }

    // Create a file to store image
    fun createImageUri(): Uri? {
        val contentValues = ContentValues().apply {
            put(
                MediaStore.Images.Media.DISPLAY_NAME,
                PI_DOCUMENT + System.currentTimeMillis() + JPG
            )
            put(MediaStore.Images.Media.MIME_TYPE, IMAGE_MIME)
        }
        return context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
    }

    Row(
        modifier = Modifier
            .padding(top = 16.dp)
            .wrapContentWidth(),
    ) {
        Button(
            onClick = {
                val uri = createImageUri()
                cameraImageUri.value = uri
                uri?.let { cameraLauncher.launch(it) }
            },
            modifier = Modifier
                .wrapContentWidth()
                .padding(end = 8.dp)
                .clip(RoundedCornerShape(8.dp)),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = White,
                contentColor = PrimaryBlue
            ),
            border = BorderStroke(1.dp, GrayLight01)
        ) {
            TextWithIconOnLeft(
                text = stringResource(R.string.txt_Camera),
                icon = ImageVector.vectorResource(id = R.drawable.camera_img),
                textColor = Dark_03,
                iconColor = Color.Unspecified,
                onClick = {
                    val uri = createImageUri()
                    cameraImageUri.value = uri
                    uri?.let { cameraLauncher.launch(it) }
                }
            )
        }

        Button(
            onClick = {
                galleryLauncher.launch(IMAGE_ALL_TYPE)
            },
            modifier = Modifier
                .wrapContentWidth()
                .clip(RoundedCornerShape(8.dp)),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = White,
                contentColor = PrimaryBlue
            ),
            border = BorderStroke(1.dp, GrayLight01)
        ) {
            TextWithIconOnLeft(
                text = stringResource(R.string.txt_Gallery),
                icon = ImageVector.vectorResource(id = R.drawable.gallery_img),
                textColor = Dark_03,
                iconColor = Color.Unspecified,
                onClick = {
                    galleryLauncher.launch(IMAGE_ALL_TYPE)
                }
            )
        }
    }
}

@Composable
fun BottomSheetCameraGallery(
    onImageUri: (Uri?) -> Unit,
) {
    val context = LocalContext.current

    // For Camera
    val cameraImageUri = remember { mutableStateOf<Uri?>(null) }
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        onImageUri(cameraImageUri.value)
    }

    // For Gallery
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            onImageUri(uri)
        }

    // Create a file to store image
    fun createImageUri(): Uri? {
        val contentValues = ContentValues().apply {
            put(
                MediaStore.Images.Media.DISPLAY_NAME,
                PI_DOCUMENT + System.currentTimeMillis() + JPG
            )
            put(MediaStore.Images.Media.MIME_TYPE, IMAGE_MIME)
        }
        return context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
    }

    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .wrapContentWidth(),
    ) {
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .clickable {
                    val uri = createImageUri()
                    cameraImageUri.value = uri
                    uri?.let { cameraLauncher.launch(it) }
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_circle_camera),
                contentDescription = IMG_DESCRIPTION,
                modifier = Modifier.size(60.dp),
                tint = Color.Unspecified
            )
            Text(
                text = stringResource(R.string.txt_Camera),
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(top = 16.dp, start = 8.dp, end = 8.dp),
                fontFamily = fontMedium,
                fontSize = 15.sp,
                color = contactUsTxt,
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = Modifier
                .wrapContentWidth()
                .padding(start = 15.dp)
                .clickable {
                    galleryLauncher.launch(IMAGE_ALL_TYPE)
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_circle_gallery),
                contentDescription = IMG_DESCRIPTION,
                modifier = Modifier.size(60.dp),
                tint = Color.Unspecified
            )
            Text(
                text = stringResource(R.string.txt_Gallery),
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(top = 16.dp, start = 8.dp, end = 8.dp),
                fontFamily = fontMedium,
                fontSize = 15.sp,
                color = contactUsTxt,
                textAlign = TextAlign.Center
            )
        }

    }
}