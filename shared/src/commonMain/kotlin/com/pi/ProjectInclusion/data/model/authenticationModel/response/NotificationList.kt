package com.pi.ProjectInclusion.data.model.authenticationModel.response

data class NotificationList(
    val applicationName: String,
    val body: String,
    val id: Int,
    val isRead: Int,
    val sendDate: String,
    val title: String
)