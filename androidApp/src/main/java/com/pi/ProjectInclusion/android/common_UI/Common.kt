package com.pi.ProjectInclusion.android.common_UI

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pi.ProjectInclusion.BannerColor03
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.DARK_BODY_TEXT
import com.pi.ProjectInclusion.DARK_DEFAULT_BUTTON_TEXT
import com.pi.ProjectInclusion.DARK_TITLE_TEXT
import com.pi.ProjectInclusion.DarkBlue
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.Dark_02
import com.pi.ProjectInclusion.Dark_03
import com.pi.ProjectInclusion.Dark_Selected_BG
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.GrayLight01
import com.pi.ProjectInclusion.GrayLight02
import com.pi.ProjectInclusion.GrayLight03
import com.pi.ProjectInclusion.LightBlue
import com.pi.ProjectInclusion.LightGreen06
import com.pi.ProjectInclusion.OrangeSubTitle
import com.pi.ProjectInclusion.PRIMARY_AURO_BLUE
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.PrimaryBlueLt
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.utils.fontBold
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.fontRegular
import com.pi.ProjectInclusion.android.utils.fontSemiBold
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION
import kotlinx.coroutines.delay
import com.pi.ProjectInclusion.constants.ConstantVariables.KEY_FEMALE
import com.pi.ProjectInclusion.constants.ConstantVariables.KEY_MALE
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
                        .clickable { onCallClick() },
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
                        onClick = { onCallClick() })
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
                    border = BorderStroke(1.dp, color = GrayLight01)
                ) {
                    TextWithIconOnLeft(
                        text = stringResource(R.string.txt_otp_whatsapp),
                        icon = ImageVector.vectorResource(id = R.drawable.ic_whatsapp_otp),
                        textColor = Black,
                        iconColor = Color.Unspecified,
                        modifier = Modifier.padding(10.dp),
                        onClick = {
                            onWhatsappClick()
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
                ),
            verticalArrangement = Arrangement.Top
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
                width = 1.dp,
                color = when {
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
            disabledContainerColor = GrayLight03
        )
    )
}

@Composable
fun MobileTextField(
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
            if (it.length <= 10 && it.all { char -> char.isDigit() }) {
                number.value = it

                //  Hide keyboard on 10 digits
                if (it.length == 10) {
                    keyboardController?.hide()
                }
            }
        },
        enabled = trueFalse,
        placeholder = { Text(hint, color = GrayLight01, fontSize = 14.sp) },
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
                width = 1.dp,
                color = when {
                    isFocused -> LightBlue
                    isSystemInDarkTheme() -> Dark_02
                    else -> GrayLight02
                }, shape = RoundedCornerShape(8.dp)
            ),
        interactionSource = interactionSource,
        textStyle = TextStyle(
            fontFamily = fontSemiBold,
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
            disabledContainerColor = GrayLight03
        )
    )
}

@Preview
@Composable
fun OTPBtnUi(
    title: String = "Continue", onClick: () -> Unit = {},
) {
    Button(
        onClick = onClick, modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = White,
            contentColor = PrimaryBlue
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
    onBackButtonClick: () -> Unit = {}
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
            }
    ) {
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
                    modifier = Modifier
                        .padding(start = 10.dp)
                )
                Text(
                    subtitle,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    fontFamily = fontMedium,
                    color = subtitleColor,
                    modifier = Modifier
                        .padding(start = 10.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun SmallBtnUi(
    title: String = "Continue", onClick: () -> Unit = {}, enabled: Boolean = false,
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .wrapContentSize()
            .clip(
                RoundedCornerShape(
                    5.dp
                )
            ),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) {
                if (isSystemInDarkTheme()) {
                    Dark_Selected_BG
                } else {
                    DarkBlue
                }
            } else {
                if (isSystemInDarkTheme()) {
                    GrayLight03
                } else {
                    GrayLight03
                }
            },
            contentColor = if (enabled) {
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
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    5.dp
                )
            ),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) {
                if (isSystemInDarkTheme()) {
                    Dark_Selected_BG
                } else {
                    DarkBlue
                }
            } else {
                if (isSystemInDarkTheme()) {
                    GrayLight03
                } else {
                    GrayLight03
                }
            },
            contentColor = if (enabled) {
                White
            } else {
                White
            }
        )
    ) {
        Text(
            title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp, top = 8.dp),
            fontSize = 16.sp,
            color = if (enabled) {
                White
            } else {
                White
            },
            textAlign = TextAlign.Center
        )
    }
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
        visualTransformation = if (otpText.length > 0) VisualTransformation.None else PasswordVisualTransformation(
            '*'
        ),

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
                    color = PrimaryBlue,
                    fontSize = 12.sp,
                    fontFamily = fontRegular
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
                    color = PrimaryBlue,
                    fontFamily = fontRegular
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
                text = annotatedText,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Gray,
                    textAlign = TextAlign.Center,
                    fontFamily = fontRegular
                ),
                onClick = { offset ->
                    annotatedText.getStringAnnotations("TERMS", offset, offset)
                        .firstOrNull()?.let { onTermsClick() }

                    annotatedText.getStringAnnotations("PRIVACY", offset, offset)
                        .firstOrNull()?.let { onPrivacyClick() }
                }
            )
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
                            if (isSystemInDarkTheme()) {
                                PRIMARY_AURO_BLUE
                            } else {
                                PrimaryBlue
                            }
                        }
                    } else {
                        if (isSystemInDarkTheme()) {
                            LightBlue
                        } else {
                            LightBlue
                        }
                    },
                    shape = RoundedCornerShape(6.dp)
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
    ) {
        Icon(
            imageVector = icon, contentDescription = null, // Decorative element
            tint = iconColor
        )

        Spacer(modifier = Modifier.width(4.dp)) // Add space between text and icon

        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 5.dp),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = textColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start
            )
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
            },
            focusedTextColor = if (isSystemInDarkTheme()) {
                White
            } else {
                Black
            },
            unfocusedTextColor = if (isSystemInDarkTheme()) {
                Dark_03
            } else {
                White
            },
            focusedIndicatorColor = if (isSystemInDarkTheme()) {
                Dark_03
            } else {
                White
            },
            unfocusedContainerColor = if (isSystemInDarkTheme()) {
                Dark_03
            } else {
                White
            },
            unfocusedIndicatorColor = if (isSystemInDarkTheme()) {
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
        verticalAlignment = Alignment.CenterVertically,
        modifier = padding
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { it }, // Disabled for display-only
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
            },
            modifier = Modifier.size(20.dp)
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
        targetValue = clampedPercentage,
        animationSpec = tween(durationMillis = 100)
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
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
fun CustomHorizontalProgressBar(progressBar: Float) {
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
                        BannerColor03
                    }
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fraction = progress)
                    .background(LightGreen06)
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
                        DarkBlue
                    } else {
                        DarkBlue
                    }
                ),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .background(
                        color = if (isSystemInDarkTheme()) {
                            DarkBlue
                        } else {
                            DarkBlue
                        }
                    ), verticalAlignment = Alignment.CenterVertically,
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
                        modifier = Modifier
                            .background(
                                color = if (isSystemInDarkTheme()) {
                                    DarkBlue
                                } else {
                                    DarkBlue
                                }
                            )
                    ) {
                        Text(
                            text = studentName.toString(),
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp),
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                            color = if (isSystemInDarkTheme()) {
                                DARK_TITLE_TEXT
                            } else {
                                White
                            },
                            textAlign = TextAlign.Start
                        )

                        Text(
                            text = grade.toString(),
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp),
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                            color = if (isSystemInDarkTheme()) {
                                DARK_TITLE_TEXT
                            } else {
                                White
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
    modifier: Modifier = Modifier,
    value: MutableState<String> = remember { mutableStateOf("") },
    placeholder: String = "Enter Here",
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
        modifier = Modifier
            .wrapContentHeight(),
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
                text = placeholder, modifier = Modifier
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
    onSelected: () -> Unit = {}
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
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = if (isSelected)painterResource(id = selectedIcon) else painterResource(id = icon),
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
                        value = text,
                        onValueChange = { newText ->
                            text = newText
                        },
                        modifier = Modifier
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
                                text = searchHere,
                                color = Color.Gray,
                                fontFamily = fontRegular
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
                        ),
                        singleLine = true
                    )
                }
                val selectedItem = remember { mutableStateOf<String?>(null) }
                val filteredListWithOther =
                    filteredList + listOf(otherOption)

                LazyColumn() {
                    items(filteredListWithOther) { item ->
                        val isSelected = selectedItem.value == item.toString()
                        Text(
                            text = item,
                            modifier = Modifier
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
                                },
                            style = MaterialTheme.typography.bodyLarge
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
