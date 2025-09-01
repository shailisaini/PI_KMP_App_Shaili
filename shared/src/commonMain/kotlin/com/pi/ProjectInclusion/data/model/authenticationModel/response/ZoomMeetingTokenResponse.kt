package com.pi.ProjectInclusion.data.model.authenticationModel.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ZoomMeetingTokenResponse(
    @SerialName("access_token") var accessToken: String? = null,
    @SerialName("token_type") var tokenType: String? = null,
    @SerialName("refresh_token") var refreshToken: String? = null,
    @SerialName("expires_in") var expiresIn: Int? = null,
    @SerialName("scope") var scope: String? = null,
    @SerialName("api_url") var apiUrl: String? = null,
)
