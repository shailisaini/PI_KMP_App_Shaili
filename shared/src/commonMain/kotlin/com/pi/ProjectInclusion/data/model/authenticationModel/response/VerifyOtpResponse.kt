package com.pi.ProjectInclusion.data.model.authenticationModel.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifyOtpResponse(
    @SerialName("message")
    val message: String? = null,

    @SerialName("statusCode")
    val statusCode: Int? = null,

    @SerialName("status")
    val status: Boolean? = null,

    @SerialName("response")
    val response: LoginResponse? = null,

    ) {
    @Serializable
    data class LoginResponse(

        // token will come in case of Forget/Change/Reset password else empty
        @SerialName("token")
        val token: String? = null,

        @SerialName("message")
        val message: String? = null

        )
}