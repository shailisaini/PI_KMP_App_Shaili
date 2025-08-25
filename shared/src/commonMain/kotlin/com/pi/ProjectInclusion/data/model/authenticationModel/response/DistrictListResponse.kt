package com.pi.ProjectInclusion.data.model.authenticationModel.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DistrictListResponse(
    @SerialName("id") var id: Int? = null,
    @SerialName("name") var name: String? = null,
    @SerialName("code") var code: String? = null,
    @SerialName("stateID") var stateID: Int? = null,
    @SerialName("stateName") var stateName: String? = null,
    @SerialName("divisionID") var divisionID: Int? = null,
    @SerialName("divisionName") var divisionName: String? = null,
    @SerialName("priority") var priority: Int? = null,
    @SerialName("status") var status: Int? = null,
)
