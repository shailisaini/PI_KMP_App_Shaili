package com.pi.ProjectInclusion.data.model.authenticationModel.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReasonListResponse(
    @SerialName("methodType") var methodType: String? = null,
    @SerialName("status") var status: Boolean? = null,
    @SerialName("statusCode") var statusCode: Int? = null,
    @SerialName("message") var message: String? = null,
    @SerialName("error") var error: String? = null,
    @SerialName("response") var response: List<ReasonResponse>? = null,
    @SerialName("responseDate") var responseDate: String? = null,
    @SerialName("exception") var exception: String? = null,
) {

    @Serializable
    data class ReasonResponse(

        @SerialName("id") var id: String? = null,
        @SerialName("name") var name: String? = null,
        @SerialName("priority") var priority: Int? = null,
        @SerialName("status") var status: Int? = null,
        @SerialName("createdAt") var createdAt: String? = null,
        @SerialName("updatedAt") var updatedAt: String? = null,
        @SerialName("createdBy") var createdBy: String? = null,
        @SerialName("updatedBy") var updatedBy: String? = null,

        )
}