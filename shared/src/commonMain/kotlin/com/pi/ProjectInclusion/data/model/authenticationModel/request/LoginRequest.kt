package com.pi.ProjectInclusion.data.model.authenticationModel.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val userName: String? = null,
    val password: String? = null,
    val usertypeId: Int? = null,
    val languageId: Int? = null,
)

@Serializable
data class ForgetPasswordRequest(
    val username: String? = null,
    val userTypeId: Int? = null,
    val newPassword: String? = null,
)

@Serializable
data class ChangePasswordRequest(
    val userId: Int? = null,
    val oldPassword: String? = null,
    val newPassword: String? = null,
)