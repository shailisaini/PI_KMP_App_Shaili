package com.pi.ProjectInclusion.data.model.authenticationModel.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetUserTypeResponse(
    @SerialName("message")
    val message: String? = null,

    @SerialName("statusCode")
    val statusCode: Int? = null,

    @SerialName("status")
    val status: Boolean? = null,

    @SerialName("response")
    val response: List<UserTypeResponse>? = null,

    ) {
    @Serializable
    data class UserTypeResponse(
        @SerialName("id")
        val id: String? = null,

        @SerialName("name")
        val name: String? = null,

        @SerialName("icon")
        val icon: String? = null,

        @SerialName("status")
        val status: Int? = null,

        @SerialName("isShowOnApp")
        val isShowOnApp: Int? = null,

        @SerialName("createdBy")
        val createdBy: String? = null,

        )
}