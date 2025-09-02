package com.pi.ProjectInclusion.data.model.authenticationModel.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ZoomMeetingListResponse(
    @SerialName("page_size") var pageSize: Int? = null,
    @SerialName("total_records") var totalRecords: Int? = null,
    @SerialName("next_page_token") var nextPageToken: String? = null,
    @SerialName("meetings") var meetings: List<Meetings>? = null,
) {
    @Serializable
    data class Meetings(

        @SerialName("uuid") var uuid: String? = null,
        @SerialName("id") var id: Long? = null,
        @SerialName("host_id") var hostId: String? = null,
        @SerialName("topic") var topic: String? = null,
        @SerialName("type") var type: Int? = null,
        @SerialName("start_time") var startTime: String? = null,
        @SerialName("duration") var duration: Int? = null,
        @SerialName("timezone") var timezone: String? = null,
        @SerialName("created_at") var createdAt: String? = null,
        @SerialName("join_url") var joinUrl: String? = null,

        )
}
