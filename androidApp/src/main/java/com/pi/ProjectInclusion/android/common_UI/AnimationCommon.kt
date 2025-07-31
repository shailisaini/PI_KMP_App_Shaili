package com.pi.ProjectInclusion.android.common_UI

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


// Animation For forward & Backward screen
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedRouteHost(
    currentRoute: String,
    isForward: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable (String) -> Unit
) {
    val duration = 400

    AnimatedContent(
        targetState = currentRoute,
        label = "Route Transition",
        transitionSpec = {
            if (isForward) {
                (slideInHorizontally(
                    animationSpec = tween(duration),
                    initialOffsetX = { it }
                ) + fadeIn(animationSpec = tween(duration))) with
                        (slideOutHorizontally(
                            animationSpec = tween(duration),
                            targetOffsetX = { -it }
                        ) + fadeOut(animationSpec = tween(duration)))
            } else {
                (slideInHorizontally(
                    animationSpec = tween(duration),
                    initialOffsetX = { -it }
                ) + fadeIn(animationSpec = tween(duration))) with
                        (slideOutHorizontally(
                            animationSpec = tween(duration),
                            targetOffsetX = { it }
                        ) + fadeOut(animationSpec = tween(duration)))
            }.using(SizeTransform(clip = false))
        },
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) { route ->
        content(route)
    }
}