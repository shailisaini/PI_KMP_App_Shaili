package com.pi.ProjectInclusion.android.common_UI

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.DARK_DEFAULT_BUTTON_TEXT
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
import com.pi.ProjectInclusion.PRIMARY_AURO_BLUE
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.android.R
import kotlinx.coroutines.delay

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
                    .height(120.dp)
            ) {
                if (isShowBackButton) {
                    Image(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(top = 16.dp, start = 8.dp)
                            .offset(y = 16.dp, x = 10.dp)
                            .size(50.dp) // adjust the size as needed
                            .clickable(onClick = onBackButtonClick),
                        painter = painterResource(id = R.drawable.round_back_key),
                        contentDescription = "back_button"
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
                        .padding(top = 16.dp)
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
fun MobileTextField(
    colors: ColorScheme,
    trueFalse: Boolean,
    modifier: Modifier = Modifier,
    number: MutableState<String> = remember { mutableStateOf("") },
    hint: String = remember { mutableStateOf("") }.toString(),
) {
    TextField(
        value = number.value,
        onValueChange = {
            if (it.length <= 10 && it.all { char -> char.isDigit() }) {
                number.value = it
            }
        },
        enabled = trueFalse,
        placeholder = { Text(hint, color = GrayLight01, fontSize = 14.sp) },
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        visualTransformation = VisualTransformation.None,

        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .border(
                width = 1.dp, color = if (isSystemInDarkTheme()) {
                    Dark_02
                } else {
                    LightBlue
                }, shape = RoundedCornerShape(8.dp)
            ),
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