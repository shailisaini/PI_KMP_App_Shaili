package com.pi.ProjectInclusion.data.model.authenticationModel.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserTrackRequestResponse(
    @SerialName("methodType") var methodType: String? = null,
    @SerialName("status") var status: Boolean? = null,
    @SerialName("statusCode") var statusCode: Int? = null,
    @SerialName("message") var message: String? = null,
    @SerialName("error") var error: String? = null,
    @SerialName("response") var response: List<UserTrackResponse>? = null,
    @SerialName("responseDate") var responseDate: String? = null,
    @SerialName("exception") var exception: String? = null,
) {
    @Serializable
    data class UserTrackResponse(

        @SerialName("id") var id: String? = null,
        @SerialName("requestTypeId") var requestTypeId: String? = null,
        @SerialName("requestTypeName") var requestTypeName: String? = null,
        @SerialName("requestStatusId") var requestStatusId: String? = null,
        @SerialName("requestStatusName") var requestStatusName: String? = null,
        @SerialName("userId") var userId: String? = null,
        @SerialName("priority") var priority: Int? = null,
        @SerialName("status") var status: Int? = null,

        )
}
