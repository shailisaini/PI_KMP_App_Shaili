package com.pi.ProjectInclusion.data.model.AuthenticationModel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ValidateUserResponse(
    @SerialName("message")
    val message: String? = null,

    @SerialName("statusCode")
    val statusCode: Int? = null,

    @SerialName("status")
    val status: Boolean? = null,

    @SerialName("response")
    val response: ValidateResponse? = null
) {

    @Serializable
    data class ValidateResponse(

        @SerialName("message")
        val message: String? = null,

        )
}