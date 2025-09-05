package com.pi.ProjectInclusion.data.model.profileModel.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackRequestResponse(
    @SerialName("status")
    val status: Boolean? = null,

    @SerialName("message")
    val message: String,

    @SerialName("response")
    val response: RequestResponse? = null,

    @SerialName("error")
    val error: String
) {
    @Serializable
    data class RequestResponse(
        @SerialName("already_requested")
        val already_requested: Boolean? = null,

        @SerialName("message")
        val message: String? = null
    )
}