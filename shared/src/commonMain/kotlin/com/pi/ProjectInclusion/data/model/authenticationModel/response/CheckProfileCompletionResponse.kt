package com.pi.ProjectInclusion.data.model.authenticationModel.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckProfileCompletionResponse(
    @SerialName("methodType") var methodType: String? = null,
    @SerialName("status") var status: Boolean? = null,
    @SerialName("statusCode") var statusCode: Int? = null,
    @SerialName("message") var message: String? = null,
    @SerialName("error") var error: String? = null,
    @SerialName("response") var response: CheckProfileResponse? = null,
    @SerialName("responseDate") var responseDate: String? = null,
    @SerialName("exception") var exception: String? = null,
) {
    @Serializable
    data class CheckProfileResponse(

        @SerialName("profile_completed") var profileCompleted: Boolean? = null,

        )
}
