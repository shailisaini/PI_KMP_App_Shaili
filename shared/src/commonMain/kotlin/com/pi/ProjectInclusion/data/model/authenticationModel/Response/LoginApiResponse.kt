package com.pi.ProjectInclusion.data.model.authenticationModel.Response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginApiResponse(
    @SerialName("methodType")
    val methodType: String? = null,

    @SerialName("status")
    val status: Boolean? = null,

    @SerialName("statusCode")
    val statusCode: Int? = null,

    @SerialName("message")
    val message: String? = null,

    @SerialName("error")
    val error: String? = null,

    @SerialName("response")
    val response: LoginResponse? = null,

    ) {
    @Serializable
    data class LoginResponse(
        @SerialName("access_token")
        val accessToken: String? = null,

        @SerialName("user")
        val user: User? = null
    )

    @Serializable
    data class User(
        @SerialName("id")
        val id: String? = null,

        @SerialName("username")
        val username: String? = null,

        @SerialName("password")
        val password: String? = null,

        @SerialName("email")
        val email: String? = null,

        @SerialName("mobile")
        val mobile: String? = null,

        @SerialName("loginAttempt")
        val loginAttempt: Int? = null,

        @SerialName("lastlogin")
        val lastLogin: String? = null,

        @SerialName("loginflag")
        val loginFlag: Int? = null,

        @SerialName("userExpInDays")
        val userExpInDays: Int? = null,

        @SerialName("passwordExpInDays")
        val passwordExpInDays: Int? = null,

        @SerialName("isProfileLocked")
        val isProfileLocked: Int? = null,

        @SerialName("registrationSource")
        val registrationSource: String? = null,

        @SerialName("userTypeId")
        val userTypeId: String? = null,

        @SerialName("roleId")
        val roleId: String? = null,

        @SerialName("createdDate")
        val createdDate: String? = null,

        @SerialName("updatedDate")
        val updatedDate: String? = null,

        @SerialName("createdBy")
        val createdBy: String? = null,

        @SerialName("updatedBy")
        val updatedBy: String? = null,

        @SerialName("status")
        val status: Int? = null,

        @SerialName("lastAccountInactiveDate")
        val lastAccountInactiveDate: String? = null,

        @SerialName("lastAccountRestoredDate")
        val lastAccountRestoredDate: String? = null
    )
}
