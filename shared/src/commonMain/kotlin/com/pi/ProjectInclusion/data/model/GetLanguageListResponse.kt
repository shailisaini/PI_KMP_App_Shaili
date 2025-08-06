package com.pi.ProjectInclusion.data.model

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

            @SerialName("translated_name")
            val translated_name: String? = null,

            @SerialName("lang_icon")
            val lang_icon: String? = null,

            @SerialName("status")
            val status: Int? = null,

            @SerialName("createdBy")
            val createdBy: String? = null,

        )
    }