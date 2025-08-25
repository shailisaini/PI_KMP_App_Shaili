package com.pi.ProjectInclusion.data.model.authenticationModel.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BlockListResponse(
    @SerialName("id") var id: Int? = null,
    @SerialName("createdDate") var createdDate: String? = null,
    @SerialName("updatedDate") var updatedDate: String? = null,
    @SerialName("createdBy") var createdBy: Int? = null,
    @SerialName("updatedBy") var updatedBy: String? = null,
    @SerialName("status") var status: Int? = null,
    @SerialName("priority") var priority: Int? = null,
    @SerialName("name") var name: String? = null,
    @SerialName("code") var code: String? = null,
    @SerialName("districtId") var districtId: Int? = null,
    @SerialName("districtName") var districtName: String? = null,
)
