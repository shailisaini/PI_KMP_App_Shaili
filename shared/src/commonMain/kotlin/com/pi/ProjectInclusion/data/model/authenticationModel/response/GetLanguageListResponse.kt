package com.pi.ProjectInclusion.data.model.authenticationModel.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetLanguageListResponse(

    @SerialName("message")
    val message: String? = null,

    @SerialName("statusCode")
    val statusCode: Int? = null,

    @SerialName("status")
    val status: Boolean? = null,

    @SerialName("response")
    val response: List<LanguageResponse>? = null,

    ) {
    @Serializable
    data class LanguageResponse(
        @SerialName("id")
        val id: String? = null,

        @SerialName("name")
        val name: String? = null,

        @SerialName("translatedName")
        val translatedName: String? = null,

        @SerialName("langIcon")
        val langIcon: String? = null,

        @SerialName("status")
        val status: Int? = null,

        @SerialName("createdBy")
        val createdBy: String? = null,

        )
}