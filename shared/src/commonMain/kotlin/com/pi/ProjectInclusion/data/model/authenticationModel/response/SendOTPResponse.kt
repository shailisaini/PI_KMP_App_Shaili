package com.pi.ProjectInclusion.data.model.authenticationModel.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendOTPResponse(
    @SerialName("message")
    val message: String? = null,

    @SerialName("error")
    val error: String? = null,

    @SerialName("statusCode")
    val statusCode: Int? = null,

    @SerialName("status")
    val status: Boolean? = null,

    @SerialName("response") val response: OTPResponse? = null,

    @SerialName("exception") val exception: String? = null

    ) {
    @Serializable
    data class OTPResponse(
        @SerialName("mobileNo")
        val mobileNo: String? = null,

        @SerialName("otp")
        val otp: String? = null,

        @SerialName("message")
        val message: String? = null
        )
}