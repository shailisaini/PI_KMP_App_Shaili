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
data class LoginWithOtpRequest(
    val username: String? = null,
    val otp: String? = null,
    val userTypeId: Int? = null,
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

@Serializable
data class CreatePasswordRequest(
    val userName: String? = null,
    val password: String? = null,
    val mobile: String? = null,
    val usertypeId: Int? = null,
    val languageId: Int? = null,
)

@Serializable
data class CertificateRequest(
    val partnerID: Int? = null,
    val piUserID: Int? = null,
)

@Serializable
data class FirstStepProfileRequest(
    val firstname: String? = null,
    val middlename: String? = null,
    val lastname: String? = null,
    val gender: String? = null,
    val mobile: String? = null,
    val whatsapp: String? = null,
    val dob: String? = null,
    val email: String? = null,
)

@Serializable
data class ProfessionalProfileRequest(
    val udiseCode: String? = null,
    val state: Int? = null,
    val district: Int? = null,
    val block: Int? = null,
    val school: Int? = null,
    val reason: Int? = null,
    val profession: Int? = null,
    val qualification: Int? = null,
    val specialization: Int? = null,
    val crrNo: String? = null,
)