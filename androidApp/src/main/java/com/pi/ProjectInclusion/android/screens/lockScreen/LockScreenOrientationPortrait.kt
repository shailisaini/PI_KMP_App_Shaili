package com.pi.ProjectInclusion.android.screens.lockScreen

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect

@SuppressLint("SourceLockedOrientationActivity")
@Composable
fun LockScreenOrientationPortrait() {
    val activity = LocalActivity.current
    DisposableEffect(Unit) {
        val old = activity?.requestedOrientation
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        onDispose {
            if (old != null) {
                activity.requestedOrientation = old
            }
        }
    }
}

