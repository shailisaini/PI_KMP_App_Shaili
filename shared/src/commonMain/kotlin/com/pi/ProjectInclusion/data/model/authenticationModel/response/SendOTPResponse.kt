package com.pi.ProjectInclusion.data.model.authenticationModel.response

import com.pi.ProjectInclusion.data.model.authenticationModel.response.SendOTPResponse.OTPResponse
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject

@Serializable
data class SendOTPResponse(
    @SerialName("message")
    val message: String? = null,

    @SerialName("error")
    val error: String? = null,

    @SerialName("statusCode")
    val statusCode: Int? = null,

    @SerialName("status")
    val status: Boolean? = null,

    @Serializable(with = OtpResponseSerializer::class)
    @SerialName("response") val response: OTPResponse? = null,

    @SerialName("exception") val exception: String? = null

    ) {
    @Serializable
    data class OTPResponse(
        @SerialName("mobileNo")
        val mobileNo: String? = null,

        @SerialName("otp")
        val otp: String? = null,

        @SerialName("message")
        val message: String? = null
        )
}

// response is different
object OtpResponseSerializer : KSerializer<OTPResponse?> {
    override val descriptor: SerialDescriptor =
        OTPResponse.serializer().descriptor

    override fun deserialize(decoder: Decoder): OTPResponse? {
        val input = decoder as? JsonDecoder ?: error("")
        val element = input.decodeJsonElement()

        return when (element) {
            is JsonObject -> input.json.decodeFromJsonElement(OTPResponse.serializer(), element)
            is JsonArray -> null  // [] → null
            is JsonNull -> null   // null → null
            else -> null
        }
    }

    override fun serialize(encoder: Encoder, value: OTPResponse?) {
        if (value == null) {
            encoder.encodeNull()
        } else {
            encoder.encodeSerializableValue(OTPResponse.serializer(), value)
        }
    }
}