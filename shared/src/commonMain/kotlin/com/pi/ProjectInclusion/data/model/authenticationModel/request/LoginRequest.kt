package com.pi.ProjectInclusion.data.model.authenticationModel.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val userName: String? = null,
    val password: String? = null,
    val usertypeId: Int? = null,
    val languageId: Int? = null
)