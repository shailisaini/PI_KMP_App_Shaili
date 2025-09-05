package com.pi.ProjectInclusion.data.model.profileModel.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChangeRequestResponse(
    @SerialName("status")
    val status: Boolean? = null,

    @SerialName("message")
    val message: String,

    @SerialName("response")
    val response: String,

    @SerialName("error")
    val error: String,
)