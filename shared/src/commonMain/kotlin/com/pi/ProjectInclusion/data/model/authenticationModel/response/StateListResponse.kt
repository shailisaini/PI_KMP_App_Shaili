package com.pi.ProjectInclusion.data.model.authenticationModel.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StateListResponse(
    @SerialName("name") var name: String? = null,
    @SerialName("code") var code: String? = null,
    @SerialName("countryID") var countryID: Int? = null,
    @SerialName("countryName") var countryName: String? = null,
    @SerialName("id") var id: Int? = null,
    @SerialName("createdDate") var createdDate: String? = null,
    @SerialName("updatedDate") var updatedDate: String? = null,
    @SerialName("createdBy") var createdBy: Int? = null,
    @SerialName("updatedBy") var updatedBy: Int? = null,
    @SerialName("status") var status: Int? = null,
    @SerialName("priority") var priority: Int? = null,
)
