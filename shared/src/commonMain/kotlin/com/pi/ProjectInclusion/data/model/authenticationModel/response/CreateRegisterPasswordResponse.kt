package com.pi.ProjectInclusion.data.model.authenticationModel.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateRegisterPasswordResponse(
    @SerialName("methodType") var methodType: String? = null,
    @SerialName("status") var status: Boolean? = null,
    @SerialName("statusCode") var statusCode: Int? = null,
    @SerialName("message") var message: String? = null,
    @SerialName("error") var error: String? = null,
    @SerialName("response") var response: CreateRegisterResponse? = CreateRegisterResponse(),
    @SerialName("responseDate") var responseDate: String? = null,
    @SerialName("exception") var exception: String? = null,
) {
    @Serializable
    data class CreateRegisterResponse(

        @SerialName("username") var username: String? = null,
        @SerialName("password") var password: String? = null,
        @SerialName("mobile") var mobile: String? = null,
        @SerialName("userTypeId") var userTypeId: Int? = null,
        @SerialName("email") var email: String? = null,
        @SerialName("loginAttempt") var loginAttempt: String? = null,
        @SerialName("lastlogin") var lastlogin: String? = null,
        @SerialName("loginflag") var loginflag: String? = null,
        @SerialName("userExpInDays") var userExpInDays: String? = null,
        @SerialName("passwordExpInDays") var passwordExpInDays: String? = null,
        @SerialName("isProfileLocked") var isProfileLocked: String? = null,
        @SerialName("registrationSource") var registrationSource: String? = null,
        @SerialName("roleId") var roleId: String? = null,
        @SerialName("createdDate") var createdDate: String? = null,
        @SerialName("updatedDate") var updatedDate: String? = null,
        @SerialName("createdBy") var createdBy: String? = null,
        @SerialName("updatedBy") var updatedBy: String? = null,
        @SerialName("status") var status: Int? = null,
        @SerialName("lastAccountInactiveDate") var lastAccountInactiveDate: String? = null,
        @SerialName("lastAccountRestoredDate") var lastAccountRestoredDate: String? = null,
        @SerialName("id") var id: String? = null,

        )
}