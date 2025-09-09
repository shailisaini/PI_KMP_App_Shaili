package com.pi.ProjectInclusion.data.model.authenticationModel.response

import com.pi.ProjectInclusion.data.model.authenticationModel.response.VerifyOtpResponse.VerifyOptResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonTransformingSerializer

@Serializable
data class VerifyOtpResponse(
    @SerialName("message")
    val message: String? = null,

    @SerialName("statusCode")
    val statusCode: Int? = null,

    @SerialName("status")
    val status: Boolean? = null,

    @Serializable(with = ResponseSerializer::class)
    @SerialName("response")
    val response: VerifyOptResponse? = null,

    ) {
    @Serializable
    data class VerifyOptResponse(

        // token will come in case of Forget/Change/Reset password else empty
        @SerialName("token")
        val token: String? = null,

        @SerialName("message")
        val message: String? = null

        )
}

object ResponseSerializer : JsonTransformingSerializer<VerifyOtpResponse>(VerifyOtpResponse.serializer()) {
    override fun transformDeserialize(element: JsonElement): JsonElement {
        return when (element) {
            is JsonArray -> JsonNull      // if backend sends []
            is JsonObject -> element      // valid object
            else -> JsonNull              // covers null or other weird cases
        }
    }
}