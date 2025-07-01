package com.pi.ProjectInclusion.constants

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.Dark_01

object CommonFunction {

    var isValidMobileNumber: Boolean = false

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        connectivityManager?.let {
            val network = it.activeNetwork
            val networkCapabilities = it.getNetworkCapabilities(network)
            return networkCapabilities != null &&
                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }
        return false
    }

    @Composable
    fun LoginScreenTitle(title: String, colors: Color) {
        Text(
            title,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            color = colors,
            modifier = Modifier
                .padding(top = 10.dp)
        )
    }

    @Composable
    fun ShowError(message: String, colors: ColorScheme) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .background(
                    if (isSystemInDarkTheme()) {
                        Dark_01
                    } else {
                        Color.White
                    }
                )
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = message,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = colors.onSurface,
                )
            }
        }
    }
}