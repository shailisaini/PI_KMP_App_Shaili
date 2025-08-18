package com.pi.ProjectInclusion.data.model.authenticationModel.Response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginApiResponse(
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
        @SerialName("access_token")
        val access_token: String? = null,

        )
}
