package com.pi.ProjectInclusion.data.model.authenticationModel.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForgetPasswordResponse(
    @SerialName("methodType") var methodType: String? = null,
    @SerialName("status") var status: Boolean? = null,
    @SerialName("statusCode") var statusCode: Int? = null,
    @SerialName("message") var message: String? = null,
    @SerialName("error") var error: String? = null,
    @SerialName("response") var response: String? = null,
    @SerialName("responseDate") var responseDate: String? = null,
    @SerialName("exception") var exception: String? = null,
)
