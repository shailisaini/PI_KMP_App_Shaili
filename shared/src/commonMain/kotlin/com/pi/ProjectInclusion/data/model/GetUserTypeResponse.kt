package com.pi.ProjectInclusion.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetUserTypeResponse(
    @SerialName("data")
    val data: List<Data>,

    @SerialName("error")
    val error: String,

    @SerialName("isSuccess")
    val isSuccess: Boolean
) {
    @Serializable
    data class Data(
        @SerialName("id")
        val id: String,

        @SerialName("name")
        val name: String
    )
}