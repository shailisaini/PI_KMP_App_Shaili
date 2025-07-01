package com.pi.ProjectInclusion.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetLanguageListResponse(
    @SerialName("data")
    val `data`: Data,

    @SerialName("error")
    val error: String,

    @SerialName("isSuccess")
    val isSuccess: Boolean
) {
    @Serializable
    data class Data(
        @SerialName("limit")
        val limit: String,

        @SerialName("page")
        val page: String,

        @SerialName("results")
        val results: List<Result>,

        @SerialName("totalCount")
        val totalCount: Int
    ) {
        @Serializable
        data class Result(
            @SerialName("id")
            val id: Int,

            @SerialName("name")
            val name: String,

            @SerialName("icon")
            val icon: String,

            @SerialName("nativeName")
            val nativeName: String,

            @SerialName("status")
            val status: String,

            @SerialName("languageCode")
            val languageCode: String? = null,

            @SerialName("priority")
            val priority: String? = null,

            var isSelected: Boolean = false // Defaulted, not from API
        )
    }
}