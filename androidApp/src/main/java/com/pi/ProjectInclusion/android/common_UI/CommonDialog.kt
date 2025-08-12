package com.pi.ProjectInclusion.android.common_UI

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.BorderBlue
import com.pi.ProjectInclusion.Dark_02
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.GrayLight04
import com.pi.ProjectInclusion.LightRed01
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
    onSubmit: () -> Unit = {}
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
    Dialog(onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false)) {
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

    Dialog(onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false)) {
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
fun AccountRecoverDialog(onDismiss: () -> Unit = {}, onRestore: () -> Unit = {}) {
    Dialog(onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false, dismissOnClickOutside = false)) {
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
                        text = stringResource(R.string.txt_90_days_left),
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
fun CameraGalleryDialog(selectedUri: MutableState<Uri?> = mutableStateOf(null) , onDismiss: () -> Unit = {}) {
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
    fun checkAllPermissions(): Boolean {
        val permissionsToCheck = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return permissionsToCheck.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    val permissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasAllPermissions.value = permissions.all { it.value }
        if (hasAllPermissions.value) {
            // Now show camera/gallery
        } else {
            context.toast(context.getString(R.string.txt_permission_grant))
        }
    }

    LaunchedEffect(Unit) {
        if (checkAllPermissions()) {
            hasAllPermissions.value = true
        } else {
            val permissionsToRequest = arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            permissionsLauncher.launch(permissionsToRequest)
        }
    }
}