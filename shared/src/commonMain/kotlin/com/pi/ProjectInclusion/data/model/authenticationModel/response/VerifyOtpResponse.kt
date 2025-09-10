package com.pi.ProjectInclusion.data.model.authenticationModel.response

import com.pi.ProjectInclusion.data.model.authenticationModel.response.VerifyOtpResponse.OtpResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.VerifyOtpResponse.VerifyOptResponse
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
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
    val response: OtpResponse? = null

    ) {
    @Serializable
    data class OtpResponse(

        // token will come in case of Forget/Change/Reset password else empty
        @SerialName("token")
        val token: String? = null,

        @SerialName("message")
        val message: String? = null

        )
}


// response is different
object ResponseSerializer : KSerializer<OtpResponse?> {
    override val descriptor: SerialDescriptor =
        OtpResponse.serializer().descriptor

    override fun deserialize(decoder: Decoder): OtpResponse? {
        val input = decoder as? JsonDecoder ?: error("")
        val element = input.decodeJsonElement()

        return when (element) {
            is JsonObject -> input.json.decodeFromJsonElement(OtpResponse.serializer(), element)
            is JsonArray -> null  // [] → null
            is JsonNull -> null   // null → null
            else -> null
        }
    }

    override fun serialize(encoder: Encoder, value: OtpResponse?) {
        if (value == null) {
            encoder.encodeNull()
        } else {
            encoder.encodeSerializableValue(OtpResponse.serializer(), value)
        }
    }
}