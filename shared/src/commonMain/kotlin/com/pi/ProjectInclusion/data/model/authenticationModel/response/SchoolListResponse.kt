package com.pi.ProjectInclusion.data.model.authenticationModel.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SchoolListResponse(
    @SerialName("status") var status: Int? = null,
    @SerialName("errorCode") var errorCode: String? = null,
    @SerialName("message") var message: String? = null,
    @SerialName("response") var response: List<SchoolResponse>? = null,
) {
    @Serializable
    data class SchoolResponse(

        @SerialName("id") var id: Int? = null,
        @SerialName("name") var name: String? = null,
        @SerialName("stateId") var stateId: Int? = null,
        @SerialName("stateName") var stateName: String? = null,
        @SerialName("districtId") var districtId: Int? = null,
        @SerialName("districtName") var districtName: String? = null,
        @SerialName("udiseCode") var udiseCode: String? = null,
        @SerialName("blockId") var blockId: Int? = null,
        @SerialName("blockName") var blockName: String? = null,
        @SerialName("clusterName") var clusterName: String? = null,
        @SerialName("villageName") var villageName: String? = null,
        @SerialName("schoolTypeId") var schoolTypeId: Int? = null,
        @SerialName("status") var status: Int? = null,
        @SerialName("regionId") var regionId: String? = null,
        @SerialName("regionName") var regionName: String? = null,

        )
}
