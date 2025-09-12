package com.pi.ProjectInclusion.constants

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pi.ProjectInclusion.Gray
import com.pi.ProjectInclusion.constants.ConstantVariables.IMG_DESCRIPTION

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
    fun LoginScreenTitle(
        title: String,
        colors: Color,
        subtitleColor: Color,
        backgroundColor: Color,
        subtitle: String,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(backgroundColor)
                .padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = colors,
                modifier = Modifier
                    .padding(top = 10.dp)
            )
            Text(
                subtitle,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Normal,
                color = subtitleColor,
                modifier = Modifier
                    .padding(top = 10.dp)
            )
        }
    }

    @Composable
    fun ShowError(message: String, colors: Color, image: Painter) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = image,
                contentDescription = IMG_DESCRIPTION,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .background(Color.Unspecified),
            )

            Text(
                text = message,
                modifier = Modifier.wrapContentSize(),
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = colors,
                textAlign = TextAlign.Start
            )
        }
    }

    @Composable
    fun NoDataFound(message: String, image: Painter) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = image,
                contentDescription = IMG_DESCRIPTION,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .background(Color.Unspecified),
            )

            Text(
                text = message,
                modifier = Modifier.wrapContentSize(),
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Gray,
                textAlign = TextAlign.Start
            )
        }
    }

}