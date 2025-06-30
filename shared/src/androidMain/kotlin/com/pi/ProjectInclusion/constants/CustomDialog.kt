package com.pi.ProjectInclusion.constants

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.PrimaryBlueLt
import com.pi.ProjectInclusion.Transparent
import androidx.compose.material3.*
import androidx.compose.runtime.*


@Preview
@Composable
fun CustomDialog(
    isVisible: Boolean = true,
    onDismiss: () -> Unit = {},
    message: String = "Loading...",
) {
    val colors = MaterialTheme.colorScheme

    BackHandler(isVisible) {
        onDismiss()
    }
    
    if (isVisible) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding()
                        .background(color = Transparent)
                        .fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding()
                            .background(
                                    Color.White
                            )
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(150.dp)
                                .padding(40.dp),
                            trackColor = PrimaryBlueLt,
                            color = PrimaryBlue
                        )
                    }
                }
            },
            confirmButton = {

            },
            dismissButton = {
            },
            modifier = Modifier.fillMaxWidth(0.9f), containerColor = Color.Transparent
        )
    }
}