package com.pi.ProjectInclusion.data.model.profileModel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackListModel(
    @SerialName("name")
    val name: String? = null,

    @SerialName("reviewStatus")
    val reviewStatus: String,

    @SerialName("date")
    val date: String,

    @SerialName("statusColor")
    val statusColor: String,

    @SerialName("reason")
    val reason: String?
)