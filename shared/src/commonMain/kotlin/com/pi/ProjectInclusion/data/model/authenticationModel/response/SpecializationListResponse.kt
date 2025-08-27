package com.pi.ProjectInclusion.data.model.authenticationModel.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpecializationListResponse(
    @SerialName("id") var id: Int? = null,
    @SerialName("name") var name: String? = null,
    @SerialName("professionId") var professionId: Int? = null,
    @SerialName("qualificationId") var qualificationId: Int? = null,
    @SerialName("priority") var priority: Int? = null,
    @SerialName("status") var status: Int? = null,
    @SerialName("createdBy") var createdBy: Int? = null,
    @SerialName("createdDate") var createdDate: String? = null,
    @SerialName("updatedBy") var updatedBy: String? = null,
    @SerialName("updatedDate") var updatedDate: String? = null,
)
