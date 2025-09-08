package com.pi.ProjectInclusion.data.model.profileModel

import kotlinx.serialization.Serializable

@Serializable
data class ProfileNameChangeRequest(
    val requestTypeId : String? = null,
    val appVersion: String? = null,
    val description: String? = null,
)
@Serializable
data class ChangePasswordRequest(
    val oldPassword: String? = null,
    val newPassword: String? = null,
)
