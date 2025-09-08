package com.pi.ProjectInclusion.data.model.authenticationModel.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ValidateUserResponse(
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
    val response: ValidateResponse? = null,

    ) {
    @Serializable
    data class ValidateResponse(
        @SerialName("message")
        val message: String? = null,

        @SerialName("daysLeft")
        val daysLeft: Int? = null,

        @SerialName("user")
        val user: ValidateUser? = null,
    )

    @Serializable
    data class ValidateUser(
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
    )
}
