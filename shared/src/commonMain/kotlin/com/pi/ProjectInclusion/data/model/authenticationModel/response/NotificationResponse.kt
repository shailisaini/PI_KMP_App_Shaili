package com.pi.ProjectInclusion.data.model.authenticationModel.response

data class NotificationResponse(
    val count: Int,
    val exception: Any,
    val message: String,
    val response: List<NotificationList>,
    val status: Boolean,
    val statusCode: Int
)