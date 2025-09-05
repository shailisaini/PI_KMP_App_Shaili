package com.pi.ProjectInclusion.android.screens.Profile

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.White

@Composable
fun PrivacyPolicy() {

    var strLMSUrl =  "https://projectinclusion.in/privacy-policy.aspx"
    var strPartnerName by remember { mutableStateOf("") }
    var strLanguageName by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf(0) }
    var languageId by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        WebViewScreen(strLMSUrl)
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(url: String) {
    var webViewState by remember { mutableStateOf(WebViewState.Loading) }
    Box(
        Modifier
            .fillMaxSize()
            .background(
                if (isSystemInDarkTheme()) {
                    Dark_01
                } else {
                    White
                }
            )
    ) {
        AndroidView(
            factory = { context ->
                WebView(context).requestFocusFromTouch()
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    isFocusable = true
                    isFocusableInTouchMode = true
                    requestFocus()

                    webViewClient = WebViewClient()

                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            webViewState = WebViewState.Loaded

                            view?.evaluateJavascript(
                                """
                                document.querySelector('#menuButton')?.addEventListener('click', function() {
                                    Android.openDrawerFromWeb();
                                });
                                """.trimIndent(),
                                null
                            )
                        }

                        @Deprecated("Deprecated in Java")
                        override fun onReceivedError(
                            view: WebView?,
                            errorCode: Int,
                            description: String?,
                            failingUrl: String?,
                        ) {
                            super.onReceivedError(view, errorCode, description, failingUrl)
                            webViewState = WebViewState.Error
                            println("Web view error information :- $failingUrl")
                            Toast.makeText(
                                context, "$url is not working.", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }, update = { webView ->
                webView.loadUrl(url)
            }, modifier = Modifier.fillMaxSize()
        )
        if (webViewState == WebViewState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}
enum class WebViewState {
    Loading, Loaded, Error;
}
@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PrivacyPolicyScreen() {
    PrivacyPolicy()
}