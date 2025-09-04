package com.pi.ProjectInclusion.data.model.authenticationModel.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForceUpdateResponse(
    @SerialName("status") var status: Int? = null,
    @SerialName("errorCode") var errorCode: String? = null,
    @SerialName("message") var message: String? = null,
    @SerialName("response") var response: ForceUpdateData? = null,
) {
    @Serializable
    data class ForceUpdateData(
        @SerialName("deviceName") var deviceName: String? = null,
        @SerialName("deviceOsVersion") var deviceOsVersion: Double? = null,
        @SerialName("latestAppVersion") var latestAppVersion: Double? = null,
        @SerialName("isForceUpdate") var isForceUpdate: Int? = null,
        @SerialName("id") var id: Int? = null,
        @SerialName("createdDate") var createdDate: String? = null,
        @SerialName("updatedDate") var updatedDate: String? = null,
        @SerialName("createdBy") var createdBy: Int? = null,
        @SerialName("updatedBy") var updatedBy: Int? = null,
        @SerialName("status") var status: Int? = null,
        @SerialName("priority") var priority: Int? = null,
    )
}
