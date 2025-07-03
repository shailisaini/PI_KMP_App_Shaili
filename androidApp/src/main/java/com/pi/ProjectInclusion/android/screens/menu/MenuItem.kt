package com.pi.ProjectInclusion.android.screens.menu

import androidx.compose.ui.graphics.vector.ImageVector

// Navigation Drawer
data class MenuItem(
    val id: String,
    val title: String,
    val contentDescription: String,
    val icon: ImageVector
)

// Bottom Menu
data class BottomNavigationItems(
    val appRoute: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
)
data class TabItem(
    var title : String
)

