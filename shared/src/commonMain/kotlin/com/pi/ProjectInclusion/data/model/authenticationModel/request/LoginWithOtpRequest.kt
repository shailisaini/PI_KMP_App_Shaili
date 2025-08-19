package com.pi.ProjectInclusion.data.model.authenticationModel.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginWithOtpRequest(
    val userName: String? = null,
    val otp: String? = null,
    val userTypeId: Int? = null,

)
