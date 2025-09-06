package com.pi.ProjectInclusion.android.common_UI

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.BorderBlue
import com.pi.ProjectInclusion.DarkGrey02
import com.pi.ProjectInclusion.Dark_02
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.GrayLight04
import com.pi.ProjectInclusion.LightRed01
import com.pi.ProjectInclusion.LightText
import com.pi.ProjectInclusion.LightYellow2
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.RedLogout
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.utils.fontBold
import com.pi.ProjectInclusion.android.utils.fontMedium
import com.pi.ProjectInclusion.android.utils.fontRegular
import com.pi.ProjectInclusion.android.utils.toast
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION

// Dialog for delete profile
@Preview
@Composable
fun DeleteAccountPasswordDialog(
    onDismiss: () -> Unit = {},
    onSubmit: () -> Unit = {},
) {
    var enterPasswordStr = rememberSaveable { mutableStateOf("") }
    val enterPassword = stringResource(R.string.txt_Enter_your_password)
    val showPassword = remember { mutableStateOf(false) }
    var isValidPassword by remember { mutableStateOf(true) }
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp) // 20dp margin from left & right
        ) {
            Card(
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .background(White)
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Text(
                        text = stringResource(R.string.txt_enter_password),
                        fontSize = 15.sp,
                        color = Black,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    PasswordTextField(
                        password = enterPasswordStr,
                        showPassword = showPassword,
                        hint = enterPassword
                    )

                    if (!isValidPassword) {
                        Text(
                            stringResource(R.string.txt_Passwords_must_be_8_16_characters),
                            color = LightRed01,
                            modifier = Modifier.padding(horizontal = 10.dp),
                            fontSize = 10.sp
                        )
                    }

                    Row(
                        modifier = Modifier
                            .padding(vertical = 15.dp, horizontal = 10.dp)
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
                                modifier = Modifier.padding(vertical = 5.dp),
                                text = stringResource(R.string.txt_cancel),
                                fontSize = 15.sp,
                                fontFamily = fontMedium,
                                color = BorderBlue,
                                textAlign = TextAlign.Center
                            )
                        }
                        // check Status
                        Button(
                            onClick = {

                                if (enterPasswordStr.value.isEmpty()) {
                                    isValidPassword = false
                                } else {
                                    isValidPassword = true
                                    onSubmit()
                                }

                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .clip(RoundedCornerShape(9.dp)),
                            shape = RoundedCornerShape(9.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PrimaryBlue,
                                contentColor = White
                            ),
                            border = BorderStroke(1.dp, BorderBlue)
                        ) {
                            Text(
                                text = stringResource(R.string.txt_delete),
                                fontSize = 15.sp,
                                fontFamily = fontMedium,
                                color = White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Preview
@Composable
fun AccountDeleteDialog(onDismiss: () -> Unit = {}, onClick: () -> Unit = {}) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .padding(20.dp)
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
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_account_delete),
                        contentDescription = IMG_DESCRIPTION,
                        modifier = Modifier.size(61.dp),
                        tint = Color.Unspecified
                    )

                    Text(
                        text = stringResource(R.string.txt_account_delete),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 17.dp, start = 9.dp, end = 9.dp),
                        fontFamily = fontBold,
                        fontSize = 19.sp,
                        color = Black,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = stringResource(R.string.txt_account_delete_desc),
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
                                text = stringResource(R.string.txt_cancel),
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
                                text = stringResource(R.string.txt_delete),
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

@SuppressLint("UnusedBoxWithConstraintsScope")
@Preview
@Composable
fun LogoutDialog(onDismiss: () -> Unit = {}, onClick: () -> Unit = {}) {

    val context = LocalContext.current
    val activity = context as? Activity

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .padding(20.dp)
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
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_account_delete),
                        contentDescription = IMG_DESCRIPTION,
                        modifier = Modifier.size(61.dp),
                        tint = Color.Unspecified
                    )

                    Text(
                        text = stringResource(R.string.txt_logout),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 17.dp, start = 9.dp, end = 9.dp),
                        fontFamily = fontBold,
                        fontSize = 19.sp,
                        color = Black,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = stringResource(R.string.txt_logout_desc),
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
                                text = stringResource(R.string.txt_cancel),
                                fontSize = 15.sp,
                                fontFamily = fontMedium,
                                color = BorderBlue
                            )
                        }
                        // check Status
                        Button(
                            onClick = {
                                onClick()
                                activity?.finish()
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
                                text = stringResource(R.string.txt_logout),
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

@SuppressLint("UnusedBoxWithConstraintsScope")
@Preview
@Composable
fun AccountRecoverDialog(msg: String = "", onDismiss: () -> Unit = {}, onRestore: () -> Unit = {}) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnClickOutside = false
        )
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .padding(20.dp)
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

                    Text(
                        text = stringResource(R.string.txt_account_found),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 17.dp, start = 9.dp, end = 9.dp),
                        fontFamily = fontBold,
                        fontSize = 19.sp,
                        color = Black,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = stringResource(R.string.txt_recover_desc),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 3.dp, start = 9.dp, end = 9.dp),
                        fontFamily = fontRegular,
                        fontSize = 15.sp,
                        color = GrayLight04,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = msg + stringResource(R.string.txt_90_days_left),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 3.dp, start = 9.dp, end = 9.dp),
                        fontFamily = fontRegular,
                        fontSize = 15.sp,
                        color = RedLogout,
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
                                text = stringResource(R.string.txt_No),
                                fontSize = 15.sp,
                                fontFamily = fontMedium,
                                color = BorderBlue
                            )
                        }
                        // check Status
                        Button(
                            onClick = {
                                onRestore()
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
                                text = stringResource(R.string.txt_restore),
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

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedBoxWithConstraintsScope")
@Preview
@Composable
fun CameraGalleryDialog(
    selectedUri: MutableState<Uri?> = mutableStateOf(null),
    onDismiss: () -> Unit = {},
) {
    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        }, sheetState = sheetState
    ) {

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color.Unspecified)
                .fillMaxWidth()

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, start = 8.dp, end = 8.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(R.string.choose_an_option),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 20.dp, end = 8.dp),
                    fontFamily = fontRegular,
                    fontSize = 15.sp,
                    color = Gray,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 20.dp)
                        .fillMaxWidth()
                ) {

                    // gallery & camera button
                    BottomSheetCameraGallery { selectedImageUri ->
                        if (selectedImageUri != null) {
//                                        Logger.d("SelectedURI: $selectedImageUri")
                            selectedUri.value = selectedImageUri
                            onDismiss()
                            // Use the URI as needed (e.g., upload, display, etc.)
                        } else {
//                                        Logger.d("SelectedURI:$selectedImageUri")
                            onDismiss()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CameraPermission(hasAllPermissions: MutableState<Boolean>, context: Context) {
    val permissionsToCheck = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_MEDIA_IMAGES
        )
    } else {
        listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    fun checkAllPermissions(): Boolean {
        return permissionsToCheck.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    val permissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasAllPermissions.value = permissionsToCheck.all {
            permissions[it] == true
        }
        /* if (!hasAllPermissions.value) {
             context.toast(context.getString(R.string.txt_permission_grant))
         }*/
    }

    LaunchedEffect(hasAllPermissions.value) {
        if (!hasAllPermissions.value) {
            if (checkAllPermissions()) {
                hasAllPermissions.value = true
            } else {
                permissionsLauncher.launch(permissionsToCheck.toTypedArray())
            }
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun TransferSuccessfulDialog(name: String, onDismiss: () -> Unit = {}) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .padding(20.dp)
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
                        imageVector = ImageVector.vectorResource(id = R.drawable.tick_icon),
                        contentDescription = IMG_DESCRIPTION,
                        modifier = Modifier.size(121.dp),
                        tint = Color.Unspecified
                    )

                    Text(
                        text = stringResource(R.string.txt_trans_success),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 17.dp, start = 9.dp, end = 9.dp),
                        fontFamily = fontBold,
                        fontSize = 19.sp,
                        color = Black,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = buildAnnotatedString {

                            withStyle(style = SpanStyle(color = GrayLight04)) {
                                append(stringResource(R.string.txt_trans_success_dec1))
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = Black,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append(" " + name + " ")
                            }
                            withStyle(style = SpanStyle(color = GrayLight04)) {
                                append(stringResource(R.string.txt_trans_success_dec2))
                            }
                        },
//                        text = stringResource(R.string.txt_trans_success_dec),
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
                                containerColor = PrimaryBlue,
                                contentColor = White
                            ),
                            border = BorderStroke(1.dp, BorderBlue)
                        ) {
                            Text(
                                text = stringResource(R.string.txt_Got_It),
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

@SuppressLint("UnusedBoxWithConstraintsScope")
@Preview
@Composable
fun AlreadyRequested(onDismiss: () -> Unit = {}) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .padding(20.dp)
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
                        imageVector = ImageVector.vectorResource(id = R.drawable.tick_icon),
                        contentDescription = IMG_DESCRIPTION,
                        modifier = Modifier.size(121.dp),
                        tint = Color.Unspecified
                    )

                    Text(
                        text = stringResource(R.string.txt_already_requested),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 17.dp, start = 9.dp, end = 9.dp),
                        fontFamily = fontBold,
                        fontSize = 19.sp,
                        color = Black,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = stringResource(R.string.txt_already_requested_dec),
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
                                containerColor = PrimaryBlue,
                                contentColor = White
                            ),
                            border = BorderStroke(1.dp, BorderBlue)
                        ) {
                            Text(
                                text = stringResource(R.string.txt_Got_It),
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


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TransferSuccessfulDialogPreview() {
    TransferSuccessfulDialog(name = stringResource(R.string.user_name)) {}
}


@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun AlreadyAssessedDialog(
    assessorName: String,
    schoolName: String,
    onCancel: () -> Unit = {},
    onTransferRequest: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = { onCancel() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .padding(20.dp)
                    .clip(RoundedCornerShape(21.dp))
                    .background(if (isSystemInDarkTheme()) Dark_02 else White)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 20.dp, horizontal = 15.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Top Icon
                    Icon(
                        painter = painterResource(id = R.drawable.already_ass_img), // replace with your vector
                        contentDescription = null,
                        modifier = Modifier.size(90.dp),
                        tint = Color.Unspecified
                    )

                    // Title
                    Text(
                        text = stringResource(R.string.txt_already_assessed),
                        modifier = Modifier
                            .padding(top = 12.dp),
                        fontFamily = fontBold,
                        fontSize = 18.sp,
                        color = Black,
                        textAlign = TextAlign.Center
                    )

                    // Description
                    Text(
                        text = stringResource(R.string.txt_already_assessed_desc),
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .fillMaxWidth(),
                        fontFamily = fontRegular,
                        fontSize = 14.sp,
                        color = GrayLight04,
                        textAlign = TextAlign.Center
                    )

                    // Assessor Info Card
                    Box(
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .fillMaxWidth()
                            .drawDashedBorder(
                                color = LightText,
                                strokeWidth = 1.dp,
                                dashLength = 4.dp,
                                gapLength = 5.dp,
                                cornerRadius = 12.dp
                            )
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = assessorName,
                                fontSize = 16.sp,
                                fontFamily = fontMedium,
                                color = BorderBlue
                            )
                            Text(
                                text = schoolName,
                                fontSize = 14.sp,
                                fontFamily = fontRegular,
                                color = DarkGrey02
                            )
                        }
                    }

                    // Bottom Description
                    Text(
                        text = stringResource(R.string.txt_transfer_request_msg),
                        modifier = Modifier
                            .padding(top = 15.dp, start = 12.dp, end = 12.dp),
                        fontFamily = fontRegular,
                        fontSize = 14.sp,
                        color = GrayLight04,
                        textAlign = TextAlign.Center
                    )

                    // Buttons
                    Row(
                        modifier = Modifier
                            .padding(top = 20.dp, bottom = 10.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Cancel
                        OutlinedButton(
                            onClick = { onCancel() },
                            modifier = Modifier.weight(0.8f),
                            shape = RoundedCornerShape(9.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = BorderBlue
                            ),
                            border = BorderStroke(1.dp, BorderBlue)
                        ) {
                            Text(
                                text = stringResource(R.string.txt_cancel),
                                fontSize = 15.sp,
                                fontFamily = fontMedium
                            )
                        }

                        // Send Transfer Request
                        Button(
                            onClick = { onTransferRequest() },
                            modifier = Modifier.weight(1.2f),
                            shape = RoundedCornerShape(9.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PrimaryBlue,
                                contentColor = White
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.txt_send_transfer_request),
                                fontSize = 15.sp,
                                fontFamily = fontMedium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AlreadyAssessedDialogPreview() {
    AlreadyAssessedDialog(
        assessorName = stringResource(R.string.txt_dummy_name),
        schoolName = stringResource(R.string.txt_dummy_school)
    )
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun TransferConfirmationDialog(
    studentName: String,
    schoolName: String,
    onCancel: () -> Unit = {},
    onConfirmTransfer: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = { onCancel() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .padding(20.dp)
                    .clip(RoundedCornerShape(21.dp))
                    .background(if (isSystemInDarkTheme()) Dark_02 else White)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 20.dp, horizontal = 15.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Top Warning Icon
                    Icon(
                        painter = painterResource(id = R.drawable.warning_img),
                        contentDescription = null,
                        modifier = Modifier.size(70.dp),
                        tint = Color.Unspecified
                    )

                    // Title
                    Text(
                        text = stringResource(R.string.txt_transfer_confirmation),
                        modifier = Modifier.padding(top = 12.dp),
                        fontFamily = fontBold,
                        fontSize = 19.sp,
                        color = Black,
                        textAlign = TextAlign.Center
                    )

                    // Description
                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(R.string.txt_confirm_dec))
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = Black
                                )
                            ) {
                                append(" " + studentName)
                            }
                            append(" from ")
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = Black
                                )
                            ) {
                                append(schoolName)
                            }
                            append(".")
                        },
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .fillMaxWidth(),
                        fontFamily = fontRegular,
                        fontSize = 15.sp,
                        color = GrayLight04,
                        textAlign = TextAlign.Center
                    )

                    // Warning Note Box
                    Box(
                        modifier = Modifier
                            .padding(top = 15.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(LightYellow2)
                            .fillMaxWidth()
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.txt_transfer_note),
                            fontSize = 13.sp,
                            fontFamily = fontRegular,
                            color = GrayLight04,
                            textAlign = TextAlign.Center
                        )
                    }

                    // Buttons
                    Row(
                        modifier = Modifier
                            .padding(top = 20.dp, bottom = 10.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = { onCancel() },
                            modifier = Modifier.weight(0.8f),
                            shape = RoundedCornerShape(9.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = PrimaryBlue
                            ),
                            border = BorderStroke(1.dp, PrimaryBlue)
                        ) {
                            Text(
                                text = stringResource(R.string.txt_cancel),
                                fontSize = 15.sp,
                                fontFamily = fontMedium
                            )
                        }
                        Button(
                            onClick = { onConfirmTransfer() },
                            modifier = Modifier.weight(1.2f),
                            shape = RoundedCornerShape(9.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PrimaryBlue,
                                contentColor = White
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.txt_confirm_transfer),
                                fontSize = 15.sp,
                                fontFamily = fontMedium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TransferConfirmationDialogPreview() {
    TransferConfirmationDialog(
        studentName = stringResource(R.string.txt_dummy_name),
        schoolName = stringResource(R.string.txt_dummy_school)
    )
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Preview
@Composable
fun RequestSendDialog(onDismiss: () -> Unit = {}) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .padding(20.dp)
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
                        imageVector = ImageVector.vectorResource(id = R.drawable.check_box),
                        contentDescription = IMG_DESCRIPTION,
                        modifier = Modifier.size(121.dp),
                        tint = Color.Unspecified
                    )

                    Text(
                        text = stringResource(R.string.txt_request_sent_head),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 17.dp, start = 9.dp, end = 9.dp),
                        fontFamily = fontBold,
                        fontSize = 19.sp,
                        color = Black,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = stringResource(R.string.txt_request_sent_dec),
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
                                containerColor = PrimaryBlue,
                                contentColor = White
                            ),
                            border = BorderStroke(1.dp, BorderBlue)
                        ) {
                            Text(
                                text = stringResource(R.string.txt_Got_It),
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

@SuppressLint("UnusedBoxWithConstraintsScope")
@Preview
@Composable
fun ReScreeningDialog(onReScreening: () -> Unit = {}) {
    Dialog(
        onDismissRequest = { onReScreening() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .padding(20.dp)
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
                        imageVector = ImageVector.vectorResource(id = R.drawable.re_screening),
                        contentDescription = IMG_DESCRIPTION,
                        modifier = Modifier.size(121.dp),
                        tint = Color.Unspecified
                    )

                    Text(
                        text = stringResource(R.string.txt_re_screening_head),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 17.dp, start = 9.dp, end = 9.dp),
                        fontFamily = fontBold,
                        fontSize = 19.sp,
                        color = Black,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = stringResource(R.string.txt_re_screening_desc),
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
                                onReScreening()
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
                                text = stringResource(R.string.txt_re_screening_btn),
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

@SuppressLint("UnusedBoxWithConstraintsScope")
@Preview
@Composable
fun CommonAlertDialog(
    alertMessage: String = "",
    onDismiss: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .padding(20.dp)
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
                        imageVector = ImageVector.vectorResource(id = R.drawable.warning_img),
                        contentDescription = IMG_DESCRIPTION,
                        modifier = Modifier.size(121.dp),
                        tint = Color.Unspecified
                    )

                    Text(
                        text = alertMessage,
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
                                containerColor = PrimaryBlue,
                                contentColor = White
                            ),
                            border = BorderStroke(1.dp, BorderBlue)
                        ) {
                            Text(
                                text = stringResource(R.string.txt_Ok_Got_it),
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