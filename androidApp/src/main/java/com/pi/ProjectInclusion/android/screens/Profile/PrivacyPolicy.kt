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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.Dark_01
import com.pi.ProjectInclusion.PrimaryBlue
import com.pi.ProjectInclusion.White
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.navigation.AppRoute
import com.pi.ProjectInclusion.constants.BackHandler
import com.pi.ProjectInclusion.constants.CommonFunction.ShowError
import com.pi.ProjectInclusion.constants.CommonFunction.isNetworkAvailable
import com.pi.ProjectInclusion.constants.ConstantVariables.IS_COMING_FROM
import com.pi.ProjectInclusion.constants.ConstantVariables.REGISTER_NEW
import com.pi.ProjectInclusion.constants.ConstantVariables.TERMS_CONDITION
import com.pi.ProjectInclusion.domain.ConnectivityObserver
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun PrivacyPolicy(onBack: () -> Unit) {

    var strLMSPrivacyUrl = "https://projectinclusion.in/privacy-policy.aspx"
    var strLMSTermsUrl = "https://projectinclusion.in/terms-and-condition"
    val viewModel: LoginViewModel = koinViewModel()
    val context = LocalContext.current
    val errColor = PrimaryBlue
    var isInternetAvailable by remember { mutableStateOf(true) }
    val internetMessage = stringResource(R.string.txt_oops_no_internet)

    BackHandler {
        onBack()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        isInternetAvailable = isNetworkAvailable(context)
        if (!isInternetAvailable) {
            ShowError(
                internetMessage,
                errColor,
                painterResource(R.drawable.sad_emoji)
            )
        } else {
            if (viewModel.getPrefData(IS_COMING_FROM) == TERMS_CONDITION) {
                WebViewScreen(strLMSTermsUrl)
            } else {
                WebViewScreen(strLMSPrivacyUrl)
            }
        }
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
                                """.trimIndent(), null
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
    val onBack: () -> Unit = {}
    PrivacyPolicy(onBack)
}