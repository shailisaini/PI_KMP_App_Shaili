package com.pi.ProjectInclusion.android.screens.registration

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel

@Composable
fun EnterUserScreen1(navController: NavHostController, viewModel: LoginViewModel) {
    var isDialogVisible by remember { mutableStateOf(false) }
    val uiState by viewModel.uiStateType.collectAsStateWithLifecycle()

}