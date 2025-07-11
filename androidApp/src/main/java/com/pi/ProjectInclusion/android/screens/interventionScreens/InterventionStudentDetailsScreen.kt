package com.pi.ProjectInclusion.android.screens.interventionScreens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.pi.ProjectInclusion.android.common_UI.BackButtonPress
import com.pi.ProjectInclusion.android.common_UI.DetailsBackgroundUi
import com.pi.ProjectInclusion.android.navigation.AppRoute

@Composable
fun InterventionStudentDetailsScreen(navHostController: NavHostController) {

    DetailsBackgroundUi(
        isShowBackButton = true, onBackButtonClick = {
            BackButtonPress(navHostController, AppRoute.InterventionScreen.route)
        },
        content = {})
}