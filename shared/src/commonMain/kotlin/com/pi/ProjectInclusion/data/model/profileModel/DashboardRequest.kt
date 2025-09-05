package com.pi.ProjectInclusion.data.model.profileModel

import kotlinx.serialization.Serializable

@Serializable
data class ProfileNameChangeRequest(
    val requestTypeId : String? = null,
    val appVersion: String? = null,
    val description: String? = null,
)
