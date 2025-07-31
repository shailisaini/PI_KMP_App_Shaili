package com.pi.ProjectInclusion.android.screens.screeningScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ScreeningOneReportScreen(onNext: () -> Unit, onBack: () -> Unit) {

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ScreeningOneReportScreenPreview() {
    val onNext: () -> Unit = {}
    val onBack: () -> Unit = {}
    ScreeningOneReportScreen(onNext, onBack)
}